package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Use a specific cache name for IDs
    @Cacheable(value = "usersById", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Use a specific cache name for Emails
    @Cacheable(value = "usersByEmail", key = "#email")
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // When saving, we need to refresh the ID cache AND clear the Email cache
    // to prevent the "old data" problem.
    @Caching(
            put = { @CachePut(value = "usersById", key = "#result.id", condition = "#result != null") },
            evict = { @CacheEvict(value = "usersByEmail", key = "#result.email", condition = "#result != null") }
    )
    public User saveUser(User user) {
        if (user.getId() == null) {
            // Check DB directly to avoid accidental caching during the check
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
            }
        }
        return userRepository.save(user);
    }

    @Caching(evict = {
            @CacheEvict(value = "usersById", key = "#id"),
            @CacheEvict(value = "usersByEmail", allEntries = true) // Email is harder to evict without the object, so we clear the email cache
    })
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}