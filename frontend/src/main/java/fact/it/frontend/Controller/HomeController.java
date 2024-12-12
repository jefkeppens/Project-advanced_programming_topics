package fact.it.frontend.Controller;

import fact.it.frontend.Service.EventService;
import fact.it.frontend.Service.RideService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final EventService eventService;
    private final RideService rideService;

    public HomeController(EventService eventService, RideService rideService) {
        this.eventService = eventService;
        this.rideService = rideService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/events")
    public String events(Model model){
        model.addAttribute("events", eventService.getAllEvents());
        return "events";
    }

    @GetMapping("/rides")
    public String rides(Model model){
        model.addAttribute("rides", rideService.getAllRides());
        return "rides";
    }
}

