package com.marcinchowaniec.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;

@ApplicationScoped
@Entity
public class Repository extends PanacheEntity {

    public String node_id;
    public String name;
    public String url;
    public String owner;

}
