package rest.services.User.POST;

import jakarta.ws.rs.core.Response;
import rest.dto.UserDTO;
import rest.repositories.UserRepository;

public interface UserServicePostInterface {
    public Response createUser(UserDTO newUser, UserRepository userRepository);
}
