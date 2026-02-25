package com.example.demo.integration.config;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostgresContainerConfiguration {

    @Autowired
    private UserRepository userRepository;

    private static PostgreSQLContainer postgresContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:18.1")
            .withReuse(true);

    @BeforeAll
    public static void setup() {
        postgresContainer.start();
        System.out.println("Postgres started");
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Test
    public void contextLoads() {
        System.out.println("Context loaded");
    }

    @Test
    public void shouldSaveUser() {
        User user = new User("test","test@gamil.com", "qwertyuio123");
        User savedUser = userRepository.save(user);
        System.out.println("User saved");


        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("test");
        assertThat(savedUser.getEmail()).isEqualTo("test@gamil.com");

    }

    @Test
    public void getUser() {
        // Arrange
//        User user = new User("adminUser","adminUser@gamil.com", "123456789");
//        userRepository.save(user);

        // Act
        User savedUser = userRepository.findByUsername("adminUser").orElse(null);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("adminUser");
    }
}
