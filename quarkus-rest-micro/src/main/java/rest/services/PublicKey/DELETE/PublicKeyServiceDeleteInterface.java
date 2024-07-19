package rest.services.PublicKey.DELETE;

import jakarta.ws.rs.core.Response;
import rest.repositories.PublicKeyRepository;

public interface PublicKeyServiceDeleteInterface {
    public Response deletePublicKey(Long id, PublicKeyRepository publicKeyRepository);
}
