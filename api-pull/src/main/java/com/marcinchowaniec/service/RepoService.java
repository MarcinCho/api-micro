package com.marcinchowaniec.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.entity.Repo;
import com.marcinchowaniec.exceptions.UserNotFoundException;
import com.marcinchowaniec.httpClient.GithubHttpClient;
import com.marcinchowaniec.repository.RepoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class RepoService {

    @Inject
    RepoRepository repoRepository;

    @Inject
    GithubHttpClient githubHttpClient;

    private static final Logger logger = LoggerFactory.getLogger(RepoService.class);

    @Transactional
    public void saveUserRepos(List<Repo> userRepos) {
        logger.info("Saving " + userRepos.size() + " to internal DB");
        repoRepository.getEntityManager().merge(userRepos);
    }

    @Transactional
    public void saveUserRepo(Repo userRepo, String username) {
        logger.info("Saving " + userRepo.name + " to internal db");
        userRepo.user_login = username;
        repoRepository.getEntityManager().merge(userRepo);
    }

    @Transactional
    public void saveUserRepo(Repo userRepo) {
        logger.info("Saving " + userRepo.name + " without username");
        repoRepository.persist(userRepo);
    }

    public List<Repo> listReposFromClient(String username) throws NotFoundException {
        logger.info("Checking if user " + username + " has any repos in internal DB");
        List<Repo> repos = repoRepository.reposByLogin(username).orElseThrow(NotFoundException::new);
        try {
            if (repos.isEmpty()) {
                repos = githubHttpClient.getReposFromApi(username);
                repos.forEach(repo -> saveUserRepo(repo, username));
                return repos;
            } else {
                return repos;
            }
        } catch (UserNotFoundException e) {
            throw new NotFoundException();
        }
    }

    public Optional<Repo> singleRepo(String repoName) {
        return repoRepository.find("name", repoName).firstResultOptional();
    }

    @Transactional
    public boolean deleteRepoById(Long id) {
        return repoRepository.deleteById(id);
    }

    @Transactional
    public Long deleteRepoByLogin(String login) {
        return repoRepository.deleteByUserLogin(login);
    }

    public boolean updateRepo(Repo repo) {
        repoRepository.getEntityManager().merge(repo);
        return true;
    }

    public Repo findById(Long id) {
        return repoRepository.findById(id);
    }

}
