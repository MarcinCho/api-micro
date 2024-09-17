package com.marcincho.librarysearch.service;

import java.util.List;
import java.util.Optional;

import com.marcincho.librarysearch.DTOs.AuthorDto;
import com.marcincho.librarysearch.entity.Author;

public interface IAuthorService {

    Optional<Author> fetchAuthor(String name);

    List<Author> fetchAuthors();

    void deleteAuthor(Long id);

    boolean updateAuthor(AuthorDto author);

    Author createAuthor(AuthorDto author);

}
