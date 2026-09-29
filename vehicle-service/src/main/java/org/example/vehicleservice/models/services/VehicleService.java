package org.example.vehicleservice.models.services;

import org.example.vehicleservice.models.entities.Vehicle;

public interface VehicleService {
    Vehicle getVehicleById(Long id);
}
