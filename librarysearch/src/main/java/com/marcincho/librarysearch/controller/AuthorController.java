package com.marcincho.librarysearch.controller;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcincho.librarysearch.DTOs.ResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcincho.librarysearch.entity.Author;
import com.marcincho.librarysearch.service.IAuthorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "author/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthorController {

    IAuthorService authorService;

    @GetMapping()
    public ResponseEntity<Optional<Author>> getAuthor(@RequestParam String name) {
        return ResponseEntity.ok(authorService.fetchAuthorByName(name));
    }

    @GetMapping("all")
    public ResponseEntity<List<Author>> getAuthors() {
        List<Author> authors = authorService.fetchAuthors();
        if (authors.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(authors);
        }
    }

    @DeleteMapping()
    public ResponseEntity<ResponseDTO> deleteAuthor(@RequestParam Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("key")
    public ResponseEntity<Author> getByKey(@RequestParam String key) {
        Optional<Author> author;
        try {
            author = authorService.fetchAuthorByKey(key);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return author.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
