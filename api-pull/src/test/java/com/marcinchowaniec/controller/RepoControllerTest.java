package com.marcinchowaniec.controller;

import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import io.restassured.response.Response;

@QuarkusTest
public class RepoControllerTest {

    // @InjectMock
    // private RepoController repoController;

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
        String reqBody = """
                {
                    "id" : 23232323,
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

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("marcincho", response.jsonPath().getString("login"));

    }

    // @Test
    // @Disabled
    // void testPostSingleRepo() {
    // }

    // @Test
    // @Disabled
    // void testUpdateSingleRepo() {

    // }

    // @Mock
    // private RepoService repoService;

    // @InjectMocks
    // private RepoController repoController;

    // @BeforeEach
    // public void setup() {
    // MockitoAnnotations.openMocks(this);
    // }

    // @Test
    // public void testDeleteRepoById_RepoDeleted() {
    // // Arrange
    // Long id = 1L;
    // when(repoService.deleteRepoById(id)).thenReturn(true);

    // // Act
    // Response response = repoController.deleteRepoById(id);

    // // Assert
    // Assertions.assertEquals(200, response.getStatus());
    // InfoResponseDto responseEntity = (InfoResponseDto) response.getEntity();
    // Assertions.assertEquals(200, responseEntity.status_code());
    // Assertions.assertEquals("Repo with Id: " + id + " was deleted from internal
    // db.", responseEntity.info());
    // }

    // @Test
    // public void testDeleteRepoById_RepoNotFound() {
    // // Arrange
    // Long id = 1L;
    // when(repoService.deleteRepoById(id)).thenReturn(false);

    // // Act
    // Response response = repoController.deleteRepoById(id);

    // // Assert
    // Assertions.assertEquals(418, response.getStatus());
    // InfoResponseDto responseEntity = (InfoResponseDto) response.getEntity();
    // Assertions.assertEquals(418, responseEntity.status_code());
    // Assertions.assertEquals("Repo with Id: " + id + " was deleted from internal
    // db.", responseEntity.info());
    // }

}
