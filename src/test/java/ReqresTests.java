import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ReqresTests {

    @Test
    void singleUserTest() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200);
    }

    @Test
    void singleUserNotFoundTest() {
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void createUserTest() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\"," +
                        "\"job\": \"leader\"}")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", Matchers.is("morpheus"))
                .body("job", Matchers.is("leader"));
    }

    @Test
    void successfulRegistration() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"," +
                        "\"password\": \"pistol\"}")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token", Matchers.is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulRegistration() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"sydney@fife\"}")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error", Matchers.is("Missing password"));
    }

    @Test
    void successfulLogin() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"," +
                        "\"password\": \"cityslicka\"}")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", Matchers.is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulLogin() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"}")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", Matchers.is("Missing password"));
    }
}
