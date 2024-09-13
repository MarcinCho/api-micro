package com.marcinchowaniec.repository;

import java.util.List;
import java.util.Optional;

import com.marcinchowaniec.entity.Repo;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class RepoRepository implements PanacheRepository<Repo> {

    public Optional<List<Repo>> reposByLogin(String username) {
        return Optional.of(find("user_login", username).list());
    }

    public Long deleteByUserLogin(String login) {
        return delete("user_login", login);
    }

    public Optional<List<Repo>> reposByUserId(Long id) {
        String nativeQuery = "SELECT * FROM repo WHERE user_id =" + id;

        return Optional.of(Panache.getSession().createNativeQuery(nativeQuery, Repo.class).list());
    }

}
