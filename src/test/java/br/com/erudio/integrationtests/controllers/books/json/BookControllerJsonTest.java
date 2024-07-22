package br.com.erudio.integrationtests.controllers.books.json;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.AccountCredentialsVO;
import br.com.erudio.integrationtests.vo.BooksVO;
import br.com.erudio.integrationtests.vo.TokenVO;
import br.com.erudio.integrationtests.wrappers.WrapperBooksVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerJsonTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static BooksVO books;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        books = new BooksVO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException {

        AccountCredentialsVO user = new AccountCredentialsVO("Vinicius", "lemes");

        var accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/books/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        mockBooks();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(books)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        BooksVO persistedBooks = objectMapper.readValue(content, BooksVO.class);
        books = persistedBooks;

        assertNotNull(persistedBooks);

        assertNotNull(persistedBooks.getId());
        assertNotNull(persistedBooks.getAuthor());
        assertNotNull(persistedBooks.getLaunchDate());
        assertNotNull(persistedBooks.getPrice());
        assertNotNull(persistedBooks.getTitle());

        assertTrue(persistedBooks.getId() > 0);

        assertEquals("Robert C. Martin", persistedBooks.getAuthor());
        assertEquals(89.99, persistedBooks.getPrice());
        assertEquals("Clean Code", persistedBooks.getTitle());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        books.setPrice(90.00);

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(books)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BooksVO persistedBooks = objectMapper.readValue(content, BooksVO.class);
        books = persistedBooks;

        assertNotNull(persistedBooks);

        assertNotNull(persistedBooks.getId());
        assertNotNull(persistedBooks.getAuthor());
        assertNotNull(persistedBooks.getLaunchDate());
        assertNotNull(persistedBooks.getPrice());
        assertNotNull(persistedBooks.getTitle());

        assertTrue(persistedBooks.getId() > 0);

        assertEquals("Robert C. Martin", persistedBooks.getAuthor());
        assertEquals(90.00, persistedBooks.getPrice());
        assertEquals("Clean Code", persistedBooks.getTitle());
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        mockBooks();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", books.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BooksVO persistedBooks = objectMapper.readValue(content, BooksVO.class);
        books = persistedBooks;

        assertNotNull(persistedBooks);

        assertNotNull(persistedBooks.getId());
        assertNotNull(persistedBooks.getAuthor());
        assertNotNull(persistedBooks.getLaunchDate());
        assertNotNull(persistedBooks.getPrice());
        assertNotNull(persistedBooks.getTitle());

        assertTrue(persistedBooks.getId() > 0);

        assertEquals("Robert C. Martin", persistedBooks.getAuthor());
        assertEquals(90.00, persistedBooks.getPrice());
        assertEquals("Clean Code", persistedBooks.getTitle());
    }

    @Test
    @Order(4)
    public void testDelete() throws JsonMappingException, JsonProcessingException {

        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", books.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    public void testFindAll() throws JsonProcessingException, JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 0, "size", 10, "direction", "asc")
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            //.as(new TypeRef<List<PersonVO>>() {});
                            .asString();

        WrapperBooksVO persistedBooks = objectMapper.readValue(content, WrapperBooksVO.class);
        var books = persistedBooks.getEmbeddedBooks().getBooksVOS();

        BooksVO foundBookOne = books.get(1);
        assertEquals(9,foundBookOne.getId());

        assertNotNull(foundBookOne);
        assertNotNull(foundBookOne.getId());
        assertNotNull(foundBookOne.getAuthor());
        assertNotNull(foundBookOne.getLaunchDate());
        assertNotNull(foundBookOne.getPrice());
        assertNotNull(foundBookOne.getTitle());

        assertEquals("Brian Goetz e Tim Peierls", foundBookOne.getAuthor());
        assertEquals(80.00, foundBookOne.getPrice());
        assertEquals("Java Concurrency in Practice", foundBookOne.getTitle());

        BooksVO foundBooksFour = books.get(3);
        assertEquals(8, foundBooksFour.getId());

        assertNotNull(foundBooksFour);
        assertNotNull(foundBooksFour.getId());
        assertNotNull(foundBooksFour.getAuthor());
        assertNotNull(foundBooksFour.getLaunchDate());
        assertNotNull(foundBooksFour.getPrice());
        assertNotNull(foundBooksFour.getTitle());

        assertEquals("Eric Evans", foundBooksFour.getAuthor());
        assertEquals(92.00, foundBooksFour.getPrice());
        assertEquals("Domain Driven Design", foundBooksFour.getTitle());
    }

    @Test
    @Order(6)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/books/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given().spec(specificationWithoutToken)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(403);
    }

    private void mockBooks() {
        books.setAuthor("Robert C. Martin");
        LocalDate localDate = LocalDate.of(2023, 6, 13);
        books.setLaunchDate(new Date());
        books.setPrice(89.99);
        books.setTitle("Clean Code");
    }
}
