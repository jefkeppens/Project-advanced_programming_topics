package fact.it.eventservice.service;

import fact.it.eventservice.dto.EventRequest;
import fact.it.eventservice.dto.EventResponse;
import fact.it.eventservice.model.Event;
import fact.it.eventservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public void createEvent(EventRequest eventRequest){
        Event event = Event.builder()
                .name(eventRequest.getName())
                .date(LocalDate.now())
                .location(eventRequest.getLocation())
                .build();

        eventRepository.save(event);
    }

    public List<EventResponse> getAllEvents(){
        List<Event> events = eventRepository.findAll();
        return events.stream().map(this::mapToEventResponse).toList();
    }

    public EventResponse getEventByName(String name) {
        Event event = eventRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return mapToEventResponse(event);
    }

    public List<EventResponse> getEventsByLocation(String location) {
        List<Event> events = eventRepository.findByLocation(location);
        return events.stream().map(this::mapToEventResponse).toList();
    }

    public void updateEvent(String name, EventRequest eventRequest){
        Optional<Event> optionalEvent = eventRepository.findByName(name);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setName(eventRequest.getName());
            event.setLocation(eventRequest.getLocation());
            eventRepository.save(event);
        }
    }

    public void deleteEvent(String name) {
        Optional<Event> optionalEvent = eventRepository.findByName(name);
        if (optionalEvent.isPresent()) {
            eventRepository.delete(optionalEvent.get());
        } else {
            throw new RuntimeException("Event not found with name: " + name);
        }
    }

    private EventResponse mapToEventResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .date(event.getDate())
                .location(event.getLocation())
                .build();
    }
}
