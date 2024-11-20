package fact.it.rideservice.repository;

import fact.it.rideservice.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RideRepository extends MongoRepository<Ride, String> {
    Optional<Ride> findByName(String name);
    List<Ride> findByType(String type);
}
