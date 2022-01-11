package com.alef.library.services;

import com.alef.library.entities.UserEntity;

import java.util.List;

public interface UserService {

    void createUser(UserEntity user);

    void updateUser(String id, UserEntity user);

//    UserEntity getUserById(String id);
//
//    List<UserEntity> getAllUsers();

    void deleteUser(String id);
}
