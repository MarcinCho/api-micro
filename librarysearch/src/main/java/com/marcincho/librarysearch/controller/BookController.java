package com.marcincho.librarysearch.controller;

import com.marcincho.librarysearch.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "book/", produces = "application/json", consumes = "application/json")

public class BookController {

    @GetMapping("title")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam("title") String title) {

        return null;
    }

    @GetMapping("author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("title") String title) {

        return null;
    }


}
