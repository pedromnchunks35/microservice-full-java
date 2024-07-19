package rest.services.PublicKey.POST;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.PublicKeyDTO;
import rest.exceptions.AlreadyExistsException;
import rest.repositories.PublicKeyRepository;

@ApplicationScoped
public class PublicKeyServicePostImpl implements PublicKeyServicePostInterface {

    @Override
    public Response createPublicKey(PublicKeyDTO newPublicKey, PublicKeyRepository publicKeyRepository) {
        PublicKeyDTO result;
        try {
            result = publicKeyRepository.createPublicKey(newPublicKey);
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
