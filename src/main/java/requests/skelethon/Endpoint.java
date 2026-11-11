package requests.skelethon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import models.*;

@Getter
@AllArgsConstructor
public enum Endpoint {
    ADMIN_USER(
            "/admin/users",
            CreateUserRequest.class,
            CreateUserResponse.class
    ),

    LOGIN(
            "/auth/login",
            LoginUserRequest.class,
            LoginUserResponse.class
    ),

    ACCOUNTS(
            "/accounts",
            BaseModel.class,
            AccountResponse.class
    ),

    DEPOSIT(
            "accounts/deposit",
            MakeDepositRequest.class,
            MakeDepositResponse.class
    ),
    GET_ACCOUNTS(
            "customer/accounts",
            BaseModel.class,
            CustomerResponse.class
    ),
    TRANSFER(
            "accounts/transfer",
            MakeTransferRequest.class,
            MakeTransferResponse.class
    ),
    PUT_PROFILE(
            "customer/profile",
            ChangeUserNameRequest.class,
            ChangeUserNameResponse.class
    ),
    GET_PROFILE(
            "customer/profile",
            BaseModel.class,
            GetUserProfileResponce.class
    ),
    GET_USERS(
            "/admin/users",
            BaseModel.class,
            BaseModel.class
    );


    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
}
