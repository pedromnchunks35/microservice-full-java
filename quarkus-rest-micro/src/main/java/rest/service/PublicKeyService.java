package rest.service;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import rest.dto.PublicKeyDTO;
import rest.entity.PublicKey;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;
import rest.mapper.PublicKeyMapperImpl;

@ApplicationScoped
public class PublicKeyService implements PanacheRepository<PublicKey> {
    /**
     * @param page, number of the page
     * @param size, size of each page
     * @return The list of a dto public key
     */
    public List<PublicKeyDTO> getAllPublicKeys(int page, int size) {
        List<PublicKey> result = findAll().page(page, size).list();
        return PublicKeyMapperImpl.publicKeyListToPublicKeyDTOList(result);
    }

    /**
     * @param id, the id of the publickey we want to get
     * @return
     */
    public PublicKeyDTO getPublicKeyById(Long id) {
        PublicKey result = findById(id);
        return PublicKeyMapperImpl.publicKeyToPublicKeyDTO(result);
    }

    /**
     * @param updatePublicKey, the public key we want to update
     * @return
     * @throws DoesNotExistException
     */
    public PublicKeyDTO updatePublicKey(PublicKeyDTO updatePublicKey) throws DoesNotExistException {
        PublicKeyDTO checkObj = getPublicKeyById(updatePublicKey.getId());
        if (checkObj == null) {
            throw new DoesNotExistException("publickey");
        } else {
            persist(PublicKeyMapperImpl.publicKeyDTOtoPublicKey(updatePublicKey));
        }
        return updatePublicKey;
    }

    /**
     * @param newPublicKey, the new Publickey obj to be added to the database
     * @return
     * @throws AlreadyExistsException
     */
    public PublicKeyDTO createPublicKey(PublicKeyDTO newPublicKey) throws AlreadyExistsException {
        if (newPublicKey.getId() != null) {
            throw new AlreadyExistsException("publickey");
        } else {
            persist(PublicKeyMapperImpl.publicKeyDTOtoPublicKey(newPublicKey));
        }
        return newPublicKey;
    }

    /**
     * @param id
     * @return
     * @throws DoesNotExistException
     */
    public boolean deletePublicKey(Long id) throws DoesNotExistException {
        PublicKeyDTO checkObj = getPublicKeyById(id);
        if (checkObj == null) {
            throw new DoesNotExistException("publickey");
        } else {
            delete(PublicKeyMapperImpl.publicKeyDTOtoPublicKey(checkObj));
        }
        return true;
    }

    /**
     * @param id, the id of the public key that we wish to set as the main public
     *            key
     * @return
     * @throws DoesNotExistException
     */
    public boolean setMainPublicKey(Long id) throws DoesNotExistException {
        PublicKeyDTO checkObj = getPublicKeyById(id);
        if (checkObj == null) {
            throw new DoesNotExistException("publickey");
        }
        update("IN_USAGE=false WHERE IN_USAGE=true");
        update("IN_USAGE=true WHERE ID=?1", id);
        return true;
    }

    /**
     * @return the current in usage publickey
     */
    public PublicKeyDTO getMainPublicKey() {
        PublicKey queryResult = find("* WHERE IN_USAGE=true").firstResult();
        return PublicKeyMapperImpl.publicKeyToPublicKeyDTO(queryResult);
    }
}
