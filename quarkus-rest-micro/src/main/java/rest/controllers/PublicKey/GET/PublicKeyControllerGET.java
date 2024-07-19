package rest.controllers.PublicKey.GET;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.repositories.PublicKeyRepository;
import rest.services.PublicKey.GET.PublicKeyServiceGetImpl;
import rest.utils.headers.HeadersOperations;
import rest.utils.keycloak.KeycloakOperationsImpl;

@Path("publickey")
public class PublicKeyControllerGET {
    @Inject
    PublicKeyRepository publicKeyService;
    @Inject
    PublicKeyServiceGetImpl publicKeyServiceGetImpl;
    @Inject
    HeadersOperations headersOperations;
    @Inject
    KeycloakOperationsImpl keycloakOperationsImpl;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{page}/{size}")
    public Response getAllPublicKeys(@PathParam("page") int page, @PathParam("size") int size,
            @Context HttpHeaders headers) {
                if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
                    return Response
                            .ok(new GeneralResponse.GeneralResponseBuilder<>()
                                    .setMessage("You should present a valid token or have the role of admin").build())
                            .status(401).build();
                }
        return publicKeyServiceGetImpl.getAllPublicKeys(page, size, publicKeyService);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getPublicKeyById(@PathParam("id") Long id, @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return publicKeyServiceGetImpl.getPublicKeyById(id, publicKeyService);
    }
}
