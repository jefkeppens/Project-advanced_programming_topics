package fact.it.rideservice.service;

import fact.it.rideservice.model.Ride;
import fact.it.rideservice.repository.RideRepository;
import fact.it.rideservice.dto.RideResponse;
import fact.it.rideservice.dto.RideRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepository rideRepository;

    public void createRide(RideRequest rideRequest){
        Ride ride = Ride.builder()
                .name(rideRequest.getName())
                .type(rideRequest.getType())
                .capacity(rideRequest.getCapacity())
                .duration(rideRequest.getDuration())
                .responsibleEmail(rideRequest.getResponsibleEmail())
                .build();

        rideRepository.save(ride);
    }

    public List<RideResponse> getAllRides() {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream().map(this::mapToRideResponse).toList();
    }

    public RideResponse getRideByName(String name) {
        Ride ride = rideRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        return mapToRideResponse(ride);
    }

    public List<RideResponse> getRidesByType(String type) {
        List<Ride> rides = rideRepository.findByType(type); // Querying by type
        return rides.stream().map(this::mapToRideResponse).toList();
    }


    public void updateRide(String name, RideRequest rideRequest) {
        Optional<Ride> optionalRide = rideRepository.findByName(name);
        if (optionalRide.isPresent()) {
            Ride ride = optionalRide.get();
            ride.setName(rideRequest.getName());
            ride.setType(rideRequest.getType());
            ride.setCapacity(rideRequest.getCapacity());
            ride.setDuration(rideRequest.getDuration());
            rideRepository.save(ride);
        } else {
            throw new RuntimeException("Ride not found with name: " + name);
        }
    }

    public void deleteRide(String name) {
        Optional<Ride> optionalRide = rideRepository.findByName(name);
        if (optionalRide.isPresent()) {
            rideRepository.delete(optionalRide.get());
        } else {
            throw new RuntimeException("Ride not found with name: " + name);
        }
    }

    private RideResponse mapToRideResponse(Ride ride) {
        return RideResponse.builder()
                .id(ride.getId())
                .name(ride.getName())
                .type(ride.getType())
                .capacity(ride.getCapacity())
                .duration(ride.getDuration())
                .build();
    }
}
