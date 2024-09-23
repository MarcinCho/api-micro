package com.marcincho.librarysearch.service;

import com.marcincho.librarysearch.entity.Book;

import java.util.List;

public interface IBookService {

    List<Book> fetchBooksByTitle();

    List<Book> fetchBooksByAuthor();

    Book fetchBookById(Long id);

    Book fetchBookByKey(String key);

    void deleteBook(Long id);

    Book updateBook(Book book);

    Book createBook(Book book);

}
