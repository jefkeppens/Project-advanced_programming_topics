package fact.it.person.controller;

import fact.it.person.dto.PersonRequest;
import fact.it.person.dto.PersonResponse;
import fact.it.person.model.Person;
import fact.it.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createPerson(@RequestBody PersonRequest personRequest) {
        personService.createPerson(personRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonResponse> getByEmail(@RequestParam String email) {
        return personService.getByEmail(email);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonResponse> getAllPeople() {
        return personService.getAllPeople();
    }
}
