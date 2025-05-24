package com.example.smartparking.service;

import com.example.smartparking.repository.ParkingLocationRepository;
import com.example.smartparking.dto.ParkingLocationRequest;
import com.example.smartparking.exception.ParkingLocationNotFoundException;
import com.example.smartparking.model.ParkingLocation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ParkingLocationService {
    private final ParkingLocationRepository parkingLocationRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public ParkingLocationService(ParkingLocationRepository parkingLocationRepository) {
        this.parkingLocationRepository = parkingLocationRepository;
    }

    public ParkingLocation createParkingLocation(ParkingLocationRequest request) {
        String now = LocalDateTime.now().format(formatter);
        ParkingLocation parkingLocation = new ParkingLocation(
                request.name,
                request.address,
                request.isAvailable,
                request.pricePerHour,
                request.totalSpots,
                request.availableSpots,
                request.imageUrl,
                request.description);
        parkingLocation.setCreatedAt(now);
        parkingLocation.setUpdatedAt(now);
        return parkingLocationRepository.save(parkingLocation);
    }

    public List<ParkingLocation> getParkingLocations() {
        return parkingLocationRepository.findAll();
    }

    public ParkingLocation getParkingLocationById(Long id) {
        return parkingLocationRepository.findById(id)
                .orElseThrow(() -> new ParkingLocationNotFoundException(id));
    }

    public ParkingLocation deleteParkingLocation(Long id) {
        ParkingLocation parkingLocation = getParkingLocationById(id);
        parkingLocationRepository.delete(parkingLocation);
        return parkingLocation;
    }

    public ParkingLocation updateParkingLocation(Long id, ParkingLocationRequest request) {
        String now = LocalDateTime.now().format(formatter);
        ParkingLocation parkingLocation = parkingLocationRepository.findById(id)
                .orElseThrow(() -> new ParkingLocationNotFoundException(id));

        parkingLocation.setName(request.name);
        parkingLocation.setAddress(request.address);
        parkingLocation.setIsAvailable(request.isAvailable);
        parkingLocation.setPricePerHour(request.pricePerHour);
        parkingLocation.setTotalSpots(request.totalSpots);
        parkingLocation.setAvailableSpots(request.availableSpots);
        parkingLocation.setImageUrl(request.imageUrl);
        parkingLocation.setDescription(request.description);
        parkingLocation.setUpdatedAt(now);

        return parkingLocationRepository.save(parkingLocation);
    }

}
