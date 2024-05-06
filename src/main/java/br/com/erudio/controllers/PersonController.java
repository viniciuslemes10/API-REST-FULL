package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.services.PersonService;
import br.com.erudio.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/person/v1")
@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_YML})
    public List<PersonVO> findAll() {
        return personService.findAll();
    }

    @GetMapping(value = "/{id}",
            produces =  {MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_YML})
    public PersonVO findById(@PathVariable(value = "id") Long id) {
        return personService.findById(id);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_YML},
            consumes= {MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_YML})
    public ResponseEntity<PersonVO> create(@RequestBody PersonVO personVO) {
        PersonVO personVOCreated = personService.create(personVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personVOCreated);
    }

    @PostMapping(value = "/v2",
            produces= {MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_YML})
    public ResponseEntity<PersonVOV2> createV2(@RequestBody PersonVOV2 personVO) {
        PersonVOV2 personVOCreated = personService.createV2(personVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personVOCreated);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_YML})
    public PersonVO update(@RequestBody PersonVO person) {
        return personService.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
   }