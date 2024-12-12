package fact.it.frontend.Controller;


import fact.it.frontend.Request.PersonRequest;
import fact.it.frontend.Service.AuthService;
import fact.it.frontend.Service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/people/add")
    public String addEventPage() {
        if(authService.getToken() == null){
            return "errorunauth";
        }
        return "addPeople";
    }

    @PostMapping("/people/add")
    public String addPeople(@RequestParam("name") String name, @RequestParam("visitor") boolean visitor,
                            @RequestParam("phone") String phone, @RequestParam("email") String email, Model model) {
        PersonRequest personRequest = new PersonRequest(name, visitor, phone, email);
        String responseMessage = (String) personService.addPerson(personRequest); // Expect a String
        System.out.println(responseMessage); // Log the response if needed
        model.addAttribute("people", personService.getAllPeople());
        return "people";
    }

    @RequestMapping("/people/delete/{personEmail}")
    public String deletePerson(@PathVariable String personEmail, Model model) {
        String responseMessage = (String) personService.deletePerson(personEmail); // Expect a String
        System.out.println(responseMessage); // Log the response if needed
        model.addAttribute("people", personService.getAllPeople());
        return "people";
    }

}
