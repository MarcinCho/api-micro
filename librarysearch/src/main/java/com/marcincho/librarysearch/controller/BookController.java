package com.marcincho.librarysearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcincho.librarysearch.entity.Book;
import com.marcincho.librarysearch.service.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "book/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)

@AllArgsConstructor
public class BookController {


    IBookService bookService;

    @GetMapping("title")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam("title") String title) {
        List<Book> books = null;
        try {
            books = bookService.fetchBooksByTitle(title);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String author) {
        List<Book> books = null;
        try {
            books = bookService.fetchBooksByAuthor(author);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(books);
    }


}
