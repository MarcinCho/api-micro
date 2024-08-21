package com.marcinchowaniec.controller;

import java.util.List;
import java.util.Optional;

import com.marcinchowaniec.dto.UserReposDTO;
import com.marcinchowaniec.entity.GithubUser;
import com.marcinchowaniec.entity.UserRepo;
import com.marcinchowaniec.httpClient.GithubHttpClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/gh")
public class GithubApi {

    @Inject
    GithubHttpClient githubHttpClient;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from GH Controller";
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<GithubUser> getUsernameResponse(@PathParam("username") String username) {
        return githubHttpClient.getGithubUser(username);
    }

    @GET
    @Path("/repos/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<List<UserRepo>> getUsernameRepos(@PathParam("username") String username) {
        return githubHttpClient.getUserRepos(username);
    }
}
