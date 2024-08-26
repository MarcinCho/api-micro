package com.marcinchowaniec.repository;

import java.util.Optional;

import com.marcinchowaniec.entity.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Optional<User> findByLogin(String login) {
        return find("login", login).singleResultOptional();
    }

    public long deleteByLogin(String login) {
        return delete("login", login);
    }

}
