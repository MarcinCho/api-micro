package com.marcinchowaniec.repository;

import java.util.Optional;

import com.marcinchowaniec.entity.GithubUser;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GithubUserRepository implements PanacheRepository<GithubUser> {

    public Optional<GithubUser> findByLogin(String login) {
        return find("login", login).singleResultOptional();
    }

}
