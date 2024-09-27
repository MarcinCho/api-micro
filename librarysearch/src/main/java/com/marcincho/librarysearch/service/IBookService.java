package com.marcincho.librarysearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcincho.librarysearch.entity.Book;

import java.util.List;

public interface IBookService {

    List<Book> fetchBooksByTitle(String title) throws JsonProcessingException;

    List<Book> fetchBooksByAuthor(String author) throws JsonProcessingException;

    Book fetchBookById(Long id);

    Book fetchBookByKey(String key);

    void deleteBook(Long id);

    Book updateBook(Book book);

    Book createBook(Book book);

}
