package rest.services.PublicKey.PUT;

import jakarta.ws.rs.core.Response;
import rest.dto.PublicKeyDTO;
import rest.repositories.PublicKeyRepository;

public interface PublicKeyServicePutInterface {
    public Response updatePublicKey(PublicKeyDTO updatePublicKey, PublicKeyRepository publicKeyRepository);

    public Response setMainPublicById(Long id,PublicKeyRepository publicKeyRepository);
}
