package br.com.erudio.unitests.mapper;

import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.unitests.mapper.mocks.BooksMock;
import br.com.erudio.unitests.mapper.mocks.PersonMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import  static org.junit.jupiter.api.Assertions.*;

public class DozerMapperTest {
    PersonMock inputObject;
    BooksMock inputObjectBook;

    @BeforeEach
    public void setUp() {
        inputObject = new PersonMock();
        inputObjectBook = new BooksMock();
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
    public void parseEntityToVOBookTest() {
        BooksVO output = DozerMapper.parseObject(inputObjectBook.mockEntity(), BooksVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("Author: 0", output.getAuthor());
        assertEquals("Title: 0", output.getTitle());
        LocalDate dateFixed = LocalDate.of(2000, 10, 11);
        Date date = Date.from(dateFixed.atStartOfDay().toInstant(ZoneOffset.UTC));
        assertEquals(date, output.getLaunchDate());
        assertEquals(Double.valueOf(0D), output.getPrice());
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

    @Test
    public void parseEntityListToVOListBookTest() {
        List<BooksVO> outputList = DozerMapper.parseListObject(inputObjectBook.mockListEntity(), BooksVO.class);
        BooksVO vo1 = outputList.get(0);

        assertEquals(Long.valueOf(0L), vo1.getKey());
        assertEquals("Author: 0", vo1.getAuthor());
        assertEquals("Title: 0", vo1.getTitle());
        LocalDate dateFixed = LocalDate.of(2000, 10, 11);
        Date date = Date.from(dateFixed.atStartOfDay().toInstant(ZoneOffset.UTC));
        assertEquals(date, vo1.getLaunchDate());
        assertEquals(Double.valueOf(0D), vo1.getPrice());

        BooksVO vo8 = outputList.get(8);

        assertEquals(Long.valueOf(8L), vo8.getKey());
        assertEquals("Author: 8", vo8.getAuthor());
        assertEquals("Title: 8", vo8.getTitle());
        LocalDate dateFixed8 = LocalDate.of(2000, 10, 11);
        Date date8 = Date.from(dateFixed.atStartOfDay().toInstant(ZoneOffset.UTC));
        assertEquals(date8, vo8.getLaunchDate());
        assertEquals(Double.valueOf(8D), vo8.getPrice());
    }
}
