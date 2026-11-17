package iteration1.ui.senior;

import api.generators.RandomData;
import api.models.CreateUserRequest;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.steps.AdminSteps;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import common.annotations.UserSession;
import common.storage.SessionStorage;
import org.junit.jupiter.api.*;
import ui.pages.BankAlert;
import ui.pages.UserDashboard;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChangeUserNameTest  extends BaseUiTest {
    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }

    @Order(1)
    @Test
    @UserSession
    public void changeUserName() {
        // Получаем пользователя
        CreateUserRequest userRequest = SessionStorage.getUser(1);

        // ШАГИ ТЕСТА
        // ШАГ 5: пользователь меняет имя
        String newName = RandomData.getName();
        new UserDashboard()
                .open()
                .openEditProfilePage()
                .nameInputShouldBeVisible()
                .enterNameInput(newName)
                .newNameWasEntered(newName)
                .saveChanges();

        new UserDashboard().checkAlertMessageAndAccept
                (BankAlert.NAME_UPDATED_SUCCESSFULLY.getMessage());

        // ШАГ 6: проверка, что имя изменилось на API
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_PROFILE,
                ResponseSpecs.usernameChanged(newName))
                .get();

    }

    @Order(2)
    @Test
    @UserSession
    public void userCanNotChangeNameWithInvalidData() {
        // Получаем пользователя
        CreateUserRequest userRequest = SessionStorage.getUser(1);

        String invalidName = "Elena123 Kuznetsova!";

        new UserDashboard()
                .open()
                .openEditProfilePage()
                .nameInputShouldBeVisible()
                .enterNameInput(invalidName)
                .saveChanges();

        new UserDashboard().checkAlertMessageAndAccept(
                BankAlert.NAME_UNSUCCESSFUL_UPDATE.getMessage());

        new CrudRequester(
                RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_PROFILE,
                ResponseSpecs.usernameNotChanged(null))
                .get();
    }
}
