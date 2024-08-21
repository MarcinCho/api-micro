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
import com.marcinchowaniec.entity.GithubUser;
import com.marcinchowaniec.entity.UserRepo;
import com.marcinchowaniec.service.GithubUserService;
import com.marcinchowaniec.service.UserRepoService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GithubHttpClient {

    @Inject
    GithubUserService githubUserService;

    @Inject
    UserRepoService userRepoService;

    private static final Logger logger = LoggerFactory.getLogger(GithubHttpClient.class);

    // @Transactional
    public Optional<GithubUser> getGithubUser(String username) {
        if (githubUserService.checkRepoOwnerByLogin(username)) {
            return githubUserService.getRepoOwnerByLogin(username);
        } else {
            System.out.println("Wanting to grab " + username);
            String url = String.format("https://api.github.com/users/%s", username);

            HttpClient client = HttpClient.newBuilder().build();
            try {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                System.out.println(response.body());
                ObjectMapper ObjectMapper = new ObjectMapper().configure(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false);
                GithubUser repoOwner = ObjectMapper.readValue(response.body(), GithubUser.class);
                githubUserService.saveRepoOwner(repoOwner);
                return Optional.of(repoOwner);
            } catch (IOException | InterruptedException e) {
                System.err.println("Are we catching this ??? " + e.getMessage());
                return null;
            }
        }

    }

    // @Transactional
    public Optional<List<UserRepo>> getUserRepos(String username) {
        if (!githubUserService.checkRepoOwnerByLogin(username)) {
            logger.info("Repos of not saved user, getting userinfo");
            this.getGithubUser(username);
        }
        logger.info("Pulling repositories from Github " + username + " account");
        String url = String.format("https://api.github.com/users/%s/repos", username);
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
            List<UserRepo> userRepos = mapper.readValue(response.body(), new TypeReference<List<UserRepo>>() {
            });
            System.out.println("does it work?");
            System.out.println(userRepos);
            userRepos.parallelStream().forEach(userRepo -> userRepoService.saveUserRepo(userRepo));

            return Optional.of(userRepos);

        } catch (IOException | InterruptedException e) {
            System.err.println("Are we catching this ??? " + e.getMessage());
            return null;
        }

    }
}
