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

public class ChangeUserName {
    @BeforeAll
    public static void setupRestAssured() {
        RestAssured.filters(
                List.of(new RequestLoggingFilter(),
                        new ResponseLoggingFilter()));
    }
    //Позитивный кейс по изменению имени юзера
    @Test
    public void userCanChangeNameTest() {
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
                              "name": "Elena Kuznetsova"
                            }
                            """)
                .put("http://localhost:4111/api/v1/customer/profile")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    //негативные тесты по изменению имени
    public static Stream<Arguments> userNameInvalidData() {
        return Stream.of(
                Arguments.of("Elena ", 400, "Name must contain two words with letters only"),
                Arguments.of("Elena", 400, "Name must contain two words with letters only"),
                Arguments.of("Elena  Kuz", 400, "Name must contain two words with letters only"),
                Arguments.of("Elena  Kuz ", 400, "Name must contain two words with letters only"),
                Arguments.of("Elena!  Kuz$", 400, "Name must contain two words with letters only"),
                Arguments.of("123 456", 400, "Name must contain two words with letters only"),
                Arguments.of(" ", 400, "Name must contain two words with letters only"),
                Arguments.of("", 400, "Name must contain two words with letters only")
        );
    }
    //негативные кейсы
    @MethodSource("userNameInvalidData")
    @ParameterizedTest
    public void userCanNotChangeNameWithInvalidData(String name, int expectedStatus, String expectedMessage) {
        String requestBody = String.format(
                """
                        {
                          "name": "%s"
                        }
                        """, name, expectedStatus, expectedMessage);

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
                .put("http://localhost:4111/api/v1/customer/profile")
                .then()
                .statusCode(expectedStatus)
                .body(Matchers.equalTo(expectedMessage));
    }

    //Негативный кейс по изменению имени юзера со значением null
    @Test
    public void userCanNotChangeNameWithNullTest() {
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
        //изменение имени юзера со значением null
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", userAuthHeader)
                .body("""
                            {
                              "name": null
                            }
                            """)
                .put("http://localhost:4111/api/v1/customer/profile")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
