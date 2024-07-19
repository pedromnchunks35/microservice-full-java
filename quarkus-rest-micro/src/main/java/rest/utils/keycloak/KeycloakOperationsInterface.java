package rest.utils.keycloak;

import java.io.IOException;

import org.keycloak.admin.client.Keycloak;

import com.fasterxml.jackson.databind.JsonNode;

public interface KeycloakOperationsInterface {
    public boolean createUser(String username, String password, String email, String firstName, String lastName,
            String realm, Keycloak root);

    public JsonNode introspectToken(String token, String urlIntrospect, String clientID, String clientSecret)
            throws IOException;

    public boolean hasRole(String token, String roleName, JsonNode introspectionResult);
}
