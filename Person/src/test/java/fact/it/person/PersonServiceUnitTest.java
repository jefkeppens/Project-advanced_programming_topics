package fact.it.person;

import fact.it.person.dto.PersonRequest;
import fact.it.person.dto.PersonResponse;
import fact.it.person.model.Person;
import fact.it.person.repository.PersonRepository;
import fact.it.person.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceUnitTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Test
    public void testCreatePerson_Success() {
        // Arrange
        PersonRequest personRequest = PersonRequest.builder()
                .name("John Doe")
                .phone("123456789")
                .email("john.doe@example.com")
                .visitor(true)
                .build();

        when(personRepository.findByEmail(personRequest.getEmail())).thenReturn(Optional.empty());
        when(personRepository.findByPhone(personRequest.getPhone())).thenReturn(Optional.empty());

        // Act
        boolean result = personService.createPerson(personRequest);

        // Assert
        assertTrue(result);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void testCreatePerson_EmailAlreadyExists() {
        // Arrange
        PersonRequest personRequest = PersonRequest.builder()
                .name("Jane Doe")
                .phone("987654321")
                .email("jane.doe@example.com")
                .visitor(false)
                .build();

        when(personRepository.findByEmail(personRequest.getEmail())).thenReturn(Optional.of(new Person()));
        when(personRepository.findByPhone(personRequest.getPhone())).thenReturn(Optional.empty());

        // Act
        boolean result = personService.createPerson(personRequest);

        // Assert
        assertFalse(result);
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    public void testGetByEmail_Success() {
        // Arrange
        String email = "john.doe@example.com";
        Person person = Person.builder()
                .id("1")
                .name("John Doe")
                .email(email)
                .phone("123456789")
                .visitor(true)
                .build();

        when(personRepository.findByEmail(email)).thenReturn(Optional.of(person));

        // Act
        PersonResponse personResponse = personService.getByEmail(email);

        // Assert
        assertNotNull(personResponse);
        assertEquals(email, personResponse.getEmail());
        verify(personRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testGetByEmail_NotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(personRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        PersonResponse personResponse = personService.getByEmail(email);

        // Assert
        assertNull(personResponse);
        verify(personRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testGetAllPeople() {
        // Arrange
        Person person1 = Person.builder()
                .id("1")
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("123456789")
                .visitor(true)
                .build();

        Person person2 = Person.builder()
                .id("2")
                .name("Jane Smith")
                .email("jane.smith@example.com")
                .phone("987654321")
                .visitor(false)
                .build();

        when(personRepository.findAll()).thenReturn(Arrays.asList(person1, person2));

        // Act
        List<PersonResponse> people = personService.getAllPeople();

        // Assert
        assertEquals(2, people.size());
        assertEquals("John Doe", people.get(0).getName());
        assertEquals("Jane Smith", people.get(1).getName());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void testUpdatePerson_Success() {
        // Arrange
        String email = "john.doe@example.com";
        Person existingPerson = Person.builder()
                .id("1")
                .name("John Doe")
                .email(email)
                .phone("123456789")
                .visitor(true)
                .build();

        PersonRequest updatedPersonRequest = PersonRequest.builder()
                .name("John Doe Updated")
                .phone("111222333")
                .email(email)
                .visitor(false)
                .build();

        when(personRepository.findByEmail(email)).thenReturn(Optional.of(existingPerson));

        // Act
        boolean result = personService.updatePerson(email, updatedPersonRequest);

        // Assert
        assertTrue(result);
        assertEquals("John Doe Updated", existingPerson.getName());
        assertEquals("111222333", existingPerson.getPhone());
        verify(personRepository, times(1)).save(existingPerson);
    }

    @Test
    public void testUpdatePerson_NotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        PersonRequest updatedPersonRequest = PersonRequest.builder()
                .name("Updated Name")
                .phone("123123123")
                .email(email)
                .visitor(true)
                .build();

        when(personRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = personService.updatePerson(email, updatedPersonRequest);

        // Assert
        assertFalse(result);
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    public void testRemovePerson_Success() {
        // Arrange
        String email = "john.doe@example.com";
        Person person = Person.builder()
                .id("1")
                .name("John Doe")
                .email(email)
                .phone("123456789")
                .visitor(true)
                .build();

        when(personRepository.findByEmail(email)).thenReturn(Optional.of(person));

        // Act
        boolean result = personService.removePerson(email);

        // Assert
        assertTrue(result);
        verify(personRepository, times(1)).delete(person);
    }

    @Test
    public void testRemovePerson_NotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(personRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = personService.removePerson(email);

        // Assert
        assertFalse(result);
        verify(personRepository, never()).delete(any(Person.class));
    }
}
