package com.example.spring_security_jwt.services;

import com.example.spring_security_jwt.converters.EntityToDto;
import com.example.spring_security_jwt.dtos.UserDto;
import com.example.spring_security_jwt.exceptions.CustomException;
import com.example.spring_security_jwt.models.User;
import com.example.spring_security_jwt.repositories.UserRepository;
import com.example.spring_security_jwt.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class UserServiceTest {
    @Mock
    PasswordEncoder passwordEncoder;
    @Captor
    ArgumentCaptor<User> userCaptor;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("rose");
        user.setRole("ADMIN");
        user.setPassword("11");
        UserDto userDto = EntityToDto.UserEntityToUserDto(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("112");
        ResponseEntity<UserDto> userDtoResponseEntity = userService.saveUser(userDto);

        // capturing the saved user
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertNotNull(savedUser);
        // verifying the saved username with captured username
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());

    }

    @Test
    void getUsers() {
        User user1 = new User(1L, "ravina", "11", "MANAGER");
        User user2 = new User(2L, "ravi", "11", "ADMIN");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<List<User>> allUsers = userService.getAllUsers();
        List<User> getUsersList = allUsers.getBody();
        assertNotNull(getUsersList);
        assertEquals(2, getUsersList.size());

        verify(userRepository, times(1)).findAll();

    }

    @Test
    void getUserById() {
        User user = new User(1L, "ravina", "11", "MANAGER");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        ResponseEntity<UserDto> userByUserId = userService.getUserByUserId(user.getUserId());
        UserDto userDto = userByUserId.getBody();

        assertNotNull(userDto);
        assertThat(user.getUsername()).isEqualTo(userDto.getUsername());
        // capturing user id
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        // verifying captured id
        verify(userRepository).findById(idCaptor.capture());
        Long capturedId = idCaptor.getValue();
        assertEquals(user.getUserId(), capturedId);


    }

    @Test
    void getUserByIdException() {
        User user = new User(1L, "ravina", "11", "MANAGER");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(CustomException.class, () -> {
                    userService.getUserByUserId(2L);
                }
        );
    }

    @Test
    void updateUser() {
        User user = new User(1L, "jaya", "11", "MANAGER");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        user.setRole("Admin");

        User updatedUser = userService.updateUserForTest(user, 1L);

        assertNotNull(updatedUser);
        assertEquals("Admin", updatedUser.getRole());

        // capturing the user passed to save()
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        // verifying saved user role
        assertEquals(updatedUser.getRole(), capturedUser.getRole());
        // verifying username
        assertEquals(updatedUser.getUsername(), capturedUser.getUsername());
    }

    @Test
    void deleteUser() {
        User user = new User(1L, "jaya", "11", "MANAGER");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));
        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserException() {
        User user = new User(1L, "jaya", "11", "MANAGER");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doThrow(new CustomException(HttpStatus.NOT_FOUND, "user not found ")).when(userRepository).delete(any(User.class));
        assertThrowsExactly(CustomException.class, () -> userService.deleteUser(2L));

    }
}
