package rest.services.User.GET;

import jakarta.ws.rs.core.Response;
import rest.repositories.UserRepository;

public interface UserServiceGetInterface {
    public Response getUserByUsername(String username, UserRepository userRepository);

    public Response getAllUsers(int page, int size, UserRepository userRepository);
}
