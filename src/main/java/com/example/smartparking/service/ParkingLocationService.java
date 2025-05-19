package com.example.smartparking.service;

import com.example.smartparking.repository.ParkingLocationRepository;
import com.example.smartparking.dto.ParkingLocationRequest;
import com.example.smartparking.model.ParkingLocation;
import java.util.Optional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParkingLocationService {
    private final ParkingLocationRepository parkingLocationRepository;

    public ParkingLocationService(ParkingLocationRepository parkingLocationRepository) {
        this.parkingLocationRepository = parkingLocationRepository;
    }

    public ParkingLocation createParkingLocation(ParkingLocationRequest request) {
        ParkingLocation parkingLocation = new ParkingLocation(
                request.name,
                request.address,
                request.isAvailable,
                request.pricePerHour,
                request.totalSpots,
                request.availableSpots,
                request.imageUrl,
                request.description);
        return parkingLocationRepository.save(parkingLocation);
    }

    public ParkingLocation getParkingLocationById(Long id) {
        return parkingLocationRepository.findById(id).orElse(null);
    }

    public List<ParkingLocation> getParkingLocations() {
        return parkingLocationRepository.findAll();
    }

    public ParkingLocation deleteParkingLocation(Long id) {
        ParkingLocation parkingLocation = getParkingLocationById(id);
        if (parkingLocation != null) {
            parkingLocationRepository.delete(parkingLocation);
        }
        return parkingLocation;
    }

    public Optional<ParkingLocation> updateParkingLocation(Long id, ParkingLocationRequest request) {
        return parkingLocationRepository.findById(id).map(e -> {
            e.setName(request.name);
            e.setAddress(request.address);
            e.setIsAvailable(request.isAvailable);
            e.setPricePerHour(request.pricePerHour);
            e.setTotalSpots(request.totalSpots);
            e.setAvailableSpots(request.availableSpots);
            e.setImageUrl(request.imageUrl);
            e.setDescription(request.description);
            return parkingLocationRepository.save(e);
        });
    }

}
