package com.example.spring_security_jwt.services;

import com.example.spring_security_jwt.dtos.UserDto;
import com.example.spring_security_jwt.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<UserDto> saveUser(UserDto userDto);

    ResponseEntity<UserDto> getUserByUserId(long id);

    ResponseEntity<List<User>> getAllUsers();

    ResponseEntity<UserDto> updateUser(UserDto userDto);

    User updateUserForTest(User user, Long id);

    void deleteUser(Long id);
}

