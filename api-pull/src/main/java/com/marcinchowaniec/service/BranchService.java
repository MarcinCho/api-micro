package com.marcinchowaniec.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marcinchowaniec.entity.Branch;
import com.marcinchowaniec.entity.Repo;
import com.marcinchowaniec.repository.BranchRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BranchService {

    private static final Logger logger = LoggerFactory.getLogger(BranchService.class);

    @Inject
    BranchRepository branchRepository;

    @Transactional
    public Branch saveBranch(Branch branch, Repo repo) {
        logger.info("Trying to save " + repo.name);
        branch.repo = repo;
        branchRepository.getEntityManager().merge(branch);
        return branch;
    }

}
