package fact.it.person.controller;

import fact.it.person.dto.PersonRequest;
import fact.it.person.dto.PersonResponse;
import fact.it.person.model.Person;
import fact.it.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping
    public ResponseEntity<String> createPerson(@RequestBody PersonRequest personRequest) {
        boolean result = personService.createPerson(personRequest);
        return result
                ? ResponseEntity.status(HttpStatus.CREATED).body("Person created successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create person or email might already exist");
    }

    @GetMapping("/{email}")
    public ResponseEntity<PersonResponse> getByEmail(@PathVariable String email) {
        PersonResponse personResponse = personService.getByEmail(email);
        return personResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(personResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonResponse>> getAllPeople() {
        List<PersonResponse> people = personService.getAllPeople();
        return people.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(people)
                : ResponseEntity.status(HttpStatus.OK).body(people);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updatePerson(@PathVariable String email, @RequestBody PersonRequest personRequest) {
        boolean result = personService.updatePerson(email, personRequest);
        return result
                ? ResponseEntity.status(HttpStatus.OK).body("Person updated successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update person, email might not exist");
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deletePerson(@PathVariable String email) {
        boolean result = personService.removePerson(email);
        return result
                ? ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete person, email might not exist");
    }
}

