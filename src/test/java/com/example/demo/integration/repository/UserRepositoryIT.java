package com.example.demo.integration.repository;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.integration.config.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = {"spring.cache.type=none"})
class UserRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUser() {
        // given
        User user = new User();
        user.setUsername("test123");
        user.setEmail("test123@mail.com");
        user.setPassword("123123");
        userRepository.save(user);

        // when
        Optional<User> result = userRepository.findByUsername("test123");

        // then
        assertTrue(result.isPresent());
        assertEquals("test123", result.get().getUsername());
    }
}
