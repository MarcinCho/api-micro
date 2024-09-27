package com.marcincho.librarysearch.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.marcincho.librarysearch.Utils.LibraryMappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcincho.librarysearch.DTOs.AuthorDTO;
import com.marcincho.librarysearch.Utils.QueryCreator;
import com.marcincho.librarysearch.apiClient.ApiClient;
import com.marcincho.librarysearch.entity.Author;
import com.marcincho.librarysearch.repository.AuthorRepository;
import com.marcincho.librarysearch.service.IAuthorService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class AuthorServiceImpl implements IAuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);
    private final AuthorRepository authorRepository;

    @Override

    public Optional<Author> fetchAuthorByName(String name) {
        try {
            return Optional.of(authorRepository.findByName(name).getFirst());
        } catch (NoSuchElementException e) {
            try {
                var response = ApiClient.requestQuery(QueryCreator.author(name));
                Author author = LibraryMappers.mapToAuthor(response, false);
                authorRepository.save(author);
                return Optional.of(author);
            } catch (JsonProcessingException ex) {
                logger.error(ex.getMessage());
                return Optional.empty();
            }
        }

    }

    @Override
    public List<Author> fetchAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public boolean updateAuthor(AuthorDTO author) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAuthor'");
    }

    @Override
    public Author createAuthor(AuthorDTO author) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAuthor'");
    }

    @Override
    public Optional<Author> fetchAuthorByKey(String key) throws JsonProcessingException {
        try {
            Author author = authorRepository.findByKey(key);
            if (author == null){
                logger.info("Trying to save {}", key);
                String authorApi = ApiClient.requestByKey(key);
                Author authorToDB = LibraryMappers.mapToAuthor(authorApi, true);
                authorRepository.save(authorToDB);
                return Optional.of(authorToDB);
            } else return Optional.empty();
        } catch (JsonProcessingException e) {
            logger.info("To catch {}", e.getMessage());
            return Optional.empty();
        }
    }

}
