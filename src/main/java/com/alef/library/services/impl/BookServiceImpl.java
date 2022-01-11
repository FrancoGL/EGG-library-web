package com.alef.library.services.impl;

import com.alef.library.entities.BookEntity;
import com.alef.library.errors.ServiceError;
import com.alef.library.repositories.BookRepository;
import com.alef.library.services.BookService;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bRepository;

    @Autowired
    public BookServiceImpl(BookRepository bRepository) {
        this.bRepository = bRepository;
    }

    // ** Save ** //

    @Override
    @Transactional
    public void saveBook(BookEntity book) throws ServiceError {

        validation(book);

        book.setCurrentCopies(book.getCopies());
        book.setLentCopies(book.getCopies());

        bRepository.save(book);
    }

    // ** Update ** //

    @Override
    @Transactional
    public void updateBook(String id, BookEntity book) throws ServiceError {

        validation(book);

        book.setId(id);

        book.setCurrentCopies(book.getCopies());
        book.setLentCopies(book.getCopies());

        bRepository.save(book);
    }

    // ** Get Book By Id ** //

    @Override
    @Transactional(readOnly = true)
    public BookEntity getBookById(String id) throws ServiceError {

        BookEntity book = bRepository.findById(id).orElse(null);

        if(book == null) {
            throw new ServiceError("Book not found");
        }

        return book;
    }

    // ** Get All Books ** //

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooks() {
        return bRepository.findAll();
    }

    // ** Delete ** //

    @Override
    @Transactional
    public void deleteBook(String id) throws ServiceError {

        BookEntity entity = bRepository.findById(id).orElse(null);

        if(entity == null) {
            throw new ServiceError("Book not found");
        }

        if(entity.getDeleted()) {
            entity.setDeleted(!entity.getDeleted());
            bRepository.save(entity);
        } else {
            bRepository.deleteById(id);
        }
    }

    private void validation(BookEntity book) throws ServiceError {

        if(bRepository.existsBookEntityByTitle(book.getTitle())) {
            throw new ServiceError("The book already exists");
        }

        if(book.getIsbn().toString().length() < 10) {
            throw new ServiceError("Invalid ISBN");
        }

        if(book.getTitle().isBlank() || book.getTitle().length() < 5) {
            throw new ServiceError("The name is mandatory and has to contain unless five characters");
        }

        if(book.getPublicationYear().toString().length() < 4 || book.getPublicationYear() == null) {
            throw new ServiceError("The publication year is mandatory");
        }

        if(book.getCopies() < 0) {
            throw new ServiceError("Invalid number of copies");
        }

        if(!book.getImage().contains("jpg") && !book.getImage().contains("png")) {
            throw new ServiceError("Invalid image");
        }
    }
}
