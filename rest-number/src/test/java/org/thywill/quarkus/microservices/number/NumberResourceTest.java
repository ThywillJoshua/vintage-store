package org.thywill.quarkus.microservices.number;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
class NumberResourceTest {
    @Test
    void shouldGenerateIsbnNumbers() {
        given()
          .when().get("/api/numbers")
          .then()
             .statusCode(200)
             .body("isbn_13", startsWith("13-"))
                .body("isbn_10", startsWith("10-"))
                .body(not(hasItem("generationDate")));
    }

}