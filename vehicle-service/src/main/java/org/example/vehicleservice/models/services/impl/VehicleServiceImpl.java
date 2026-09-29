package org.example.vehicleservice.models.services.impl;

import org.example.vehicleservice.exceptions.VehicleNotFoundException;
import org.example.vehicleservice.models.entities.Vehicle;
import org.example.vehicleservice.models.repositories.VehicleRepository;
import org.example.vehicleservice.models.services.VehicleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }
}
