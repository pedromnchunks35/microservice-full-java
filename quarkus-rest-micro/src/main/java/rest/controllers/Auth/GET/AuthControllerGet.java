package rest.controllers.Auth.GET;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.CredentialsDTO;
import rest.services.Auth.GET.AuthServiceGetImpl;

@Path("auth")
public class AuthControllerGet {
    @Inject
    AuthServiceGetImpl authServiceGetImpl;

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response login(@RequestBody CredentialsDTO credentials) {
        return authServiceGetImpl.getToken(credentials.getUsername(), credentials.getPassword());
    }
}
