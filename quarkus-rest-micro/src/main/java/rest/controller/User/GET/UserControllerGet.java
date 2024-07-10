package rest.controller.User.GET;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.UserDTO;
import rest.service.UserService;

@Path("user")
public class UserControllerGet {
    @Inject
    UserService userService;

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUsername(@PathParam("username") String username) {
        UserDTO result = userService.getUserByUsername(username);
        if (result == null) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error, does not exist").build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response.ok(result).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/{page}/{size}")
    public Response getAllUsers(@PathParam("page") int page, @PathParam("size") int size) {
        if (page < 0 || size < 0) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        List<UserDTO> result = userService.getAllUsers(page, size);
        return Response.ok(result)
                .status(Response.Status.ACCEPTED).build();
    }
}
