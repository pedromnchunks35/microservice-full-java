package rest.controller.PublicKey.DELETE;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.exceptions.DoesNotExistException;
import rest.service.PublicKeyService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;

@Path("publickey")
public class PublicKeyControllerDELETE {
    @Inject
    PublicKeyService publicKeyService;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deletePublicKey(@PathParam("id") Long id) {
        boolean result;
        try {
            result = publicKeyService.deletePublicKey(id);
        } catch (DoesNotExistException e) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error, the given id does not exist on the db")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response.ok(
                new GeneralResponse.GeneralResponseBuilder<Boolean>()
                        .setMessage("Success")
                        .setResponse(result)
                        .build())
                .status(Response.Status.ACCEPTED)
                .build();
    }
}
