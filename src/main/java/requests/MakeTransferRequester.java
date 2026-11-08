package requests;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.BaseModel;
import models.MakeTransferRequest;

import static io.restassured.RestAssured.given;

public class MakeTransferRequester extends Request<MakeTransferRequest>{
    public MakeTransferRequester(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        super(requestSpecification, responseSpecification);
    }

    @Override
    public ValidatableResponse get() {
        return null;
    }

    @Override
    public ValidatableResponse post(MakeTransferRequest model) {
        return given()
                .spec(requestSpecification)
                .body(model)
                .post("/api/v1/accounts/transfer")
                .then()
                .assertThat()
                .spec(responseSpecification);
    }

    @Override
    public ValidatableResponse put(MakeTransferRequest model) {
        return null;
    }
}
