package com.alef.library.services;

import com.alef.library.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {

    void saveAuthor(AuthorEntity author);

    void updateAuthor(String id, AuthorEntity author);

    AuthorEntity getAuthorById(String id);

    List<AuthorEntity> getAllAuthors();

    void deleteAuthor(String id);
}
