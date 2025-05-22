package com.example.smartparking.service;

import org.springframework.stereotype.Service;
import com.example.smartparking.dto.UserRequest;
import com.example.smartparking.model.User;
import com.example.smartparking.repository.UserRepository;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.name);
        user.setCitizenId(userRequest.citizenId);
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
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userRequest.name);
            user.setCitizenId(userRequest.citizenId);
            return userRepository.save(user);
        }
        return null;
    }

}
