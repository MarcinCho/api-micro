package com.marcinchowaniec.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.dto.InfoResponseDto;
import com.marcinchowaniec.entity.Repo;
import com.marcinchowaniec.service.RepoService;

import jakarta.inject.Inject;
import jakarta.resource.ResourceException;
import jakarta.transaction.Transactional;
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

@Path("/repo")
public class RepoController {

    @Inject
    RepoService repoService;

    private static final Logger logger = LoggerFactory.getLogger(RepoService.class);

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<Repo>> getUserWithRepos(@PathParam("username") String username) throws NotFoundException {
        logger.info("Repo GET method invoked.");
        List<Repo> repos = repoService.listReposFromClient(username);
        return ResponseBuilder.ok(repos, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/single/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Repo> getSingleRepoByName(@PathParam("name") String name) throws NotFoundException {
        logger.info("Repo single GET method invoked.");
        Repo repo = repoService.singleRepo(name).orElseThrow(NotFoundException::new);
        return ResponseBuilder.ok(repo, MediaType.APPLICATION_JSON).build();

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
    public RestResponse<InfoResponseDto> deleteRepoById(@PathParam("id") Long id) throws NotFoundException {
        logger.info("Repo DELETE by id method invoked.");
        boolean deleted = repoService.deleteRepoById(id);

        if (deleted) {
            return ResponseBuilder
                    .accepted(new InfoResponseDto("Repo with Id: " + id + " was deleted from internal db.", new Date()))
                    .build();
        }
        return ResponseBuilder
                .ok(new InfoResponseDto("Repo with Id: " + id + " was NOT deleted from internal db.", new Date()))
                .build();
    }

    @DELETE
    @Produces("application/json")
    @Path("/login/{login}")
    public RestResponse<InfoResponseDto> deleteRepoByLogin(@PathParam("login") String login) throws NotFoundException {
        logger.info("Repo DELETE by login method invoked.");
        Long deleted = repoService.deleteRepoByLogin(login);

        if (deleted > 0) {
            return ResponseBuilder
                    .accepted(new InfoResponseDto(deleted + " repos of user " + login, new Date()))
                    .build();
        }
        return ResponseBuilder
                .ok(new InfoResponseDto("Repos for user " + login + " was NOT deleted from internal db.", new Date()))
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