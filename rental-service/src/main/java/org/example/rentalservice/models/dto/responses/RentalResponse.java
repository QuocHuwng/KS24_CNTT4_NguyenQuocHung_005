package org.example.rentalservice.models.dto.responses;

import org.example.rentalservice.models.constants.RentalStatus;

import java.util.List;

public record RentalResponse(
        Long id,
        String customerName,
        String customerEmail,
        Double totalAmount,
        RentalStatus status,
        List<RentalDetailResponse> items
) {
}
