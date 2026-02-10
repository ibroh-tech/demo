package com.example.demo.integration.repository;

import com.example.demo.entity.User;
import com.example.demo.integration.config.PostgresContainerConfiguration;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(PostgresContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")

public class TestPosgresContainerConnection extends PostgresContainerConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUser() {
        User user = new User("TestUser", "testuser@gmail.com", "testuser123");
        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findByEmail(savedUser.getEmail());
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser, foundUser.get());

    }
}
