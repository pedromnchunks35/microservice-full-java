package rest.controller.keycloak;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/users")
public class UsersResource {
    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/me")
    public User me() {
        return new User(identity);
    }

    public static class User {
        private final String userName;

        User(SecurityIdentity identity) {
            this.userName = identity.getPrincipal().getName();
        }

        public String getUsername() {
            return userName;
        }
    }
}
