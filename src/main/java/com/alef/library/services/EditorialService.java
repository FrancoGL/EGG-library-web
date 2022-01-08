package com.alef.library.services;

import com.alef.library.entities.EditorialEntity;

import java.util.List;

public interface EditorialService {

    void saveEditorial(EditorialEntity editorial);

    void updateEditorial(String id, EditorialEntity editorial);

    EditorialEntity getEditorialById(String id);

    List<EditorialEntity> getAllEditorials();

    void deleteEditorial(String id);
}
