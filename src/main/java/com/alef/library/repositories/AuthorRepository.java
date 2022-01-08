package com.alef.library.repositories;

import com.alef.library.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, String> {

    Boolean existsAuthorEntityByName(String name);
}
