package requests.steps;

import generators.RandomModelGenerator;
import io.restassured.specification.RequestSpecification;
import models.CreateAccountResponse;
import models.CreateUserRequest;
import models.CreateUserResponse;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.ValidatedCrudRequester;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class AdminSteps {
    public static CreateUserRequest createUser() {
        CreateUserRequest userRequest =
                RandomModelGenerator.generate(CreateUserRequest.class);

        new ValidatedCrudRequester<CreateUserResponse>(
                RequestSpecs.adminSpec(),
                Endpoint.ADMIN_USER,
                ResponseSpecs.entityWasCreated())
                .post(userRequest);

        return userRequest;
    }

    public static CreateAccountResponse createUserAndAccount() {
        //Создаём пользователя
        CreateUserRequest userRequest = createUser();

        //Авторизуемся под созданным пользователем
        RequestSpecification userSpec = RequestSpecs.authAsUser(
                userRequest.getUsername(),
                userRequest.getPassword()
        );

        //Создаём счёт от имени пользователя
        return new ValidatedCrudRequester<CreateAccountResponse>(
                userSpec,
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post(null);
    }
}