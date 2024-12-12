package fact.it.frontend.Controller;

import fact.it.frontend.Request.EventRequest;
import fact.it.frontend.Service.AuthService;
import fact.it.frontend.Service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class EventController {

    private final EventService eventService;
    private final AuthService authService;

    public EventController(EventService eventService, AuthService authService) {
        this.eventService = eventService;
        this.authService = authService;
    }

    @GetMapping("/events")
    public String events(Model model){
        model.addAttribute("events", eventService.getAllEvents());
        return "events";
    }

    @GetMapping("/events/add")
    public String addEventPage() {
        if(authService.getToken() == null){
            return "errorunauth";
        }
        return "addEvent";
    }

    @PostMapping("/events/add")
    public String addEvent(@RequestParam("name") String name, @RequestParam("date") String date, @RequestParam("location") String location, Model model) {
        LocalDate eventDate = LocalDate.parse(date);
        EventRequest eventRequest = new EventRequest(name, eventDate, location);
        eventService.addEvent(eventRequest);
        model.addAttribute("events", eventService.getAllEvents());
        return "events";
    }

    @RequestMapping("/events/delete/{eventName}")
    public String deleteEvent(@PathVariable String eventName, Model model) {
        if(authService.getToken() == null){
            return "errorunauth";
        }
        eventService.deleteEvent(eventName);
        model.addAttribute("events", eventService.getAllEvents());
        return "events";
    }
}
