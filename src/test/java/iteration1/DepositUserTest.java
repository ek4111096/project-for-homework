package iteration1;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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

public class DepositUserTest {
    @BeforeAll
    public static void setupRestAssured() {
        RestAssured.filters(
                List.of(new RequestLoggingFilter(),
                        new ResponseLoggingFilter()));
    }


    //Предусловие, выполняемое перед всеми тестами, но 1 раз
    @Test
    public void createUserAndCreateAccount() {
        // создание пользователя
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .body("""
                        {
                          "username": "milli2013",
                          "password": "myPassword456!",
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
    //Позитивный тест-кейс депозит 5000 граница
    @Test
    public void userCanMakeDepositSum5000Test() {
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
                .body("""
                            {
                              "id": 2,
                              "balance": 5000
                            }
                            """)
                .post("http://localhost:4111/api/v1/accounts/deposit")
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
                        "find { it.id == 2 }.transactions.find { it.type == 'DEPOSIT' && it.amount == 5000 }",
                        notNullValue()
                );
    }

    //Позитивный тест-кейс депозит 4999 граница
    @Test
    public void userCanMakeDepositSum4999Test() {
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
                .body("""
                            {
                              "id": 2,
                              "balance": 4999
                            }
                            """)
                .post("http://localhost:4111/api/v1/accounts/deposit")
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
                        "find { it.id == 2 }.transactions.find { it.type == 'DEPOSIT' && it.amount == 4999 }",
                        notNullValue()
                );
    }

    //негативные тест-кейсы
    public static Stream<Arguments> depositInvalidData() {
        return Stream.of(
                Arguments.of(2, 5001.0, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(2, 5000.1, 400, "Deposit amount cannot exceed 5000"),
                Arguments.of(2, -1.0, 400, "Deposit amount must be at least 0.01"),
                Arguments.of(2, 0.0, 400, "Deposit amount must be at least 0.01"),
                Arguments.of(88, 1000.0, 403, "Unauthorized access to account")
        );
    }

    @MethodSource("depositInvalidData")
    @ParameterizedTest
    public void userCanNotMakeDepositWithInvalidData(int id, double balance, int expectedStatus, String expectedMessage) {
        String requestBody = String.format(
                """
                        {
                          "id": %s,
                          "balance": %s
                        }
                        """, id, balance, expectedStatus, expectedMessage);

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
                .post("http://localhost:4111/api/v1/accounts/deposit")
                .then()
                .statusCode(expectedStatus)
                .body(Matchers.equalTo(expectedMessage));
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
                "find { it.id == 2 }.transactions.find { it.type == 'DEPOSIT' && it.amount == " + amount + " }",
                Matchers.nullValue());

    }
    //проверка через get того, что тест-кейс с отрицательным значением не изменил состояние сервера
    @Test
    public void checkThatUserCanNotMakeDepositWithINegativeData() {
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
                        "find { it.id == 2 }.transactions.findAll { it.type == 'DEPOSIT' && it.amount == (-1.0) }",
                        Matchers.empty()
                );
    }
}

