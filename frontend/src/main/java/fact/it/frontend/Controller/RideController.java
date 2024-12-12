package fact.it.frontend.Controller;

import fact.it.frontend.Request.RideRequest;
import fact.it.frontend.Service.AuthService;
import fact.it.frontend.Service.RideService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RideController {

    private final RideService rideService;
    private final AuthService authService;

    public RideController(RideService rideService, AuthService authService) {
        this.rideService = rideService;
        this.authService = authService;
    }

    @GetMapping("/rides")
    public String events(Model model){
        model.addAttribute("rides", rideService.getAllRides());
        return "rides";
    }

    @GetMapping("/rides/add")
    public String addEventPage() {
        if(authService.getToken() == null){
            return "errorunauth";
        }
        return "addRide";
    }

    @PostMapping("/rides/add")
    public String addRide(@RequestParam("name") String name, @RequestParam("type") String type, @RequestParam("capacity") int capacity, @RequestParam("duration") int duration, Model model) {
        RideRequest rideRequest = new RideRequest(name, type, capacity, duration);
        rideService.addRide(rideRequest);
        model.addAttribute("rides", rideService.getAllRides());
        return "rides";
    }

    @RequestMapping("/rides/delete/{rideName}")
    public String deleteRide(@PathVariable String rideName, Model model) {
        if(authService.getToken() == null){
            return "errorunauth";
        }
        rideService.deleteRide(rideName);
        model.addAttribute("rides", rideService.getAllRides());
        return "rides";
    }
}
