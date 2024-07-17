package rest.service;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import rest.dto.PublicKeyDTO;
import rest.entity.PublicKey;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;
import rest.mapper.PublicKeyMapperImpl;
import rest.utils.crypto.KeyUtils;

@ApplicationScoped
public class PublicKeyService implements PanacheRepository<PublicKey> {
    /**
     * @param page, number of the page
     * @param size, size of each page
     * @return The list of a dto public key
     */
    @Transactional
    public List<PublicKeyDTO> getAllPublicKeys(int page, int size) {
        List<PublicKey> result = findAll().page(page, size).list();
        return PublicKeyMapperImpl.publicKeyListToPublicKeyDTOList(result);
    }

    /**
     * @param id, the id of the publickey we want to get
     * @return
     */
    @Transactional
    public PublicKeyDTO getPublicKeyById(Long id) {
        PublicKey result = findById(id);
        return PublicKeyMapperImpl.publicKeyToPublicKeyDTO(result);
    }

    /**
     * @param updatePublicKey, the public key we want to update
     * @return
     * @throws DoesNotExistException
     */
    @Transactional
    public PublicKeyDTO updatePublicKey(PublicKeyDTO updatePublicKey) throws DoesNotExistException {
        PublicKey result = findById(updatePublicKey.getId());
        if (result == null) {
            throw new DoesNotExistException("publickey");
        } else {
            if (updatePublicKey.getChangedAt() != null) {
                result.setchangedAt(updatePublicKey.getChangedAt());
            }
            if (updatePublicKey.getKey() != null) {
                result.setKey(KeyUtils.stringToByteArray(updatePublicKey.getKey()));
            }
            persist(result);
        }
        return PublicKeyMapperImpl.publicKeyToPublicKeyDTO(result);
    }

    /**
     * @param newPublicKey, the new Publickey obj to be added to the database
     * @return
     * @throws AlreadyExistsException
     */
    @Transactional
    public PublicKeyDTO createPublicKey(PublicKeyDTO newPublicKey) throws AlreadyExistsException {
        PublicKey pubKey = PublicKeyMapperImpl.publicKeyDTOtoPublicKey(newPublicKey);
        if (newPublicKey.getId() != null) {
            throw new AlreadyExistsException("publickey");
        } else {
            persist(pubKey);
        }
        return PublicKeyMapperImpl.publicKeyToPublicKeyDTO(pubKey);
    }

    /**
     * @param id
     * @return
     * @throws DoesNotExistException
     */
    @Transactional
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
    @Transactional
    public boolean setMainPublicKey(Long id) throws DoesNotExistException {
        PublicKeyDTO checkObj = getPublicKeyById(id);
        if (checkObj == null) {
            throw new DoesNotExistException("publickey");
        }
        update("inUsage=false WHERE inUsage=true");
        update("inUsage=true WHERE id=?1", id);
        return true;
    }

    /**
     * @return the current in usage publickey
     */
    @Transactional
    public PublicKeyDTO getMainPublicKey() {
        PublicKey queryResult = find("inUsage", true).firstResult();
        return PublicKeyMapperImpl.publicKeyToPublicKeyDTO(queryResult);
    }
}
