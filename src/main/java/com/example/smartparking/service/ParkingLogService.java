package com.example.smartparking.service;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.smartparking.repository.ParkingLogRepository;
import com.example.smartparking.model.ParkingLog;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingLogService {

    private final ParkingLogRepository logRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    public ParkingLogService(ParkingLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void log(String action, String ipAddress, String entity, String apiEndpoint) {
        String createdAt = LocalDateTime.now().format(formatter);
        ParkingLog log = new ParkingLog(action, ipAddress, entity, createdAt, apiEndpoint);
        logRepository.save(log);
    }

    public List<ParkingLog> getLogs() {
        return logRepository.findAll();
    }

}
