package com.marcincho.librarysearch.controller;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("library/")
@AllArgsConstructor
public class LibraryController {

    IAuthorService authorService;

    @GetMapping("author")
    public ResponseEntity<Optional<Author>> getAuthor(@RequestParam String name) {
        return ResponseEntity.ok(authorService.fetchAuthor(name));
    }

    @GetMapping("author/all")
    public ResponseEntity<List<Author>> getAuthors() {
        List<Author> authors = authorService.fetchAuthors();
        if (authors.isEmpty()) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.ok(authors);
        }
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping()
    public ResponseEntity deleteAuthor(@RequestParam Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.accepted().build();
    }

}
