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
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@SpringBootTest
class UserServiceRedisTest extends BaseRedisTestContainerConfiguration {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager; // Inject the CacheManager

    @MockitoSpyBean
    private UserRepository userRepository;

    @Test
    void shouldCacheUser() {
        // 1. Setup user
        User user = new User();
        user.setUsername("redis");
        user.setEmail("re" + System.currentTimeMillis() + "@example.com");
        user.setPassword("redis123");

        // 2. Save the user
        User savedUser = userService.saveUser(user);

        // 3. Clear the CORRECT cache name
        cacheManager.getCache("usersById").evict(savedUser.getId());  // Changed from "users" to "usersById"

        // 4. First call: Cache is empty, hits DB
        userService.getUserById(savedUser.getId());

        // 5. Second call: Data is cached, skips DB
        userService.getUserById(savedUser.getId());

        // 6. Verify findById was called exactly once
        verify(userRepository, times(1)).findById(savedUser.getId());
    }
}