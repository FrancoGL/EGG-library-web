package com.alef.library.repositories;

import com.alef.library.entities.UserEntity;
import com.alef.library.security.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Boolean existsUserEntityByName(String name);

    Optional<UserEntity> findUserEntityByEmail(String email);

    Boolean existsUserEntityByRole(Role role);
}
