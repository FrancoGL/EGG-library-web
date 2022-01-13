package com.alef.library.services.impl;

import com.alef.library.entities.LoanEntity;
import com.alef.library.entities.UserEntity;
import com.alef.library.errors.ServiceError;
import com.alef.library.repositories.UserRepository;
import com.alef.library.security.enums.Role;
import com.alef.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository uRepository;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository uRepository, BCryptPasswordEncoder encoder) {
        this.uRepository = uRepository;
        this.encoder = encoder;
    }

    // ** Create ** //
    @Override
    @Transactional
    public void createUser(UserEntity user) throws ServiceError {

        validation(user);

        if(!uRepository.existsUserEntityByRole(Role.ADMIN)) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

        user.setPassword(encoder.encode(user.getPassword()));

        uRepository.save(user);
    }

    // ** Update ** //
    @Override
    @Transactional
    public void updateUser(String id, UserEntity user) throws ServiceError {

//        validation(user);

        if(user.getPassword() != null) {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        user.setId(id);

        System.out.println(user.getImage());

        uRepository.save(user);
    }

    // ** Get User By Id ** //
    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserById(String id) {

        UserEntity user = uRepository.findById(id).orElse(null);

        if(user == null) {
            throw new ServiceError("User not found");
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanEntity> getLoans(String id) {

        UserEntity user = getUserById(id);

        return user.getLoans();
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity entity = uRepository.findUserEntityByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));

        GrantedAuthority  authority = new SimpleGrantedAuthority("ROLE_" + entity.getRole());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attributes.getRequest().getSession();

        session.setAttribute("id", entity.getId());
        session.setAttribute("name", (entity.getName() + " " + entity.getLastName()));

        return new User(entity.getEmail(), entity.getPassword(), Collections.singletonList(authority));
    }

    private void validation(UserEntity user) {

        if(uRepository.existsUserEntityByName(user.getName())) {
            throw new ServiceError("User already exist");
        }

        if(user.getDni().toString().length() < 8) {
            throw new ServiceError("Invalid DNI");
        }

        if(user.getName() == null || user.getName().isEmpty()) {
            throw new ServiceError("You have to provided a valid name");
        }
        if(user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new ServiceError("You have to provide a surname");
        }

        if(user.getEmail() == null || !user.getEmail().contains("@") || !user.getEmail().contains(".com")) {
            throw new ServiceError("You have to provide a valid email");
        }
        if(user.getPassword().length() < 8) {
            throw new ServiceError("You have to provide a password with at least eight characters.");
        }
    }
}
