package com.alef.library.services.impl;

import com.alef.library.entities.EditorialEntity;
import com.alef.library.errors.ServiceError;
import com.alef.library.repositories.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alef.library.services.EditorialService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EditorialServiceImpl implements EditorialService {

    private final EditorialRepository eRepository;

    @Autowired
    public EditorialServiceImpl(EditorialRepository eRepository) {
        this.eRepository = eRepository;
    }

    // ** Save **/
    @Override
    @Transactional
    public void saveEditorial(EditorialEntity editorial) throws ServiceError {

        if(editorial.getName().isBlank()) {
            throw new ServiceError("Name is mandatory");
        }

        if(eRepository.existsEditorialEntityByName(editorial.getName())) {
            throw new ServiceError("Editorial already exist");
        }

        eRepository.save(editorial);
    }

    // ** Update ** //
    @Override
    @Transactional
    public void updateEditorial(String id, EditorialEntity editorial) throws ServiceError {

        if(editorial.getName().isBlank()) {
            throw new ServiceError("Name is mandatory");
        }

        editorial.setId(id);

        eRepository.save(editorial);
    }

    // ** Get By Id ** //
    @Override
    @Transactional(readOnly = true)
    public EditorialEntity getEditorialById(String id) throws ServiceError {
        EditorialEntity editorial = eRepository.findById(id).orElse(null);

        if(editorial == null) {
            throw new ServiceError("Editorial not found");
        }

        return editorial;
    }
    // ** Get All Editorials ** //
    @Override
    @Transactional(readOnly = true)
    public List<EditorialEntity> getAllEditorials() {
        return eRepository.findAll();
    }


    @Override
    @Transactional
    public void deleteEditorial(String id) throws ServiceError {

        EditorialEntity entity = eRepository.findById(id).orElse(null);

        if(entity == null) {
            throw new ServiceError("Editorial not found");
        }

        if(entity.getDeleted()) {
            entity.setDeleted(!entity.getDeleted());
            eRepository.save(entity);
        } else {
            eRepository.deleteById(id);
        }
    }
}
