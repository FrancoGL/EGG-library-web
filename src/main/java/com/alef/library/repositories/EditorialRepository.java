package com.alef.library.repositories;

import com.alef.library.entities.EditorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepository extends JpaRepository<EditorialEntity, String> {

    Boolean existsEditorialEntityByName(String name);
}
