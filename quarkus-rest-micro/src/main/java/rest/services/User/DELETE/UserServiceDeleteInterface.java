package rest.services.User.DELETE;

import jakarta.ws.rs.core.Response;
import rest.repositories.UserRepository;

public interface UserServiceDeleteInterface {
    public Response deleteUser(String username, UserRepository userRepository);
}
