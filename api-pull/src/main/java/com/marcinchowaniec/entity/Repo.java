package com.marcinchowaniec.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@ApplicationScoped
@Entity
@Table(name = "repo")
public class Repo extends PanacheEntityBase {

    @Id
    public Long id;

    @Column(name = "repo_name")
    public String name;

    @Column(name = "repo_url")
    public String url;

    public Date created_at;

    public Date updated_at;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "repo")
    public List<Branch> branches;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

}
