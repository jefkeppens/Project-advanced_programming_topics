package fact.it.ticket;

import fact.it.ticket.dto.PersonResponse;
import fact.it.ticket.dto.TicketRequest;
import fact.it.ticket.dto.TicketResponse;
import fact.it.ticket.model.Ticket;
import fact.it.ticket.repository.TicketRepository;
import fact.it.ticket.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceUnitTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ticketService, "personServiceBaseUrl", "http://localhost:8083");
    }

    @Test
    public void testOrderTicket_Success() {
        // Arrange
        TicketRequest ticketRequest = new TicketRequest("john.doe@example.com", "Concert");

        PersonResponse personResponse = new PersonResponse("1", "John Doe", "john.doe@example.com", "1234567890", true);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("http://localhost:8083/api/person/" + ticketRequest.getPersonEmail())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PersonResponse.class)).thenReturn(Mono.just(personResponse));

        Ticket ticket = Ticket.builder()
                .ticketNumber(UUID.randomUUID().toString())
                .eventName(ticketRequest.getEventName())
                .purchaseDate(LocalDate.now())
                .personEmail(ticketRequest.getPersonEmail())
                .build();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // Act
        boolean result = ticketService.orderTicket(ticketRequest);

        // Assert
        assertTrue(result);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testOrderTicket_Failure_PersonNotVisitor() {
        // Arrange
        TicketRequest ticketRequest = new TicketRequest("jane.doe@example.com", "Concert");

        PersonResponse personResponse = new PersonResponse("2", "Jane Doe", "jane.doe@example.com", "9876543210", false);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("http://localhost:8083/api/person/" + ticketRequest.getPersonEmail())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PersonResponse.class)).thenReturn(Mono.just(personResponse));

        // Act
        boolean result = ticketService.orderTicket(ticketRequest);

        // Assert
        assertFalse(result);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    public void testUpdateTicket() {
        // Arrange
        String ticketNumber = "12345";
        TicketRequest ticketRequest = new TicketRequest("jane.doe@example.com", "Updated Event");

        Ticket ticket = Ticket.builder()
                .ticketNumber(ticketNumber)
                .eventName("Original Event")
                .personEmail("john.doe@example.com")
                .purchaseDate(LocalDate.now())
                .build();

        when(ticketRepository.findByTicketNumber(ticketNumber)).thenReturn(List.of(ticket));

        // Act
        boolean result = ticketService.updateTicket(ticketNumber, ticketRequest);

        // Assert
        assertTrue(result);
        assertEquals("Updated Event", ticket.getEventName());
        assertEquals("jane.doe@example.com", ticket.getPersonEmail());
        verify(ticketRepository, times(1)).updateByTicketNumber(ticketNumber, ticket);
    }

    @Test
    public void testRemoveTicket() {
        // Arrange
        String ticketNumber = "12345";
        Ticket ticket = Ticket.builder()
                .ticketNumber(ticketNumber)
                .eventName("Concert")
                .personEmail("john.doe@example.com")
                .purchaseDate(LocalDate.now())
                .build();

        when(ticketRepository.findByTicketNumber(ticketNumber)).thenReturn(List.of(ticket));

        // Act
        boolean result = ticketService.removeTicket(ticketNumber);

        // Assert
        assertTrue(result);
        verify(ticketRepository, times(1)).delete(ticket);
    }

    @Test
    public void testGetAllTickets() {
        // Arrange
        Ticket ticket1 = Ticket.builder()
                .id(1)
                .ticketNumber("12345")
                .eventName("Concert")
                .personEmail("john.doe@example.com")
                .purchaseDate(LocalDate.now())
                .build();

        Ticket ticket2 = Ticket.builder()
                .id(2)
                .ticketNumber("67890")
                .eventName("Festival")
                .personEmail("jane.doe@example.com")
                .purchaseDate(LocalDate.now())
                .build();

        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));

        // Act
        List<TicketResponse> tickets = ticketService.getAllTickets();

        // Assert
        assertEquals(2, tickets.size());
        assertEquals("Concert", tickets.get(0).getEventName());
        assertEquals("Festival", tickets.get(1).getEventName());
        verify(ticketRepository, times(1)).findAll();
    }
}
