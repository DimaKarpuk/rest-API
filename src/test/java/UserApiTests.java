
import models.ResponseBodyModel;
import models.UserApiModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserApiTests extends TestBase {
    @DisplayName("Успешная регистрация пользователя")
    @Test
    void successfulCreateUserTest() {
        UserApiModel authData = new UserApiModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        ResponseBodyModel response = given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().all()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .log().all()
                .statusCode(200)
                .extract().as(ResponseBodyModel.class);
        assertEquals(4,response.getId());
        assertEquals("QpwL5tke4Pnpja7X4",response.getToken());
    }

    @DisplayName("Не успешная регистрация пользователя")
    @Test
    void unsuccessfulCreateUserTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": }";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().all()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .log().all()
                .statusCode(400);
    }

    @DisplayName("Пойск пользователя")
    @Test
    void searchUserTest() {
        given()
                .log().uri()
                .log().all()
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .log().all()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }

    @DisplayName("Изменения пользователя")
    @Test
    void putUpdateUserTest() {
        String authData = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().all()
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .log().all()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", is(notNullValue()));
    }

    @DisplayName("Изменения имени пользователя")
    @Test
    void pathUpdateUserTest() {
        String authData = "{\"name\": \"Dmitry\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().all()
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .log().all()
                .statusCode(200)
                .body("name", is("Dmitry"))
                .body("updatedAt", is(notNullValue()));
    }

    @DisplayName("Удаление пользователя")
    @Test
    void deleteUserTest() {

        given()
                .contentType(JSON)
                .log().uri()
                .log().all()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .log().all()
                .statusCode(204);
    }
}
