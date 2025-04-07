package com.example.spring_security_jwt.repositories;

import com.example.spring_security_jwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
