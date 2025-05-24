package com.example.smartparking.service;

import org.springframework.stereotype.Service;
import com.example.smartparking.dto.UserRequest;
import com.example.smartparking.exception.InvalidUserDataException;
import com.example.smartparking.exception.UserAlreadyExistsException;
import com.example.smartparking.exception.UserNotFoundException;
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
        validateUserRequest(userRequest);

        if (userRepository.findByCitizenId(userRequest.citizenId) != null) {
            throw new UserAlreadyExistsException("A user with citizenId " + userRequest.citizenId + " already exists.");
        }

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
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found."));
    }

    public User getUserByCitizenId(Long citizenId) {
        User user = userRepository.findByCitizenId(citizenId);
        if (user == null) {
            throw new UserNotFoundException("User with citizenId " + citizenId + " not found.");
        }
        return user;
    }

    public List<User> getUsersByName(String name) {
        List<User> users = userRepository.findByName(name);
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found with name " + name);
        }
        return users;
    }

    public User deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        userRepository.delete(user);
        return user;
    }

    public User updateUser(Long id, UserRequest userRequest) {
        validateUserRequest(userRequest);
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        if (userRepository.findByCitizenId(userRequest.citizenId) != null) {
            throw new UserAlreadyExistsException("A user with citizenId " + userRequest.citizenId + " already exists.");
        }
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }

        String now = LocalDateTime.now().format(formatter);

        user.setName(userRequest.name);
        user.setCitizenId(userRequest.citizenId);
        user.setUpdatedAt(now);
        return userRepository.save(user);

    }

    private void validateUserRequest(UserRequest userRequest) {
        if (userRequest.name == null || userRequest.name.trim().isEmpty()) {
            throw new InvalidUserDataException("User name is required.");
        }
        if (userRequest.citizenId == null || userRequest.citizenId <= 0) {
            throw new InvalidUserDataException("Valid citizen ID is required.");
        }
    }

}
