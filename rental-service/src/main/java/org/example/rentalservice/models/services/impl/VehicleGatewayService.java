package org.example.rentalservice.models.services.impl;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.example.rentalservice.clients.VehicleClient;
import org.example.rentalservice.exceptions.VehicleNotFoundException;
import org.example.rentalservice.exceptions.VehicleServiceException;
import org.example.rentalservice.models.dto.responses.VehicleResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleGatewayService {

    private final VehicleClient vehicleClient;

    @CircuitBreaker(
            name = "vehicleService",
            fallbackMethod = "getVehicleByIdFallback"
    )
    public VehicleResponse getVehicleById(Long vehicleId) {
        try {
            VehicleResponse vehicle = vehicleClient.getVehicleById(vehicleId);

            if (vehicle == null
                    || vehicle.vehicleId() == null
                    || vehicle.vehicleName() == null
                    || vehicle.vehicleType() == null
                    || vehicle.pricePerDay() == null
                    || vehicle.pricePerDay() < 0) {
                throw new VehicleServiceException("Invalid vehicle data");
            }

            return vehicle;
        } catch (FeignException.NotFound e) {
            throw new VehicleNotFoundException(vehicleId);
        } catch (FeignException e) {
            throw new VehicleServiceException(
                    "Vehicle service is unavailable",
                    e
            );
        }
    }

    private VehicleResponse getVehicleByIdFallback(
            Long vehicleId,
            Throwable throwable
    ) {
        if (throwable instanceof VehicleNotFoundException) {
            throw (VehicleNotFoundException) throwable;
        }

        if (throwable instanceof VehicleServiceException) {
            throw (VehicleServiceException) throwable;
        }

        throw new VehicleServiceException(
                "Vehicle service is unavailable",
                throwable
        );
    }
}