package fact.it.rideservice.controller;

import fact.it.rideservice.dto.RideResponse;
import fact.it.rideservice.model.Ride;
import fact.it.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import fact.it.rideservice.dto.RideRequest;

import java.util.List;

@RestController
@RequestMapping("/api/ride")
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RideResponse> getAllRides(){
        return rideService.getAllRides();
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public RideResponse getRideByName(@PathVariable String name) {
        return rideService.getRideByName(name);
    }

    @GetMapping("/type/{type}")
    @ResponseStatus(HttpStatus.OK)
    public List<RideResponse> getRidesByType(@PathVariable String type) {
        return rideService.getRidesByType(type);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createRide(@RequestBody RideRequest rideRequest){
        rideService.createRide(rideRequest);
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRide(@PathVariable String name, @RequestBody RideRequest rideRequest) {
        rideService.updateRide(name, rideRequest);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRide(@PathVariable String name) {
        rideService.deleteRide(name);
    }
}
