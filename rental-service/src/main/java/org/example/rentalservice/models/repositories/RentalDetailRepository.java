package org.example.rentalservice.models.repositories;

import org.example.rentalservice.models.entities.RentalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalDetailRepository extends JpaRepository<RentalDetail, Long> {
}
