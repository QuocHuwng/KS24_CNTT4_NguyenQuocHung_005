package org.example.rentalservice.exceptions;

public class VehicleServiceException extends RuntimeException {

    public VehicleServiceException(String message) {
        super(message);
    }

    public VehicleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
