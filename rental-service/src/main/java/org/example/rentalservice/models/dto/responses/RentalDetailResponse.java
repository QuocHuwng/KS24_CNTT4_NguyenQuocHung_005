package org.example.rentalservice.models.dto.responses;

public record RentalDetailResponse(
        Long id,
        Long vehicleId,
        String vehicleName,
        String vehicleType,
        Integer rentalDays,
        Double pricePerDay,
        Double lineTotal
) {
}
