package iteration1.senior;

import iteration1.middle.BaseTest;
import models.AccountResponse;
import models.CreateUserRequest;
import models.MakeDepositRequest;
import models.MakeTransferRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.CrudRequester;
import requests.skelethon.requesters.ValidatedCrudRequester;
import requests.steps.AdminSteps;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.stream.Stream;

import static generators.TestUtils.repeat;

public class TransferUserTest extends BaseTest {
    @ParameterizedTest
    @ValueSource(doubles = {10000.0, 9999.99})
    public void userCanTransfer(double amount) {
        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        AccountResponse account =
                new ValidatedCrudRequester<AccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                        Endpoint.ACCOUNTS,
                        ResponseSpecs.entityWasCreated())
                        .post(null);

        //Создаём второй счёт
        AccountResponse secondAccount =
                new ValidatedCrudRequester<AccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                        Endpoint.ACCOUNTS,
                        ResponseSpecs.entityWasCreated())
                        .post(null);

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(account.getId())
                .balance(5000)
                .build();

        //Выполняем депозит 5 раз
        CrudRequester depositRequester = new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK()
        );

        repeat(5, () -> depositRequester.post(depositRequest));

        //Создаём запрос на трансфер
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(account.getId())
                .receiverAccountId(secondAccount.getId())
                .amount(amount)
                .build();
        //Выполняем трансфер
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.TRANSFER,
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);
        //Проверяем состояния сервера через get запрос
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_ACCOUNTS,
                ResponseSpecs.transferTransactionExists(account.getId(), amount))
                .get(account.getId());
    }

    //Позитивный тест-кейс перевод со счета первого пользователя на счет второго пользователя 1000
    @Test
    public void userCanTransferMoneyToAnotherUser() {

        //Создаём первого пользователя
        CreateUserRequest firstUserRequest = AdminSteps.createUser();

        //Создаём счёт первого пользователя
        AccountResponse firstUserAccount =
                new ValidatedCrudRequester<AccountResponse>(RequestSpecs.authAsUser(firstUserRequest.getUsername(), firstUserRequest.getPassword()),
                        Endpoint.ACCOUNTS,
                        ResponseSpecs.entityWasCreated())
                        .post(null);

        //Создаём второго пользователя
        CreateUserRequest secondUserRequest = AdminSteps.createUser();

        //Создаём счёт второго пользователя
        AccountResponse secondUserAccount =
                new ValidatedCrudRequester<AccountResponse>(RequestSpecs.authAsUser(secondUserRequest.getUsername(), secondUserRequest.getPassword()),
                        Endpoint.ACCOUNTS,
                        ResponseSpecs.entityWasCreated())
                        .post(null);

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstUserAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит 5 раз
        CrudRequester depositRequester = new CrudRequester(RequestSpecs.authAsUser(firstUserRequest.getUsername(), firstUserRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK()
        );
        repeat(5, () -> depositRequester.post(depositRequest));

        //Создаём запрос на трансфер
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(firstUserAccount.getId())
                .receiverAccountId(secondUserAccount.getId())
                .amount(1000)
                .build();

        //Выполняем трансфер 1000 второму пользователю
        new CrudRequester(RequestSpecs.authAsUser(firstUserRequest.getUsername(), firstUserRequest.getPassword()),
                Endpoint.TRANSFER,
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);
        //Проверяем состояния сервера через get запрос
        new CrudRequester(RequestSpecs.authAsUser(firstUserRequest.getUsername(), firstUserRequest.getPassword()),
                Endpoint.GET_ACCOUNTS,
                ResponseSpecs.transferTransactionExists(firstUserAccount.getId(), 1000))
                .get(firstUserAccount.getId());

    }

    //Негативные проверки
    private static CreateUserRequest userRequest;
    private static AccountResponse firstAccount;
    private static AccountResponse secondAccount;

    @BeforeAll
    static void setUp() {
        //Создаём пользователя
        userRequest = AdminSteps.createUser();

        //Создаём первый счёт
        firstAccount = new ValidatedCrudRequester<AccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);

        //Создаём второй счёт
        secondAccount = new ValidatedCrudRequester<AccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
    }
    static Stream<Arguments> transferInvalidData() {
        return Stream.of(
                Arguments.of(10001.0, 400, "Transfer amount cannot exceed 10000"),
                Arguments.of(10000.1, 400, "Transfer amount cannot exceed 10000"),
                Arguments.of(0.0, 400, "Transfer amount must be at least 0.01"),
                Arguments.of(6000.0, 400, "Invalid transfer: insufficient funds or invalid accounts")
        );
    }

    @ParameterizedTest
    @MethodSource("transferInvalidData")
    public void userCannotMakeInvalidTransfer(double amount, int expectedStatus, String expectedMessage) {

        //Создаём запрос на трансфер
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(firstAccount.getId())
                .receiverAccountId(secondAccount.getId())
                .amount(amount)
                .build();

        //Выполняем негативный трансфер
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.TRANSFER,
                ResponseSpecs.requestReturnsError(expectedStatus, expectedMessage))
                .post(transferRequest);
        //Проверяем, что трансфер не появился
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_ACCOUNTS,
                ResponseSpecs.transferTransactionDoesNotExist(firstAccount.getId(), amount))
                .get(firstAccount.getId());
    }
}


