package rest.controller.User.POST;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.UserDTO;
import rest.exceptions.AlreadyExistsException;
import rest.service.UserService;

@Path("user")
public class UserControllerPost {
    @Inject
    UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@RequestBody UserDTO newUser) {
        UserDTO insertedUser;
        try {
            insertedUser = userService.createUser(newUser);
        } catch (AlreadyExistsException e) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>().setMessage("Error, already exists")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response.ok(insertedUser)
                .status(Response.Status.ACCEPTED).build();
    }
}
