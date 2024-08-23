package com.marcinchowaniec.entity;

import java.util.HashSet;
import java.util.Set;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@ApplicationScoped
@Entity
public class GithubUser extends PanacheEntity {

    @Column(unique = true)
    public String login;
    public String avatar_url;
    public String url;
    public String html_url;
    public String repos_url;
    public String name;
    @OneToMany(cascade = CascadeType.ALL)
    public Set<UserRepo> repos = new HashSet<>();

    public void setLogin(String login) {
        this.login = login.toLowerCase();
    }

}
