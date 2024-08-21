package com.marcinchowaniec.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@ApplicationScoped
@Entity
public class GithubUser extends PanacheEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(name = "user_id")
    // public Long id;
    @JsonProperty("id")
    // @Column(unique = true)
    public Long userId;
    @Column(unique = true)
    public String login;
    // public String node_id;
    public String avatar_url;
    // public String gravatar_id;
    public String url;
    public String html_url;
    // public String followers_url;
    // public String following_url;
    // public String gists_url;
    // public String starred_url;
    // public String subscriptions_url;
    // public String organizations_url;
    public String repos_url;
    // public String events_url;
    // public String received_events_url;
    // public String type;
    // public boolean site_admin;
    public String name;
    // public String company;
    // public String blog;
    // public String location;
    // public String email;
    // public boolean hireable;
    // public String bio;
    // public String twitter_username;
    // public int public_repos;
    // public int public_gists;
    // public int followers;
    // public int following;
    // public Date created_at;
    // public Date updated_at;
    // @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, targetEntity = UserRepo.class)
    public Set<UserRepo> repos;
}
