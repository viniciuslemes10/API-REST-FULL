package br.com.erudio.unitests.mapper;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.unitests.mapper.mocks.PersonMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import  static org.junit.jupiter.api.Assertions.*;

public class DozerMapperTest {
    PersonMock inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new PersonMock();
    }

    @Test
    public void parseEntityToVOTest() {
        PersonVO output = DozerMapper.parseObject(inputObject.mockEntity(), PersonVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("First Name: 0", output.getFirstName());
        assertEquals("Last Name: 0", output.getLastName());
        assertEquals("Address: 0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PersonVO> outputList = DozerMapper.parseListObject(inputObject.mockEntityList(), PersonVO.class);
        PersonVO personVOZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), personVOZero.getKey());
        assertEquals("First Name: 0", personVOZero.getFirstName());
        assertEquals("Last Name: 0", personVOZero.getLastName());
        assertEquals("Address: 0", personVOZero.getAddress());
        assertEquals("Male", personVOZero.getGender());

        PersonVO personVOSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), personVOSeven.getKey());
        assertEquals("First Name: 7", personVOSeven.getFirstName());
        assertEquals("Last Name: 7", personVOSeven.getLastName());
        assertEquals("Address: 7", personVOSeven.getAddress());
        assertEquals("Female", personVOSeven.getGender());

        PersonVO personVOTweLve = outputList.get(12);

        assertEquals(Long.valueOf(12L), personVOTweLve.getKey());
        assertEquals("First Name: 12", personVOTweLve.getFirstName());
        assertEquals("Last Name: 12", personVOTweLve.getLastName());
        assertEquals("Address: 12", personVOTweLve.getAddress());
        assertEquals("Male", personVOTweLve.getGender());
    }
}
