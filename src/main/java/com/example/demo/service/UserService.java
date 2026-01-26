package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "users", key = "#email")
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @CachePut(value = "users", key = "#result.id", condition = "#result != null")
    public User saveUser(User user) {
        // Check if user with this email already exists
        if (user.getId() == null) {  // Only check for new users
            User existingUser = getUserByEmail(user.getEmail());
            if (existingUser != null) {
                throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
            }
        }
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}