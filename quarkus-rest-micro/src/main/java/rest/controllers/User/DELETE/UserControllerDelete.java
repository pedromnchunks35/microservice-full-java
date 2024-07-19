package rest.controllers.User.DELETE;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.repositories.UserRepository;
import rest.services.User.DELETE.UserServiceDeleteImpl;
import rest.utils.headers.HeadersOperations;
import rest.utils.keycloak.KeycloakOperationsImpl;

@Path("user")
public class UserControllerDelete {
    @Inject
    UserRepository userRepository;
    @Inject
    UserServiceDeleteImpl userServiceDeleteImpl;
    @Inject
    HeadersOperations headersOperations;
    @Inject
    KeycloakOperationsImpl keycloakOperationsImpl;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public Response deleteUser(@PathParam("username") String username, @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return userServiceDeleteImpl.deleteUser(username, userRepository);
    }
}
