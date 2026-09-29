package org.example.rentalservice.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rental_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RentalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "quantity")
    private Integer rentalDays;

    @Column(name = "price_per_day")
    private Double pricePerDay;
}
