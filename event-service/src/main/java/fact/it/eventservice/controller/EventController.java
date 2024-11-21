package fact.it.eventservice.controller;

import fact.it.eventservice.dto.EventRequest;
import fact.it.eventservice.dto.EventResponse;
import fact.it.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public EventResponse getEventByName(@PathVariable String name) {
        return eventService.getEventByName(name);
    }

    @GetMapping("/location/{location}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getEventByLocation(@PathVariable String location) {
        return eventService.getEventsByLocation(location);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody EventRequest eventRequest) {
        eventService.createEvent(eventRequest);
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent(@PathVariable String name, @RequestBody EventRequest eventRequest) {
        eventService.updateEvent(name, eventRequest);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@PathVariable String name) {
        eventService.deleteEvent(name);
    }
}
