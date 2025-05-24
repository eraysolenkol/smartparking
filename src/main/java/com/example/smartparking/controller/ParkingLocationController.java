package com.example.smartparking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.example.smartparking.service.ParkingLocationService;
import com.example.smartparking.dto.ParkingLocationRequest;
import com.example.smartparking.model.ParkingLocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking-locations")
public class ParkingLocationController {

    private final ParkingLocationService parkingLocationService;

    public ParkingLocationController(ParkingLocationService parkingLocationService) {
        this.parkingLocationService = parkingLocationService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingLocation>> getParkingLocations() {
        List<ParkingLocation> parkingLocations = parkingLocationService.getParkingLocations();
        return ResponseEntity.ok(parkingLocations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLocation> getParkingLocationById(@PathVariable Long id) {
        ParkingLocation parkingLocation = parkingLocationService.getParkingLocationById(id);
        return ResponseEntity.ok(parkingLocation);
    }

    @PostMapping
    public ResponseEntity<ParkingLocation> createParkingLocation(@RequestBody ParkingLocationRequest request) {
        ParkingLocation parkingLocation = parkingLocationService.createParkingLocation(request);
        return new ResponseEntity<>(parkingLocation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingLocation> updateParkingLocation(@PathVariable Long id,
            @RequestBody ParkingLocationRequest request) {
        ParkingLocation parkingLocation = parkingLocationService.updateParkingLocation(id, request);
        return ResponseEntity.ok(parkingLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParkingLocation> deleteParkingLocation(@PathVariable Long id) {
        ParkingLocation parkingLocation = parkingLocationService.deleteParkingLocation(id);
        return ResponseEntity.ok(parkingLocation);
    }

}
