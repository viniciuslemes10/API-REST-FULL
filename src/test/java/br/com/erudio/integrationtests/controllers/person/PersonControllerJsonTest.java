package br.com.erudio.integrationtests.controllers.person;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.AccountCredentialsVO;
import br.com.erudio.integrationtests.vo.PersonVO;
import br.com.erudio.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
    import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;

    private static ObjectMapper objectMapper;

    private static PersonVO person;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = new PersonVO();
    }

    @Test
    @Order(0)
    public void testAuthorization() {
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
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockPerson();

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(person)
                    .when()
                        .post()
                .then()
                    .statusCode(201)
                        .extract()
                            .body()
                                .asString();

        PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
        assertTrue(createdPerson.getId() > 0);

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getGender());

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallman", createdPerson.getLastName());
        assertEquals("New York City, New York, US", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(2)
    public void testFindById() throws JsonProcessingException {
        mockPerson();

        var content = given()
                .spec(specification)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .pathParam("id",person.getId())
                    .when()
                        .get("{id}")
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        PersonVO personCreate = objectMapper.readValue(content, PersonVO.class);
        System.out.println(personCreate.getId());
        assertNotNull(personCreate);
        assertNotNull(personCreate.getId());
        assertNotNull(personCreate.getFirstName());
        assertNotNull(personCreate.getLastName());
        assertNotNull(personCreate.getGender());
        assertNotNull(personCreate.getGender());

        assertTrue(personCreate.getId() > 0);

        assertEquals("Richard", personCreate.getFirstName());
        assertEquals("Stallman", personCreate.getLastName());
        assertEquals("New York City, New York, US", personCreate.getAddress());
        assertEquals("Male", personCreate.getGender());

    }

    @Test
    @Order(3)
    public void testUpdate() throws JsonProcessingException {
        person.setLastName("Stallman Last");

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(person)
                    .when()
                        .post()
                .then()
                    .statusCode(201)
                        .extract()
                            .body()
                                .asString();

        PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
        assertTrue(createdPerson.getId() > 0);

        assertEquals(person.getId(), createdPerson.getId());
        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getGender());

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallman Last", createdPerson.getLastName());
        assertEquals("New York City, New York, US", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(4)
    public void testDelete() {
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .pathParam("id", person.getId())
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
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            //.as(new TypeRef<List<PersonVO>>() {});
                            .asString();

        List<PersonVO> persistedPerson = objectMapper.readValue(content, new TypeReference<List<PersonVO>>() {});
        PersonVO foundPersonOne = persistedPerson.get(0);
        assertEquals(3,foundPersonOne.getId());

        assertNotNull(foundPersonOne);
        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getGender());

        assertEquals("Lucas", foundPersonOne.getFirstName());
        assertEquals("Souza", foundPersonOne.getLastName());
        assertEquals("Bragança - São Paulo - Brasil", foundPersonOne.getAddress());
        assertEquals("Male", foundPersonOne.getGender());

        PersonVO foundPersonSeven = persistedPerson.get(2);
        assertEquals(7, foundPersonSeven.getId());

        assertNotNull(foundPersonSeven);
        assertNotNull(foundPersonSeven.getId());
        assertNotNull(foundPersonSeven.getAddress());
        assertNotNull(foundPersonSeven.getFirstName());
        assertNotNull(foundPersonSeven.getLastName());
        assertNotNull(foundPersonSeven.getGender());

        assertEquals("Carlos", foundPersonSeven.getFirstName());
        assertEquals("Souza", foundPersonSeven.getLastName());
        assertEquals("São Caetano - São Paulo", foundPersonSeven.getAddress());
        assertEquals("Male", foundPersonSeven.getGender());
    }

    @Test
    @Order(6)
    public void testFindAllWithoutToken() throws JsonProcessingException, JsonProcessingException {

        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/person/v1")
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

    private void mockPerson() {
        person.setId(1L);
        person.setFirstName("Richard");
        person.setLastName("Stallman");
        person.setAddress("New York City, New York, US");
        person.setGender("Male");
    }
}
