package org.example.vehicleservice.exceptions;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long id) {
        super("Không tìm thấy xe với id: " + id);
    }
}
