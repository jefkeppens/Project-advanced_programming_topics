package fact.it.frontend.Controller;

import fact.it.frontend.Request.EventRequest;
import fact.it.frontend.Service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/add")
    public String addEventPage() {
        return "addEvent";
    }

    // Handle the form submission and add event
    @PostMapping("/events/add")
    public String addEvent(@RequestParam("name") String name,
                           @RequestParam("date") String date,
                           @RequestParam("location") String location,
                           Model model) {
        // Convert date from String to LocalDate (you can adjust the format if needed)
        LocalDate eventDate = LocalDate.parse(date);

        // Create an EventRequest object with the form data
        EventRequest eventRequest = new EventRequest(name, eventDate, location);

        // Call the service to add the event via API Gateway
        Object result = eventService.addEvent(eventRequest);

        // Add message based on the result
        model.addAttribute("message", result != null ? "Event added successfully" : "Failed to add event");
        return "events"; // Redirect or show a confirmation message (you can adjust this)
    }






}
