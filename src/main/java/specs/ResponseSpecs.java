package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import models.AccountResponse;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.util.Arrays;

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
                .expectBody(errorKey, Matchers.hasItem(errorValue))
                .build();
    }

    public static ResponseSpecification depositTransactionExists(
            long accountId, double amount) {

        String expectedAmount = String.format(java.util.Locale.US, "%.2f", amount);

        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId +
                                " }.transactions.find { it.type == 'DEPOSIT' && " +
                                "it.amount.toBigDecimal().compareTo(" +
                                expectedAmount + "G) == 0 }",
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

    public static ResponseSpecification depositTransactionDoesNotExist(long accountId, double amount) {
        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId
                                + " }.transactions.find { it.type == 'DEPOSIT' && it.amount == " + amount + " }",
                        Matchers.nullValue()
                )
                .build();
    }

    public static ResponseSpecification transferTransactionExists(long accountId, double amount) {
        String expectedAmount = String.format(java.util.Locale.US, "%.2f", amount);

        return defaultResponseBuilder()
                .expectBody(
                        "find { it.id == " + accountId +
                                " }.transactions.find { it.type == 'TRANSFER_OUT' && " +
                                "it.amount.toBigDecimal().compareTo(" + expectedAmount + "G) == 0 }",
                        Matchers.notNullValue()
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
            long accountId, double amount) {
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

    public static ResponseSpecification userWasNotCreated(String username) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectBody("username", Matchers.not(Matchers.hasItem(username)))
                .build();
    }
}