package com.example.spring_security_jwt.repositories;

import com.example.spring_security_jwt.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * test the save operation of the repository
     * verifies that the User entity is correctly persisted and returned entity contain a not null ID
     */
    @Test
    @DisplayName("It should save user into database")
    void save() {
        //Arrange
        User user = new User();
        user.setUsername("sam");
        user.setPassword("11");
        user.setRole("ADMIN");
        //Act
        User savedUser = userRepository.save(user);
        // Assert
        assertNotNull(savedUser);
        assertThat(savedUser.getUserId()).isNotEqualTo(null);
        assertEquals("sam", savedUser.getUsername());
    }

    /**
     * tests the findAll method of the repository
     * verifies that return list of users is not null and size of list of users matches the expected size
     */
    @Test
    void getAllUsers() {
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertThat(users).isNotNull();
        assertEquals(7, users.size());
    }

    /**
     * tests the findById method of repository
     * validates if returned user is not null and username matches with the expected username
     */
    @Test
    void getUserById() {
        User getUser = userRepository.findById(4L).get();
        assertNotNull(getUser);
        assertEquals("sia", getUser.getUsername());
    }

    /**
     * tests that an existing user entity is updated correctly or not
     */
    @Test
    void updateUser() {
        Optional<User> existingUserOptional = userRepository.findById(11L);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setRole("MANAGER");
            existingUser.setUsername("meera");

            User updatedUser = userRepository.save(existingUser);
            assertEquals("MANAGER", updatedUser.getRole());
            assertEquals("meera", updatedUser.getUsername());
            assertEquals("11", updatedUser.getPassword());
        }
    }

    /**
     * tests the deleteById method of repository
     */
    @Test
    void deleteUser() {
        User existingUser = userRepository.findByUsername("sam");
        userRepository.deleteById(existingUser.getUserId());

        List<User> users = userRepository.findAll();
        assertEquals(7, users.size());

    }

}
