package rest.services.Auth.POST;

import org.keycloak.admin.client.Keycloak;

import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.ws.rs.core.Response;
import rest.dto.RegisterInfoDTO;
import rest.repositories.UserRepository;
import rest.services.User.POST.UserServicePostImpl;
import rest.utils.keycloak.KeycloakOperationsImpl;

public interface AuthServicePostInterface {
    public Response Register(RegisterInfoDTO credentials, UserRepository userRepository,
            KeycloakOperationsImpl keycloakOperationsImpl,UserServicePostImpl userServicePostImpl,TransactionManager transactionManager, Keycloak root)  throws IllegalStateException, SystemException;
}
