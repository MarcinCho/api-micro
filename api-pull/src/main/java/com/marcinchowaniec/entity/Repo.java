package com.marcinchowaniec.entity;

import java.util.Date;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@ApplicationScoped
@Entity
@Table(name = "gh_repo")
public class Repo extends PanacheEntityBase {

    @Id
    public Long id;
    @NotBlank
    public String user_login;
    @NotBlank
    public String name;
    public String url;
    public Date created_at;
    public Date updated_at;

}
