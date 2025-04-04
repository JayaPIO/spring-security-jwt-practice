package com.example.spring_security_jwt.services.impl;

import com.example.spring_security_jwt.converters.DtoToEntity;
import com.example.spring_security_jwt.dtos.UserDto;
import com.example.spring_security_jwt.models.User;
import com.example.spring_security_jwt.repositories.UserRepository;
import com.example.spring_security_jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * saving user details into db
     * @param userDto
     * @return ResponseEntity<UserDto>
     */
    @Override
    public ResponseEntity<UserDto> saveUser(UserDto userDto) {
        User user = DtoToEntity.UserDtoToUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
