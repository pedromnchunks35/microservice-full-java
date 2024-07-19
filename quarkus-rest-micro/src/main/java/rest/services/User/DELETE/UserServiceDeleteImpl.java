package rest.services.User.DELETE;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.exceptions.DoesNotExistException;
import rest.repositories.UserRepository;
@ApplicationScoped
public class UserServiceDeleteImpl implements UserServiceDeleteInterface {

    @Override
    public Response deleteUser(String username, UserRepository userRepository) {
        boolean result;
        try {
            result = userRepository.deleteUser(username);
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
