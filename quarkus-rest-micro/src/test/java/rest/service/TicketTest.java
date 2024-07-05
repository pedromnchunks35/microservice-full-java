package rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import rest.dto.TicketDTO;
import rest.entity.PublicKey;
import rest.entity.Ticket;
import rest.entity.User;
import rest.mapper.TicketMapperImpl;

@QuarkusTest
public class TicketTest {
        @InjectMock
        TicketService ticketService;

        @Test
        public void getTicketsByIdTest() {
                PanacheMock.mock(Ticket.class);
                PanacheMock.mock(PublicKey.class);
                PanacheMock.mock(User.class);
                Long id_public_key = Long.valueOf(1);
                PublicKey publicKey = new PublicKey.PublicKeyBuilder()
                                .setId(id_public_key)
                                .setchangedAt(new Date())
                                .setcreatedBy(new byte[] { 111, 00, 11 })
                                .build();
                Long id_user = Long.valueOf(1);
                User user = new User.UserBuilder()
                                .setCountry("PT")
                                .setId(id_user)
                                .setLocation("Lado B")
                                .setPhoneNumber("939249242")
                                .setPostalCode("Rua das camelias")
                                .build();
                UUID id_ticket = UUID.randomUUID();
                Ticket ticket = new Ticket.TicketBuilder()
                                .setDay((short) 1)
                                .setHour((short) 2)
                                .setId(id_ticket)
                                .setMonth((short) 7)
                                .setPublicKey(publicKey)
                                .setUser(user)
                                .setYear((short) 2000)
                                .build();
                assertEquals(Ticket.findById(id_ticket), null);
                Mockito.when(Ticket.findById(id_ticket)).thenReturn(ticket);
                assertEquals(Ticket.findById(id_ticket), ticket);
                Mockito.when(ticketService.getTicketsById(id_ticket))
                                .thenReturn(TicketMapperImpl.ticketToTicketDTO(ticket));
                TicketDTO ticketDTO = ticketService.getTicketsById(id_ticket);
                verify(ticketService).findById(id_ticket);
                TicketDTO tocheck = TicketMapperImpl.ticketToTicketDTO(ticket);
                assertEquals(tocheck.getId(), ticketDTO.getId());
        }

        @Test
        public void getTicketsByUsernameTest() {

        }

        @Test
        public void getAllTicketsTest() {

        }

        @Test
        public void getLastInsertedTicketByUsernameTest() {

        }

        @Test
        public void createTicketTest() {

        }
}
