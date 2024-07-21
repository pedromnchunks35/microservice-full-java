package service.ticketoperations;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.management.openmbean.InvalidKeyException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import service.exceptions.InvalidFieldException;
import service.exceptions.InvalidTicketException;
import service.ticket.Ticket;

@QuarkusTest
public class TicketOperationsTest {
    @Test
    public void isValidTicketTest() throws InvalidTicketException {
        Calendar c = Calendar.getInstance();
        JSONObject mockedJson = new JSONObject();
        mockedJson.put("year", c.get(Calendar.YEAR) + 1);
        mockedJson.put("month", 3);
        mockedJson.put("day", 2);
        mockedJson.put("hour", 2);
        mockedJson.put("username", "pedrocas");
        TicketOperations.isValidTicket(mockedJson, "pedrocas");
        mockedJson.put("year", c.get(Calendar.YEAR));
        mockedJson.put("month", c.get(Calendar.MONTH) + 2);
        TicketOperations.isValidTicket(mockedJson, "pedrocas");
        mockedJson.put("month", c.get(Calendar.MONTH) + 1);
        mockedJson.put("day", c.get(Calendar.DAY_OF_MONTH) + 1);
        TicketOperations.isValidTicket(mockedJson, "pedrocas");
        mockedJson.put("day", c.get(Calendar.DAY_OF_MONTH));
        mockedJson.put("hour", c.get(Calendar.HOUR) + 1);
        TicketOperations.isValidTicket(mockedJson, "pedrocas");
        try {
            mockedJson.put("year", c.get(Calendar.YEAR) - 1);
            TicketOperations.isValidTicket(mockedJson, "pedrocas");
        } catch (InvalidTicketException e) {
            assertEquals(e.getMessage().contains("Ticket expired"), true);
        }
        try {
            mockedJson.put("year", c.get(Calendar.YEAR));
            mockedJson.put("month", c.get(Calendar.MONTH));
            TicketOperations.isValidTicket(mockedJson, "pedrocas");
        } catch (InvalidTicketException e) {
            assertEquals(e.getMessage().contains("Ticket expired"), true);
        }
        try {
            mockedJson.put("month", c.get(Calendar.MONTH));
            TicketOperations.isValidTicket(mockedJson, "pedrocas");
        } catch (InvalidTicketException e) {
            assertEquals(e.getMessage().contains("Ticket expired"), true);
        }
        try {
            mockedJson.put("month", c.get(Calendar.MONTH) + 1);
            mockedJson.put("day", c.get(Calendar.DAY_OF_MONTH) - 2);
            TicketOperations.isValidTicket(mockedJson, "pedrocas");
        } catch (InvalidTicketException e) {
            assertEquals(e.getMessage().contains("Ticket expired"), true);
        }
        try {
            mockedJson.put("day", c.get(Calendar.DAY_OF_MONTH));
            mockedJson.put("hour", c.get(Calendar.HOUR));
            TicketOperations.isValidTicket(mockedJson, "pedrocas");
        } catch (InvalidTicketException e) {
            assertEquals(e.getMessage().contains("Ticket expired"), true);
        }

    }

    @Test
    public void checkTicketTest() throws InvalidFieldException {
        Ticket ticket = Ticket.newBuilder()
                .setTicket("dnoisnoifniosfhn")
                .setUsername("peskfpjfjoiqjo").build();
        TicketOperations.checkTicket(ticket);
        try {
            Ticket ticket2 = Ticket.newBuilder()
                    .setUsername("peskfpjfjoiqjo").build();
            TicketOperations.checkTicket(ticket2);
        } catch (InvalidFieldException e) {
            assertEquals(e.getMessage().contains("ticket hash"), true);
        }

        try {
            Ticket ticket2 = Ticket.newBuilder()
                    .setTicket("adinahdoiajhdiojhaoi").build();
            TicketOperations.checkTicket(ticket2);
        } catch (InvalidFieldException e) {
            assertEquals(e.getMessage().contains("username"), true);
        }

        try {
            Ticket ticket2 = Ticket.newBuilder()
                    .setUsername("ds")
                    .setTicket("adinahdoiajhdiojhaoi").build();
            TicketOperations.checkTicket(ticket2);
        } catch (InvalidFieldException e) {
            assertEquals(e.getMessage().contains("username"), true);
        }

        try {
            Ticket ticket2 = Ticket.newBuilder()
                    .setUsername("dssjmpofjiosnhfiohn")
                    .setTicket("ad").build();
            TicketOperations.checkTicket(ticket2);
        } catch (InvalidFieldException e) {
            assertEquals(e.getMessage().contains("ticket hash"), true);
        }
    }

