package rest.services.Auth.GET;

import org.keycloak.admin.client.Keycloak;

import jakarta.ws.rs.core.Response;

public interface AuthServiceGetInterface {
    public Response getToken(String username, String password);
}
