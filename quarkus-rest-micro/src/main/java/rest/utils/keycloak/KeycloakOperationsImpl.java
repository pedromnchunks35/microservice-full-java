package rest.utils.keycloak;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class KeycloakOperationsImpl implements KeycloakOperationsInterface {

    @Override
    public boolean createUser(String username, String password, String email, String firstName, String lastName,
            String realm, Keycloak root) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);
        user.setEmail(email);
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        user.setCredentials(Arrays.asList(passwordCred));
        Response response = root.realm(realm).users().create(user);
        return response.getStatus() == 200 ? true : false;
    }

    public JsonNode introspectToken(String token, String urlIntrospect, String clientID, String clientSecret)
            throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(urlIntrospect);

        StringEntity entity = new StringEntity(
                "client_id=" + clientID +
                        "&client_secret=" + clientSecret +
                        "&token=" + token);
        entity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(entity);

        CloseableHttpResponse response = client.execute(httpPost);
        String jsonResponse = EntityUtils.toString(response.getEntity());
        client.close();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(jsonResponse);
    }

    public boolean hasRole(String token, String roleName, JsonNode introspectionResult) {
        if (introspectionResult.get("active").asBoolean()) {
            JsonNode roles = introspectionResult.get("realm_access").get("roles");
            if (roles != null) {
                for (JsonNode role : roles) {
                    if (role.asText().equals(roleName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
