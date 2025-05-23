package com.example.smartparking.service;

import org.springframework.stereotype.Service;
import com.example.smartparking.dto.UserRequest;
import com.example.smartparking.model.User;
import com.example.smartparking.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequest userRequest) {
        String now = LocalDateTime.now().format(formatter);
        User user = new User();
        user.setName(userRequest.name);
        user.setCitizenId(userRequest.citizenId);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByCitizenId(Long citizenId) {
        return userRepository.findByCitizenId(citizenId);
    }

    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, UserRequest userRequest) {
        String now = LocalDateTime.now().format(formatter);
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userRequest.name);
            user.setCitizenId(userRequest.citizenId);
            user.setUpdatedAt(now);
            return userRepository.save(user);
        }
        return null;
    }

}
