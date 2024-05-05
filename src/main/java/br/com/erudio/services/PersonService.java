package br.com.erudio.services;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository repository;

    public List<PersonVO> findAll() {
        logger.info("Finding all people");

        return DozerMapper.parseListObject(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person");
        var entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID!"));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO personVO) {
        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(personVO, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Updating one person");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person");

        var person = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(person);
    }

}
