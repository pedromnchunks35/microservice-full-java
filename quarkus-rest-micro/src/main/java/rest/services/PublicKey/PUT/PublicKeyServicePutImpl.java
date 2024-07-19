package rest.services.PublicKey.PUT;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.PublicKeyDTO;
import rest.exceptions.DoesNotExistException;
import rest.repositories.PublicKeyRepository;

@ApplicationScoped
public class PublicKeyServicePutImpl implements PublicKeyServicePutInterface {

    @Override
    public Response updatePublicKey(PublicKeyDTO updatePublicKey, PublicKeyRepository publicKeyRepository) {
        PublicKeyDTO isUpdated;
        try {
            isUpdated = publicKeyRepository.updatePublicKey(updatePublicKey);
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

    @Override
    public Response setMainPublicById(Long id, PublicKeyRepository publicKeyRepository) {
        boolean result;
        try {
            result = publicKeyRepository.setMainPublicKey(id);
        } catch (DoesNotExistException e) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error, that id does not exist")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response.ok(
                new GeneralResponse.GeneralResponseBuilder<>()
                        .setMessage("Success")
                        .setResponse(result)
                        .build())
                .status(Response.Status.ACCEPTED)
                .build();
    }

}
