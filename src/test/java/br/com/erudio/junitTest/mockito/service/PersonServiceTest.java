package br.com.erudio.junitTest.mockito.service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNotNullException;
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
    @DisplayName("Checks that the link is not null and is correct.")
    void testFindById() {
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
    @DisplayName("Checks the person creation and whether it contains all the correct information.")
    void testCreate() {
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

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
    @DisplayName("Catching exception when data is null in the create method")
    void testCreateWhenItDataIsNull() {
        Exception exception = assertThrows(RequiredObjectIsNotNullException.class, () -> {
            service.create(null);
        });

        String actualMessage = "It is not allowed to persist a null object";
        String expectedMessage = exception.getMessage();
        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    @DisplayName("Checks if the update method has all the correct information.")
    void testUpdate() {
        Person entity = input.mockEntity(1);
        Person persisted = input.mockEntity(1);
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);
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
    @DisplayName("Catching exception when data is null in the update method")
    void testUpdateWhenItDataIsNull() {
        Exception exception = assertThrows(RequiredObjectIsNotNullException.class, () -> {
            service.update(null);
        });
        String actualMessage = "It is not allowed to persist a null object";
        String expectedMessage = exception.getMessage();
        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    @DisplayName("Checks if the person was deleted successfully.")
    void delete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    void findAll() {

    }


}