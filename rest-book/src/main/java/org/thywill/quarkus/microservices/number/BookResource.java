package org.thywill.quarkus.microservices.number;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.time.Instant;

@Path("/api/books")
@Tag(name = "Book REST endpoint")
public class BookResource {

    @Inject
    Logger logger;

    @Inject
    @RestClient
    NumberProxy numberProxy;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(
            summary = "Creates a Book",
            description = "Creates a Book with an ISBN number")
    public Response createABook(
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("yearOfPublication") int yearOfPublication,
            @FormParam("genre") String genre) {

        Book book = new Book();
        book.isbn13 = numberProxy.generateIsbnNumbers().isbn_13;
        book.title = title;
        book.author = author;
        book.yearOfPublication = yearOfPublication;
        book.genre = genre;
        book.creationDate = Instant.now();

        logger.info("Created a book: " + book);
        return Response.status(201).entity(book).build();
    }
}
