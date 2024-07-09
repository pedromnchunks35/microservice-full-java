package rest.controller.PublicKey.PUT;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.PublicKeyDTO;
import rest.exceptions.DoesNotExistException;
import rest.service.PublicKeyService;

@Path("publickey")
public class PublicKeyControllerPUT {
    @Inject
    PublicKeyService publicKeyService;

    @PUT
    public Response updatePublicKey(PublicKeyDTO updatePublicKey) {
        PublicKeyDTO isUpdated;
        try {
            isUpdated = publicKeyService.updatePublicKey(updatePublicKey);
        } catch (DoesNotExistException e) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<Object>()
                            .setMessage("Error")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response.ok(
                new GeneralResponse.GeneralResponseBuilder<PublicKeyDTO>()
                        .setMessage("Success")
                        .setResponse(isUpdated)
                        .build())
                .status(Response.Status.ACCEPTED).build();
    }
}
