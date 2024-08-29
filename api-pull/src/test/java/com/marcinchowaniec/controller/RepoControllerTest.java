package com.marcinchowaniec.controller;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.when;

@QuarkusTest
public class RepoControllerTest {

    // @InjectMock
    // private RepoController repoController;

    @Test
    void testDeleteRepoById() {
        when().delete("repo/id/557647487").then().statusCode(200);
    }

    @Test
    void testDeleteRepoByLogin() {

    }

    @Test
    void testGetSingleRepoByName() {
        when().get("repo/single/api-micro").then().assertThat().statusCode(200);
    }

    @Test
    void testGetUserWithRepos() {
        when().get("repo/marcincho").then().assertThat().statusCode(200);
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
