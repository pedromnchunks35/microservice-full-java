package rest.utils.headers;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import rest.utils.keycloak.KeycloakOperationsImpl;

public interface HeadersOperationsInterface {
    public String getBearerToken(@Context HttpHeaders headers);

    public boolean verifyRole(HttpHeaders headers, String role, KeycloakOperationsImpl keycloakOperationsImpl);
}
