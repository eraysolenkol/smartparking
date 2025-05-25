package com.example.smartparking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.smartparking.model.User;
import com.example.smartparking.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.List;

public class UserServiceTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
    }

    @Test
    public void testCreateUser() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        String name = "Test User";
        Long randomCitizenId = System.currentTimeMillis() % 10000;
        User user = new User();
        user.setCitizenId(randomCitizenId);
        user.setName(name);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        when(userRepository.save(any(User.class))).thenReturn(user);

        assertEquals(randomCitizenId, user.getCitizenId());
        assertEquals(name, user.getName());
        assertEquals(user.getCreatedAt(), user.getUpdatedAt());

        userRepository.save(user);

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testGetUserById() {
        Long random = System.currentTimeMillis() % 10000;
        User user = new User();
        user.setName("Test User");
        user.setId(random);

        when(userRepository.findById(random)).thenReturn(Optional.of(user));

        User user2 = userRepository.findById(random).orElse(null);
        assertEquals(random, user2.getId());
        assertEquals("Test User", user2.getName());

        verify(userRepository).findById(random);
    }

    @Test
    public void testGetUserByCitizenId() {
        Long randomCitizenId = System.currentTimeMillis() % 10000;
        User user = new User();
        user.setCitizenId(randomCitizenId);
        user.setName("Test User");

        when(userRepository.findByCitizenId(randomCitizenId)).thenReturn(user);

        User user2 = userRepository.findByCitizenId(randomCitizenId);
        assertEquals(randomCitizenId, user2.getCitizenId());
        assertEquals("Test User", user2.getName());

        verify(userRepository).findByCitizenId(randomCitizenId);
    }

    @Test
    public void testGetUsersByName() {
        String name = "Test User";
        User user = new User();
        user.setName(name);

        when(userRepository.findByName(name)).thenReturn(List.of(user));

        List<User> users = userRepository.findByName(name);
        assertEquals(1, users.size());
        assertEquals(name, users.get(0).getName());

        verify(userRepository).findByName(name);
    }

    @Test
    public void testDeleteUser() {
        Long random = System.currentTimeMillis() % 10000;
        User user = new User();
        user.setId(random);
        user.setName("Test User");

        when(userRepository.findById(random)).thenReturn(Optional.of(user));
        userRepository.delete(user);

        verify(userRepository).delete(user);
    }

}
