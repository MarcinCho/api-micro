package com.marcincho.librarysearch.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcincho.librarysearch.Utils.LibraryMappers;
import com.marcincho.librarysearch.Utils.QueryCreator;
import com.marcincho.librarysearch.apiClient.ApiClient;
import com.marcincho.librarysearch.entity.Book;
import com.marcincho.librarysearch.repository.AuthorRepository;
import com.marcincho.librarysearch.repository.BookRepository;
import com.marcincho.librarysearch.service.IAuthorService;
import com.marcincho.librarysearch.service.IBookService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class BookServiceImpl implements IBookService {


    private BookRepository bookRepository;
    private IAuthorService authorService;

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);


    @Override
    public List<Book> fetchBooksByTitle(String title) throws JsonProcessingException {
        List<Book> books = bookRepository.findByTitleContaining(title);
        if (books.isEmpty()) {
            String booksResponse = ApiClient.requestQuery(QueryCreator.title(title));
            return LibraryMappers.mapToBookList(booksResponse);
        }
        return bookRepository.findByTitleContaining(title);
    }

    @Override
    public List<Book> fetchBooksByAuthor(String author) throws JsonProcessingException {
        List<Book> books = bookRepository.findByAuthorName(author);
        if (books.isEmpty()) {
            String booksResponse = ApiClient.requestQuery(QueryCreator.bookByAuthor(author));
            List<Book> booksToDB = LibraryMappers.mapToBookList(booksResponse);
            booksToDB.forEach(book -> book.getAuthorKey().forEach(authorKey -> {
                try {
                    authorService.fetchAuthorByKey(authorKey);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }));
            bookRepository.saveAll(booksToDB);
            return LibraryMappers.mapToBookList(booksResponse);
        }
        return bookRepository.findByAuthorName(author);
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
