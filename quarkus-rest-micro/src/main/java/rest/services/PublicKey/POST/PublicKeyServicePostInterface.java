package rest.services.PublicKey.POST;


import jakarta.ws.rs.core.Response;
import rest.dto.PublicKeyDTO;
import rest.repositories.PublicKeyRepository;

public interface PublicKeyServicePostInterface {
    public Response createPublicKey(PublicKeyDTO newPublicKey, PublicKeyRepository publicKeyRepository);
}
