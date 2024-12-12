package fact.it.frontend.Controller;

import fact.it.frontend.Service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final EventService eventService;

    public HomeController(EventService eventService) {
        this.eventService = eventService;
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
}

