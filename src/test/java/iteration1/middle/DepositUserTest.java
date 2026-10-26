package iteration1.middle;

import generators.RandomData;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import models.CreateUserRequest;
import models.MakeDepositRequest;
import models.UserRole;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.AdminCreateUserRequester;
import requests.CreateAccountRequester;
import requests.GetUserAccountsRequester;
import requests.MakeDepositRequester;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class DepositUserTest extends BaseTest{
    @Test
    public void createUserAndCreateAccount() {
        //Предусловие, выполняемое перед всеми тестами, но 1 раз
        //запрос на создание юзера
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();
        //админ создает юзера
        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated())
                .post(userRequest);
        // Создание аккаунта у пользователя
        int accountId = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post(null)
                .extract()
                .jsonPath()
                .getInt("id");
        //Позитивный тест-кейс депозит 5000 граница
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(accountId)
                .balance(5000)
                .build();
        new MakeDepositRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);
        //проверка состояния сервера через get запрос
        new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.depositTransactionExistsBorder5000(accountId))
                .get();
        //Позитивный тест-кейс депозит 4999 граница
        MakeDepositRequest depositRequest1 = MakeDepositRequest.builder()
                .id(accountId)
                .balance(4999)
                .build();
        new MakeDepositRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest1);
        //проверка состояния сервера через get запрос
        new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.depositTransactionExistsBorder4999(accountId))
                .get();
    }
    //негативные тесты
    private static CreateUserRequest userRequest;
    private static int accountId;

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

        accountId = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated()
        ).post(null)
                .extract()
                .jsonPath()
                .getInt("id");
    }

    static Stream<Arguments> depositInvalidData() {
        return Stream.of(
                Arguments.of(true, 5001.0, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(true, 5000.1, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(true, -1.0, 400, "Deposit amount must be at least 0.01"),
                Arguments.of(true, 0.0, 400, "Deposit amount must be at least 0.01"),
                Arguments.of(false, 1000.0, 403, "Unauthorized access to account")
        );
    }

    @ParameterizedTest
    @MethodSource("depositInvalidData")
    void userCanNotMakeDepositWithInvalidData(boolean ownAccount, double balance, int expectedStatus, String expectedMessage) {
        int id = ownAccount ? accountId : 88;

        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(id)
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

