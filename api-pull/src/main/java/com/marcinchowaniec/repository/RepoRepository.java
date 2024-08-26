package com.marcinchowaniec.repository;

import java.util.List;

import com.marcinchowaniec.entity.Repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RepoRepository implements PanacheRepository<Repo> {

    public List<Repo> reposByLogin(String username) {
        return find("user_login", username).list();
    }

    public Long deleteByUserLogin(String login) {
        return delete("user_login", login);
    }

}
