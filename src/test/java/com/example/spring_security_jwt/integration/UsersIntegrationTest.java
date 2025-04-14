package com.example.spring_security_jwt.integration;

import com.example.spring_security_jwt.models.User;
import com.example.spring_security_jwt.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UsersIntegrationTest {
    private static RestTemplate restTemplate;
    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost:";
    @Autowired
    private UserRepository userRepository;

    /**
     * Runs once before all tests
     * initializes the Rest template
     */
    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    /**
     * Runs before every test case
     * constructs the full base url
     */
    @BeforeEach
    public void beforeSetUp() {
        baseUrl = baseUrl + port + "/home";
    }

    /**
     * Runs after every test case
     * cleans up by deleting all users fom database
     */
    @AfterEach
    public void afterSetUp() {
        userRepository.deleteAll();
    }

    /**
     * test to verify that a new user is created and successfully persisted into db
     */
    @Test
    void shouldCreateUserTest() {
        User user = new User("sam", "11", "ADMIN");
        User newUser = restTemplate.postForObject(baseUrl + "/register", user, User.class);

        assertNotNull(newUser);
        assertThat(newUser.getUserId()).isNotNull();
    }

    /**
     * test to fetch all users from database
     */
    @Test
    void shouldFetchAllUsersTest() {
        User user1 = new User("sam", "11", "ADMIN");
        User user2 = new User("ram", "11", "MANAGER");

        restTemplate.postForObject(baseUrl + "/register", user1, User.class);
        restTemplate.postForObject(baseUrl + "/register", user2, User.class);

        List<User> users = restTemplate.getForObject(baseUrl + "/users", List.class);
        assertThat(users.size()).isEqualTo(2);
    }

}
