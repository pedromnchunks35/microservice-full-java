package rest.controllers.User.GET;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.repositories.UserRepository;
import rest.services.User.GET.UserServiceGetImpl;
import rest.utils.headers.HeadersOperations;
import rest.utils.keycloak.KeycloakOperationsImpl;

@Path("user")
public class UserControllerGet {
    @Inject
    UserRepository userRepository;
    @Inject
    UserServiceGetImpl userServiceGetImpl;
    @Inject
    HeadersOperations headersOperations;
    @Inject
    KeycloakOperationsImpl keycloakOperationsImpl;

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUsername(@PathParam("username") String username, @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return userServiceGetImpl.getUserByUsername(username, userRepository);
    }

    @GET
    @Path("/{page}/{size}")
    public Response getAllUsers(@PathParam("page") int page, @PathParam("size") int size,
            @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return userServiceGetImpl.getAllUsers(page, size, userRepository);
    }
}
