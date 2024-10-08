package com.marcinchowaniec.controller;

import java.util.Date;

import org.hibernate.id.IdentifierGenerationException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.dto.InfoResponseDto;
import com.marcinchowaniec.dto.UserDto;
import com.marcinchowaniec.entity.User;
import com.marcinchowaniec.mapper.UserMapper;
import com.marcinchowaniec.service.RepoService;
import com.marcinchowaniec.service.UserService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserController {

    @Inject
    UserService githubUserService;

    private static final Logger logger = LoggerFactory.getLogger(RepoService.class);

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsernameResponse(@PathParam("username") String username) {
        try {
            User githubUser = githubUserService.getUserByLogin(username.toLowerCase())
                    .orElseThrow(NotFoundException::new);
            return Response.status(200).entity(githubUser).build();
        } catch (NotFoundException | IllegalArgumentException e) {
            return Response.status(404).entity(new InfoResponseDto(404, username + " not found.", new Date())).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(UserDto userDto) {
        try {
            logger.info("User POST method invoked.");
            if (githubUserService.checkUserByLogin(userDto.login())) {
                return Response.accepted(githubUserService.getUserByLogin(userDto.login())).build();
            } else {
                User newUser = UserMapper.mapToUser(userDto);
                githubUserService.saveUser(newUser);
                return Response.status(201).entity(newUser).build();
            }
        } catch (NotFoundException e) {
            return Response.status(404).entity(new InfoResponseDto(404, "username not found", new Date())).build();
        } catch (IdentifierGenerationException e) {
            return Response.status(400).entity(new InfoResponseDto(400, e.getMessage(), new Date())).build();
        }

    }

    @Transactional
    @DELETE
    @Path("/{username}")
    public RestResponse<InfoResponseDto> deleteUser(@PathParam("username") String username) {
        logger.info("User DELETE method invoked.");
        Long resp = githubUserService.deleteUser(username);
        return ResponseBuilder
                .ok(new InfoResponseDto(200, "Here there is a user id " + resp, new Date()), MediaType.APPLICATION_JSON)
                .build();
    }
}
