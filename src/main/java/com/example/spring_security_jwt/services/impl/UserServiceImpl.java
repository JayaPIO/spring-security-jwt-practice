package com.example.spring_security_jwt.services.impl;

import com.example.spring_security_jwt.converters.DtoToEntity;
import com.example.spring_security_jwt.converters.EntityToDto;
import com.example.spring_security_jwt.dtos.UserDto;
import com.example.spring_security_jwt.exceptions.CustomException;
import com.example.spring_security_jwt.models.User;
import com.example.spring_security_jwt.repositories.UserRepository;
import com.example.spring_security_jwt.services.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * saving user details into db
     *
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

    /**
     * fetching user from Database using userId
     * @param id
     * @return ResponseEntity<UserDto>
     */
    @Override
    @Cacheable("usersCache") // stores result in usersCache
    public ResponseEntity<UserDto> getUserByUserId(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = EntityToDto.UserEntityToUserDto(user);
            return ResponseEntity.status(HttpStatus.FOUND).body(userDto);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, "Event Not Found With Given" + id);
        }
    }
}
