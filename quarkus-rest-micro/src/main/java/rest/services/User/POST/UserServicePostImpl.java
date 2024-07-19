package rest.services.User.POST;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.UserDTO;
import rest.exceptions.AlreadyExistsException;
import rest.repositories.UserRepository;
@ApplicationScoped
public class UserServicePostImpl implements UserServicePostInterface {

    @Override
    public Response createUser(UserDTO newUser, UserRepository userRepository) {
        UserDTO insertedUser;
        try {
            insertedUser = userRepository.createUser(newUser);
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
