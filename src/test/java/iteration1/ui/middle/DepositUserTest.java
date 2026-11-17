package iteration1.ui.middle;

import api.models.CreateAccountResponse;
import api.models.CreateUserRequest;
import api.models.LoginUserRequest;
import api.models.TransactionResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.requests.steps.AdminSteps;
import api.requests.steps.UserSteps;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import ui.pages.BankAlert;
import ui.pages.DepositMoneyPage;
import ui.pages.UserDashboard;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DepositUserTest extends BaseUiTest{
    @Test
    public void userCanMakeDepositTest() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет

        //Создаём пользователя
        CreateUserRequest user = AdminSteps.createUser();

        authAsUser(user);

        //Создаём счёт
        CreateAccountResponse account = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(user.getUsername(), user.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String accountNumber = account.getAccountNumber();

        //ШАГ 5: Выполняем депозит на 5000
        new UserDashboard()
                .open()
                .openDepositPage()
                .chooseAnAccount(accountNumber)
                .enterAmount(5000)
                .depositMoney();

        new UserDashboard().checkAlertMessageAndAccept
                (BankAlert.DEPOSIT_MADE_SUCCESSFULLY.getMessage() + account.getId() + "!");

        // ШАГ 6: проверка, что транзакция с депозитом была создана на API
        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(user.getUsername(), user.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + account.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(1);

        TransactionResponse createdTransaction = existingTransaction[0];

        assertThat(createdTransaction).isNotNull();
        assertThat(existingTransaction).anySatisfy(transaction -> {
            assertThat(transaction.getAmount()).isEqualTo(5000.0);
            assertThat(transaction.getType()).isEqualTo("DEPOSIT");
        });

    }

    @Test
    public void userCannotMakeDepositWithInvalidData() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет

        //Создаём пользователя
        CreateUserRequest user = AdminSteps.createUser();

        authAsUser(user);

        //Создаём счёт
        CreateAccountResponse account = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(user.getUsername(), user.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String accountNumber = account.getAccountNumber();

        //ШАГ 5: Выполняем депозит на 5000
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
