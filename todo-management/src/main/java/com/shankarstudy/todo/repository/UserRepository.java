package com.shankarstudy.todo.repository;

import com.shankarstudy.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Get All Standard CRUD methods for free

    // Now create custom query methods, Spring JPA will internally create query just by below standard method name
    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);
}
