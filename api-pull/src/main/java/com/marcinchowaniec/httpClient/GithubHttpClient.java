package com.marcinchowaniec.httpClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.marcinchowaniec.entity.User;
import com.marcinchowaniec.entity.Repo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GithubHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(GithubHttpClient.class);

    @Transactional
    public Optional<User> getGithubUser(String username) {
        System.out.println("Wanting to grab " + username);
        String url = String.format("https://api.github.com/users/%s", username);

        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            logger.info("Pulling " + username + " from github");
            ObjectMapper ObjectMapper = new ObjectMapper().configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
            User repoOwner = ObjectMapper.readValue(response.body(), User.class);
            return Optional.of(repoOwner);
        } catch (IOException | InterruptedException e) {
            System.err.println("Are we catching this ??? " + e.getMessage());
            return null;
        } catch (NotFoundException e) {
            logger.error("Not found in Github API!");
            return null;
        }
    }

    public List<Repo> getReposDto(String username) {
        logger.info("Pulling repositories as DTOs from Github " + username + " account");
        String url = String.format("https://api.github.com/users/%s/repos", username);
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
            List<Repo> userRepos = mapper.readValue(response.body(), new TypeReference<List<Repo>>() {
            });
            return userRepos;
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid username info : " + e.getMessage());
            return null;
        }
    }

    public List<Repo> getReposFromApi(String username) {
        logger.info("Pulling repositories as DTOs from Github " + username + " account");
        String url = String.format("https://api.github.com/users/%s/repos", username);
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
            List<Repo> userRepos = mapper.readValue(response.body(), new TypeReference<List<Repo>>() {
            });
            return userRepos;
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid username info : " + e.getMessage());
            return null;
        }
    }
}
