package com.example.smartparking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.smartparking.service.UserService;
import com.example.smartparking.dto.UserRequest;
import com.example.smartparking.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/citizen/{citizenId}")
    public User getUserByCitizenId(@PathVariable Long citizenId) {
        return userService.getUserByCitizenId(citizenId);
    }

    @GetMapping("/name/{name}")
    public List<User> getUsersByName(@PathVariable String name) {
        return userService.getUsersByName(name);
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
