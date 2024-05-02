package br.com.erudio.controllers;

import br.com.erudio.model.Person;
import br.com.erudio.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/person")
@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @RequestMapping(method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll() {
        return personService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    public Person findById(@PathVariable(value = "id") String id) {
        return personService.findById(id);
    }
   }