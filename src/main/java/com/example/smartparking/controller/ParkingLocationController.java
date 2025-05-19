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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.Optional;

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
    public List<ParkingLocation> getParkingLocations() {
        return parkingLocationService.getParkingLocations();
    }

    @GetMapping("/{id}")
    public ParkingLocation getParkingLocationById(@PathVariable Long id) {
        return parkingLocationService.getParkingLocationById(id);
    }

    @PostMapping
    public ParkingLocation createParkingLocation(@RequestBody ParkingLocationRequest request) {
        return parkingLocationService.createParkingLocation(request);
    }

    @PutMapping("/{id}")
    public ParkingLocation updateParkingLocation(@PathVariable Long id, @RequestBody ParkingLocationRequest request) {
        Optional<ParkingLocation> updatedParkingLocation = parkingLocationService.updateParkingLocation(id, request);
        if (updatedParkingLocation.isPresent()) {
            return updatedParkingLocation.get();
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteParkingLocation(@PathVariable Long id) {
        ParkingLocation parkingLocation = parkingLocationService.getParkingLocationById(id);
        if (parkingLocation != null) {
            parkingLocationService.deleteParkingLocation(id);
        }
    }

}
