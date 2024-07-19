package rest.controllers.User.PUT;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.UserDTO;
import rest.repositories.UserRepository;
import rest.services.User.PUT.UserServicePutImpl;
import rest.utils.headers.HeadersOperations;
import rest.utils.keycloak.KeycloakOperationsImpl;

@Path("user")
public class UserControllerPut {
    @Inject
    UserRepository userRepository;
    @Inject
    UserServicePutImpl userServicePutImpl;
    @Inject
    HeadersOperations headersOperations;
    @Inject
    KeycloakOperationsImpl keycloakOperationsImpl;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@RequestBody UserDTO updateUser, @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return userServicePutImpl.updateUser(updateUser, userRepository);
    }
}
