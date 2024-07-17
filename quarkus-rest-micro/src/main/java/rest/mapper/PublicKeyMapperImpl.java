package rest.mapper;

import java.util.ArrayList;
import java.util.List;

import rest.dto.PublicKeyDTO;
import rest.entity.PublicKey;
import rest.utils.crypto.KeyUtils;

public class PublicKeyMapperImpl {
    public static PublicKey publicKeyDTOtoPublicKey(PublicKeyDTO publicKeyDTO) {
        if (publicKeyDTO == null) {
            return null;
        }
        PublicKey finalResult = new PublicKey.PublicKeyBuilder()
                .setId(publicKeyDTO.getId())
                .setchangedAt(publicKeyDTO.getChangedAt())
                .setKey(KeyUtils.stringToByteArray(publicKeyDTO.getKey()))
                .setTickets(TicketMapperImpl.ticketDTOlistToTicketList(publicKeyDTO.getTickets()))
                .setInUsage(publicKeyDTO.isInUsage())
                .build();
        return finalResult;
    }

    public static PublicKeyDTO publicKeyToPublicKeyDTO(PublicKey publicKey) {
        if (publicKey == null) {
            return null;
        }

        PublicKeyDTO finalResult = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setId(publicKey.getId())
                .setChangedAt(publicKey.getchangedAt())
                .setKey(KeyUtils.byteArrayToString(publicKey.getKey()))
                .setTickets(TicketMapperImpl.ticketListToTicketDTOList(publicKey.getTickets()))
                .setInUsage(publicKey.isInUsage())
                .build();
        return finalResult;
    }

    public static PublicKey publicKeyDTOtoPublicKeyTail(PublicKeyDTO publicKeyDTO) {
        if (publicKeyDTO == null) {
            return null;
        }
        PublicKey finalResult = new PublicKey.PublicKeyBuilder()
                .setId(publicKeyDTO.getId())
                .setchangedAt(publicKeyDTO.getChangedAt())
                .setKey(KeyUtils.stringToByteArray(publicKeyDTO.getKey()))
                .setInUsage(publicKeyDTO.isInUsage())
                .build();
        return finalResult;
    }

    public static PublicKeyDTO publicKeyToPublicKeyDTOTail(PublicKey publicKey) {
        if (publicKey == null) {
            return null;
        }

        PublicKeyDTO finalResult = new PublicKeyDTO.PublicKeyDTOBuilder()
                .setId(publicKey.getId())
                .setChangedAt(publicKey.getchangedAt())
                .setKey(KeyUtils.byteArrayToString(publicKey.getKey()))
                .setInUsage(publicKey.isInUsage())
                .build();
        return finalResult;
    }

    public static List<PublicKey> publicKeyDTOListToPublicKeyList(List<PublicKeyDTO> publicKeyDTOList) {
        if (publicKeyDTOList == null) {
            return null;
        }
        List<PublicKey> publicKeyList = new ArrayList<>();
        for (int i = 0; i < publicKeyDTOList.size(); i++) {
            PublicKey publicKey = publicKeyDTOtoPublicKey(publicKeyDTOList.get(i));
            publicKeyList.add(publicKey);
        }
        return publicKeyList;
    }

    public static List<PublicKeyDTO> publicKeyListToPublicKeyDTOList(List<PublicKey> publicKeyList) {
        if (publicKeyList == null) {
            return null;
        }
        List<PublicKeyDTO> publicKeyDTOList = new ArrayList<>();
        for (int i = 0; i < publicKeyList.size(); i++) {
            PublicKeyDTO publicKeyDTO = publicKeyToPublicKeyDTO(publicKeyList.get(i));
            publicKeyDTOList.add(publicKeyDTO);
        }
        return publicKeyDTOList;
    }
}
