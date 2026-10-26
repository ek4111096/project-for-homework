package iteration1.middle;

import generators.RandomData;
import io.restassured.http.ContentType;
import models.CreateUserRequest;
import models.MakeDepositRequest;
import models.MakeTransferRequest;
import models.UserRole;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.*;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class TransferUserTest {
    @Test
    public void createUserAndCreateAccount() {
        //Предусловие, выполняемое перед всеми тестами, но 1 раз
        //запрос на создание юзера
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();
        //Админ создает юзера
        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated())
                .post(userRequest);
        // Создание аккаунта у пользователя
        int accountFirstId = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post(null)
                .extract()
                .jsonPath()
                .getInt("id");
        //Кладём 25000 на счёт четырьмя депозитами по 5000
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(accountFirstId)
                .balance(5000)
                .build();
        MakeDepositRequester depositRequester = new MakeDepositRequester(
                RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK()
        );
        for (int i = 0; i < 5; i++) {
            depositRequester.post(depositRequest);
        }
        //Создание второго аккаунта у пользователя
        int accountSecondId = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post(null)
                .extract()
                .jsonPath()
                .getInt("id");
        //Создание 2 юзера
        CreateUserRequest secondUserRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();
        //Админ создает второго юзера
        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated())
                .post(secondUserRequest);
        // Создание аккаунта у второго пользователя
        int secondUserAccountId = new CreateAccountRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.entityWasCreated())
                .post(null)
                .extract()
                .jsonPath()
                .getInt("id");
        //перевод с 1 счета на другой граница 10000
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(accountFirstId)
                .receiverAccountId(accountSecondId)
                .amount(10000)
                .build();
        new MakeTransferRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);
        //проверка состояния сервера через get запрос
        new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.transferTransactionExistsBorder10000(accountFirstId))
                .get();
        //перевод с 1 счета на другой граница 9999
        MakeTransferRequest transferRequest1 = MakeTransferRequest.builder()
                .senderAccountId(accountFirstId)
                .receiverAccountId(accountSecondId)
                .amount(9999)
                .build();
        new MakeTransferRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest1);
        //проверка состояния сервера через get запрос
        new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.transferTransactionExistsBorder9999(accountFirstId))
                .get();
        //Позитивный тест-кейс перевод со счета одного юзера на счет другого юзера 1000
        MakeTransferRequest transferToAnotherUserRequest = MakeTransferRequest.builder()
                .senderAccountId(accountFirstId)
                .receiverAccountId(secondUserAccountId)
                .amount(1000)
                .build();
        new MakeTransferRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .post(transferToAnotherUserRequest);
        //проверка состояния сервера через get запрос
        new GetUserAccountsRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.transferTransactionExistsBorder1000(accountFirstId))
                .get();
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
