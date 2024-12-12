package fact.it.frontend.Controller;

import fact.it.frontend.Service.EventService;
import fact.it.frontend.Service.RideService;
import org.springframework.stereotype.Controller;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }




}
