package iteration1;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class TransferUserTest {
    @BeforeAll
    public static void setupRestAssured() {
        RestAssured.filters(
                List.of(new RequestLoggingFilter(),
                        new ResponseLoggingFilter()));
    }

    //Предусловие, выполняемое перед всеми тестами, но 1 раз
    @Test
    public void loginUserAndCreate2Account() {
        // получаем токен юзера
        String userAuthHeader = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                        {
                          "username": "milli2013",
                          "password": "myPassword456!"
                        }
                        """)
                .post("http://localhost:4111/api/v1/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .header("Authorization");

        // создаем 2 аккаунт(счет)
        given()
                .header("Authorization", userAuthHeader)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post("http://localhost:4111/api/v1/accounts")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void userCanMakeTransferSum10000Test() {
        // получаем токен юзера
        String userAuthHeader = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                        {
                          "username": "milli2013",
                          "password": "myPassword456!"
                        }
                        """)
                .post("http://localhost:4111/api/v1/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .header("Authorization");
        //перевод с 1 счета на другой граница 10000
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", userAuthHeader)
                .body("""
                        {
                          "senderAccountId": 2,
                          "receiverAccountId": 3,
                          "amount": 10000
                        }
                        """)
                .post("http://localhost:4111/api/v1/accounts/transfer")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
        //добавила проверку состяния сервера через get запрос
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", userAuthHeader)
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "find { it.id == 2 }.transactions.find { it.type == 'TRANSFER_OUT' && it.amount == 10000 }",
                        notNullValue()
                );
    }
    @Test
    public void userCanMakeTransferSum9999Test() {
        // получаем токен юзера
        String userAuthHeader = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                        {
                          "username": "milli2013",
                          "password": "myPassword456!"
                        }
                        """)
                .post("http://localhost:4111/api/v1/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .header("Authorization");
        //перевод с 1 счета на другой граница 9999
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", userAuthHeader)
                .body("""
                        {
                          "senderAccountId": 2,
                          "receiverAccountId": 3,
                          "amount": 9999
                        }
                        """)
                .post("http://localhost:4111/api/v1/accounts/transfer")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
        //проверка состояния сервера через get
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", userAuthHeader)
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "find { it.id == 2 }.transactions.find { it.type == 'TRANSFER_OUT' && it.amount == 9999 }",
                        notNullValue()
                );
    }

    public static Stream<Arguments> transferInvalidData() {
        return Stream.of(
                Arguments.of(2, 3, 10001.0, 400, "Transfer amount cannot exceed 10000"),
                Arguments.of(2, 3, 10000.1, 400, "Transfer amount cannot exceed 10000"),
                Arguments.of(2, 3, -1.0, 400, "Transfer amount must be at least 0.01"),
                Arguments.of(2, 3, 0.0, 400, "Transfer amount must be at least 0.01"),
                Arguments.of(2, 3, 6000, 400, "Invalid transfer: insufficient funds or invalid accounts")
        );
    }
    //негативные тест-кейсы
    @MethodSource("transferInvalidData")
    @ParameterizedTest
    public void userCanNotMakeTransferWithInvalidData(int senderAccountId, int receiverAccountId, double amount, int expectedStatus, String expectedMessage) {
        String requestBody = String.format(
                """
                        {
                          "senderAccountId": %s,
                          "receiverAccountId": %s,
                          "amount": %s
                        }
                        """, senderAccountId, receiverAccountId, amount, expectedStatus, expectedMessage);

        // получаем токен юзера
        String userAuthHeader = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                        {
                          "username": "milli2013",
                          "password": "myPassword456!"
                        }
                        """)
                .post("http://localhost:4111/api/v1/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .header("Authorization");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", userAuthHeader)
                .body(requestBody)
                .post("http://localhost:4111/api/v1/accounts/transfer")
                .then()
                .statusCode(expectedStatus)
                .body(Matchers.equalTo(expectedMessage));
    }
    //проверки через get того, что негативные тест-кейсы не изменили состояние сервера
    public static Stream<Arguments> transferInvalidResponceData() {
        return Stream.of(
                Arguments.of(10001.0),
                Arguments.of(10000.1),
                Arguments.of(6000),
                Arguments.of(0.0)
        );
    }

    @MethodSource("transferInvalidResponceData")
    @ParameterizedTest
    public void checkThatUserCanNotMakeTransferWithInvalidData(double amount) {
        // получаем токен юзера
        String userAuthHeader = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                        {
                          "username": "milli2013",
                          "password": "myPassword456!"
                        }
                        """)
                .post("http://localhost:4111/api/v1/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .header("Authorization");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", userAuthHeader)
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "find { it.id == 2 }.transactions.find { it.type == 'TRANSFER_OUT' && it.amount == " + amount + " }",
                        Matchers.nullValue());

    }
    //проверка через get того, что тест-кейс с отрицательным значением не изменил состояние сервера
    @Test
    public void checkThatUserCanNotMakeTransferWithINegativeData() {
        // получаем токен юзера
        String userAuthHeader = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                        {
                          "username": "milli2013",
                          "password": "myPassword456!"
                        }
                        """)
                .post("http://localhost:4111/api/v1/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .header("Authorization");

        given()
                .header("Authorization", userAuthHeader)
                .accept(ContentType.JSON)
                .when()
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "find { it.id == 2 }.transactions.findAll { it.type == 'TRANSFER_OUT' && it.amount == (-1.0) }",
                        Matchers.empty()
                );
    }

    //Предусловие, выполняемое 1 раз
    @Test
    public void createUserAndCreateAccount() {
        // создание пользователя
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .body("""
                        {
                          "username": "milli2014",
                          "password": "myPassword789!",
                          "role": "USER"
                        }
                        """)
                .post("http://localhost:4111/api/v1/admin/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

        // получаем токен юзера
        String userAuthHeader = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                        {
                          "username": "milli2014",
                          "password": "myPassword789!"
                        }
                        """)
                .post("http://localhost:4111/api/v1/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .header("Authorization");

        // создаем аккаунт(счет)
        given()
                .header("Authorization", userAuthHeader)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post("http://localhost:4111/api/v1/accounts")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

    }
    //Позитивный тест-кейс перевод со счета одного юзера на счет другого юзера 1000
    @Test
    public void userCanMakeTransferToAnotherUserTest() {
        // получаем токен юзера
        String userAuthHeader = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                        {
                          "username": "milli2013",
                          "password": "myPassword456!"
                        }
                        """)
                .post("http://localhost:4111/api/v1/auth/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .header("Authorization");
        //перевод со счета одного юзера на счет другого юзера 1000
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", userAuthHeader)
                .body("""
                        {
                          "senderAccountId": 2,
                          "receiverAccountId": 4,
                          "amount": 1000
                        }
                        """)
                .post("http://localhost:4111/api/v1/accounts/transfer")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
        //проверка состояния сервера через get
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", userAuthHeader)
                .get("http://localhost:4111/api/v1/customer/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "find { it.id == 2 }.transactions.find { it.type == 'TRANSFER_OUT' && it.amount == 1000 }",
                        notNullValue()
                );
    }


}
