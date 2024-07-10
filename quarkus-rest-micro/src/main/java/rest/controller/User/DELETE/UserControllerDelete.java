package rest.controller.User.DELETE;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.exceptions.DoesNotExistException;
import rest.service.UserService;

@Path("user")
public class UserControllerDelete {
    @Inject
    UserService userService;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public Response deleteUser(@PathParam("username") String username) {
        boolean result;
        try {
            result = userService.deleteUser(username);
        } catch (DoesNotExistException e) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error, the user does not exist")
                            .build())
                    .build();
        }
        return Response.ok(new GeneralResponse.GeneralResponseBuilder<Boolean>()
                .setMessage("Success")
                .setResponse(result)
                .build())
                .status(Response.Status.ACCEPTED)
                .build();
    }
}
