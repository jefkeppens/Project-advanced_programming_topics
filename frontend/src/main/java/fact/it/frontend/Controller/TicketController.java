package fact.it.frontend.Controller;

import fact.it.frontend.Service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    public String events(Model model){
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "tickets";
    }
}
