package rest.controllers.PublicKey.DELETE;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.repositories.PublicKeyRepository;
import rest.services.PublicKey.DELETE.PublicKeyServiceDeleteImpl;
import rest.utils.headers.HeadersOperations;
import rest.utils.keycloak.KeycloakOperationsImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;

@Path("publickey")
public class PublicKeyControllerDELETE {
    @Inject
    PublicKeyRepository publicKeyService;
    @Inject
    PublicKeyServiceDeleteImpl publicKeyServiceDeleteImpl;
    @Inject
    HeadersOperations headersOperations;
    @Inject
    KeycloakOperationsImpl keycloakOperationsImpl;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deletePublicKey(@PathParam("id") Long id, @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return publicKeyServiceDeleteImpl.deletePublicKey(id, publicKeyService);
    }
}
