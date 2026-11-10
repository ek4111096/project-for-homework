package iteration1.ui;

import com.codeborne.selenide.*;
import generators.RandomData;
import iteration1.api.senior.BaseTest;
import models.CreateAccountResponse;
import models.CreateUserRequest;
import models.LoginUserRequest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.CrudRequester;
import requests.skelethon.requesters.ValidatedCrudRequester;
import requests.steps.AdminSteps;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChangeUserNameTest extends BaseTest {
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

    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }

    @Order(1)
    @Test
    public void changeUserName() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

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
        // ШАГ 5: пользователь меняет имя
        $(".user-name").shouldBe(Condition.visible).click();

        // ШАГ 5. Вводим новое имя
        SelenideElement nameInput = $("[placeholder='Enter new name']");

        nameInput.shouldBe(Condition.visible, Condition.enabled);

        String newName = RandomData.getName();
        System.out.println("Generated name: " + newName);

        nameInput.setValue(newName);

        // Проверяем, что имя действительно введено
        nameInput.shouldHave(Condition.value(newName));

        $(Selectors.byText("💾 Save Changes")).click();


        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).isEqualTo("✅ Name updated successfully!");
        alert.accept();

        // ШАГ 6: проверка, что имя изменилось на API
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_PROFILE,
                ResponseSpecs.usernameChanged(newName))
                .get();

    }

    @Order(2)
    @Test
    public void userCanNotChangeNameWithInvalidData() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

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
        // ШАГ 5: пользователь меняет имя
        $(".user-name").click();

        // Ждём появления поля редактирования
        SelenideElement nameInput = $("[placeholder='Enter new name']");
        nameInput.shouldBe(Condition.visible);

        // Вводим невалидное имя
        String invalidName = "Elena123 Kuznetsova!";
        nameInput.clear();
        nameInput.setValue(invalidName);

        nameInput.shouldHave(Condition.value(invalidName));

        $(Selectors.byText("💾 Save Changes")).click();


        Alert alert = switchTo().alert();
        String alertText = alert.getText();

        assertThat(alertText).isEqualTo("Name must contain two words with letters only");
        alert.accept();

        // ШАГ 6: проверка, что имя изменилось на API
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_PROFILE,
                ResponseSpecs.usernameNotChanged(null))
                .get();

    }
}
