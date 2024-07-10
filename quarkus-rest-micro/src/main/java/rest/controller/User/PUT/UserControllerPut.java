package rest.controller.User.PUT;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.UserDTO;
import rest.exceptions.DoesNotExistException;
import rest.service.UserService;

@Path("user")
public class UserControllerPut {
    @Inject
    UserService userService;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@RequestBody UserDTO updateUser) {
        UserDTO result;
        try {
            result = userService.updateUser(updateUser);
        } catch (DoesNotExistException e) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error, the user does not exist")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response.ok(
                result).status(Response.Status.ACCEPTED)
                .build();
    }
}
