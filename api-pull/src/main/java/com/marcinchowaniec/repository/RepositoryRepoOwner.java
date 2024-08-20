package com.marcinchowaniec.repository;

import java.util.Optional;

import com.marcinchowaniec.entity.RepoOwner;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RepositoryRepoOwner implements PanacheRepository<RepoOwner> {

    public Optional<RepoOwner> findByLogin(String login) {
        return find("login", login).singleResultOptional();
    }

}
