package com.marcptr.cine.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcptr.cine.document.User;
import com.marcptr.cine.model.enums.Role;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByRole(Role role);
}