package iteration1.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import generators.RandomData;
import iteration1.api.senior.BaseTest;
import models.*;
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

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class TransferUserTest extends BaseTest {
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
    public void userCanTransfer() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

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
        // ШАГ 7: юзер выполняет трансфер

        $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();
        $("select.account-selector").selectOptionContainingText(firstAccountNumber);

        $("[placeholder='Enter recipient name']").setValue(userRequest.getUsername());
        $("[placeholder='Enter recipient account number']").setValue(secondAccountNumber);

        $("[placeholder='Enter amount']").setValue("100");

        $(".form-check-input").click();

        $(".btn-primary").shouldHave(text("Send Transfer")).click();

        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).contains("✅ Successfully transferred $100 to account ACC");
        assertThat(alertText).contains(secondAccountNumber);
        alert.accept();

        // ШАГ 8: проверка, что транзакция с трансфером была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(2);

        assertThat(existingTransaction).anySatisfy(transaction -> {
            assertThat(transaction.getAmount()).isEqualTo(100.0);
            assertThat(transaction.getType()).isEqualTo("TRANSFER_OUT");
        });
    }

    //НЕГАТИВНЫЕ КЕЙСЫ
    @Test
    public void userCannotTransferWithInvalidAccount() {
        //НЕвалидный счет
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

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
        // ШАГ 7: юзер выполняет трансфер

        $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();
        $("select.account-selector").selectOptionContainingText(firstAccountNumber);

        $("[placeholder='Enter recipient name']").setValue(userRequest.getUsername());
        $("[placeholder='Enter recipient account number']").setValue("ACC900");

        $("[placeholder='Enter amount']").setValue("500");

        $(".form-check-input").click();

        $(".btn-primary").shouldHave(text("Send Transfer")).click();

        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).contains("❌ No user found with this account number.");
        alert.accept();

        // ШАГ 8: проверка, что транзакция с трансфером НЕ была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(1);

        assertThat(existingTransaction).noneMatch(transaction ->
            transaction.getAmount() == 500.0 &&
                    transaction.getType().equals("TRANSFER_OUT"));
    }

    @Test
    public void userCannotTransferWithoutCheckbox() {
        //Не нажат чекбокс
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

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
        // ШАГ 7: юзер выполняет трансфер

        $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();
        $("select.account-selector").selectOptionContainingText(firstAccountNumber);

        $("[placeholder='Enter recipient name']").setValue(userRequest.getUsername());
        $("[placeholder='Enter recipient account number']").setValue(secondAccountNumber);

        $("[placeholder='Enter amount']").setValue("600");


        $(".btn-primary").shouldHave(text("Send Transfer")).click();

        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).contains("❌ Please fill all fields and confirm.");
        alert.accept();

        // ШАГ 8: проверка, что транзакция с трансфером НЕ была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(1);

        assertThat(existingTransaction).noneMatch(transaction ->
                transaction.getAmount() == 600.0 &&
                        transaction.getType().equals("TRANSFER_OUT"));
    }

    @Test
    public void userCanRepeatTransfer() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет
        // ШАГ 7: юзер выполняет трансфер

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        //Создаём запрос на трансфер
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(firstAccount.getId())
                .receiverAccountId(secondAccount.getId())
                .amount(505)
                .build();
        //Выполняем трансфер
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.TRANSFER,
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);

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
        // ШАГ 8: юзер выполняет трансфер

        $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();
        $(Selectors.byText("🔁 Transfer Again")).click();

        // ШАГ 9: находит TRANSFER_OUT и нажимает Repeat
        $$("ul.list-group li")
                .filterBy(text("TRANSFER_OUT"))
                .first()
                .$("button")
                .click();

        // ШАГ 10: выбирает счёт для повторного перевода
        $("select.form-control").selectOptionContainingText(secondAccountNumber);

        // ШАГ 11: подтверждает данные перевода
        $("#confirmCheck").click();

        // ШАГ 12: отправляет повторный перевод
        $(Selectors.byText("🚀 Send Transfer")).click();

        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).contains("✅ Transfer of $505 successful from Account");
        assertThat(alertText).contains(String.valueOf(firstAccount.getId()));
        assertThat(alertText).contains(String.valueOf(secondAccount.getId()));
        alert.accept();

        // ШАГ 13: проверка, что транзакция с трансфером была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(3);
    }

    @Test
    public void userCanRepeatTransferWithSearch() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет
        // ШАГ 7: юзер выполняет трансфер

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        //Создаём запрос на трансфер
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(firstAccount.getId())
                .receiverAccountId(secondAccount.getId())
                .amount(506)
                .build();
        //Выполняем трансфер
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.TRANSFER,
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);

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
        // ШАГ 8: юзер выполняет трансфер

        $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();
        $(Selectors.byText("🔁 Transfer Again")).click();

        // ШАГ 9: вводит username в поле поиска
        $("[placeholder='Enter name to find transactions']")
                .setValue(userRequest.getUsername());

        // ШАГ 10: нажимает Search Transactions
        $(Selectors.byText("🔍 Search Transactions")).click();


        // ШАГ 11: находит TRANSFER_OUT и нажимает Repeat
        $$("ul.list-group li")
                .filterBy(text("TRANSFER_OUT"))
                .first()
                .$("button")
                .click();

        // ШАГ 12: выбирает счёт для повторного перевода
        $("select.form-control").selectOptionContainingText(secondAccountNumber);

        // ШАГ 13: подтверждает данные перевода
        $("#confirmCheck").click();

        // ШАГ 14: отправляет повторный перевод
        $(Selectors.byText("🚀 Send Transfer")).click();

        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).contains("✅ Transfer of $506 successful from Account");
        assertThat(alertText).contains(String.valueOf(firstAccount.getId()));
        assertThat(alertText).contains(String.valueOf(secondAccount.getId()));
        alert.accept();

        // ШАГ 13: проверка, что транзакция с трансфером была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(3);
    }

    //негативный тест с поиском транзакции
    @Test
    public void userCannotRepeatTransferWithSearchWithInvalidData() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет
        // ШАГ 7: юзер выполняет трансфер

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        //Создаём запрос на трансфер
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(firstAccount.getId())
                .receiverAccountId(secondAccount.getId())
                .amount(506)
                .build();
        //Выполняем трансфер
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.TRANSFER,
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);

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
        // ШАГ 8: юзер выполняет трансфер

        $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();
        $(Selectors.byText("🔁 Transfer Again")).click();

        // ШАГ 9: вводим невалидное имя в поле поиска
        String invalidName = "WrongUser123";

        $("[placeholder='Enter name to find transactions']")
                .setValue(invalidName);

        // ШАГ 10: нажимаем Search Transactions
        $(Selectors.byText("🔍 Search Transactions")).click();

        // ШАГ 11: проверяем сообщение об отсутствии пользователя
        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).isEqualTo("❌ No matching users found.");

        alert.accept();

        // ШАГ 12: проверяем, что исходные транзакции не изменились
        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions")
                .then().assertThat()
                .statusCode(200)
                .extract()
                .as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(2);
    }

}
