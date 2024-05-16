package br.com.erudio.integrationtests.controllers.withjson;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonVO person;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = new PersonVO();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockPerson();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "https://erudio.com.br")
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

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

        PersonVO personCreate = objectMapper.readValue(content, PersonVO.class);

        assertNotNull(personCreate);
        assertNotNull(personCreate.getId());
        assertNotNull(personCreate.getFirstName());
        assertNotNull(personCreate.getLastName());
        assertNotNull(personCreate.getGender());
        assertNotNull(personCreate.getGender());
        assertTrue(personCreate.getId() > 0);
        assertEquals("Vinicius", personCreate.getFirstName());
        assertEquals("Lemes", personCreate.getLastName());
        assertEquals("São Paulo - São Paulo", personCreate.getAddress());
        assertEquals("Male", personCreate.getGender());

    }

    private void mockPerson() {
        person.setFirstName("Vinicius");
        person.setLastName("Lemes");
        person.setAddress("São Paulo - São Paulo");
        person.setGender("Male");
    }
}
