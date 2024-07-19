package rest.services.PublicKey.GET;
import jakarta.ws.rs.core.Response;
import rest.repositories.PublicKeyRepository;

public interface PublicKeyServiceGetInterface {
    public Response getAllPublicKeys(int page, int size,
            PublicKeyRepository publicKeyRepository);

    public Response getPublicKeyById(Long id, PublicKeyRepository publicKeyRepository);
}
