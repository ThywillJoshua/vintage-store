package org.thywill.quarkus.microservices.number;

import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

import org.jboss.logging.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    @Retry(maxRetries = 3, delay = 1000) // Adding retry for ISBN generation
    @Fallback(fallbackMethod = "fallback_createABook")
    public Response createABook(
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("yearOfPublication") int yearOfPublication,
            @FormParam("genre") String genre) {

        Book book = new Book();

        book.title = title;
        book.author = author;
        book.isbn13 = numberProxy.generateIsbnNumbers().isbn_13;
        book.yearOfPublication = yearOfPublication;
        book.genre = genre;
        book.creationDate = Instant.now();

        logger.info("Created a book: " + book);
        return Response.status(201).entity(book).build();
    }

    public Response fallback_createABook(
            String title,
            String author,
            int yearOfPublication,
            String genre) throws FileNotFoundException {
        Book book = new Book();
        book.isbn13 = "Will be set later"; // Indicate that ISBN is not available
        book.title = title;
        book.author = author;
        book.yearOfPublication = yearOfPublication;
        book.genre = genre;
        book.creationDate = Instant.now();

        saveBookOnDisk(book);
        logger.info("Book saved on disk: " + book);
        return Response.status(206).entity(book).build();
    }

    private void saveBookOnDisk(Book book) throws FileNotFoundException {
        String bookJSON = JsonbBuilder.create().toJson(book);

        try (PrintWriter out = new PrintWriter("book-" + Instant.now().toEpochMilli() + ".json")) {
            out.println(bookJSON);
        } catch (FileNotFoundException e) {
            logger.error("Error writing book to disk: " + e.getMessage());
            throw e; // Re-throw the exception for higher-level handling if needed
        }
    }
}
