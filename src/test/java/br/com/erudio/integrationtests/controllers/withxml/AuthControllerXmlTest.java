package br.com.erudio.integrationtests.controllers.withxml;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.AccountCredentialsVO;
import br.com.erudio.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {
    private static TokenVO tokenVO;

    @Test
    @Order(1)
    public void testSigninXml() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("Vinicius", "lemes");

        tokenVO = given()
                .basePath("/auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_XML)
                .body(user)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .as(TokenVO.class);

        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());
    }

    @Test
    @Order(2)
    public void testRefreshXml() throws JsonMappingException, JsonProcessingException {

        var newTokenVO = given()
                .basePath("/auth/refresh")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_XML)
                .pathParam("username", tokenVO.getUserName())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getAccessToken())
                .when()
                    .put("{username}")
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .as(TokenVO.class);

        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());
    }
}
