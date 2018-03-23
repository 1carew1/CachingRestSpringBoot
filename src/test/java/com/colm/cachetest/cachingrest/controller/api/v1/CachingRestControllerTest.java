package com.colm.cachetest.cachingrest.controller.api.v1;

import io.restassured.http.ContentType;
import io.restassured.internal.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CachingRestControllerTest {

    @LocalServerPort
    int port;

    @Test
    public void testCreateBatch() {
        given()
                .port(port)
                .when()
                .post("/api/v1/batch")
                .then()
                .contentType(ContentType.JSON)
                .body("cacheType", is("ehcache"))
                .body("setupComment", is("Dry Run"));
    }

    @Test
    public void testClassifyImageWithNoMetrics() throws IOException {
        final byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/testImages/testImage1.jpeg"));

        given()
                .port(port)
                .multiPart("file", "myFile", bytes)
                .when()
                .post("/api/v1/classify")
                .then()
                .contentType(ContentType.JSON)
                .body("label", is("French bulldog"))
                .extract().path("probability");

    }

    @Test
    public void testPullingClassifiedImageFromCache() throws IOException {
        final byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/testImages/testImage1.jpeg"));
        Integer batchId = given()
                .port(port)
                .when()
                .post("/api/v1/batch")
                .then()
                .contentType(ContentType.JSON)
                .body("cacheType", is("ehcache"))
                .body("setupComment", is("Dry Run"))
                .extract()
                .path("id");


        given()
                .port(port)
                .multiPart("file", "myFile", bytes)
                .when()
                .post("/api/v1/classify/" + batchId)
                .then()
                .contentType(ContentType.JSON)
                .body("label", is("French bulldog"));

        given()
                .port(port)
                .multiPart("file", "myFile", bytes)
                .when()
                .post("/api/v1/checkcache/" + batchId)
                .then()
                .contentType(ContentType.JSON)
                .body("label", is("French bulldog"));
    }


    @Test
    public void testIndexPage(){
        given().port(port).when().get("/").then().assertThat().contentType(ContentType.HTML);
    }

}
