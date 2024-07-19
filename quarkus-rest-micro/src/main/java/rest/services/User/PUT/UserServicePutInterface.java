package rest.services.User.PUT;

import jakarta.ws.rs.core.Response;
import rest.dto.UserDTO;
import rest.repositories.UserRepository;

public interface UserServicePutInterface {
    public Response updateUser(UserDTO updateUser, UserRepository userRepository);
}
