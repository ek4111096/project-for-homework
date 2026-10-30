package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import models.AccountResponse;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.util.Arrays;



import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

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

    public static ResponseSpecification depositTransactionExists(
            long accountId, double amount) {

        return new ResponseSpecBuilder()
                .expectBody(
                        "find { it.id == " + accountId +
                                " }.transactions.find { it.type == 'DEPOSIT' && " +
                                "it.amount.toString() == '" + amount + "' }",
                        notNullValue())
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

    public static void transferTransactionExists(
            AccountResponse[] accounts,
            long accountId,
            double expectedAmount) {

        AccountResponse account = Arrays.stream(accounts)
                .filter(a -> a.getId() == accountId)
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError("Account not found: " + accountId));

        boolean transactionExists = account.getTransactions().stream()
                .anyMatch(transaction ->
                        "TRANSFER_OUT".equals(transaction.getType())
                                && BigDecimal.valueOf(transaction.getAmount())
                                .compareTo(BigDecimal.valueOf(expectedAmount)) == 0);

        if (!transactionExists) {
            throw new AssertionError(
                    "Transfer transaction not found. Amount: " + expectedAmount);
        }
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

    public static ResponseSpecification accountExists(long accountId) {
        return new ResponseSpecBuilder()
                .expectBody("id", hasItem((int) accountId))
                .build();
    }
}