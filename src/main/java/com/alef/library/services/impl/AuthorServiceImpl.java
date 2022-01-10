package com.alef.library.services.impl;

import com.alef.library.entities.AuthorEntity;
import com.alef.library.errors.ServiceError;
import com.alef.library.repositories.AuthorRepository;
import com.alef.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository aRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository aRepository) {
        this.aRepository = aRepository;
    }

    // ** Save ** //
    @Override
    @Transactional
    public void saveAuthor(AuthorEntity author) throws ServiceError {

        if(author.getName().isBlank()) {
            throw new ServiceError("Name is mandatory");
        }

        if(aRepository.existsAuthorEntityByName(author.getName())) {
            throw new ServiceError("Author already exist");
        }

        if(!author.getPhoto().contains("jpg") && !author.getPhoto().contains("png")) {
            throw new ServiceError("Invalid image");
        }

        aRepository.save(author);
    }

    @Override
    @Transactional
    public void updateAuthor(String id, AuthorEntity author) throws ServiceError {

        if(author.getName().isBlank()) {
            throw new ServiceError("Name is mandatory");
        }

        if(!author.getPhoto().contains("jpg") && !author.getPhoto().contains("png")) {
            throw new ServiceError("Invalid image");
        }

        author.setId(id);

        aRepository.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorEntity getAuthorById(String id) throws ServiceError {

        AuthorEntity author = aRepository.findById(id).orElse(null);

        if(author == null) {
            throw new ServiceError("Author not found");
        }

        return author;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> getAllAuthors() {
        return aRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteAuthor(String id) {

        AuthorEntity entity = aRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new ServiceError("Author not found");
        }

        if(entity.getDeleted()) {
            entity.setDeleted(!entity.getDeleted());
            aRepository.save(entity);
        } else {
            aRepository.deleteById(id);
        }
    }
}
