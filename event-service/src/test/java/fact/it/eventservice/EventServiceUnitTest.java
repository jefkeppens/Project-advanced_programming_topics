package fact.it.eventservice;

import fact.it.eventservice.dto.EventRequest;
import fact.it.eventservice.dto.EventResponse;
import fact.it.eventservice.model.Event;
import fact.it.eventservice.repository.EventRepository;
import fact.it.eventservice.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceUnitTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    public void testCreateEvent() {
        // Arrange
        EventRequest eventRequest = new EventRequest();
        eventRequest.setName("Test Event");
        eventRequest.setLocation("Test Location");

        // Act
        eventService.createEvent(eventRequest);

        // Assert
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    public void testGetAllEvents() {
        // Arrange
        Event event1 = new Event(1L, "Event 1", LocalDate.now(), "Location 1");
        Event event2 = new Event(2L, "Event 2", LocalDate.now(), "Location 2");

        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        // Act
        List<EventResponse> events = eventService.getAllEvents();

        // Assert
        assertEquals(2, events.size());
        assertEquals("Event 1", events.get(0).getName());
        assertEquals("Location 1", events.get(0).getLocation());

        verify(eventRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateEvent() {
        // Arrange
        String existingEventName = "Event 1";
        Event existingEvent = new Event(1L, existingEventName, LocalDate.now(), "Old Location");

        EventRequest updateRequest = new EventRequest();
        updateRequest.setName("Updated Event");
        updateRequest.setLocation("New Location");

        when(eventRepository.findByName(existingEventName)).thenReturn(Optional.of(existingEvent));

        // Act
        eventService.updateEvent(existingEventName, updateRequest);

        // Assert
        assertEquals("Updated Event", existingEvent.getName());
        assertEquals("New Location", existingEvent.getLocation());
        verify(eventRepository, times(1)).save(existingEvent);
    }

    @Test
    public void testUpdateEvent_NotFound() {
        // Arrange
        String nonExistentEventName = "Non-existent Event";
        EventRequest updateRequest = new EventRequest();
        updateRequest.setName("Updated Event");
        updateRequest.setLocation("New Location");

        when(eventRepository.findByName(nonExistentEventName)).thenReturn(Optional.empty());

        // Act
        eventService.updateEvent(nonExistentEventName, updateRequest);

        // Assert
        verify(eventRepository, times(0)).save(any(Event.class));
    }

    @Test
    public void testDeleteEvent() {
        // Arrange
        String existingEventName = "Event 1";
        Event existingEvent = new Event(1L, existingEventName, LocalDate.now(), "Location 1");

        when(eventRepository.findByName(existingEventName)).thenReturn(Optional.of(existingEvent));

        // Act
        eventService.deleteEvent(existingEventName);

        // Assert
        verify(eventRepository, times(1)).delete(existingEvent);
    }

    @Test
    public void testDeleteEvent_NotFound() {
        // Arrange
        String nonExistentEventName = "Non-existent Event";

        when(eventRepository.findByName(nonExistentEventName)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> eventService.deleteEvent(nonExistentEventName));
        assertEquals("Event not found with name: Non-existent Event", exception.getMessage());

        verify(eventRepository, times(0)).delete(any(Event.class));
    }
}
