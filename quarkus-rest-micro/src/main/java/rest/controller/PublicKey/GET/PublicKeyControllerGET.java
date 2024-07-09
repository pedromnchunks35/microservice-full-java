package rest.controller.PublicKey.GET;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.PublicKeyDTO;
import rest.service.PublicKeyService;

@Path("publickey")
public class PublicKeyControllerGET {
    @Inject
    PublicKeyService publicKeyService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{page}/{size}")
    public Response getAllPublicKeys(@PathParam("page") int page, @PathParam("size") int size) {
        if (page < 0 || size < 0) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<Object>()
                            .setMessage("Error")
                            .build())
                    .status(Response.Status.BAD_REQUEST).build();
        }
        List<PublicKeyDTO> publicKeyDTOList = publicKeyService.getAllPublicKeys(page, size);
        return Response.ok(publicKeyDTOList).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getPublicKeyById(@PathParam("id") Long id) {
        if (id < Long.valueOf(0)) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<Object>()
                            .setMessage("Error")
                            .build())
                    .status(Response.Status.BAD_REQUEST).build();
        }
        PublicKeyDTO publicKeyDTO = publicKeyService.getPublicKeyById(id);
        return Response.ok(publicKeyDTO).status(Response.Status.ACCEPTED).build();
    }
}
