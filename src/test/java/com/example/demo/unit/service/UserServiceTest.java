package com.example.demo.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldSaveUser() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@mail.com");
        user.setPassword("123");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        // when
        User savedUser = userService.saveUser(user);

        // then
        assertNotNull(savedUser);
        assertEquals("test", savedUser.getUsername());

        verify(userRepository, times(1)).save(user);
    }
}

