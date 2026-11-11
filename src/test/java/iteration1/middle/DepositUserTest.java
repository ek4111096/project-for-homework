package iteration1.middle;

import generators.RandomData;
import models.AccountResponse;
import models.CreateUserRequest;
import models.MakeDepositRequest;
import models.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import requests.AdminCreateUserRequester;
import requests.CreateAccountRequester;
import requests.GetUserAccountsRequester;
import requests.MakeDepositRequester;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class DepositUserTest extends BaseTest{
    //добавлена параметризация
    //убран JSONPath
    @ParameterizedTest
    @ValueSource(doubles = {5000.0, 4999.99})
    public void createUserAndCreateAccount(double amount) {

        //создаём пользователя
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();

        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated())
                .post(userRequest);

        //создаём аккаунт
        AccountResponse accountResponse = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post()
                .extract()
                .as(AccountResponse.class);

        long accountId = accountResponse.getId();

        //выполняем депозит с параметризованной суммой
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(accountId)
                .balance(amount)
                .build();

        new MakeDepositRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        // Проверяем состояние сервера через GET
        new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.depositTransactionExists(accountId, amount))
                .get();
    }
    //негативные тесты
    private static CreateUserRequest userRequest;
    private static long accountId;

    @BeforeAll
    static void setUp() {
        userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();

        new AdminCreateUserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .post(userRequest);
        //Убрала JSONPath
        AccountResponse accountResponse = new CreateAccountRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post()
                .extract()
                .as(AccountResponse.class);

        accountId = accountResponse.getId();
    }

    static Stream<Arguments> depositInvalidData() {
        return Stream.of(
                Arguments.of(accountId, 5001.0, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(accountId, 5000.1, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(accountId, -1.0, 400, "Deposit amount must be at least 0.01"),
                Arguments.of(accountId, 0.0, 400, "Deposit amount must be at least 0.01"),
                Arguments.of(88L, 1000.0, 403, "Unauthorized access to account")
        );
    }

    @ParameterizedTest
    @MethodSource("depositInvalidData")
    void userCanNotMakeDepositWithInvalidData(long accountId, double balance, int expectedStatus, String expectedMessage) {
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(accountId)
                .balance(balance)
                .build();

        new MakeDepositRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.requestReturnsError(expectedStatus, expectedMessage))
        .post(depositRequest);
    }

    //проверки через get того, что негативные тест-кейсы не изменили состояние сервера
    public static Stream<Arguments> depositInvalidResponceData() {
        return Stream.of(
                Arguments.of(5001.0),
                Arguments.of(5000.1),
                Arguments.of(0.0)
        );
    }

    @MethodSource("depositInvalidResponceData")
    @ParameterizedTest
    public void checkThatUserCanNotMakeDepositWithInvalidData(double amount) {
        new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.depositTransactionDoesNotExist(accountId, amount))
                .get();
    }
}

