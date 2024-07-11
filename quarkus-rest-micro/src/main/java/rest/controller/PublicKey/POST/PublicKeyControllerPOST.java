package rest.controller.PublicKey.POST;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.PublicKeyDTO;
import rest.exceptions.AlreadyExistsException;
import rest.service.PublicKeyService;

@Path("publickey")
public class PublicKeyControllerPOST {
    @Inject
    PublicKeyService publicKeyService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPublicKey(@RequestBody PublicKeyDTO newPublicKey) {
        PublicKeyDTO result;
        try {
            result = publicKeyService.createPublicKey(newPublicKey);
        } catch (AlreadyExistsException e) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error, the publickey already exists")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response.ok(result)
                .status(Response.Status.ACCEPTED)
                .build();
    }
}
