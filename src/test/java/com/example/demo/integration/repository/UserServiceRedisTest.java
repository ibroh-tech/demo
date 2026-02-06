package com.example.demo.integration.repository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.example.demo.entity.User;
import com.example.demo.integration.config.BaseRedisTestContainerConfiguration;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@SpringBootTest
class UserServiceRedisTest extends BaseRedisTestContainerConfiguration {

    @Autowired
    private UserService userService;

    @MockitoSpyBean
    private UserRepository userRepository;


    @Test
    void shouldCacheUser() {
        User user = new User();
        user.setUsername("redis");
        user.setEmail("redis@example.com");
        user.setPassword("redis123");

        userService.saveUser(user);

        userService.getUserById(user.getId());
        userService.getUserById(user.getId());

        verify(userRepository, times(1)).findById(user.getId());
    }
}
