package org.thywill.quarkus.microservices.number;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "This is a book")
public class Book {
    @Schema(required = true)
    @JsonbProperty("isbn_13")
    public String isbn13;

    @Schema(required = true)
    public String title;

    @Schema(required = true)
    public String author;

    @Schema(required = true)
    @JsonbProperty("year_of_publication")
    public int yearOfPublication;

    @Schema(required = true)
    public String genre;

    @JsonbDateFormat("yyyy/MM/dd")
    @JsonbProperty("creation_date")
    @Schema(implementation = String.class, format = "date", required = true)
    public Instant creationDate;

    @Override
    public String toString() {
        return "Book{" +
                "isbn13='" + isbn13 + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", genre='" + genre + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
