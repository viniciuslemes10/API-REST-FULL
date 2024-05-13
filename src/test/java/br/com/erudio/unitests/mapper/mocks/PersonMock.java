package br.com.erudio.unitests.mapper.mocks;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.model.person.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonMock {
    public Person mockEntity() {
        return mockEntity(0);
    }

    public PersonVO mockVO() {
        return mockVO(0);
    }

    public List<PersonVO> mockVOList() {
        List<PersonVO> personVOS = new ArrayList<>();
        for(int i = 0; i < 14; i++){
            personVOS.add(mockVO(i));
        }
        return personVOS;
    }

    public List<Person> mockEntityList() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            personList.add(mockEntity(i));
        }
        return personList;
    }

    public PersonVO mockVO(Integer number) {
        PersonVO personVO = new PersonVO();
        personVO.setFirstName("First Name: " + number);
        personVO.setLastName("Last Name: " + number);
        personVO.setAddress("Address: " + number);
        personVO.setGender(((number % 2) == 0) ? "Male" : "Female");
        personVO.setKey(number.longValue());
        return personVO;
    }

    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setFirstName("First Name: " + number);
        person.setLastName("Last Name: " + number);
        person.setAddress("Address: " + number);
        person.setGender(((number % 2) == 0) ? "Male" : "Female");
        person.setId(number.longValue());
        return person;
    }
}
