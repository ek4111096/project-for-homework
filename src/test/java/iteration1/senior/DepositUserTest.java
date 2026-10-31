package iteration1.senior;

import iteration1.middle.BaseTest;
import models.*;
import org.junit.jupiter.api.BeforeAll;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class DepositUserTest extends BaseTest {
    //Данные для негативных тестов
    private static CreateUserRequest owner;
    private static long accountId;

    @BeforeAll
    static void setUp() {
        //Создаём пользователя
        owner = AdminSteps.createUser();

        //Создаём счёт
        AccountResponse account = new ValidatedCrudRequester<AccountResponse>(RequestSpecs.authAsUser(owner.getUsername(), owner.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);

        accountId = account.getId();
    }


    @ParameterizedTest
    @ValueSource(doubles = {4999.99, 5000})
    public void userCanDeposit(double amount) {
        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        AccountResponse account =
                new ValidatedCrudRequester<AccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                        Endpoint.ACCOUNTS,
                        ResponseSpecs.entityWasCreated())
                        .post(null);

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(account.getId())
                .balance(amount)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        //Проверяем состояния сервера через get запрос
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_ACCOUNTS,
                ResponseSpecs.depositTransactionExists(account.getId(), amount))
                .get(account.getId());
    }

    //Негативные проверки
    static Stream<Arguments> depositInvalidData() {
        return Stream.of(
                Arguments.of(accountId, 5001.0, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(accountId, 5000.1, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(accountId, 0.0, 400, "Deposit amount must be at least 0.01"),
                Arguments.of(88L, 1000.0, 403, "Unauthorized access to account")
        );
    }

    @ParameterizedTest
    @MethodSource("depositInvalidData")
    void userCannotMakeInvalidDeposit(long accountID, double amount, int expectedStatus, String expectedMessage) {
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(accountID)
                .balance(amount)
                .build();

        //Выполняем негативный депозит
        new CrudRequester(RequestSpecs.authAsUser(owner.getUsername(), owner.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsError(expectedStatus, expectedMessage))
                .post(depositRequest);

        //Проверяем, что невалидный депозит не создал транзакцию
        new CrudRequester(RequestSpecs.authAsUser(owner.getUsername(), owner.getPassword()),
                Endpoint.GET_ACCOUNTS,
                ResponseSpecs.depositTransactionDoesNotExist(accountId, amount))
                .get(accountId);
    }
}
