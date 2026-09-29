package org.example.rentalservice.models.dto.responses;

import com.fasterxml.jackson.annotation.JsonAlias;

public record VehicleResponse(
        @JsonAlias("id") Long vehicleId,
        String vehicleName,
        String vehicleType,
        Double pricePerDay
) {
}
