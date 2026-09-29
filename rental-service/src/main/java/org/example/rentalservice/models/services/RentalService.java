package org.example.rentalservice.models.services;

import org.example.rentalservice.models.dto.requests.CreateRentalRequest;
import org.example.rentalservice.models.dto.responses.RentalResponse;

public interface RentalService {
    RentalResponse createRental(CreateRentalRequest request);
}
