package rest.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import rest.dto.PublicKeyDTO;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;
@QuarkusTest
public class PublicKeyRepositoryTest {
    @Inject
    UserTransaction userTransaction;
    @Inject
    PublicKeyRepository publicKeyRepository;

    @BeforeEach
    public void setUp() throws Exception {
        userTransaction.begin();
    }

    @AfterEach
    public void cleanUp() throws Exception {
        userTransaction.rollback();
    }

    @Test
    public void createPublicKeyTest() throws AlreadyExistsException {
        PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setChangedAt(new Date())
                .setInUsage(false)
                .setKey("lUL")
                .build();
        PublicKeyDTO resultant = publicKeyRepository.createPublicKey(newPublicKeyDTO);
        assertEquals(newPublicKeyDTO.getChangedAt() != null, true);
        assertEquals(newPublicKeyDTO.getKey(), resultant.getKey());
        assertEquals(newPublicKeyDTO.isInUsage(), resultant.isInUsage());
        try {
            publicKeyRepository.createPublicKey(newPublicKeyDTO);
        } catch (AlreadyExistsException e) {
            assertEquals(e.getMessage().contains("publickey"), true);
        }
    }

    @Test
    public void getPublicKeyByIdTest() throws AlreadyExistsException {
        PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setChangedAt(new Date())
                .setInUsage(false)
                .setKey("LUL")
                .build();
        PublicKeyDTO resultant = publicKeyRepository.createPublicKey(newPublicKeyDTO);
        PublicKeyDTO getResultById = publicKeyRepository.getPublicKeyById(resultant.getId());
        assertEquals(newPublicKeyDTO.getChangedAt() != null, true);
        assertEquals(newPublicKeyDTO.getKey(), getResultById.getKey());
        assertEquals(newPublicKeyDTO.isInUsage(), getResultById.isInUsage());
        assertEquals(resultant.getId(), getResultById.getId());
        PublicKeyDTO emptyResult = publicKeyRepository.getPublicKeyById(Long.valueOf(10000));
        assertEquals(emptyResult, null);
    }

    @Test
    public void updatePublicKeyTest() throws AlreadyExistsException, DoesNotExistException {
        PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setChangedAt(new Date())
                .setInUsage(false)
                .setKey("lul")
                .build();
        PublicKeyDTO resultant = publicKeyRepository.createPublicKey(newPublicKeyDTO);
        resultant.setKey("Lul");
        resultant.setChangedAt(new Date());
        PublicKeyDTO updated = publicKeyRepository.updatePublicKey(resultant);
        assertEquals(updated.getKey(), resultant.getKey());
        assertEquals(updated.getChangedAt(), resultant.getChangedAt());
        try {
            PublicKeyDTO newPub = new PublicKeyDTO.PublicKeyDTOBuilder()
                    .setId(Long.valueOf(10000))
                    .build();
            publicKeyRepository.updatePublicKey(newPub);
        } catch (DoesNotExistException e) {
            assertEquals(e.getMessage().contains("publickey"), true);
        }
    }

    @Test
    public void deletePublicKeyTest() throws AlreadyExistsException {
        PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setChangedAt(new Date())
                .setInUsage(false)
                .setKey("lul")
                .build();
        PublicKeyDTO resultant = publicKeyRepository.createPublicKey(newPublicKeyDTO);
        boolean isDeleted = publicKeyRepository.deleteById(resultant.getId());
        assertEquals(isDeleted, true);
        try {
            publicKeyRepository.deletePublicKey(Long.valueOf(1000));
        } catch (DoesNotExistException e) {
            assertEquals(e.getMessage().contains("publickey"), true);
        }
    }

    @Test
    public void setMainPublicKeyTest()
            throws AlreadyExistsException, DoesNotExistException, SecurityException, IllegalStateException,
            RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
        // ? Create first publickey
        PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setChangedAt(new Date())
                .setInUsage(false)
                .setKey("lul")
                .build();
        PublicKeyDTO resultant = publicKeyRepository.createPublicKey(newPublicKeyDTO);
        // ? Create second publickey
        PublicKeyDTO newPublicKeyDTO2 = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setChangedAt(new Date())
                .setInUsage(false)
                .setKey("lul")
                .build();
        PublicKeyDTO resultant2 = publicKeyRepository.createPublicKey(newPublicKeyDTO2);
        // ? Update it
        boolean gotUpdated = publicKeyRepository.setMainPublicKey(resultant.getId());
        assertEquals(gotUpdated, true);
        // ? Check if it got updated on the first
        resultant = publicKeyRepository.getPublicKeyById(resultant.getId());
        assertEquals(resultant.isInUsage(), false);
        // ? Update the second
        boolean gotUpdated2 = publicKeyRepository.setMainPublicKey(resultant2.getId());
        assertEquals(gotUpdated2, true);
        // ? Check in the sec if it got updated
        resultant2 = publicKeyRepository.getPublicKeyById(resultant2.getId());
        assertEquals(resultant2.isInUsage(), false);
        // ? Check if after update it got the first one to false
        resultant = publicKeyRepository.getPublicKeyById(resultant.getId());
        assertEquals(resultant.isInUsage(), false);
        try {
            publicKeyRepository.setMainPublicKey(Long.valueOf(10000));
        } catch (DoesNotExistException e) {
            assertEquals(e.getMessage().contains("publickey"), true);
        }
    }

    @Test
    public void getMainPublicKeyTest() throws AlreadyExistsException, DoesNotExistException {
        PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setChangedAt(new Date())
                .setInUsage(false)
                .setKey("lul")
                .build();
        PublicKeyDTO resultant = publicKeyRepository.createPublicKey(newPublicKeyDTO);
        PublicKeyDTO obj = publicKeyRepository.getMainPublicKey();
        assertEquals(obj, null);
        publicKeyRepository.setMainPublicKey(resultant.getId());
        obj = publicKeyRepository.getMainPublicKey();
        assertEquals(obj != null, true);
    }

    @Test
    public void getAllPublicKeysTest() throws AlreadyExistsException {
        for (int i = 0; i < 10; i++) {
            PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                    .setChangedAt(new Date())
                    .setInUsage(false)
                    .setKey("lul")
                    .build();
            publicKeyRepository.createPublicKey(newPublicKeyDTO);
        }
        List<PublicKeyDTO> result = publicKeyRepository.getAllPublicKeys(0, 30);
        assertEquals(result.size(), 10);
    }
}
