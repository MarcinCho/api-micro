package com.marcinchowaniec.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.dto.InfoResponseDto;
import com.marcinchowaniec.dto.RepoListDto;
import com.marcinchowaniec.entity.Repo;
import com.marcinchowaniec.service.RepoService;

import jakarta.inject.Inject;
import jakarta.resource.ResourceException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/repo")
public class RepoController {

    @Inject
    RepoService repoService;

    private static final Logger logger = LoggerFactory.getLogger(RepoService.class);

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<RepoListDto> getUserWithRepos(@PathParam("username") String username) {
        try {
            logger.info("Repo GET method invoked.");
            List<Repo> repos = repoService.listReposFromClient(username);
            if (repos.isEmpty()) {
                return ResponseBuilder
                        .ok(new RepoListDto("No repositories found for " + username, null),
                                MediaType.APPLICATION_JSON)
                        .build();
            }
            var repoResponse = new RepoListDto(username, repos);
            return ResponseBuilder.ok(repoResponse, MediaType.APPLICATION_JSON).build();

        } catch (BadRequestException e) {
            return ResponseBuilder
                    .ok(new RepoListDto("Something is wrong with requested username :" + username, null),
                            MediaType.APPLICATION_JSON)
                    .build();
        } catch (NotFoundException e) {
            return ResponseBuilder
                    .ok(new RepoListDto("Username: " + username + " not found in GH database", null),
                            MediaType.APPLICATION_JSON)
                    .build();
        }

    }

    @GET
    @Path("/single/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<RepoListDto> getSingleRepoByName(@PathParam("name") String name) throws NotFoundException {
        try {
            logger.info("Repo single GET method invoked.");
            Repo repo = repoService.singleRepo(name).orElseThrow(NotFoundException::new);
            return ResponseBuilder.ok(new RepoListDto("Repo name: " + name, List.of(repo)), MediaType.APPLICATION_JSON)
                    .build();
        } catch (NotFoundException e) {
            return ResponseBuilder
                    .ok(new RepoListDto("Repo name: " + name + " not found", null), MediaType.APPLICATION_JSON).build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<Repo> postSingleRepo(Repo repo) throws ResourceException {
        logger.info("Repo POST method invoked.");
        Optional<Repo> db_repo = repoService.singleRepo(repo.name);
        if (!db_repo.isEmpty()) {
            return ResponseBuilder.ok(db_repo.get(), MediaType.APPLICATION_JSON).build();
        } else {
            logger.info("About to create a new single repo");
            return ResponseBuilder.ok(repo, MediaType.APPLICATION_JSON).header("created", true).build();
        }
    }

    @DELETE
    @Produces("application/json")
    @Path("/id/{id}")
    public Response deleteRepoById(@PathParam("id") Long id) throws NotFoundException {
        logger.info("Repo DELETE by id method invoked.");
        boolean deleted = repoService.deleteRepoById(id);

        if (deleted) {
            return Response.status(200).entity(
                    new InfoResponseDto(200, "Repo with Id: " + id + " was deleted from internal db.", new Date()))
                    .build();
        }
        return Response.status(418)
                .entity(new InfoResponseDto(418, "Repo with Id: " + id + " was deleted from internal db.", new Date()))
                .build();

    }

    @DELETE
    @Produces("application/json")
    @Path("/login/{login}")
    public RestResponse<InfoResponseDto> deleteRepoByLogin(@PathParam("login") String login) throws NotFoundException {
        logger.info("-------------- Repo DELETE by login method invoked. --------------");
        logger.info("Deleting repos for user: " + login);
        Long deleted = repoService.deleteRepoByLogin(login);
        logger.info("Deleted " + deleted + " repos for user: " + login);
        if (deleted > 0) {
            logger.info("Deleted repos for user: " + login + " accepted");
            logger.info("------------------------------------------------------------------");
            return ResponseBuilder
                    .accepted(
                            new InfoResponseDto(202, deleted + " repos of user " + login, new Date()))
                    .build();
        }
        logger.info("Deleted repos for user: " + login + " not found");
        logger.info("------------------------------------------------------------------");
        return ResponseBuilder
                .ok(new InfoResponseDto(404,
                        "Repos for user " + login + " was NOT deleted from internal db. \n Was not found in DB",
                        new Date()))
                .build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Repo updateSingleRepo(Repo repo) {
        logger.info("Repo PUT method invoked.");
        Repo db_repo = repoService.findById(repo.id);
        if (db_repo != null) {
            db_repo.url = repo.url;
            repoService.saveUserRepo(db_repo);
            return repo;
        }
        return null;
    }
}