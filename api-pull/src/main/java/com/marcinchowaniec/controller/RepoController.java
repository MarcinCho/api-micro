package com.marcinchowaniec.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.dto.InfoResponseDto;
import com.marcinchowaniec.dto.RepoListDto;
import com.marcinchowaniec.entity.Branch;
import com.marcinchowaniec.entity.Repo;
import com.marcinchowaniec.service.RepoService;

import jakarta.inject.Inject;
import jakarta.resource.ResourceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public Response getUserWithRepos(
            @PathParam("username") @NotNull(message = "Please provide username") String username) {
        try {
            logger.info("Repo GET method invoked.");
            List<Repo> repos = repoService.listRepos(username);
            if (repos.isEmpty()) {
                return Response.status(404)
                        .entity(new InfoResponseDto(404,
                                "No repositories found for " + username, new Date()))
                        .build();
            }
            var repoResponse = new RepoListDto(username, repos);
            return Response.ok(repoResponse, MediaType.APPLICATION_JSON).build();

        } catch (BadRequestException e) {
            return Response
                    .ok(new RepoListDto("Something is wrong with requested username :" + username,
                            null),
                            MediaType.APPLICATION_JSON)
                    .build();
        } catch (NotFoundException e) {
            return Response
                    .status(404)
                    .entity(new InfoResponseDto(404, "User " + username + " not found", new Date()))
                    .build();
        }

    }

    @GET
    @Path("/single/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSingleRepoByName(@PathParam("name") String name) throws NotFoundException {
        try {
            logger.info("Repo single GET method invoked.");
            Repo repo = repoService.singleRepo(name).orElseThrow(NotFoundException::new);
            return Response.status(200).entity(repo).build();
        } catch (NotFoundException e) {
            return Response.status(404)
                    .entity(new RepoListDto("Repo name: " + name + " not found", null)).build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postSingleRepo(@Valid Repo repo) throws ResourceException {
        logger.info("Repo POST method invoked.");
        Optional<Repo> db_repo = repoService.singleRepo(repo.name);
        if (!db_repo.isEmpty()) {
            return Response.ok(db_repo.get(), MediaType.APPLICATION_JSON).build();
        } else {
            logger.info("About to create a new single repo");
            repo.id = (long) (Math.random() * 100000000);
            repoService.saveUserRepo(repo);
            return Response.status(202).entity(repo).build();
        }
    }

    @DELETE
    @Produces("application/json")
    @Path("/id/{id}")
    public Response deleteRepoById(@PathParam("id") Long id) {
        logger.info("Repo DELETE by id method invoked.");
        boolean deleted = repoService.deleteRepoById(id);

        if (deleted) {
            return Response.status(200).entity(
                    new InfoResponseDto(200,
                            "Repo with Id: " + id + " was deleted from internal db.",
                            new Date()))
                    .build();
        }
        return Response.status(418)
                .entity(new InfoResponseDto(418,
                        "Repo with Id: " + id + " was not deleted from internal db.",
                        new Date()))
                .build();

    }

    @DELETE
    @Produces("application/json")
    @Path("/login/{login}")
    public Response deleteRepoByLogin(@PathParam("login") String login) throws NotFoundException {
        Long deleted = repoService.deleteRepoByLogin(login);
        logger.info("Deleted " + deleted + " repos for user: " + login);
        if (deleted > 0) {
            return Response
                    .status(200)
                    .entity(new InfoResponseDto(200, deleted + " repos of user " + login,
                            new Date()))
                    .build();
        }
        return Response
                .status(404).entity(new InfoResponseDto(404,
                "Repos for user " + login
                + " was NOT deleted from internal db. \n Was not found in DB",
                new Date()))
                .build();

    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Response updateSingleRepo(@Valid Repo repo) {
        logger.info("Repo PUT method invoked.");
        Repo db_repo = repoService.findById(repo.id);
        if (db_repo != null) {
            db_repo = repo;
            repoService.updateUserRepo(db_repo);
            return Response.status(200).entity(
                    repo)
                    .build();
        }
        return Response.status(404)
                .entity(new InfoResponseDto(404, "Something went wrong with updating " + repo.name,
                        new Date()))
                .build();
    }

    @GET
    @Path("/branches/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReposBranches(@PathParam("username") String username) {
        try {
            List<Branch> branches = repoService.getAllUserBranches(username);
            return Response.status(200)
                    .entity(branches).build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return Response.status(404)
                    .entity(new InfoResponseDto(404,
                            "Branches for username " + " not found." + e.getMessage(),
                            new Date()))
                    .build();
        }
    }
}
