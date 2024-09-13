package com.marcinchowaniec.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@ApplicationScoped
@Table(name = "gh_user")
@Entity
public class User extends PanacheEntityBase {

    @Id
    public Long id;

    @Column(unique = true, name = "user_login")
    @NotBlank
    public String login;

    @Column(name = "user_url")
    public String url;

    public String repos_url;

    @Column(name = "user_name")
    public String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public List<Repo> repos;

    public void setLogin(String login) {

        this.login = login.toLowerCase();
    }

}