    @Test
    public void checkPrivateKeyTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String pem = "-----BEGIN PRIVATE KEY-----MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCtaZ9pAU6xsiTErj6+N5H6nc4RmjdytqjY+lNwBOVgKfjyJLGEd9fc0ApQYKl2ewfIsTiLSGJyvw/MfMqJuomCtsN/5lihekmdZXW4Z8ssUEmt/hjnhDkiLdR1BZNB8tdi4S+tBxiRlKGN/SXcbPmmeE4PthtfLKy0f9lRIBCPiHdrseJFqoNwMj0BG4qjJTW14RczOse4J5ywhCiQjDPnXkNdn2Vd9Xeyrr6L9qL+OqFam+gdHuNpwfxYFRHtmjfChap8WTp+Ba+rl63+nDZvloXvXkWXN3zlyzaecesz9HboIdKOFItjn9t8EHcyBJ4F625k3ZdRbHs6jUqAAVtpAgMBAAECggEACKcKgyzDhm5DE2U2UDtplz2keJBfT+P5TMpAu4gjAcdWWo6mJNvt82CWAyivi+/blZHyJlD4SI1r9mILoQ+jqNgBrTILHhOB9M6JLyhOpOlko7/X8kVQrHno7eXXUSUSxiZCQLnn6eumm/UTnIn7cujxzCrj9r296EuwxjF5Yg/yBvd1D7ylTNANi4L1EDwI+F1IefOYIJRx9kZmEwMArn3M/dk1VidOxBS5aMJYOUiRuL/SMeWx2uTjdc3fcyKmomkW7gb4DWTKq1zG5ATSxyHS+GU8/xKpaHXPZpq3JL5xWK7Rc3qlKi4NvZDp/kOnzLtHpzmMh2YF+rZDsuDeAQKBgQDeXuJ83uYrhf0zeSJjfUvP0oo5M9QkGHkBzsPc+F+TRocgDGH+W2nCA83xOYETgOZaNkcME6h5hAqHYTTCV/OfhT/Prq9fs6Oj2ElDs81hn1Qos6n2w7mKQZ2621AKJmeVYNFPAnZdGl50XtUpQ9YN/w7tWVlNjyZbZUjhPgcJoQKBgQDHo1GqC1mifKfHVYRNkm0cRD1Hls2oxvACyozBOnVNXrpeX6EMPxb9SSTzuTEPcL27D4r15NUpL4/Q0dH24l1V7NChuQDncdoiXDI2Pz5oJ2yho6dcHFN6deSHyTrLVNME+6MEskjMDJDK4dapRyTZljMPVQNKJ4jfvIJh9z9MyQKBgGI9NwAFlVgbDL5lwJyFaS26DNiyngEIUsVKrFr923N8EAxHjC9MAU/2UFBGoFCWfK3UFtUuBhsRC3m9pwpIwtdxjbxZpx63hQ7Iaa2gTJbZRdvpa6gZ1elPjbhUVWr2hoKG0FGAe/wrvxjYIIHndzSdvuF6vKo4GLD98RGZTpChAoGAHKY+aPW6Bkx3U27dTriNufm9BZIJr4flF7Fzo6M9BD5s5nA4z2YaaXzeta9E8B9QyxB+86zEPmXZ8LC7vf9cP6F9i0feblhzYmzys8A9t3q45zVyJhDWlgNnKWlia52frt5h1aG0Orwu5z6jrKVOD3+zkia0Eyc7SEhoOaDez6kCgYEArse4+Ty2pWunqpzUTnzue8NaNXd/Z9O7NRcMbC5ixD7vMJaPec/dSXkJXL/7+DlKAGExs1Rv9mM7Mk36xJ2dKUNb0IoffTv/gbdVywEoqh3nMiLXEzy4nCegrqUhN3rWFbfv6c9NwMZNCDmf3wOowqtDrDBclPqD2cbPMNuAFWo=-----END PRIVATE KEY-----";
        TicketOperations.checkPrivateKey(pem);
        pem = "121314";
        try {
            TicketOperations.checkPrivateKey(pem);
        } catch (InvalidKeyException e) {
            assertEquals(e.getMessage().contains("bad formated private key"), true);
        }
        pem = "-----BEGIN PRIVATE KEY-----MIIjvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCtaZ9pAU6xsiTErj6+N5H6nc4RmjdytqjY+lNwBOVgKfjyJLGEd9fc0ApQYKl2ewfIsTiLSGJyvw/MfMqJuomCtsN/5lihekmdZXW4Z8ssUEmt/hjnhDkiLdR1BZNB8tdi4S+tBxiRlKGN/SXcbPmmeE4PthtfLKy0f9lRIBCPiHdrseJFqoNwMj0BG4qjJTW14RczOse4J5ywhCiQjDPnXkNdn2Vd9Xeyrr6L9qL+OqFam+gdHuNpwfxYFRHtmjfChap8WTp+Ba+rl63+nDZvloXvXkWXN3zlyzaecesz9HboIdKOFItjn9t8EHcyBJ4F625k3ZdRbHs6jUqAAVtpAgMBAAECggEACKcKgyzDhm5DE2U2UDtplz2keJBfT+P5TMpAu4gjAcdWWo6mJNvt82CWAyivi+/blZHyJlD4SI1r9mILoQ+jqNgBrTILHhOB9M6JLyhOpOlko7/X8kVQrHno7eXXUSUSxiZCQLnn6eumm/UTnIn7cujxzCrj9r296EuwxjF5Yg/yBvd1D7ylTNANi4L1EDwI+F1IefOYIJRx9kZmEwMArn3M/dk1VidOxBS5aMJYOUiRuL/SMeWx2uTjdc3fcyKmomkW7gb4DWTKq1zG5ATSxyHS+GU8/xKpaHXPZpq3JL5xWK7Rc3qlKi4NvZDp/kOnzLtHpzmMh2YF+rZDsuDeAQKBgQDeXuJ83uYrhf0zeSJjfUvP0oo5M9QkGHkBzsPc+F+TRocgDGH+W2nCA83xOYETgOZaNkcME6h5hAqHYTTCV/OfhT/Prq9fs6Oj2ElDs81hn1Qos6n2w7mKQZ2621AKJmeVYNFPAnZdGl50XtUpQ9YN/w7tWVlNjyZbZUjhPgcJoQKBgQDHo1GqC1mifKfHVYRNkm0cRD1Hls2oxvACyozBOnVNXrpeX6EMPxb9SSTzuTEPcL27D4r15NUpL4/Q0dH24l1V7NChuQDncdoiXDI2Pz5oJ2yho6dcHFN6deSHyTrLVNME+6MEskjMDJDK4dapRyTZljMPVQNKJ4jfvIJh9z9MyQKBgGI9NwAFlVgbDL5lwJyFaS26DNiyngEIUsVKrFr923N8EAxHjC9MAU/2UFBGoFCWfK3UFtUuBhsRC3m9pwpIwtdxjbxZpx63hQ7Iaa2gTJbZRdvpa6gZ1elPjbhUVWr2hoKG0FGAe/wrvxjYIIHndzSdvuF6vKo4GLD98RGZTpChAoGAHKY+aPW6Bkx3U27dTriNufm9BZIJr4flF7Fzo6M9BD5s5nA4z2YaaXzeta9E8B9QyxB+86zEPmXZ8LC7vf9cP6F9i0feblhzYmzys8A9t3q45zVyJhDWlgNnKWlia52frt5h1aG0Orwu5z6jrKVOD3+zkia0Eyc7SEhoOaDez6kCgYEArse4+Ty2pWunqpzUTnzue8NaNXd/Z9O7NRcMbC5ixD7vMJaPec/dSXkJXL/7+DlKAGExs1Rv9mM7Mk36xJ2dKUNb0IoffTv/gbdVywEoqh3nMiLXEzy4nCegrqUhN3rWFbfv6c9NwMZNCDmf3wOowqtDrDBclPqD2cbPMNuAFWo=-----END PRIVATE KEY-----";
        try {
            TicketOperations.checkPrivateKey(pem);
        } catch (InvalidKeySpecException e) {
        }
    }

    @Test
    public void isOkToEnrollTest() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException,
            java.security.InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException,
            InvalidFieldException, InvalidTicketException {
        byte[] publicKeyPem = Files.readAllBytes(Path.of("./src/test/java/service/keypairs/public.pem"));
        String publicKeyString = TicketOperations.byteArrayToString(publicKeyPem);
        String publicKeyWithoutHeaders = TicketOperations.removeX509Headers(publicKeyString);
        boolean isIt = TicketOperations.isItValidPublicKey(publicKeyString);
        assertEquals(isIt, true);
        PublicKey publicKeyNative = TicketOperations.getPublicKeyFromKeyWithNoHeaders(publicKeyWithoutHeaders);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hour", 20);
        jsonObject.put("day", 22);
        jsonObject.put("month", 2);
        jsonObject.put("year", 3000);
        jsonObject.put("username", "pedromn35");
        String encryptedData = TicketOperations.encryptTicket(jsonObject.toString(), publicKeyNative);
        Ticket ticket = Ticket.newBuilder().setTicket(encryptedData).setUsername("pedromn35").build();
        TicketOperations.isOkToEnroll(ticket, Path.of("./src/test/java/service/keypairs/key.pem"));
    }
}
