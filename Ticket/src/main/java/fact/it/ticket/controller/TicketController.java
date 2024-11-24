package fact.it.ticket.controller;

import fact.it.ticket.dto.PersonResponse;
import fact.it.ticket.dto.TicketRequest;
import fact.it.ticket.dto.TicketResponse;
import fact.it.ticket.model.Ticket;
import fact.it.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<String> orderTicket(@RequestBody TicketRequest ticketRequest) {
        boolean result = ticketService.orderTicket(ticketRequest);
        return result
                ? ResponseEntity.status(HttpStatus.CREATED).body("Tickets ordered successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to order tickets or person not selected as visitor");
    }

    @DeleteMapping("/{ticketNumber}")
    public ResponseEntity<String> deleteTicket(@PathVariable String ticketNumber) {
        boolean result = ticketService.removeTicket(ticketNumber);
        return result
                ? ResponseEntity.status(HttpStatus.OK).body("Ticket successfully deleted")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete ticket or ticket doesn't exist");
    }

    @PutMapping("/{ticketNumber}")
    public ResponseEntity<String> updateTicket(@PathVariable String ticketNumber, @RequestBody TicketRequest ticketRequest) {
        boolean result = ticketService.updateTicket(ticketNumber, ticketRequest);
        return result
                ? ResponseEntity.status(HttpStatus.OK).body("Ticket updated successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update ticket or ticket doesn't exist");
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        List<TicketResponse> tickets = ticketService.getAllTickets();
        return tickets.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(tickets)
                : ResponseEntity.status(HttpStatus.OK).body(tickets);
    }

    @GetMapping("/{ticketNumber}")
    public ResponseEntity<TicketResponse> getByTicketNumber(@PathVariable String ticketNumber) {
        TicketResponse ticketResponse = ticketService.getByTicketNumber(ticketNumber);
        return ticketResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(ticketResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

