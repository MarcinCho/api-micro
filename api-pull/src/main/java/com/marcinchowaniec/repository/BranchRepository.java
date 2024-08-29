package com.marcinchowaniec.repository;

import com.marcinchowaniec.entity.Branch;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BranchRepository implements PanacheRepository<Branch> {

}
