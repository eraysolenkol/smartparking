package com.example.smartparking.repository;

import org.springframework.stereotype.Repository;
import com.example.smartparking.model.ParkingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface ParkingLogRepository extends JpaRepository<ParkingLog, Long> {

    List<ParkingLog> findByAction(String action);

    List<ParkingLog> findByEntity(String entity);

    List<ParkingLog> findByCreatedAtStartingWith(String createdAt);

}
