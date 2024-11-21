package fact.it.person.service;

import fact.it.person.dto.PersonRequest;
import fact.it.person.dto.PersonResponse;
import fact.it.person.model.Person;
import fact.it.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final WebClient webClient;

    public void createPerson(PersonRequest personRequest) {
        Person person = Person.builder()
                .name(personRequest.getName())
                .email(personRequest.getEmail())
                .phone(personRequest.getPhone())
                .visitor(personRequest.isVisitor())
                .build();

        personRepository.save(person);
    }

    public List<PersonResponse> getByEmail(String email) {
        List<Person> people = personRepository.findByEmail(email);

        return people.stream().map(this::mapToPersonResponse).toList();
    }

    public List<PersonResponse> getAllPeople() {
        List<Person> people = personRepository.findAll();

        return people.stream().map(this::mapToPersonResponse).toList();
    }

    private PersonResponse mapToPersonResponse(Person person) {
        return PersonResponse.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .phone(person.getPhone())
                .visitor(person.isVisitor())
                .build();

    }
}
