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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String orderTicket(@RequestBody TicketRequest ticketRequest) {
        boolean result = ticketService.orderTicket(ticketRequest);
        return (result ? "Tickets ordered successfully" : "Failed to order tickets");
    }

    @DeleteMapping("/{ticketNumber}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTicket(@PathVariable String ticketNumber) {
        boolean result = ticketService.removeTicket(ticketNumber);
        return (result ? "Ticket successfully deleted" : "Failed to delete ticket");
    }

    @PutMapping("/{ticketNumber}")
    @ResponseStatus(HttpStatus.OK)
    public String updateTicket(@PathVariable String ticketNumber, @RequestBody TicketRequest ticketRequest) {
        boolean result = ticketService.updateTicket(ticketNumber, ticketRequest);
        return (result ? "Ticket updated successfully" : "Failed to update ticket");
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TicketResponse> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public TicketResponse getByTicketNumber(@RequestParam String ticketNumber) {
        return ticketService.getByTicketNumber(ticketNumber);
    }
}
