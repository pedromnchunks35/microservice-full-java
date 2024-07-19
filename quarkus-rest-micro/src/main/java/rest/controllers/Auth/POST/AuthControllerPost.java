package rest.controllers.Auth.POST;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.RegisterInfoDTO;
import rest.repositories.UserRepository;
import rest.services.Auth.POST.AuthServicePostImpl;
import rest.services.User.POST.UserServicePostImpl;
import rest.utils.keycloak.KeycloakOperationsImpl;

@Path("/auth")
public class AuthControllerPost {
    Keycloak keycloak;
    @Inject
    UserRepository userRepository;
    @Inject
    AuthServicePostImpl authServicePostImpl;
    @Inject
    KeycloakOperationsImpl keycloakOperationsImpl;
    @Inject
    UserServicePostImpl userServicePostImpl;
    @Inject
    TransactionManager transactionManager;

    @PostConstruct
    public void initKeyCloak() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl("http://172.24.166.62:8080")
                .realm("master")
                .clientId("admin-cli")
                .clientSecret("5xMVrv55rRh5dOVSYVDgdYxxyKArLx7L")
                .grantType("password")
                .username("admin")
                .password("admin")
                .build();
    }

    @PreDestroy
    public void closeKeyCloak() {
        keycloak.close();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response Register(@RequestBody RegisterInfoDTO credentials) throws IllegalStateException, SystemException {
        return authServicePostImpl.Register(credentials, userRepository, keycloakOperationsImpl, userServicePostImpl,
                transactionManager, keycloak);
    }
}
