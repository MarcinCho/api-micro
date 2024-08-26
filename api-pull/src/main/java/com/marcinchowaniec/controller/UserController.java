package com.marcinchowaniec.controller;

import java.util.Date;
import java.util.NoSuchElementException;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.dto.InfoResponseDto;
import com.marcinchowaniec.dto.UserDto;
import com.marcinchowaniec.entity.User;
import com.marcinchowaniec.service.RepoService;
import com.marcinchowaniec.service.UserService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/user")
public class UserController {

    @Inject
    UserService githubUserService;

    private static final Logger logger = LoggerFactory.getLogger(RepoService.class);

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUsernameResponse(@PathParam("username") String username) {
        logger.info("User GET method invoked.");
        try {
            User githubUser = githubUserService.getUserByLogin(username.toLowerCase()).get();
            return githubUser;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<UserDto> postUser(UserDto userDto) {
        logger.info("User POST method invoked.");
        if (!githubUserService.checkUserByLogin(userDto.login())) {
            githubUserService.getUserByLogin(userDto.login());
        }
        return ResponseBuilder.ok(userDto, MediaType.APPLICATION_JSON).build();
    }

    @Transactional
    @DELETE
    @Path("/{login}")
    public RestResponse<InfoResponseDto> deleteUser(@PathParam("login") String login) {
        logger.info("User DELETE method invoked.");
        Long resp = githubUserService.deleteUser(login);
        return ResponseBuilder
                .ok(new InfoResponseDto("Here there is a user id " + resp, new Date()), MediaType.APPLICATION_JSON)
                .build();
    }
}
