package com.marcinchowaniec.controller;

import static org.hamcrest.CoreMatchers.containsString;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import io.restassured.response.Response;

@QuarkusTest
public class RepoControllerTest {

    @Test
    void testDeleteRepoById() {
        when().delete("repo/id/557647487").then().statusCode(200);
    }

    @Test
    void testDeleteRepoByIdFail() {
        when().delete("repo/id/557").then().statusCode(418);
    }

    @Test
    void testDeleteRepoByLoginFail() {
        when().delete("repo/login/mawswswsho").then().assertThat().statusCode(404)
                .body(containsString("NOT deleted from"));
    }

    @Test
    void testDeleteRepoByLogin() {
        when().delete("repo/login/marcincho").then().assertThat().statusCode(200)
                .body(containsString("repos of user"));
    }

    @Test
    void testGetSingleRepoByName() {
        when().get("repo/single/leveldb").then().assertThat().statusCode(200);
    }

    @Test
    void testGetSingleRepoByNameInvalid() {
        when().get("repo/single/leveldbswswsws").then().assertThat().statusCode(404);
    }

    @Test
    void testGetUserWithRepos() {
        when().get("repo/marcincho").then().assertThat().statusCode(200).body(containsString("marcincho"));
    }

    @Test
    void testGetUserWithReposInvalid() {
        when().get("repo/aaaasswswswdwdwdwd").then().assertThat().statusCode(404).body(containsString("found"));
    }

    @Test
    void testPostSingleRepoInvalid() {
        String reqBody = """
                {
                    "login": "marcincho",
                    "name": "marcinchso"
                }
                """;

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(reqBody)
                .when()
                .post("repo")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(400, response.statusCode());

    }

    @Test
    void testPostSingleRepo() {
        // Here I'll have to add som json type
        JSONObject testObject = new JSONObject();
        testObject.put("user_login", "TestUser");
        testObject.put("name", "TestRepo");
        testObject.put("url", "test.url");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(testObject.toString())
                .when()
                .post("repo")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(202, response.statusCode());
        Assertions.assertEquals("TestUser", response.jsonPath().getString("user_login"));

    }

}
