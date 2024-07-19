package rest.services.PublicKey.GET;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.PublicKeyDTO;
import rest.repositories.PublicKeyRepository;
import java.util.List;

@ApplicationScoped
public class PublicKeyServiceGetImpl implements PublicKeyServiceGetInterface {

    @Override
    public Response getAllPublicKeys(int page, int size, PublicKeyRepository publicKeyRepository) {
        if (page < 0 || size < 0) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<Object>()
                            .setMessage("Error")
                            .build())
                    .status(Response.Status.BAD_REQUEST).build();
        }
        List<PublicKeyDTO> publicKeyDTOList = publicKeyRepository.getAllPublicKeys(page, size);
        return Response.ok(publicKeyDTOList).status(Response.Status.ACCEPTED).build();
    }

    @Override
    public Response getPublicKeyById(Long id, PublicKeyRepository publicKeyRepository) {
        if (id < Long.valueOf(0)) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<Object>()
                            .setMessage("Error")
                            .build())
                    .status(Response.Status.BAD_REQUEST).build();
        }
        PublicKeyDTO publicKeyDTO = publicKeyRepository.getPublicKeyById(id);
        return Response.ok(publicKeyDTO).status(Response.Status.ACCEPTED).build();
    }

}
