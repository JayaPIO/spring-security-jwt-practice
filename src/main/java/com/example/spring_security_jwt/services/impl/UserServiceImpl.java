package com.example.spring_security_jwt.services.impl;

import com.example.spring_security_jwt.converters.DtoToEntity;
import com.example.spring_security_jwt.converters.EntityToDto;
import com.example.spring_security_jwt.dtos.UserDto;
import com.example.spring_security_jwt.exceptions.CustomException;
import com.example.spring_security_jwt.models.User;
import com.example.spring_security_jwt.repositories.UserRepository;
import com.example.spring_security_jwt.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "user already exists");
        }
        User user = DtoToEntity.UserDtoToUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    /**
     * fetching user from Database using userId
     *
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


    @Override
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new CustomException(HttpStatus.NO_CONTENT, "no user found ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    @Override
    @Transactional
    public ResponseEntity<UserDto> updateUser(UserDto userDto) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "no user found")));
        User existingUser = userOptional.get();

        existingUser.setRole(userDto.getRole());
        existingUser.setPassword("00");
        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(EntityToDto.UserEntityToUserDto(updatedUser));
    }

    @Override
    public User updateUserForTest(User user, Long id) {
        Optional<User> existingUserOptional = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "user not found")));
        User existingUser = existingUserOptional.get();
        existingUser.setRole(user.getRole());
        existingUser.setPassword("00");
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "user not found");
        }
        User existingUser = userOptional.get();
        userRepository.delete(existingUser);

    }
}
