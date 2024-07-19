package rest.services.User.PUT;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.UserDTO;
import rest.exceptions.DoesNotExistException;
import rest.repositories.UserRepository;
@ApplicationScoped
public class UserServicePutImpl implements UserServicePutInterface {

    @Override
    public Response updateUser(UserDTO updateUser, UserRepository userRepository) {
        UserDTO result;
        try {
            result = userRepository.updateUser(updateUser);
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
