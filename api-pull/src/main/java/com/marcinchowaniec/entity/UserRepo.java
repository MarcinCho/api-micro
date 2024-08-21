package com.marcinchowaniec.entity;

import java.util.Date;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@ApplicationScoped
@Entity
public class UserRepo extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "user_repo_id", referencedColumnName = "userId", nullable = true)
    public GithubUser owner;
    public String name;
    public String url;
    public Date created_at;
    public Date updated_at;

}
