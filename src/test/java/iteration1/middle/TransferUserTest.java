package iteration1.middle;

import generators.RandomData;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import requests.*;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.stream.Stream;

import static generators.TestUtils.repeat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.jupiter.api.Test;

public class TransferUserTest {
    //добавлена параметризация
    //убран JSONPath
    //вынесен цикл в универсальный метод repeat()
    @ParameterizedTest
    @ValueSource(doubles = {10000.0, 9999.99})
    public void createUserAndTransferBetweenAccounts(double amount) {
        // Создаём пользователя
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();

        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated())
                .post(userRequest);

        // Создаём первый счёт
        AccountResponse firstAccountResponse = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post()
                .extract()
                .as(AccountResponse.class);

        long accountFirstId = firstAccountResponse.getId();

        // Создаём второй счёт
        AccountResponse secondAccountResponse = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post()
                .extract()
                .as(AccountResponse.class);

        long accountSecondId = secondAccountResponse.getId();

        // Пополняем первый счёт пять раз по 5000
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(accountFirstId)
                .balance(5000)
                .build();

        MakeDepositRequester depositRequester = new MakeDepositRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK());

        repeat(5, () -> depositRequester.post(depositRequest));
        // Выполняем перевод
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(accountFirstId)
                .receiverAccountId(accountSecondId)
                .amount(amount)
                .build();

        new MakeTransferRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);

        // Проверяем состояние счетов через GET
        AccountResponse[] accounts = new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .get()
                .extract()
                .as(AccountResponse[].class);

        // Проверяем исходящую транзакцию
        ResponseSpecs.transferTransactionExists(
                accounts, accountFirstId, amount);

    }

    // Позитивный тест-кейс перевод со счета первого пользователя на счет второго пользователя 1000
    @Test
    public void userCanTransferMoneyToAnotherUser() {

        // Создаём первого пользователя
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();

        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated())
                .post(userRequest);

        // Создаём счёт первого пользователя
        AccountResponse firstAccountResponse = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post()
                .extract()
                .as(AccountResponse.class);

        long accountFirstId = firstAccountResponse.getId();

        // Создаём второго пользователя
        CreateUserRequest secondUserRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();

        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated())
                .post(secondUserRequest);

        // Создаём счёт второго пользователя
        AccountResponse secondAccountResponse = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        secondUserRequest.getUsername(),
                        secondUserRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post()
                .extract()
                .as(AccountResponse.class);

        long accountSecondId = secondAccountResponse.getId();

        // Пополняем счёт первого пользователя пять раз по 5000
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(accountFirstId)
                .balance(5000)
                .build();

        MakeDepositRequester depositRequester = new MakeDepositRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK());

        repeat(5, () -> depositRequester.post(depositRequest));

        // Переводим 1000 второму пользователю
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(accountFirstId)
                .receiverAccountId(accountSecondId)
                .amount(1000)
                .build();

        new MakeTransferRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);

        // Проверяем транзакцию
        AccountResponse[] firstUserAccounts = new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .get()
                .extract()
                .as(AccountResponse[].class);

        ResponseSpecs.transferTransactionExists(
                firstUserAccounts, accountFirstId, 1000);

    }

    //негативные тест-кейсы
    private static CreateUserRequest userRequest;
    private static int accountId1;
    private static int accountId2;

    @BeforeAll
    static void setUp() {
        userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();

        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated()
        ).post(userRequest);

        accountId1 = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated()
        ).post(null)
                .extract()
                .jsonPath()
                .getInt("id");
        accountId2 = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated()
        ).post(null)
                .extract()
                .jsonPath()
                .getInt("id");
    }

    public static Stream<Arguments> transferInvalidData() {
        return Stream.of(
                Arguments.of(10001.0, 400, "Transfer amount cannot exceed 10000"),
                Arguments.of(10000.1, 400, "Transfer amount cannot exceed 10000"),
                Arguments.of(-1.0, 400, "Transfer amount must be at least 0.01"),
                Arguments.of(0.0, 400, "Transfer amount must be at least 0.01"),
                Arguments.of(6000, 400, "Invalid transfer: insufficient funds or invalid accounts")
        );
    }
    @MethodSource("transferInvalidData")
    @ParameterizedTest
    public void userCanNotMakeTransferWithInvalidData(double amount, int expectedStatus, String expectedMessage){
            MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                    .senderAccountId(accountId1)
                    .receiverAccountId(accountId2)
                    .amount(amount)
                    .build();
            new MakeTransferRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                    ResponseSpecs.requestReturnsError(expectedStatus, expectedMessage))
                    .post(transferRequest);
    }
    //проверки через get того, что негативные тест-кейсы не изменили состояние сервера
    public static Stream<Arguments> transferInvalidResponseData() {
        return Stream.of(
                Arguments.of(5001.0),
                Arguments.of(5000.1),
                Arguments.of(0.0)
        );
    }

    @MethodSource("transferInvalidResponseData")
    @ParameterizedTest
    public void checkThatUserCanNotMakeTransferWithInvalidData(double amount) {
        new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.transferTransactionDoesNotExist(accountId1, amount))
                .get();
    }
}
