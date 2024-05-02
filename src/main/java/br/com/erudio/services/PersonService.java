package br.com.erudio.services;

import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(String id) {
        logger.info("Finding one person");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Vinicius");
        person.setLastName("Lemes");
        person.setAddress("São Paulo - São Paulo - Brasil");
        person.setGender("Male");
        return person;
    }
}
