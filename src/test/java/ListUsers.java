
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ListUsers extends TestBase {
    @DisplayName("Успешная регистрация пользователя")
    @Test
    void successfulCreateUserTest(){
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is(notNullValue()));
    }
    @DisplayName("Не успешная регистрация пользователя")
    @Test
    void unsuccessfulCreateUserTest(){
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": }";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }
    @DisplayName("Пойск пользователя")
    @Test
    void searchUserTest(){
        given()
                .log().uri()

                .when()
                .get("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id" ,is (2))
                .body("data.email" ,is ("janet.weaver@reqres.in"))
                .body("data.first_name" ,is ("Janet"))
                .body("data.last_name" ,is ("Weaver"));
    }
    @DisplayName("Изменения пользователя")
    @Test
    void putUpdateUserTest(){
        String authData = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .put("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name" ,is ("morpheus"))
                .body("job" ,is ("zion resident"))
                .body("updatedAt" ,is (notNullValue()));
    }
    @DisplayName("Изменения имени пользователя")
    @Test
    void pathUpdateUserTest(){
        String authData = "{\"name\": \"Dmitry\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name" ,is ("Dmitry"))
                .body("updatedAt" ,is (notNullValue()));
    }
    @DisplayName("Удаление пользователя")
    @Test
    void deleteUserTest(){

        given()
                .contentType(JSON)
                .log().uri()

                .when()
                .delete("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }
}
