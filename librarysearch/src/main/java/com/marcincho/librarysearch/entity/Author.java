package com.marcincho.librarysearch.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "author")
@EqualsAndHashCode(callSuper = false)
public class Author extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name")
    private String name;

    @JsonAlias({"work_count"})
    @Column(name = "work_count")
    private Long workCount;

    @Column(name = "page_key")
    private String key;

    @JsonAlias({"birth_date"})
    @Column(name = "birth_date")
    private String birthDate;

    @JsonAlias({"death_date"})
    @Column(name = "death_date")
    private String deathDate;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Book> books;

}
