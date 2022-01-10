package com.alef.library.repositories;

import com.alef.library.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

    Boolean existsBookEntityByTitle(String title);
}
