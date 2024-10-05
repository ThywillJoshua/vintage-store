package org.thywill.quarkus.microservices.number;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.time.Instant;

@Path("/api/books")
public class BookResource {

    @Inject
    Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getABook() {
        return "Hello RESTEasy";
    }

   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createABook (
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("yearOfPublication") int yearOfPublication,
            @FormParam("genre") String genre) {
    Book book = new Book();
    book.isbn13 = "To be implemented";
    book.title = title;
    book.author = author;
    book.yearOfPublication = yearOfPublication;
    book.genre = genre;
    book.creationDate = Instant.now();

    logger.info("Created a book: " + book);
    return Response.status(201).entity(book).build();
   }
}
