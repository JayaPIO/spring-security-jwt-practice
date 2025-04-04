package com.example.spring_security_jwt.services;

import com.example.spring_security_jwt.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<UserDto> saveUser(UserDto userDto);
}
