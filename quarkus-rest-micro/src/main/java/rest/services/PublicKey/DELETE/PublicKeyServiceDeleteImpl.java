package rest.services.PublicKey.DELETE;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.exceptions.DoesNotExistException;
import rest.repositories.PublicKeyRepository;

@ApplicationScoped
public class PublicKeyServiceDeleteImpl implements PublicKeyServiceDeleteInterface {

    @Override
    public Response deletePublicKey(Long id, PublicKeyRepository publicKeyRepository) {
        boolean result;
        try {
            result = publicKeyRepository.deletePublicKey(id);
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
