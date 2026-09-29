package org.example.rentalservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.rentalservice.models.dto.requests.CreateRentalRequest;
import org.example.rentalservice.models.dto.responses.RentalResponse;
import org.example.rentalservice.models.services.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity<RentalResponse> createRental(@Valid @RequestBody CreateRentalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.createRental(request));
    }
}
