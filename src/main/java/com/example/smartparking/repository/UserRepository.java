package com.example.smartparking.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.smartparking.model.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCitizenId(Long citizenId);

    List<User> findByName(String name);

    Optional<User> findById(Long id);
}
