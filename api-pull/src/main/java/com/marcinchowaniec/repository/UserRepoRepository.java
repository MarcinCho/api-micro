package com.marcinchowaniec.repository;

import com.marcinchowaniec.entity.UserRepo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepoRepository implements PanacheRepository<UserRepo> {

}
