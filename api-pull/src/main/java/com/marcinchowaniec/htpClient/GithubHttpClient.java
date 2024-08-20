package com.marcinchowaniec.htpClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcinchowaniec.dto.RepoOwnerDTO;
import com.marcinchowaniec.entity.RepoOwner;
import com.marcinchowaniec.repository.RepositoryRepoOwner;
import com.marcinchowaniec.service.RepoOwnerService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GithubHttpClient {

    @Inject
    RepoOwnerService repoOwnerService;

    @Transactional
    public Optional<RepoOwner> getGithubUser(String username) {
        if (repoOwnerService.checkRepoOwnerByLogin(username)) {
            return repoOwnerService.getRepoOwnerByLogin(username);
        } else {
            System.out.println("Wanting to grab " + username);
            String url = String.format("https://api.github.com/users/%s", username);

            HttpClient client = HttpClient.newBuilder().build();
            try {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                System.out.println(response.body());
                ObjectMapper ObjectMapper = new ObjectMapper();
                RepoOwner repoOwner = ObjectMapper.readValue(response.body(), RepoOwner.class);
                repoOwnerService.saveRepoOwner(repoOwner);
                return Optional.of(repoOwner);
            } catch (IOException | InterruptedException e) {
                System.err.println("Are we catching this ??? " + e.getMessage());
                return null;
            }
        }

    }
}
