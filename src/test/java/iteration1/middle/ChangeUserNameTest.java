package iteration1.middle;

import generators.RandomData;
import models.ChangeUserNameRequest;
import models.CreateUserRequest;
import models.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.*;
import specs.RequestSpecs;
import specs.ResponseSpecs;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeUserNameTest extends BaseTest {
    //Убран хардкод
    //Общие данные для тестов
    private static CreateUserRequest userRequest;
    private static String originalName;

    //Предусловие для негативных тестов
    @BeforeAll
    static void setUp() {
        userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();

        //Админ создаёт пользователя
        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated()
        ).post(userRequest);

        //Устанавливаем исходное имя
        originalName = RandomData.getName();

        ChangeUserNameRequest changeUserNameRequest =
                ChangeUserNameRequest.builder()
                        .name(originalName)
                        .build();

        new ChangeUserNameRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .put(changeUserNameRequest);
    }

    // Позитивный тест
    @Test
    public void changeUserName() {
        String newName = RandomData.getName();

        ChangeUserNameRequest changeUserNameRequest =
                ChangeUserNameRequest.builder()
                        .name(newName)
                        .build();

        new ChangeUserNameRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .put(changeUserNameRequest);

        // Проверяем, что имя изменилось
        new GetCustomerProfileRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.usernameChanged(newName))
                .get();
    }

    // Негативные тестовые данные
    public static Stream<Arguments> userNameInvalidData() {
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

    // Негативные тесты по изменению имени
    @ParameterizedTest
    @MethodSource("userNameInvalidData")
    public void userCanNotChangeNameWithInvalidData(String name, int expectedStatus, String expectedMessage) {

        ChangeUserNameRequest changeUserNameRequest =
                ChangeUserNameRequest.builder()
                        .name(name)
                        .build();

        new ChangeUserNameRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsError(expectedStatus, expectedMessage))
                .put(changeUserNameRequest);

        // Проверяем, что негативный запрос не изменил имя
        new GetCustomerProfileRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.usernameNotChanged(originalName))
                .get();
    }
}

