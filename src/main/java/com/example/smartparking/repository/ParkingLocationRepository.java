package com.example.smartparking.repository;

import com.example.smartparking.model.ParkingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLocationRepository extends JpaRepository<ParkingLocation, Long> {

    ParkingLocation findByName(String name);

}
