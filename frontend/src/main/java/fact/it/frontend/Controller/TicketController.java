package fact.it.frontend.Controller;

import fact.it.frontend.Service.AuthService;
import fact.it.frontend.Service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TicketController {

    private final TicketService ticketService;
    private final AuthService authService;

    public TicketController(TicketService ticketService, AuthService authService) {
        this.ticketService = ticketService;
        this.authService = authService;
    }

    @GetMapping("/tickets")
    public String events(Model model){
        if(authService.getToken() == null){
            return "errorunauth";
        }
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "tickets";
    }
}
