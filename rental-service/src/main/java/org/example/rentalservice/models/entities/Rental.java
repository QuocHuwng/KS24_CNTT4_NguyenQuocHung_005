package org.example.rentalservice.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.rentalservice.models.constants.RentalStatus;

@Entity
@Table(name = "rentals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RentalStatus status;

}
