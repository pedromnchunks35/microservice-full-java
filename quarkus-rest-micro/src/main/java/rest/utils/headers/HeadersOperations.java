package rest.utils.headers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.HttpHeaders;
import rest.utils.keycloak.KeycloakOperationsImpl;
@ApplicationScoped
public class HeadersOperations implements HeadersOperationsInterface {

    @Override
    public String getBearerToken(HttpHeaders headers) {
        List<String> authorization = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || authorization.isEmpty()) {
            return null;
        }
        return authorization.get(0).replace("Bearer ", "");
    }

    @Override
    public boolean verifyRole(HttpHeaders headers, String role, KeycloakOperationsImpl keycloakOperationsImpl) {
        String token = getBearerToken(headers);
        if (token == null) {
            return false;
        }
        try {
            JsonNode json = keycloakOperationsImpl.introspectToken(token,
                    "http://172.24.166.62:8080/realms/micro-rest/protocol/openid-connect/token/introspect",
                    "quarkus-client",
                    "PrqG26NBvbmSK8rk41IWMiSx4RKDwoKG");
            return keycloakOperationsImpl.hasRole(token, role, json);
        } catch (IOException e) {
            return false;
        }
    }
}
