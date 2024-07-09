package rest.utils.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import rest.dto.PublicKeyDTO;
import rest.dto.TicketDTO;
import rest.dto.UserDTO;
import rest.mapper.PublicKeyMapperImpl;
import rest.mapper.TicketMapperImpl;
import rest.service.PublicKeyService;
import rest.service.TicketService;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Base64;

@QuarkusTest
public class KeyUtilsTest {
        @InjectMock
        PublicKeyService publicKeyService;
        @InjectMock
        TicketService ticketService;

        @Test
        public void encryptTicketsTest() throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException,
                        IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
                // ? GENERATING THE MATERIALS AND MOCK THE SUPPOSED BEHAVIOR FOR THE CREATION OF
                // A TICKET
                String pubkey = "-----BEGIN PUBLIC KEY-----\n" +
                                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArWmfaQFOsbIkxK4+vjeR\n" +
                                "+p3OEZo3crao2PpTcATlYCn48iSxhHfX3NAKUGCpdnsHyLE4i0hicr8PzHzKibqJ\n" +
                                "grbDf+ZYoXpJnWV1uGfLLFBJrf4Y54Q5Ii3UdQWTQfLXYuEvrQcYkZShjf0l3Gz5\n" +
                                "pnhOD7YbXyystH/ZUSAQj4h3a7HiRaqDcDI9ARuKoyU1teEXMzrHuCecsIQokIwz\n" +
                                "515DXZ9lXfV3sq6+i/ai/jqhWpvoHR7jacH8WBUR7Zo3woWqfFk6fgWvq5et/pw2\n" +
                                "b5aF715Flzd85cs2nnHrM/R26CHSjhSLY5/bfBB3MgSeBetuZN2XUWx7Oo1KgAFb\n" +
                                "aQIDAQAB\n" +
                                "-----END PUBLIC KEY-----";
                when(publicKeyService.getPublicKeyById(Long.valueOf(1)))
                                .thenReturn(new PublicKeyDTO.PublicKeyDTOBuilder()
                                                .setCreatedBy(pubkey.getBytes())
                                                .setChangedAt(new Date())
                                                .setId(Long.valueOf(1))
                                                .build());
                String privateKeyPEM = "-----BEGIN PRIVATE KEY-----\n" +
                                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCtaZ9pAU6xsiTE\n" +
                                "rj6+N5H6nc4RmjdytqjY+lNwBOVgKfjyJLGEd9fc0ApQYKl2ewfIsTiLSGJyvw/M\n" +
                                "fMqJuomCtsN/5lihekmdZXW4Z8ssUEmt/hjnhDkiLdR1BZNB8tdi4S+tBxiRlKGN\n" +
                                "/SXcbPmmeE4PthtfLKy0f9lRIBCPiHdrseJFqoNwMj0BG4qjJTW14RczOse4J5yw\n" +
                                "hCiQjDPnXkNdn2Vd9Xeyrr6L9qL+OqFam+gdHuNpwfxYFRHtmjfChap8WTp+Ba+r\n" +
                                "l63+nDZvloXvXkWXN3zlyzaecesz9HboIdKOFItjn9t8EHcyBJ4F625k3ZdRbHs6\n" +
                                "jUqAAVtpAgMBAAECggEACKcKgyzDhm5DE2U2UDtplz2keJBfT+P5TMpAu4gjAcdW\n" +
                                "Wo6mJNvt82CWAyivi+/blZHyJlD4SI1r9mILoQ+jqNgBrTILHhOB9M6JLyhOpOlk\n" +
                                "o7/X8kVQrHno7eXXUSUSxiZCQLnn6eumm/UTnIn7cujxzCrj9r296EuwxjF5Yg/y\n" +
                                "Bvd1D7ylTNANi4L1EDwI+F1IefOYIJRx9kZmEwMArn3M/dk1VidOxBS5aMJYOUiR\n" +
                                "uL/SMeWx2uTjdc3fcyKmomkW7gb4DWTKq1zG5ATSxyHS+GU8/xKpaHXPZpq3JL5x\n" +
                                "WK7Rc3qlKi4NvZDp/kOnzLtHpzmMh2YF+rZDsuDeAQKBgQDeXuJ83uYrhf0zeSJj\n" +
                                "fUvP0oo5M9QkGHkBzsPc+F+TRocgDGH+W2nCA83xOYETgOZaNkcME6h5hAqHYTTC\n" +
                                "V/OfhT/Prq9fs6Oj2ElDs81hn1Qos6n2w7mKQZ2621AKJmeVYNFPAnZdGl50XtUp\n" +
                                "Q9YN/w7tWVlNjyZbZUjhPgcJoQKBgQDHo1GqC1mifKfHVYRNkm0cRD1Hls2oxvAC\n" +
                                "yozBOnVNXrpeX6EMPxb9SSTzuTEPcL27D4r15NUpL4/Q0dH24l1V7NChuQDncdoi\n" +
                                "XDI2Pz5oJ2yho6dcHFN6deSHyTrLVNME+6MEskjMDJDK4dapRyTZljMPVQNKJ4jf\n" +
                                "vIJh9z9MyQKBgGI9NwAFlVgbDL5lwJyFaS26DNiyngEIUsVKrFr923N8EAxHjC9M\n" +
                                "AU/2UFBGoFCWfK3UFtUuBhsRC3m9pwpIwtdxjbxZpx63hQ7Iaa2gTJbZRdvpa6gZ\n" +
                                "1elPjbhUVWr2hoKG0FGAe/wrvxjYIIHndzSdvuF6vKo4GLD98RGZTpChAoGAHKY+\n" +
                                "aPW6Bkx3U27dTriNufm9BZIJr4flF7Fzo6M9BD5s5nA4z2YaaXzeta9E8B9QyxB+\n" +
                                "86zEPmXZ8LC7vf9cP6F9i0feblhzYmzys8A9t3q45zVyJhDWlgNnKWlia52frt5h\n" +
                                "1aG0Orwu5z6jrKVOD3+zkia0Eyc7SEhoOaDez6kCgYEArse4+Ty2pWunqpzUTnzu\n" +
                                "e8NaNXd/Z9O7NRcMbC5ixD7vMJaPec/dSXkJXL/7+DlKAGExs1Rv9mM7Mk36xJ2d\n" +
                                "KUNb0IoffTv/gbdVywEoqh3nMiLXEzy4nCegrqUhN3rWFbfv6c9NwMZNCDmf3wOo\n" +
                                "wqtDrDBclPqD2cbPMNuAFWo=\n" +
                                "-----END PRIVATE KEY-----";
                UUID randomId = UUID.randomUUID();
                when(ticketService.getTicketsById(randomId)).thenReturn(
                                new TicketDTO.TicketDTOBuilder()
                                                .setId(randomId)
                                                .setDay((short) 1)
                                                .setHour((short) 2)
                                                .setDay((short) 3)
                                                .setMonth((short) 5)
                                                .setUser(new UserDTO.UserDTOBuilder()
                                                                .setCountry("PT")
                                                                .setLocation("Braga")
                                                                .setPhoneNumber("9129192193")
                                                                .setPostalCode("4520-123")
                                                                .setUsername("carambolas123")
                                                                .build())
                                                .setYear((short) 2004)
                                                .build());

