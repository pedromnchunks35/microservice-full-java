package rest.services.Auth.POST;

import org.keycloak.admin.client.Keycloak;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.RegisterInfoDTO;
import rest.dto.UserDTO;
import rest.repositories.UserRepository;
import rest.services.User.POST.UserServicePostImpl;
import rest.utils.checks.AuthCheck;
import rest.utils.keycloak.KeycloakOperationsImpl;

@ApplicationScoped
public class AuthServicePostImpl implements AuthServicePostInterface {
        @Transactional
        public Response Register(RegisterInfoDTO credentials, UserRepository userRepository,
                        KeycloakOperationsImpl keycloakOperationsImpl, UserServicePostImpl userServicePostImpl,
                        TransactionManager transactionManager,
                        Keycloak root) throws IllegalStateException, SystemException {
                if (!AuthCheck.isValidString(credentials.getLocation())
                                || !AuthCheck.checkLength(credentials.getLocation(), 7)) {
                        return Response.ok(new GeneralResponse.GeneralResponseBuilder<>()
                                        .setMessage("Please put a valid location")
                                        .build())
                                        .status(Response.Status.BAD_REQUEST).build();
                }
                if (!AuthCheck.isValidString(credentials.getPhoneNumber())
                                || !AuthCheck.checkLength(credentials.getPhoneNumber(), 8)) {
                        return Response.ok(new GeneralResponse.GeneralResponseBuilder<>()
                                        .setMessage("Please put a valid phone number")
                                        .build()).status(Response.Status.BAD_REQUEST).build();
                }
                if (!AuthCheck.isValidString(credentials.getPassword())
                                || !AuthCheck.checkLength(credentials.getPassword(), 8)) {
                        return Response
                                        .ok(new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("Please put a valid password")
                                                        .build())
                                                        .status(Response.Status.BAD_REQUEST)
                                        .build();
                }
                if (!AuthCheck.isValidString(credentials.getEmail())
                                || !AuthCheck.checkLength(credentials.getEmail(), 8)
                                || AuthCheck.emailExists(root, "micro-rest", credentials.getEmail())) {
                        return Response
                                        .ok(new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("Please put a valid email/it may be duplicated").build())
                                                        .status(Response.Status.BAD_REQUEST)
                                        .build();
                }
                if (!AuthCheck.isValidString(credentials.getUsername())
                                || !AuthCheck.checkLength(credentials.getUsername(), 8)
                                || AuthCheck.usernameExists(root, "micro-rest", credentials.getUsername())) {
                        return Response.ok(
                                        new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("Please put a valid username/it may be duplicated").build())
                                                        .status(Response.Status.BAD_REQUEST)
                                        .build();
                }
                if (!AuthCheck.isValidString(credentials.getFirstName())
                                || !AuthCheck.checkLength(credentials.getFirstName(), 3)) {
                        return Response.ok(new GeneralResponse.GeneralResponseBuilder<>()
                                        .setMessage("Please provide a valid first name").build())
                                        .status(Response.Status.BAD_REQUEST)
                                        .build();
                }
                if (!AuthCheck.isValidString(credentials.getLastName())
                                || !AuthCheck.checkLength(credentials.getLastName(), 3)) {
                        return Response.ok(new GeneralResponse.GeneralResponseBuilder<>()
                                        .setMessage("Please provide a valid last name").build())
                                        .status(Response.Status.BAD_REQUEST)
                                        .build();
                }
                if (!AuthCheck.isValidString(credentials.getPostalCode())
                                || !AuthCheck.checkLength(credentials.getPostalCode(), 3)) {
                        return Response.ok(new GeneralResponse.GeneralResponseBuilder<>()
                                        .setMessage("Please provide a valid postal code").build())
                                        .status(Response.Status.BAD_REQUEST)
                                        .build();
                }
                try {
                        UserDTO userDTO = new UserDTO.UserDTOBuilder()
                                        .setCountry(credentials.getCountry())
                                        .setLocation(credentials.getLocation())
                                        .setPhoneNumber(credentials.getPhoneNumber())
                                        .setPostalCode(credentials.getPostalCode())
                                        .setUsername(credentials.getUsername())
                                        .build();
                        Response userCreation = userServicePostImpl.createUser(userDTO,
                                        userRepository);
                        if (userCreation.getStatus() != 202) {
                                return userCreation;
                        }
                        keycloakOperationsImpl.createUser(credentials.getUsername(),
                                        credentials.getPassword(),
                                        credentials.getEmail(), credentials.getFirstName(),
                                        credentials.getLastName(),
                                        "micro-rest", root);
                        return Response.ok(new GeneralResponse.GeneralResponseBuilder<Boolean>().setMessage("Success")
                                        .setResponse(true).build()).status(Response.Status.ACCEPTED).build();
                } catch (Exception e) {
                        return Response.ok(new GeneralResponse.GeneralResponseBuilder<Boolean>()
                                        .setMessage("Db down or credentials server down")
                                        .setResponse(false).build()).status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .build();
                }
        }
}
