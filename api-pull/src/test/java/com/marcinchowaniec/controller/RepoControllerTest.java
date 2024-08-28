package com.marcinchowaniec.controller;

import org.junit.jupiter.api.Test;

import com.marcinchowaniec.repository.RepoRepository;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.when;

@QuarkusTest
public class RepoControllerTest {

//    @InjectMock
//    RepoRepository repoRepository;

    @Test
    void testDeleteRepoById() {
        when().delete("repo/id/557647487").then().statusCode(202);
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

    @Test
    void testPostSingleRepo() {
    }

    @Test
    void testUpdateSingleRepo() {

    }
}
