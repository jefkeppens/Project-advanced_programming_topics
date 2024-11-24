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
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public boolean createPerson(PersonRequest personRequest) {
        try {
            Optional<Person> optionalPerson = personRepository.findByEmail(personRequest.getEmail());
            Optional<Person> optionalPerson1 = personRepository.findByPhone(personRequest.getPhone());
            if (optionalPerson.isPresent() || optionalPerson1.isPresent()) {
                return false;
            }
            Person person = Person.builder()
                    .name(personRequest.getName())
                    .email(personRequest.getEmail())
                    .phone(personRequest.getPhone())
                    .visitor(personRequest.isVisitor())
                    .build();

            personRepository.save(person);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public PersonResponse getByEmail(String email) {
        Optional<Person> optionalPerson = personRepository.findByEmail(email);

        return optionalPerson.map(this::mapToPersonResponse).orElse(null);
    }

    public List<PersonResponse> getAllPeople() {
        List<Person> people = personRepository.findAll();

        return people.stream().map(this::mapToPersonResponse).toList();
    }

    public boolean updatePerson(String personEmail, PersonRequest personRequest) {
        Optional<Person> optionalPerson = personRepository.findByEmail(personEmail);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setName(personRequest.getName());
            person.setVisitor(personRequest.isVisitor());
            person.setPhone(personRequest.getPhone());
            person.setEmail(personRequest.getEmail());
            personRepository.save(person);
            return true;
        } else {
            return false;
        }
    }

    public boolean removePerson(String personEmail) {
        Optional<Person> optionalPerson = personRepository.findByEmail(personEmail);

        if (optionalPerson.isPresent()) {
            personRepository.delete(optionalPerson.get());
            return true;
        } else {
            return false;
        }
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
