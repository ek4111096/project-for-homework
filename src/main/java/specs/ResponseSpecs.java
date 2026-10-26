package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ResponseSpecs {
    private ResponseSpecs() {}

    private static ResponseSpecBuilder defaultResponseBuilder() {
        return new ResponseSpecBuilder();
    }

    public static ResponseSpecification entityWasCreated() {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_CREATED)
                .build();
    }

    public static ResponseSpecification requestReturnsOK() {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification requestReturnsBadRequest(String errorKey, String errorValue) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(errorKey, Matchers.equalTo(errorValue))
                .build();
    }

    public static ResponseSpecification depositTransactionExistsBorder5000(int accountId) {
        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId + " }.transactions.find { it.type == 'DEPOSIT' && it.amount == 5000 }",
                        Matchers.notNullValue()
                )
                .build();
    }

    public static ResponseSpecification depositTransactionExistsBorder4999(int accountId) {
        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId + " }.transactions.find { it.type == 'DEPOSIT' && it.amount == 4999 }",
                        Matchers.notNullValue()
                )
                .build();
    }

    public static ResponseSpecification requestReturnsError(int expectedStatus, String expectedMessage) {
        return defaultResponseBuilder()
                .expectStatusCode(expectedStatus)
                .expectBody(Matchers.equalTo(expectedMessage))
                .build();
    }

    public static ResponseSpecification depositTransactionDoesNotExist(int accountId, double amount) {
        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId
                                + " }.transactions.find { it.type == 'DEPOSIT' && it.amount == " + amount + " }",
                        Matchers.nullValue()
                )
                .build();
    }

    public static ResponseSpecification transferTransactionExistsBorder10000(int accountId) {
        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId + " }.transactions.find { it.type == 'TRANSFER_OUT' && it.amount == 10000 }",
                        Matchers.notNullValue()
                )
                .build();
    }

    public static ResponseSpecification transferTransactionExistsBorder9999(int accountId) {
        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId + " }.transactions.find { it.type == 'TRANSFER_OUT' && it.amount == 9999 }",
                        Matchers.notNullValue()
                )
                .build();
    }

    public static ResponseSpecification transferTransactionDoesNotExist(
            int accountId, double amount) {
        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId
                                + " }.transactions.find { it.type == 'TRANSFER_OUT' && it.amount == " + amount + " }",
                        Matchers.nullValue()
                )
                .build();
    }
    public static ResponseSpecification transferTransactionExistsBorder1000(int accountId) {
        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId + " }.transactions.find { it.type == 'TRANSFER_OUT' && it.amount == 1000 }",
                        Matchers.notNullValue()
                )
                .build();
    }

    public static ResponseSpecification usernameChanged(String expectedName) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectBody("name", Matchers.equalTo(expectedName))
                .build();
    }

    public static ResponseSpecification usernameNotChanged(String expectedName) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectBody("name", Matchers.equalTo(expectedName))
                .build();
    }
}