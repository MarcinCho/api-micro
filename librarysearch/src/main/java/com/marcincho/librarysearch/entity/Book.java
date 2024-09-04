package com.marcincho.librarysearch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Book {

    @Id
    private String isbn;
    private String title;

}
