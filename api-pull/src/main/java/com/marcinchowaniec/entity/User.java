package com.marcinchowaniec.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@ApplicationScoped
@Table(name = "gh_users")
@Entity
public class User extends PanacheEntityBase {

    @Id
    public Long id;
    @Column(unique = true)
    @NotBlank
    public String login;
    public String url;
    public String repos_url;
    public String name;
    // @OneToMany(cascade = CascadeType.ALL)
    // public Set<Repo> repos = new HashSet<>();

    public void setLogin(String login) {

        this.login = login.toLowerCase();
    }

}
