package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class LoginTests {

    @Test
    void succesfulLoginTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; //BAD PRACTICE!

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/succes-login-schema.json"))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void negativeLoginNoContentTypeTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; //BAD PRACTICE!

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));


    }
}
