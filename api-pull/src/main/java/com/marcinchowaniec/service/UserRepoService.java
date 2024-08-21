package com.marcinchowaniec.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.entity.UserRepo;
import com.marcinchowaniec.repository.UserRepoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserRepoService {

    @Inject
    UserRepoRepository userRepoRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserRepoService.class);

    // @Transactional
    // public void saveUserRepos(List<UserRepo> userRepos) {
    // logger.info("Saving " + userRepos.size() + " to internal DB");
    // userRepoRepository.persist(userRepos);
    // }

    @Transactional
    public void saveUserRepo(UserRepo userRepo) {
        logger.info("Saving " + userRepo.name + " to internal db");
    }

}
