package com.marcincho.librarysearch.repository;

import com.marcincho.librarysearch.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContaining(String title);

    List<Book> findByAuthorName(String name);


}
