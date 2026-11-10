package iteration1.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import iteration1.api.senior.BaseTest;
import models.CreateAccountResponse;
import models.CreateUserRequest;
import models.LoginUserRequest;
import models.TransactionResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.CrudRequester;
import requests.skelethon.requesters.ValidatedCrudRequester;
import requests.steps.AdminSteps;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DepositUserTest extends BaseTest {
    @BeforeAll
    public static void setupSelenoid() {
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.baseUrl = "http://192.168.1.105:3000";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";

        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true, "enableLog", true)
        );
    }
    @Test
    public void userCanDeposit() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        CreateAccountResponse account = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                        Endpoint.ACCOUNTS,
                        ResponseSpecs.entityWasCreated())
                        .post(null);
        String accountNumber = account.getAccountNumber();

        String userAuthHeader = new CrudRequester(
                RequestSpecs.unauthSpec(),
                Endpoint.LOGIN,
                ResponseSpecs.requestReturnsOK())
                .post(LoginUserRequest.builder().username(userRequest.getUsername()).password(userRequest.getPassword()).build())
                .extract()
                .header("Authorization");

        Selenide.open("/");

        executeJavaScript("localStorage.setItem('authToken', arguments[0]);", userAuthHeader);

        Selenide.open("/dashboard");

        // ШАГИ ТЕСТА
        // ШАГ 5: юзер создает депозит

        $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).click();
//        $(Selectors.byText("-- Choose an account --")).click();
//        $("button").shouldHave(text("Deposit")).click();

        $("select").selectOptionContainingText(accountNumber);

        $(".deposit-input").setValue("5000");

        $(".btn-primary").shouldHave(text("Deposit")).click();

        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).contains("✅ Successfully deposited $5000 to account ACC");
        assertThat(alertText).contains(accountNumber);
        alert.accept();


        // ШАГ 6: проверка, что транзакция с депозитом была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
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
    public void userCannotDepositWithInvalidData() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        CreateAccountResponse account = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String accountNumber = account.getAccountNumber();

        String userAuthHeader = new CrudRequester(
                RequestSpecs.unauthSpec(),
                Endpoint.LOGIN,
                ResponseSpecs.requestReturnsOK())
                .post(LoginUserRequest.builder().username(userRequest.getUsername()).password(userRequest.getPassword()).build())
                .extract()
                .header("Authorization");

        Selenide.open("/");

        executeJavaScript("localStorage.setItem('authToken', arguments[0]);", userAuthHeader);

        Selenide.open("/dashboard");

        // ШАГИ ТЕСТА
        // ШАГ 5: юзер создает депозит

        $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).click();
//        $(Selectors.byText("-- Choose an account --")).click();
//        $("button").shouldHave(text("Deposit")).click();

        $("select").selectOptionContainingText(accountNumber);

        $(".deposit-input").setValue("6000");

        $(".btn-primary").shouldHave(text("Deposit")).click();

        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).contains("❌ Please deposit less or equal to 5000$.");

        alert.accept();

        // ШАГ 6: проверка, что транзакция с депозитом не была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + account.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).noneMatch(transaction ->
                transaction.getAmount() == 6000.0 &&
                        transaction.getType().equals("DEPOSIT"));
    }
}
