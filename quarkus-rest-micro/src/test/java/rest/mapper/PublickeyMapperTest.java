package rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import rest.dto.PublicKeyDTO;
import rest.entity.PublicKey;

@QuarkusTest
public class PublickeyMapperTest {
    @Test
    public void publicKeyToPublicKeyDTOTest() {
        PublicKey pubkey = new PublicKey.PublicKeyBuilder()
                .setId(Long.valueOf(1))
                .setchangedAt(new Date())
                .setKey(new byte[] { 111, 010, 011 })
                .build();
        PublicKeyDTO pubkeyDTO = PublicKeyMapperImpl.publicKeyToPublicKeyDTO(pubkey);
        assertEquals(pubkeyDTO.getId(), pubkey.getId());
        assertEquals(pubkeyDTO.getChangedAt(), pubkey.getchangedAt());
    }

    @Test
    public void publicKeyDTOtoPublicKeyTest() {
        PublicKeyDTO pubkeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setId(Long.valueOf(1))
                .setChangedAt(new Date())
                .setKey(new byte[] { 111, 11, 00 })
                .build();
        PublicKey pubkey = PublicKeyMapperImpl.publicKeyDTOtoPublicKey(pubkeyDTO);
        assertEquals(pubkeyDTO.getId(), pubkey.getId());
        assertEquals(pubkeyDTO.getChangedAt(), pubkey.getchangedAt());
    }

    @Test
    public void publicKeyListToPublicKeyDTOListTest() {
        List<PublicKey> pubKeyList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PublicKey pubkey = new PublicKey.PublicKeyBuilder()
                    .setId(Long.valueOf(1))
                    .setchangedAt(new Date())
                    .setKey(new byte[] { 111, 010, 011 })
                    .build();
            pubKeyList.add(pubkey);
        }
        List<PublicKeyDTO> publicKeyDTOList = PublicKeyMapperImpl.publicKeyListToPublicKeyDTOList(pubKeyList);
        for (int i = 0; i < 10; i++) {
            assertEquals(publicKeyDTOList.get(i).getId(), pubKeyList.get(i).getId());
            assertEquals(publicKeyDTOList.get(i).getChangedAt(), pubKeyList.get(i).getchangedAt());
        }
    }

    @Test
    public void publicKeyDTOListToPublicKeyListTest() {
        List<PublicKeyDTO> publicKeyDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PublicKeyDTO pubkey = new PublicKeyDTO.PublicKeyDTOBuilder()
                    .setId(Long.valueOf(1))
                    .setChangedAt(new Date())
                    .setKey(new byte[] { 111, 010, 011 })
                    .build();
            publicKeyDTOList.add(pubkey);
        }
        List<PublicKey> publicKeyList = PublicKeyMapperImpl.publicKeyDTOListToPublicKeyList(publicKeyDTOList);
        for (int i = 0; i < 10; i++) {
            assertEquals(publicKeyDTOList.get(i).getId(), publicKeyList.get(i).getId());
            assertEquals(publicKeyDTOList.get(i).getChangedAt(), publicKeyList.get(i).getchangedAt());
        }
    }
}
