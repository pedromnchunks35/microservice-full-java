package rest.services.User.GET;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.UserDTO;
import rest.repositories.UserRepository;
@ApplicationScoped
public class UserServiceGetImpl implements UserServiceGetInterface {

    @Override
    public Response getUserByUsername(String username, UserRepository userRepository) {
        UserDTO result = userRepository.getUserByUsername(username);
        if (result == null) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error, does not exist").build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response.ok(result).status(Response.Status.ACCEPTED).build();
    }

    @Override
    public Response getAllUsers(int page, int size, UserRepository userRepository) {
        if (page < 0 || size < 0) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        List<UserDTO> result = userRepository.getAllUsers(page, size);
        return Response.ok(result)
                .status(Response.Status.ACCEPTED).build();
    }

}
