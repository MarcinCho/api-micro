package com.marcincho.librarysearch.service.impl;

import com.marcincho.librarysearch.entity.Book;
import com.marcincho.librarysearch.service.IBookService;

import java.util.List;

public class BookServiceImpl implements IBookService {
    @Override
    public List<Book> fetchBooksByTitle() {
        return List.of();
    }

    @Override
    public List<Book> fetchBooksByAuthor() {
        return List.of();
    }

    @Override
    public Book fetchBookById(Long id) {
        return null;
    }

    @Override
    public Book fetchBookByKey(String key) {
        return null;
    }

    @Override
    public void deleteBook(Long id) {

    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public Book createBook(Book book) {
        return null;
    }
}
