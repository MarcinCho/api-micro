package com.marcinchowaniec.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.entity.GithubUser;
import com.marcinchowaniec.entity.UserRepo;
import com.marcinchowaniec.httpClient.GithubHttpClient;
import com.marcinchowaniec.repository.GithubUserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GithubUserService {

    @Inject
    GithubUserRepository repositoryRepoOwner;

    @Inject
    UserRepoService userRepoService;

    @Inject
    GithubHttpClient githubHttpClient;

    private static final Logger logger = LoggerFactory.getLogger(GithubUserService.class);

    @Transactional
    public void saveRepoOwner(GithubUser repoOwner) {
        logger.info("Saving " + repoOwner.login + " to internal DB.");
        repositoryRepoOwner.getEntityManager().merge(repoOwner);
    }

    public Optional<GithubUser> getRepoOwnerByLogin(String login) {
        logger.info("Pulling -> " + login + " from internal DB");
        Optional<GithubUser> user = repositoryRepoOwner.findByLogin(login);
        if (user.isPresent()) {
            return user;
        } else {
            return githubHttpClient.getGithubUser(login);
        }
    }

    public boolean checkRepoOwnerByLogin(String login) {
        logger.info("Checkin if -> " + login + " exist in DB");
        return repositoryRepoOwner.findByLogin(login).isPresent();
    }

    public GithubUser getRepoOwnerWithRepos(String username) {
        GithubUser user = getRepoOwnerByLogin(username.toLowerCase()).get();
        if (!user.repos.isEmpty()) {
            logger.info("Repositories are already added to user: " + username);
            return user;
        }
        List<UserRepo> userRepos = userRepoService.listReposFromClient(username);
        user.repos.addAll(userRepos);
        saveRepoOwner(user);
        return user;
    }
}
