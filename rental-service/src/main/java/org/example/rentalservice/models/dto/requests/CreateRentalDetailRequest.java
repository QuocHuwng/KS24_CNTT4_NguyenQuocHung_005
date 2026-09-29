package org.example.rentalservice.models.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateRentalDetailRequest(
        @NotNull(message = "Vehicle id is required")
        @Min(value = 1, message = "Vehicle id must be greater than 0")
        Long vehicleId,

        @NotNull(message = "Rental days is required")
        @Min(value = 1, message = "Rental days must be greater than 0")
        Integer rentalDays
) {
}