                // ? GETTING THE TICKET TO SIGN
                TicketDTO ticketDTO = ticketService.getTicketsById(randomId);
                JSONObject jsonFormOfTicket = TicketMapperImpl.ticketDTOtoJsonObject(ticketDTO);
                // ? Getting the publickey
                PublicKeyDTO publicKeyDTO = publicKeyService.getPublicKeyById(Long.valueOf(1));
                String publicKeyStringForm = new String(publicKeyDTO.getCreatedBy());
                String publicKeyWithoutHeaders = KeyUtils.removeX509Headers(publicKeyStringForm);
                PublicKey publicKey = KeyUtils.getPublicKeyFromKeyWithNoHeaders(publicKeyWithoutHeaders);
                // ? Encrypt the ticket
                String encryptedForm = KeyUtils.encryptTicket(jsonFormOfTicket.toString(), publicKey);
                // ? Lets test out this encrypted data

                // ? Get private key
                String privateKeyWithoutHeaders = KeyUtils.removeX509Headers(privateKeyPEM);
                PrivateKey privateKey = KeyUtils.getPrivateKeyFromKeyWithNoHeaders(privateKeyWithoutHeaders);

                // ? Decrypt the data
                String decryptedData = KeyUtils.decryptMessage(privateKey, encryptedForm);

                // ? Convert it to json object
                JSONObject ticketJson = new JSONObject(decryptedData);
                assertEquals((Integer) ticketJson.get("day"), Integer.valueOf(3));
                assertEquals((String) ticketJson.get("username"), "carambolas123");
                assertEquals(UUID.fromString((String)ticketJson.get("id")), randomId);
        }
}
