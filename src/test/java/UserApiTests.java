
import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.CreateSpec.*;


public class UserApiTests extends TestBase {
    @DisplayName("Успешная регистрация пользователя")
    @Test
    void successfulCreateUserTest() {
        CreateUserRequestModel authData = new CreateUserRequestModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        CreateUserResponseBodyModel response = step("Make successful registration request", () -> {
            return given(requestSpecification)
                    .body(authData)
                    .when()
                    .post("register")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(200)
                    .extract().as(CreateUserResponseBodyModel.class);
        });
        step("Check response", () -> {
            assertEquals(4, response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @DisplayName("Не успешная регистрация пользователя")
    @Test
    void unsuccessfulCreateUserTest() {
        CreateUserRequestModel authData = new CreateUserRequestModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("");

        UnsuccessfulCreateUserResponseBodyModel response = step("Make unsuccessful registration request", () -> {
            return given(requestSpecification)
                    .body(authData)
                    .when()
                    .post("register")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(400)
                    .extract().as(UnsuccessfulCreateUserResponseBodyModel.class);
        });
        step("Check response", () -> {
            assertEquals("Missing password", response.getError());

        });
    }

    @DisplayName("Пойск пользователя")
    @Test
    void searchUserTest() {
        SearchUserModel response = step("Make successful search user request", () -> {
            return given(requestSpecification)
                    .when()
                    .get("users/2")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(200)
                    .extract().as(SearchUserModel.class);
        });
        step("Check response", () -> {
            assertEquals(2, response.getData().getId());
            assertEquals("Janet", response.getData().getFirst_name());
            assertEquals("Weaver", response.getData().getLast_name());
            assertEquals("janet.weaver@reqres.in", response.getData().getEmail());
        });
    }

    @DisplayName("Изменения пользователя")
    @Test
    void putUpdateUserTest() {
        PutUpdateUserRequestModel authData = new PutUpdateUserRequestModel();
        authData.setName("morpheus");
        authData.setJob("zion resident");

        PutUpdateResponseModel response = step("Make put update request user name and job", () -> {
            return given(requestSpecification)
                    .body(authData)
                    .when()
                    .put("users/2")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(200)
                    .extract().as(PutUpdateResponseModel.class);
        });
        step("Check response", () -> {
            Assertions.assertEquals("morpheus", response.getName());
            Assertions.assertEquals("zion resident", response.getJob());
            Assertions.assertNotEquals(null, response.getUpdatedAt());
        });
    }

    @DisplayName("Изменения имени пользователя")
    @Test
    void pathUpdateUserTest() {
        PathUpdateRequestModel authData = new PathUpdateRequestModel();
        authData.setName("Dmitry");

        PutUpdateResponseModel response = step("Make path update request user name", () -> {
            return given(requestSpecification)
                    .body(authData)
                    .when()
                    .patch("users/2")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(200)
                    .extract().as(PutUpdateResponseModel.class);
        });
        step("Check response", () -> {
            Assertions.assertEquals("Dmitry", response.getName());
            Assertions.assertNotEquals(null, response.getUpdatedAt());
        });
    }

    @DisplayName("Удаление пользователя")
    @Test
    void deleteUserTest() {
        step("Delete user", () -> {
            given(requestSpecification)
                    .when()
                    .delete("users/2")
                    .then()
                    .spec(responseSpecification)
                    .statusCode(204);
        });
    }
}
