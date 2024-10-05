package org.thywill.quarkus.microservices.number;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(title = "Number Microservice",
        description = "This microservice generates book numbers",
                version = "1.0",
                contact = @Contact(name = "@thywilljoshua", url = "https://x.com/thywilljoshua")
        ),
        externalDocs= @ExternalDocumentation(url = "https://github.com/ThywillJoshua/vintage-store"),
        tags = {
                @Tag(name = "api", description = "Public api that can be used by anybody"),
                @Tag(name = "numbers", description = "For anyone interested in isbn numbers"),
        }
)
public class NumberMicroservice extends Application {

}
