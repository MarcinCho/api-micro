package com.marcincho.librarysearch.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcincho.librarysearch.DTOs.AuthorDTO;
import com.marcincho.librarysearch.entity.Author;

public interface IAuthorService {

    Optional<Author> fetchAuthorByName(String name);

    List<Author> fetchAuthors();

    void deleteAuthor(Long id);

    boolean updateAuthor(AuthorDTO author);

    Author createAuthor(AuthorDTO author);

    Optional<Author> fetchAuthorByKey(String key) throws JsonProcessingException;
}
