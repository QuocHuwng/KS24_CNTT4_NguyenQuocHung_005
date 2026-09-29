package org.example.rentalservice.models.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.rentalservice.models.constants.RentalStatus;
import org.example.rentalservice.models.dto.requests.CreateRentalDetailRequest;
import org.example.rentalservice.models.dto.requests.CreateRentalRequest;
import org.example.rentalservice.models.dto.responses.RentalDetailResponse;
import org.example.rentalservice.models.dto.responses.RentalResponse;
import org.example.rentalservice.models.dto.responses.VehicleResponse;
import org.example.rentalservice.models.entities.Rental;
import org.example.rentalservice.models.entities.RentalDetail;
import org.example.rentalservice.models.repositories.RentalDetailRepository;
import org.example.rentalservice.models.repositories.RentalRepository;
import org.example.rentalservice.models.services.RentalService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

        private final RentalRepository rentalRepository;
        private final RentalDetailRepository rentalDetailRepository;
        private final VehicleGatewayService vehicleGatewayService;
        private final KafkaTemplate<String, String> kafkaTemplate;

        @Override
        @Transactional
        public RentalResponse createRental(CreateRentalRequest request) {
                List<VehicleResponse> vehicles = new ArrayList<>();
                double totalAmount = 0.0;

                for (CreateRentalDetailRequest item : request.items()) {
                        VehicleResponse vehicle = vehicleGatewayService.getVehicleById(item.vehicleId());

                        if (vehicle == null
                                || vehicle.vehicleId() == null
                                || vehicle.vehicleName() == null
                                || vehicle.vehicleType() == null
                                || vehicle.pricePerDay() == null
                                || vehicle.pricePerDay() < 0) {
                                throw new IllegalStateException("Invalid vehicle data");
                        }

                        vehicles.add(vehicle);
                        totalAmount += vehicle.pricePerDay() * item.rentalDays();
                }

                Rental rental = Rental.builder()
                        .customerName(request.customerName())
                        .customerEmail(request.customerEmail())
                        .totalAmount(totalAmount)
                        .status(RentalStatus.PENDING)
                        .build();

                rental = rentalRepository.save(rental);

                List<RentalDetailResponse> items = new ArrayList<>();

                for (int i = 0; i < request.items().size(); i++) {
                        CreateRentalDetailRequest requestItem = request.items().get(i);
                        VehicleResponse vehicle = vehicles.get(i);

                        RentalDetail detail = RentalDetail.builder()
                                .rental(rental)
                                .vehicleId(vehicle.vehicleId())
                                .rentalDays(requestItem.rentalDays())
                                .pricePerDay(vehicle.pricePerDay())
                                .build();

                        detail = rentalDetailRepository.save(detail);

                        double lineTotal = vehicle.pricePerDay() * requestItem.rentalDays();

                        items.add(new RentalDetailResponse(
                                detail.getId(),
                                vehicle.vehicleId(),
                                vehicle.vehicleName(),
                                vehicle.vehicleType(),
                                requestItem.rentalDays(),
                                vehicle.pricePerDay(),
                                lineTotal
                        ));
                }

                kafkaTemplate.send("rental-created", request.customerEmail());

                return new RentalResponse(
                        rental.getId(),
                        rental.getCustomerName(),
                        rental.getCustomerEmail(),
                        rental.getTotalAmount(),
                        rental.getStatus(),
                        items
                );
        }
}