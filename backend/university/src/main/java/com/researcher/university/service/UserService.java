package com.researcher.university.service;

import com.researcher.university.domain.User;
import com.researcher.university.exception.ResourceNotFoundException;
import com.researcher.university.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User createUser(@Valid User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long userId, @Valid User userRequest) {
        return userRepository.findById(userId).map(user -> {
            user.setEmail(userRequest.getEmail());
            user.setName(userRequest.getName());
            user.setLastName(userRequest.getLastName());
            user.setTitle(userRequest.getTitle());
            user.setPassword(userRequest.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }
}
