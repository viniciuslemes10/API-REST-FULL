package br.com.erudio.junitTest.mockito.service;

import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.model.books.Books;
import br.com.erudio.repositories.books.BooksRepository;
import br.com.erudio.services.books.BooksService;
import br.com.erudio.unitests.mapper.mocks.BooksMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BooksServiceTest {
    BooksMock input;

    @InjectMocks
    BooksService service;

    @Mock
    BooksRepository repository;

    @BeforeEach
    void setUp() {
        input = new BooksMock();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test by creating a book successfully")
    void testCreated() {
        Books entity = input.mockEntity(1);
        Books pesisted = entity;

        pesisted.setId(1L);

        BooksVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(pesisted);

        var result = service.created(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertNotNull(result.getAuthor());
        assertNotNull(result.getLinks());
        assertNotNull(result.getPrice());
        assertNotNull(result.getLaunchDate());
        assertEquals("Author: 1", result.getAuthor());
        assertEquals("Title: 1", result.getTitle());
        LocalDate dateFixed = LocalDate.of(2000, 10, 11);
        Date date = Date.from(dateFixed.atStartOfDay().toInstant(ZoneOffset.UTC));
        assertEquals(date, result.getLaunchDate());
        assertEquals(Double.valueOf(1D), result.getPrice());
    }

    @Test
    @DisplayName("Test searching for book successfully")
    void testFindById() {
        Books books = input.mockEntity(1);
        books.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(books));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertNotNull(result.getAuthor());
        assertNotNull(result.getLinks());
        assertNotNull(result.getPrice());
        assertNotNull(result.getLaunchDate());
        assertEquals("Author: 1", result.getAuthor());
        assertEquals("Title: 1", result.getTitle());
        LocalDate dateFixed = LocalDate.of(2000, 10, 11);
        Date date = Date.from(dateFixed.atStartOfDay().toInstant(ZoneOffset.UTC));
        assertEquals(date, result.getLaunchDate());
        assertEquals(Double.valueOf(1D), result.getPrice());
    }

//    @Test
//    @DisplayName("Searching all books successfully")
//    void findAll() {
//        List<Books> list = input.mockListEntity();
//        when(repository.findAll()).thenReturn(list);
//
//        var books = service.findAll();
//        for (int i = 1; i < books.size(); i++) {
//            assertNotNull(books.get(i));
//            assertNotNull(books.get(i).getKey());
//            assertNotNull(books.get(i).getLinks());
//            assertTrue(books.get(i).toString().contains("links: [</api/books/v1/"+i+">;rel=\"self\"]"));
//            assertEquals("Author: " + i, books.get(i).getAuthor());
//            assertEquals("Title: " + i, books.get(i).getTitle());
//            LocalDate dateFixed = LocalDate.of(2000, 10, 11);
//            Date date = Date.from(dateFixed.atStartOfDay().toInstant(ZoneOffset.UTC));
//            assertEquals(date, books.get(i).getLaunchDate());
//            assertEquals(i, books.get(i).getPrice());
//        }
//    }

    @Test
    @DisplayName("Successful book update test")
    void testUpdate() {
        Books entity = input.mockEntity(1);
        Books pesisted = entity;

        pesisted.setId(1L);

        BooksVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(pesisted);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertTrue(result.toString().contains("links: [</api/books/v1>;rel=\"self\"]"));
        assertNotNull(result.getAuthor());
        assertNotNull(result.getLinks());
        assertNotNull(result.getPrice());
        assertNotNull(result.getLaunchDate());
        assertEquals("Author: 1", result.getAuthor());
        assertEquals("Title: 1", result.getTitle());
        LocalDate dateFixed = LocalDate.of(2000, 10, 11);
        Date date = Date.from(dateFixed.atStartOfDay().toInstant(ZoneOffset.UTC));
        assertEquals(date, result.getLaunchDate());
        assertEquals(Double.valueOf(1D), result.getPrice());

    }

    @Test
    @DisplayName("Successfully deleting book")
    void testDelete() {
        Books books = input.mockEntity();
        books.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(books));

        service.delete(1L);
    }
}