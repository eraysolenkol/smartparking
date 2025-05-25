package com.example.smartparking.service;

import com.example.smartparking.repository.ParkingLocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.smartparking.dto.ParkingLocationRequest;
import com.example.smartparking.model.ParkingLocation;

public class ParkingLocationServiceTest {
    private ParkingLocationService parkingLocationService;
    private ParkingLocationRepository parkingLocationRepository;

    @BeforeEach
    public void setUp() {
        parkingLocationRepository = mock(ParkingLocationRepository.class);
        parkingLocationService = new ParkingLocationService(parkingLocationRepository);
    }

    @Test
    public void testCreateParkingLocation() {
        ParkingLocationRequest parkingLocationRequest = new ParkingLocationRequest(
                "Test Name",
                "Test Address",
                true,
                5.0,
                20,
                5,
                "Test URL",
                "Test description");

        ParkingLocation parkingLocation = new ParkingLocation(
                parkingLocationRequest.name,
                parkingLocationRequest.address,
                parkingLocationRequest.isAvailable,
                parkingLocationRequest.pricePerHour,
                parkingLocationRequest.totalSpots,
                parkingLocationRequest.availableSpots,
                parkingLocationRequest.imageUrl,
                parkingLocationRequest.description);
        parkingLocation.setId(1L);
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        parkingLocation.setCreatedAt(now);
        parkingLocation.setUpdatedAt(now);

        when(parkingLocationRepository.save(any(ParkingLocation.class))).thenReturn(parkingLocation);

        ParkingLocation location = parkingLocationService.createParkingLocation(parkingLocationRequest);
        verify(parkingLocationRepository).save(any(ParkingLocation.class));
        assertEquals("Test Name", location.getName());
        assertEquals("Test Address", location.getAddress());
        assertEquals(true, location.getIsAvailable());
        assertEquals(5.0, location.getPricePerHour());
        assertEquals(20, location.getTotalSpots());
        assertEquals(5, location.getAvailableSpots());
        assertEquals("Test URL", location.getImageUrl());
        assertEquals("Test description", location.getDescription());
        assertEquals(1L, location.getId());

    }
}
