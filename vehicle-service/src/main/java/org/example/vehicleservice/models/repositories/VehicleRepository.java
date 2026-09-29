package org.example.vehicleservice.models.repositories;

import org.example.vehicleservice.models.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
