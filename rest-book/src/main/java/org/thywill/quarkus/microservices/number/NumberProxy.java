package org.thywill.quarkus.microservices.number;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@RegisterRestClient(configKey = "number-proxy")
@Path("/api/numbers")
public interface NumberProxy {

    @GET
    IsbnThirteen generateIsbnNumbers();
}