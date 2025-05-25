package com.example.smartparking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.smartparking.service.ParkingLogService;
import com.example.smartparking.service.UserService;
import com.example.smartparking.dto.UserRequest;
import com.example.smartparking.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ParkingLogService parkingLogService;

    public UserController(UserService userService, ParkingLogService parkingLogService) {
        this.userService = userService;
        this.parkingLogService = parkingLogService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(HttpServletRequest request) {
        List<User> users = userService.getUsers();
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "User", "/api/users");
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(HttpServletRequest request, @PathVariable Long id) {
        User user = userService.getUserById(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "User", "/api/users/" + id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<User> getUserByCitizenId(HttpServletRequest request, @PathVariable Long citizenId) {
        User user = userService.getUserByCitizenId(citizenId);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "User", "/api/users/citizen/" + citizenId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<User>> getUsersByName(HttpServletRequest request, @PathVariable String name) {
        List<User> users = userService.getUsersByName(name);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "User", "/api/users/name/" + name);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(HttpServletRequest request, @RequestBody UserRequest userRequest) {
        User user = userService.createUser(userRequest);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("POST", ipAddress, "User", "/api/users");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(HttpServletRequest request, @PathVariable Long id,
            @RequestBody UserRequest userRequest) {
        User user = userService.updateUser(id, userRequest);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("PUT", ipAddress, "User", "/api/users/" + id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(HttpServletRequest request, @PathVariable Long id) {
        User user = userService.deleteUser(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("DELETE", ipAddress, "User", "/api/users/" + id);
        return ResponseEntity.ok(user);
    }
}
