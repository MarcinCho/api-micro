package com.marcinchowaniec.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.entity.RepoOwner;
import com.marcinchowaniec.repository.RepositoryRepoOwner;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RepoOwnerService {

    @Inject
    RepositoryRepoOwner repositoryRepoOwner;

    private static final Logger logger = LoggerFactory.getLogger(RepoOwnerService.class);

    @Transactional
    public void saveRepoOwner(RepoOwner repoOwner) {
        logger.info("Saving " + repoOwner.login + " to internal DB.");
        repositoryRepoOwner.persist(repoOwner);
    }

    public Optional<RepoOwner> getRepoOwnerByLogin(String login) {
        logger.info("Pulling -> " + login + " from internal DB");
        return repositoryRepoOwner.findByLogin(login);
    }

    public boolean checkRepoOwnerByLogin(String login) {
        logger.info("Checkin if -> " + login + " exist in DB");
        return repositoryRepoOwner.findByLogin(login).isPresent();
    }

}
