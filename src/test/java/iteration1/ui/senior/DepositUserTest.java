package iteration1.ui.senior;

import api.models.CreateAccountResponse;
import api.models.CreateUserRequest;
import api.models.TransactionResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.requests.steps.AdminSteps;
import api.requests.steps.UserSteps;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import common.annotations.UserSession;
import common.storage.SessionStorage;
import org.junit.jupiter.api.Test;
import ui.pages.BankAlert;
import ui.pages.UserDashboard;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DepositUserTest extends BaseUiTest {
    @Test
    @UserSession
    public void userCanMakeDepositTest() {
        CreateUserRequest user = SessionStorage.getUser(1);

        CreateAccountResponse account = UserSteps.createAccountForUser(user);

        String accountNumber = account.getAccountNumber();

        new UserDashboard()
                .open()
                .openDepositPage()
                .chooseAnAccount(accountNumber)
                .enterAmount(5000)
                .depositMoney();

        new UserDashboard().checkAlertMessageAndAccept(
                BankAlert.DEPOSIT_MADE_SUCCESSFULLY.getMessage() + account.getId() + "!");

        // Проверяем транзакцию через API
        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(user.getUsername(), user.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + account.getId() + "/transactions")
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(1);

        assertThat(existingTransaction).anySatisfy(transaction -> {
            assertThat(transaction.getAmount()).isEqualTo(5000.0);
            assertThat(transaction.getType()).isEqualTo("DEPOSIT");
        });
    }

    @Test
    @UserSession
    public void userCannotMakeDepositWithInvalidData() {
        CreateUserRequest user = SessionStorage.getUser(1);

        CreateAccountResponse account = UserSteps.createAccountForUser(user);

        String accountNumber = account.getAccountNumber();

        new UserDashboard()
                .open()
                .openDepositPage()
                .chooseAnAccount(accountNumber)
                .enterAmount(6000)
                .depositMoney();

        new UserDashboard().checkAlertMessageAndAccept
                (BankAlert.DEPOSIT_UNSUCCESSFUL.getMessage());

        // ШАГ 6: проверка, что транзакция с депозитом не была создана на API
        TransactionResponse[] existingTransaction =
                given()
                        .spec(RequestSpecs.authAsUser(user.getUsername(), user.getPassword()))
                        .get("http://localhost:4111/api/v1/accounts/" + account.getId() + "/transactions" )
                        .then().assertThat()
                        .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).noneMatch(transaction ->
                transaction.getAmount() == 6000.0 &&
                        transaction.getType().equals("DEPOSIT"));
    }
}
