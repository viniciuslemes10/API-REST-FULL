package br.com.erudio.junitTest.mockito.service;

import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.PersonService;
import br.com.erudio.unitests.mapper.mocks.PersonMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    PersonMock input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new PersonMock();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Checking that the link is not null and is correct.")
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name: 1", result.getFirstName());
        assertEquals("Last Name: 1", result.getLastName());
        assertEquals("Address: 1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}