package com.marcincho.librarysearch.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcincho.librarysearch.DTOs.AuthorDto;
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

    public Optional<Author> fetchAuthor(String name) {
        try {
            return Optional.of(authorRepository.findByName(name).getFirst());
        } catch (NoSuchElementException e) {
            try {
                var response = ApiClient.requestQuery(QueryCreator.author(name));
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false);
                JsonNode jsonNode = mapper.readTree(response);
                String dock = jsonNode.get("docs").toString();
                List<Author> authors = mapper.readValue(dock, new TypeReference<List<Author>>() {
                });
                authorRepository.save(authors.getFirst());
                return Optional.of(authors.getFirst());
            } catch (JsonProcessingException ex) {
                logger.error(ex.getMessage());
                return null;
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
    public boolean updateAuthor(AuthorDto author) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAuthor'");
    }

    @Override
    public Author createAuthor(AuthorDto author) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAuthor'");
    }

}
