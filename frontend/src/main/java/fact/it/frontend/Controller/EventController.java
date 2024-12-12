package fact.it.frontend.Controller;

import fact.it.frontend.Request.EventRequest;
import fact.it.frontend.Service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/events/delete/{eventName}")
    public String deleteEvent(@PathVariable String eventName) {
        // Call the service to delete the event
        Object response = eventService.deleteEvent(eventName);

        // Handle the response (you can choose to show a success message or error)
        // Here, I'm assuming a successful deletion redirects to the event list page
        if (response != null) {
            return "redirect:/events";  // Redirect to events listing page after successful deletion
        } else {
            // You can add error handling here, if needed
            return "index"; // Return an error page if deletion fails
        }
    }






}
