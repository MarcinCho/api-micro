package com.marcinchowaniec.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.entity.Branch;
import com.marcinchowaniec.entity.Repo;
import com.marcinchowaniec.entity.User;
import com.marcinchowaniec.exceptions.UserNotFoundException;
import com.marcinchowaniec.httpClient.GithubHttpClient;
import com.marcinchowaniec.repository.BranchRepository;
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
    BranchRepository branchRepository;

    @Inject
    GithubHttpClient githubHttpClient;

    @Inject
    BranchService branchService;

    @Inject
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(RepoService.class);

    @Transactional
    public void saveUserRepos(List<Repo> userRepos) {
        logger.info("Saving " + userRepos.size() + " to internal DB");
        repoRepository.getEntityManager().merge(userRepos);
    }

    @Transactional
    public void saveUserRepo(Repo userRepo, User user) {
        logger.info("Saving " + userRepo.name + " to internal db");
        userRepo.user = user;
        repoRepository.getEntityManager().merge(userRepo);
    }

    @Transactional
    public void saveUserRepo(Repo userRepo) {
        logger.info("Saving " + userRepo.name + " without username");
        repoRepository.persist(userRepo);
    }

    public void updateUserRepo(Repo userRepo) {
        repoRepository.getEntityManager().merge(userRepo);
    }

    public List<Repo> listRepos(String username) throws NotFoundException {
        logger.info("Checking if user " + username + " has any repos in internal DB");
        // List<Repo> repos = Repo.findByUserId(68085649);
        User user = userService.getUserByLogin(username).orElseThrow(NotFoundException::new);
        List<Repo> repos = repoRepository.reposByUserId(user.id).orElseThrow(NotFoundException::new);
        try {
            if (repos.isEmpty()) {
                repos = githubHttpClient.getReposFromApi(username);
                repos.forEach(repo -> saveUserRepo(repo, user));
                return repos;
            } else {
                return repos;
            }
        } catch (UserNotFoundException | NullPointerException e) {
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

    public List<Branch> getRepositoryBranches(Repo repo, User user) {
        List<Branch> branches = githubHttpClient.getRepoBranches(repo, user);
        branches.forEach(branch -> branchService.saveBranch(branch, repo));
        return branches;
    }

    public List<Branch> getAllUserBranches(String username) {
        User user = userService.getUserByLogin(username).orElseThrow(NotFoundException::new);
        List<Repo> repos = listRepos(username);
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<List<Branch>>> futureBranches = repos.stream()
                    .map(repo -> executor.submit(() -> getRepositoryBranches(repo, user)))
                    .collect(Collectors.toList());

            return futureBranches.stream().flatMap(futu -> {
                try {
                    return futu.get().stream();
                } catch (InterruptedException | ExecutionException ex) {
                    return null;
                }
            })
                    .collect(Collectors.toList());

        }

    }

    @Transactional
    public List<Branch> getAndSaveBranches(User user) {
        List<Branch> branches = getAllUserBranches(user.login);
        try {
            logger.info("Saving branches to db");
            branches.forEach(branch -> branchRepository.persist(branch));
            return branches;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return branches;
        }

    }
}
