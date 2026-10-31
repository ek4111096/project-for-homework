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
    @Test
    public void createUserAndCreateAccount() {
        //Предусловие, выполняемое перед всеми тестами, но 1 раз
        //запрос на создание юзера
        CreateUserRequest userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();
        //Админ создает юзера
        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated())
                .post(userRequest);
        //Позитивный кейс по изменению имени юзера
        ChangeUserNameRequest changeUserNameRequest = new ChangeUserNameRequest().builder()
                .name("Elena Nekuznetsova")
                .build();
        new ChangeUserNameRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .put(changeUserNameRequest);
        //проверка состояния сервера через get запрос
        new GetCustomerProfileRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.usernameChanged("Elena Nekuznetsova"))
                .get();
    }

    //негативные тесты по изменению имени
    private static CreateUserRequest userRequest;

    @BeforeAll
    static void setUp() {
        userRequest = CreateUserRequest.builder()
                .username(RandomData.getUsername())
                .password(RandomData.getPassword())
                .role(UserRole.USER.toString())
                .build();

        new AdminCreateUserRequester(
                RequestSpecs.adminSpec(),
                ResponseSpecs.entityWasCreated()
        ).post(userRequest);
        //Меняем имя юзера
        ChangeUserNameRequest changeUserNameRequest = new ChangeUserNameRequest().builder()
                .name("Elena Nekuznetsova")
                .build();
        new ChangeUserNameRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsOK())
                .put(changeUserNameRequest);
    }

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

    @MethodSource("userNameInvalidData")
    @ParameterizedTest
    public void userCanNotChangeNameWithInvalidData(String name, int expectedStatus, String expectedMessage) {
        ChangeUserNameRequest changeUserNameRequest = new ChangeUserNameRequest().builder()
                .name(name)
                .build();
        new ChangeUserNameRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                ResponseSpecs.requestReturnsError(expectedStatus, expectedMessage))
                .put(changeUserNameRequest);
        //проверка через get того, что негативные тест-кейсы не изменили состояние сервера
        new GetCustomerProfileRequester(
                RequestSpecs.authAsUser(
                        userRequest.getUsername(),
                        userRequest.getPassword()),
                ResponseSpecs.usernameNotChanged("Elena Nekuznetsova"))
                .get();
    }
}

