package com.marcinchowaniec.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@ApplicationScoped
@Entity
@Table(name = "repo_branch")
public class Branch extends PanacheEntity {
    public String name;
    public String sha;
    public String repoName;
    public String username;
    public String url;
}
