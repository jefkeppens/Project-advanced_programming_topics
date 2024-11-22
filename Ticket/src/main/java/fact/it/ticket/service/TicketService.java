package fact.it.ticket.service;

import fact.it.ticket.dto.PersonResponse;
import fact.it.ticket.dto.TicketRequest;
import fact.it.ticket.dto.TicketResponse;
import fact.it.ticket.model.Ticket;
import fact.it.ticket.repository.TicketRepository;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    private final WebClient webClient;


    @Value("${PERSON_SERVICE_BASEURL}")
    private String personServiceBaseUrl;

    public boolean orderTicket(TicketRequest ticketRequest) {
        try {
            PersonResponse[] personResponse = webClient.get()
                    .uri(personServiceBaseUrl + "/api/person/" + ticketRequest.getPersonEmail())
                    .retrieve()
                    .bodyToMono(PersonResponse[].class)
                    .block();
            if (personResponse != null && personResponse[0].isVisitor()) {
                Ticket ticket = Ticket.builder()
                        .ticketNumber(UUID.randomUUID().toString())
                        .eventName(ticketRequest.getEventName())
                        .purchaseDate(LocalDate.now())
                        .personEmail(ticketRequest.getPersonEmail())
                        .build();

                ticketRepository.save(ticket);

                return true;
            }
        } catch(WebClientRequestException exception) {
            return false;
        }

        return false;
    }


    public boolean updateTicket(String ticketNumber, TicketRequest ticketRequest) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber).get(0);

        ticket.setEventName(ticketRequest.getEventName());
        ticket.setPersonEmail(ticketRequest.getPersonEmail());

        ticketRepository.updateByTicketNumber(ticketNumber, ticket);

        return true;
    }

    public boolean removeTicket(String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber).get(0);

        ticketRepository.delete(ticket);

        return true;
    }

    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream().map(this::mapToTicketResponse).toList();
    }

    public TicketResponse getByTicketNumber(String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber).get(0);

        return mapToTicketResponse(ticket);
    }

    private TicketResponse mapToTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketNumber(ticket.getTicketNumber())
                .purchaseDate(ticket.getPurchaseDate())
                .eventName(ticket.getEventName())
                .build();

    }
}
