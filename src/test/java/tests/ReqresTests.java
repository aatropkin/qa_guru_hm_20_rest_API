package tests;




import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Test for REQRES_IN")
public class ReqresTests {

    @Test
    @DisplayName("Single user not found")
    void singleUserNotFoundTest() {

        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    @DisplayName("Create new user login + email")
    void createSuccessfulNewUserTest() {
        String body = "{\"name\": \"Sasha\", \"job\": \"autotester\"}"; //BAD PRACTICE!

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Sasha"))
                .body("job", is("autotester"));
    }

    @Test
    @DisplayName("Get information about user")
    void getInformationAboutUserTest() {
        int id = 5; //BAD PRACTICE!

        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/users/" + id)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/succes-getUser-schema.json"))
                .body("data.first_name", is("Charles"))
                .body("data.last_name", is("Morris"));
    }

    @Test
    @DisplayName("Registration without password")
    void registerWithoutPasswordTest() {
        String body = "{ \"email\": \"peter@klaven\" }"; //BAD PRACTICE!

        given()
                .log().uri()
                .log().method()
                .when()
                .contentType(JSON)
                .body(body)
                .post("https://reqres.in/api/login/")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Update information about user")
    void updateInformationAboutUserTest() {
        String body = "{ \"name\": \"QA\", \"job\": \"GURU\" }"; //BAD PRACTICE!
        String response = given()
                .log().uri()
                .log().method()
                .when()
                .contentType(JSON)
                .body(body)
                .patch("https://reqres.in/api/user/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response().asString();
        assertThat(response)
                .contains("\"name\":\"QA\"")
                .contains("\"job\":\"GURU\"");

    }

}
