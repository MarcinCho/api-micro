package com.marcinchowaniec.controller;

import java.util.NoSuchElementException;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import com.marcinchowaniec.entity.GithubUser;
import com.marcinchowaniec.service.GithubUserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/gh")
public class GithubApi {

    @Inject
    GithubUserService githubUserService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from GH Controller";
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public GithubUser getUsernameResponse(@PathParam("username") String username) {
        try {
            GithubUser githubUser = githubUserService.getRepoOwnerByLogin(username.toLowerCase()).get();
            return githubUser;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @GET
    @Path("/repo/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<GithubUser> getUserWithRepos(@PathParam("username") String username) {
        GithubUser user = githubUserService.getRepoOwnerWithRepos(username.toLowerCase());
        return ResponseBuilder.ok(user, MediaType.APPLICATION_JSON).build();
    }
}
