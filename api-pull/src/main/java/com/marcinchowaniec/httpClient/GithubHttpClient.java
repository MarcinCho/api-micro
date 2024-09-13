package com.marcinchowaniec.httpClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcinchowaniec.entity.Branch;

import com.marcinchowaniec.entity.User;
import com.marcinchowaniec.entity.Repo;
import com.marcinchowaniec.exceptions.UserNotFoundException;
import com.marcinchowaniec.mapper.BranchMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GithubHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(GithubHttpClient.class);

    @Transactional
    public Optional<User> getGithubUser(String username) throws UserNotFoundException {
        System.out.println("Wanting to grab " + username);
        String url = String.format("https://api.github.com/users/%s", username);

        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            logger.info("Pulling " + username + " from github");
            if (response.statusCode() == 403) {
                logger.info("User not found in GH API " + username);
                throw new UserNotFoundException("User not Found in Github api.");
            }
            var ObjectMapper = new ObjectMapper().configure(
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

    public List<Repo> getReposDto(String username) throws UserNotFoundException {
        logger.info("Pulling repositories as DTOs from Github " + username + " account");
        String url = String.format("https://api.github.com/users/%s/repos", username);
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                logger.info("User not found in GH API " + username);
                throw new UserNotFoundException("User not Found in Github api.");
            }
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
        } catch (UserNotFoundException e) {
            logger.error("Invoking User not found : " + e.getMessage());
            return null;
        }
    }

    public List<Repo> getReposFromApi(String username) throws UserNotFoundException {
        logger.info("Pulling repositories as DTOs from Github " + username + " account");
        String url = String.format("https://api.github.com/users/%s/repos", username);
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if ((response.statusCode() == 404) || (response.statusCode() == 403)) {
                logger.info("User not found in GH API " + username + " code " + response.statusCode());
                throw new UserNotFoundException("User not Found in Github api.");
            }
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

    public List<Branch> getRepoBranches(Repo repo, User user) throws NotFoundException {
        logger.info("Accessing branch information for " + repo.name + " of user " + user.login);
        String url = "https://api.github.com/repos/%s/%s/branches".formatted(user.login, repo.name);
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            switch (response.statusCode()) {
                case 404 -> {
                    logger.error("Branches not found in " + repo.name);
                    throw new NotFoundException();
                }
                case 403 -> {
                    logger.error("We wre forbidden to access " + repo.name);
                    throw new NotFoundException("Gh api forbidden");
                }
                default -> {
                    logger.info("Default case proceeding with branches");
                }

            }

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
            JsonNode jsonNode = mapper.readTree(response.body());
            String sha = jsonNode.get(0).get("commit").get("sha").asText();
            logger.info("Json node gives this one " + sha);
            List<Branch> branch = StreamSupport.stream(jsonNode.spliterator(), false)
                    .map(node -> BranchMapper.branchToEntityMapper(node, repo))
                    .collect(Collectors.toList());
            return branch;
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
