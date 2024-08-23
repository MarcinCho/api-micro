package com.marcinchowaniec.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.entity.UserRepo;
import com.marcinchowaniec.httpClient.GithubHttpClient;
import com.marcinchowaniec.repository.UserRepoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserRepoService {

    @Inject
    UserRepoRepository userRepoRepository;

    @Inject
    GithubHttpClient githubHttpClient;

    private static final Logger logger = LoggerFactory.getLogger(UserRepoService.class);

    public void saveUserRepos(List<UserRepo> userRepos) {
        logger.info("Saving " + userRepos.size() + " to internal DB");
        userRepoRepository.getEntityManager().merge(userRepos);
    }

    @Transactional
    public void saveUserRepo(UserRepo userRepo, String username) {
        logger.info("Saving " + userRepo.name + " to internal db");
        userRepoRepository.getEntityManager().merge(userRepo);
        userRepoRepository.persistAndFlush(userRepo);
    }

    public List<UserRepo> listReposFromClient(String username) {
        return githubHttpClient.getReposFromApi(username);
    }

}
