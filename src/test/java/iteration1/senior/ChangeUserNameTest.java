package iteration1.senior;

import generators.RandomData;
import iteration1.middle.BaseTest;
import models.ChangeUserNameRequest;
import models.CreateUserRequest;
import models.CreateUserResponse;
import models.GetUserProfileResponce;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.CrudRequester;
import requests.skelethon.requesters.ValidatedCrudRequester;
import requests.steps.AdminSteps;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.stream.Stream;

public class ChangeUserNameTest extends BaseTest {
    //Позитивный тест
    @Test
    public void changeUserName() {
        String newName = RandomData.getName();
        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        //Создаём запрос на изменение имени пользователя
        ChangeUserNameRequest changeUserNameRequest =
                ChangeUserNameRequest.builder()
                        .name(newName)
                        .build();

        //Меняем имя пользователя
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.PUT_PROFILE,
                ResponseSpecs.requestReturnsOK())
                .update(changeUserNameRequest);
        //Проверяем, что имя изменилось
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_PROFILE,
                ResponseSpecs.usernameChanged(newName))
                .get();

    }

    //Негативные проверки
    private static CreateUserRequest userRequest;
    private static String originalName;

    @BeforeAll
    static void setUp() {
        userRequest = AdminSteps.createUser();

        // Получаем исходное имя пользователя
        originalName = new ValidatedCrudRequester<GetUserProfileResponce>(
                RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_PROFILE,
                ResponseSpecs.requestReturnsOK())
                .get()
                .getName();
    }

    static Stream<Arguments> userNameInvalidData() {
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

    @ParameterizedTest
    @MethodSource("userNameInvalidData")
    public void userCannotChangeNameWithInvalidData(String name, int expectedStatus, String expectedMessage) {

        //Создаём запрос на изменение имени
        ChangeUserNameRequest changeUserNameRequest = ChangeUserNameRequest.builder()
                .name(name)
                .build();

        //Выполняем PUT с невалидным именем
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.PUT_PROFILE,
                ResponseSpecs.requestReturnsError(expectedStatus, expectedMessage))
                .update(changeUserNameRequest);

        //Проверяем через GET, что имя не изменилось
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.GET_PROFILE,
                ResponseSpecs.usernameNotChanged(originalName))
                .get();
    }
}
