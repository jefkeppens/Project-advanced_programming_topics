package fact.it.frontend.Controller;


import fact.it.frontend.Service.AuthService;
import fact.it.frontend.Service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonController {

    private final PersonService personService;
    private final AuthService authService;

    public PersonController(PersonService personService, AuthService authService) {
        this.personService = personService;
        this.authService = authService;
    }

    @GetMapping("/people")
    public String events(Model model){
        if(authService.getToken() == null){
            return "errorunauth";
        }
        model.addAttribute("people", personService.getAllPeople());
        return "people";
    }
}
