package com.alef.library.services;

import com.alef.library.entities.BookEntity;

import java.util.List;

public interface BookService {

    void saveBook(BookEntity book);

    void updateBook(String id, BookEntity book);

    BookEntity getBookById(String id);

    List<BookEntity> getAllBooks();

    void deleteBook(String id);
}
