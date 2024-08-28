package com.marcinchowaniec.entity;

import java.util.Date;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@ApplicationScoped
@Entity
@Table(name = "gh_repo")
public class Repo extends PanacheEntityBase {

    @Id
    public Long id;
    public String user_login;
    public String name;
    public String url;
    public Date created_at;
    public Date updated_at;

}
