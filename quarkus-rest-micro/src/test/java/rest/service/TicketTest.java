package rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import rest.dto.PublicKeyDTO;
import rest.dto.TicketDTO;
import rest.dto.UserDTO;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;

@QuarkusTest
public class TicketTest {
        @Inject
        TicketService ticketService;
        @Inject
        PublicKeyService publicKeyService;
        @Inject
        UserService userService;
        @Inject
        UserTransaction userTransaction;

        @BeforeEach
        public void setUp() throws NotSupportedException, SystemException {
                userTransaction.begin();
        }

        @AfterEach
        public void clean() throws IllegalStateException, SecurityException, SystemException {
                userTransaction.rollback();
        }

        @Test
        public void createTicketTest() throws AlreadyExistsException, DoesNotExistException {
                UserDTO newUser = new UserDTO.UserDTOBuilder()
                                .setCountry("PK")
                                .setLocation("KKKKKK")
                                .setPhoneNumber("929261723")
                                .setPostalCode("4720-106")
                                .setUsername("deletedUser")
                                .build();
                UserDTO resultantUser = userService.createUser(newUser);
                PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                                .setChangedAt(new Date())
                                .setInUsage(false)
                                .setKey(new byte[] { 111, 111 })
                                .build();
                PublicKeyDTO resultantPublicKey = publicKeyService.createPublicKey(newPublicKeyDTO);
                TicketDTO ticket = new TicketDTO.TicketDTOBuilder()
                                .setDay((short) 1)
                                .setHour((short) 1)
                                .setUser(resultantUser)
                                .setPublicKeyDTO(resultantPublicKey)
                                .setMonth((short) 2)
                                .setYear((short) 2000)
                                .build();
                TicketDTO resultantTicket = ticketService.createTicket(ticket);
                assertEquals(ticket.getYear(), resultantTicket.getYear());
        }
}
