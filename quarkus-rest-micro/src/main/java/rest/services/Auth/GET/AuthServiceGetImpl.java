package rest.services.Auth.GET;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.utils.checks.AuthCheck;
import java.lang.Exception;

@ApplicationScoped
public class AuthServiceGetImpl implements AuthServiceGetInterface {

    @Override
    public Response getToken(String username, String password) {
        if (!AuthCheck.isValidString(username) && !AuthCheck.checkLength(username, 3)) {
            return Response.ok(new GeneralResponse.GeneralResponseBuilder<>()
                    .setMessage("The given username is not valid").build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        if (!AuthCheck.isValidString(password) && !AuthCheck.checkLength(password, 3)) {
            return Response.ok(new GeneralResponse.GeneralResponseBuilder<>()
                    .setMessage("The given password is not valid").build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        try {
            Keycloak session = null;
            session = KeycloakBuilder
                    .builder()
                    .serverUrl("http://172.24.166.62:8080")
                    .realm("micro-rest")
                    .clientId("quarkus-client")
                    .clientSecret("PrqG26NBvbmSK8rk41IWMiSx4RKDwoKG")
                    .grantType("password")
                    .username(username)
                    .password(password)
                    .build();
            AccessTokenResponse token = session.tokenManager().grantToken();
            return Response.ok(token).status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            if (e.getMessage().contains("401")) {
                return Response.ok(new GeneralResponse.GeneralResponseBuilder<>()
                        .setMessage("Credentials are wrong")
                        .build())
                        .status(Response.Status.BAD_REQUEST)
                        .build();
            } else {
                return Response.ok(new GeneralResponse.GeneralResponseBuilder<>()
                        .setMessage("Something unexpected happened")
                        .build())
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .build();
            }
        }
    }
}
