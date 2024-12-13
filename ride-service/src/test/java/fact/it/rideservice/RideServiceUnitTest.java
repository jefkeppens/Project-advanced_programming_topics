package fact.it.rideservice;

import fact.it.rideservice.dto.RideRequest;
import fact.it.rideservice.dto.RideResponse;
import fact.it.rideservice.model.Ride;
import fact.it.rideservice.repository.RideRepository;
import fact.it.rideservice.service.RideService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RideServiceUnitTest {

    @InjectMocks
    private RideService rideService;

    @Mock
    private RideRepository rideRepository;

    @Test
    public void testCreateRide() {
        // Arrange
        RideRequest rideRequest = RideRequest.builder()
                .name("Roller Coaster")
                .type("Thrill")
                .capacity(20)
                .duration(5)
                .responsibleEmail("operator@example.com")
                .build();

        // Act
        rideService.createRide(rideRequest);

        // Assert
        verify(rideRepository, times(1)).save(any(Ride.class));
    }

    @Test
    public void testGetAllRides() {
        // Arrange
        Ride ride1 = Ride.builder()
                .id("1")
                .name("Ferris Wheel")
                .type("Family")
                .capacity(10)
                .duration(15)
                .responsibleEmail("operator1@example.com")
                .build();

        Ride ride2 = Ride.builder()
                .id("2")
                .name("Bumper Cars")
                .type("Fun")
                .capacity(8)
                .duration(10)
                .responsibleEmail("operator2@example.com")
                .build();

        when(rideRepository.findAll()).thenReturn(Arrays.asList(ride1, ride2));

        // Act
        List<RideResponse> rides = rideService.getAllRides();

        // Assert
        assertEquals(2, rides.size());
        assertEquals("Ferris Wheel", rides.get(0).getName());
        assertEquals("Bumper Cars", rides.get(1).getName());

        verify(rideRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateRide() {
        // Arrange
        String existingRideName = "Carousel";
        Ride existingRide = Ride.builder()
                .id("3")
                .name(existingRideName)
                .type("Family")
                .capacity(15)
                .duration(10)
                .responsibleEmail("operator3@example.com")
                .build();

        RideRequest updatedRideRequest = RideRequest.builder()
                .name("Carousel Updated")
                .type("Family")
                .capacity(18)
                .duration(12)
                .responsibleEmail("operator3@example.com")
                .build();

        when(rideRepository.findByName(existingRideName)).thenReturn(Optional.of(existingRide));

        // Act
        rideService.updateRide(existingRideName, updatedRideRequest);

        // Assert
        verify(rideRepository, times(1)).save(any(Ride.class));
        assertEquals("Carousel Updated", existingRide.getName());
        assertEquals(18, existingRide.getCapacity());
    }

    @Test
    public void testUpdateRide_NotFound() {
        // Arrange
        String nonExistentRideName = "NonExistentRide";
        RideRequest rideRequest = RideRequest.builder()
                .name("New Name")
                .type("Type")
                .capacity(20)
                .duration(10)
                .responsibleEmail("operator@example.com")
                .build();

        when(rideRepository.findByName(nonExistentRideName)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rideService.updateRide(nonExistentRideName, rideRequest);
        });

        assertEquals("Ride not found with name: NonExistentRide", exception.getMessage());
    }

    @Test
    public void testDeleteRide() {
        // Arrange
        String rideName = "Drop Tower";
        Ride ride = Ride.builder()
                .id("4")
                .name(rideName)
                .type("Thrill")
                .capacity(15)
                .duration(5)
                .responsibleEmail("operator4@example.com")
                .build();

        when(rideRepository.findByName(rideName)).thenReturn(Optional.of(ride));

        // Act
        rideService.deleteRide(rideName);

        // Assert
        verify(rideRepository, times(1)).delete(ride);
    }

    @Test
    public void testDeleteRide_NotFound() {
        // Arrange
        String nonExistentRideName = "Ghost Ride";
        when(rideRepository.findByName(nonExistentRideName)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            rideService.deleteRide(nonExistentRideName);
        });

        assertEquals("Ride not found with name: Ghost Ride", exception.getMessage());
    }
}
