package rest.controllers.PublicKey.PUT;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.PublicKeyDTO;
import rest.repositories.PublicKeyRepository;
import rest.services.PublicKey.PUT.PublicKeyServicePutImpl;
import rest.utils.headers.HeadersOperations;
import rest.utils.keycloak.KeycloakOperationsImpl;

@Path("publickey")
public class PublicKeyControllerPUT {
        @Inject
        PublicKeyRepository publicKeyRepository;
        @Inject
        PublicKeyServicePutImpl publicKeyServicePutImpl;
        @Inject
        HeadersOperations headersOperations;
        @Inject
        KeycloakOperationsImpl keycloakOperationsImpl;

        @PUT
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response updatePublicKey(@RequestBody PublicKeyDTO updatePublicKey, @Context HttpHeaders headers) {
                if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
                        return Response
                                        .ok(new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("You should present a valid token or have the role of admin")
                                                        .build())
                                        .status(401).build();
                }
                return publicKeyServicePutImpl.updatePublicKey(updatePublicKey, publicKeyRepository);
        }

        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/updateToMainById/{id}")
        public Response setMainPublicById(@PathParam("id") Long id, @Context HttpHeaders headers) {
                if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
                        return Response
                                        .ok(new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("You should present a valid token or have the role of admin")
                                                        .build())
                                        .status(401).build();
                }
                return publicKeyServicePutImpl.setMainPublicById(id, publicKeyRepository);
        }
}
