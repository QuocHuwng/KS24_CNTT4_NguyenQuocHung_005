package org.example.rentalservice.clients;

import org.example.rentalservice.models.dto.responses.VehicleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicle-service", path = "/api/v1/vehicles")
public interface VehicleClient {

    @GetMapping("/{id}")
    VehicleResponse getVehicleById(@PathVariable Long id);
}
