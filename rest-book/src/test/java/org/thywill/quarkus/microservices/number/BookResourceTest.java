package org.thywill.quarkus.microservices.number;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.matchesPattern;

@QuarkusTest
class BookResourceTest {
    @Test
    void shouldCreateABook() {
        given()
                .formParam("title", "This Love")
                .formParam("author", "Wizkid")
                .formParam("yearOfPublication", 2020)
                .formParam("genre", "Music")
          .when().post("/api/books")
          .then()
             .statusCode(201)
             .body("isbn_13", startsWith("13-"))
                .body("title", is("This Love"))
                .body("author", is("Wizkid"))
                .body("year_of_publication", is(2020))
                .body("genre", is("Music"))
                // Check that creation_date matches the format yyyy/MM/dd
                .body("creation_date", matchesPattern("\\d{4}/\\d{2}/\\d{2}"));
    }

}