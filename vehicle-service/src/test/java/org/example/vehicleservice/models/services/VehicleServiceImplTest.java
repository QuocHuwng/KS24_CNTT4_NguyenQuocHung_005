package org.example.vehicleservice.models.services;

import org.example.vehicleservice.exceptions.VehicleNotFoundException;
import org.example.vehicleservice.models.entities.Vehicle;
import org.example.vehicleservice.models.repositories.VehicleRepository;
import org.example.vehicleservice.models.services.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VehicleServiceImplTest {

    private VehicleRepository vehicleRepository;
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
        vehicleService = new VehicleServiceImpl(vehicleRepository);
    }

    @Test
    void getVehicleByIdReturnsVehicleWhenItExists() {
        Vehicle vehicle = new Vehicle(1L, "Laptop", "Laptop văn phòng", new BigDecimal("15000000.00"), 5);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.getVehicleById(1L);

        assertSame(vehicle, result);
        verify(vehicleRepository).findById(1L);
    }

    @Test
    void getVehicleByIdThrowsExceptionWhenItDoesNotExist() {
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        VehicleNotFoundException exception = assertThrows(
                VehicleNotFoundException.class,
                () -> vehicleService.getVehicleById(99L)
        );

        assertEquals("Không tìm thấy xe với id: 99", exception.getMessage());
    }

    @Test
    void getVehicleByIdUsesVehiclesCacheWithIdAsKey() throws NoSuchMethodException {
        Cacheable cacheable = VehicleServiceImpl.class
                .getMethod("getVehicleById", Long.class)
                .getAnnotation(Cacheable.class);

        assertEquals("vehicles", cacheable.cacheNames()[0]);
        assertEquals("#id", cacheable.key());
        assertTrue(cacheable.sync());
        assertTrue(Serializable.class.isAssignableFrom(Vehicle.class));
    }
}
