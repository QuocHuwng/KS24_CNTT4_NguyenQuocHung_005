package org.example.rentalservice.models.repositories;

import org.example.rentalservice.models.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
