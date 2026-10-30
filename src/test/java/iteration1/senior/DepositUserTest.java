package iteration1.senior;

import generators.RandomData;
import generators.RandomModelGenerator;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import iteration1.middle.BaseTest;
import models.*;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import requests.AdminCreateUserRequester;
import requests.CreateAccountRequester;
import requests.GetUserAccountsRequester;
import requests.MakeDepositRequester;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.CrudRequester;
import requests.skelethon.requesters.ValidatedCrudRequester;
import requests.steps.AdminSteps;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class DepositUserTest extends BaseTest {
    @ParameterizedTest
    @ValueSource(doubles = {4999.99, 5000})
    public void userCanDeposit(double amount) {
        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        CreateAccountResponse account =
                new ValidatedCrudRequester<CreateAccountResponse>(
                        RequestSpecs.authAsUser(
                                userRequest.getUsername(),
                                userRequest.getPassword()),
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

        //проверка состояния сервера через get запрос
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_ACCOUNTS,
                ResponseSpecs.depositTransactionExists(account.getId(), amount))
                .get(account.getId());




    }
    //негативные проверки
    static Stream<Arguments> depositInvalidData() {
        return Stream.of(
                Arguments.of(-1L, 5001.0, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(-1L, 5000.1, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(-1L, -1.0, 400, "Deposit amount must be at least 0.01"),
                Arguments.of(-1L, 0.0, 400, "Deposit amount must be at least 0.01"),
                Arguments.of(88L, 1000.0, 403, "Unauthorized access to account")
        );
    }

    @ParameterizedTest
    @MethodSource("depositInvalidData")
    void userCannotMakeInvalidDeposit(
            long accountID,
            double amount,
            int expectedStatus,
            String expectedMessage
    ) {
        CreateUserRequest owner = AdminSteps.createUser();

        CreateAccountResponse account =
                new ValidatedCrudRequester<CreateAccountResponse>(
                        RequestSpecs.authAsUser(
                                owner.getUsername(),
                                owner.getPassword()),
                        Endpoint.ACCOUNTS,
                        ResponseSpecs.entityWasCreated())
                        .post(null);

        //для негативных проверок использую созданный счёт,
        //если передан условный ID -1
        long actualAccountID = accountID == -1L
                ? account.getId()
                : accountID;

        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(actualAccountID)
                .balance(amount)
                .build();

        new CrudRequester(
                RequestSpecs.authAsUser(
                        owner.getUsername(),
                        owner.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsError(expectedStatus, expectedMessage)
        ).post(depositRequest);

        new CrudRequester(
                RequestSpecs.authAsUser(
                        owner.getUsername(),
                        owner.getPassword()),
                Endpoint.GET_ACCOUNTS,
                ResponseSpecs.depositTransactionDoesNotExist(account.getId(), amount)
        ).get(account.getId());
    }
}
