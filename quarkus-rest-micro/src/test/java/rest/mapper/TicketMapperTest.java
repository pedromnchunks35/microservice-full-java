package rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import rest.dto.TicketDTO;
import rest.entity.Ticket;

@QuarkusTest
public class TicketMapperTest {
    @Test
    public void TicketToTicketDTOtest() {
        Ticket ticket = new Ticket.TicketBuilder()
                .setId(UUID.randomUUID())
                .setDay((short) 1)
                .setHour((short) 1)
                .setMonth((short) 1)
                .setYear((short) 2000)
                .build();
        TicketDTO ticketDTO = TicketMapperImpl.ticketToTicketDTO(ticket);
        assertEquals(ticketDTO.getId(), ticket.getId());
        assertEquals(ticketDTO.getDay(), ticket.getDay());
        assertEquals(ticketDTO.getHour(), ticket.getHour());
        assertEquals(ticketDTO.getMonth(), ticket.getMonth());
        assertEquals(ticketDTO.getYear(), ticket.getYear());
    }

    @Test
    public void TicketDTOtoTicket() {
        TicketDTO ticketDTO = new TicketDTO.TicketDTOBuilder()
                .setId(UUID.randomUUID())
                .setDay((short) 1)
                .setHour((short) 1)
                .setMonth((short) 1)
                .setYear((short) 2000)
                .build();
        Ticket ticket = TicketMapperImpl.ticketDTOtoTicket(ticketDTO);
        assertEquals(ticketDTO.getId(), ticket.getId());
        assertEquals(ticketDTO.getDay(), ticket.getDay());
        assertEquals(ticketDTO.getHour(), ticket.getHour());
        assertEquals(ticketDTO.getMonth(), ticket.getMonth());
        assertEquals(ticketDTO.getYear(), ticket.getYear());
    }

    @Test
    public void TicketDTOListToTicketListTest() {
        TicketDTO ticketDTO = new TicketDTO.TicketDTOBuilder()
                .setId(UUID.randomUUID())
                .setDay((short) 1)
                .setHour((short) 1)
                .setMonth((short) 1)
                .setYear((short) 2000)
                .build();
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ticketDTOList.add(ticketDTO);
        }
        List<Ticket> ticketList = TicketMapperImpl.ticketDTOlistToTicketList(ticketDTOList);
        for (int i = 0; i < ticketList.size(); i++) {
            Ticket ticket = ticketList.get(i);
            assertEquals(ticketDTO.getId(), ticket.getId());
            assertEquals(ticketDTO.getDay(), ticket.getDay());
            assertEquals(ticketDTO.getHour(), ticket.getHour());
            assertEquals(ticketDTO.getMonth(), ticket.getMonth());
            assertEquals(ticketDTO.getYear(), ticket.getYear());
        }
    }

    @Test
    public void TicketDTOListToTicketList() {
        Ticket ticket = new Ticket.TicketBuilder()
                .setId(UUID.randomUUID())
                .setDay((short) 1)
                .setHour((short) 1)
                .setMonth((short) 1)
                .setYear((short) 2000)
                .build();
        List<Ticket> ticketList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ticketList.add(ticket);
        }
        List<TicketDTO> ticketDTOList = TicketMapperImpl.ticketListToTicketDTOList(ticketList);
        for (int i = 0; i < ticketDTOList.size(); i++) {
            TicketDTO ticketDTO = ticketDTOList.get(i);
            assertEquals(ticketDTO.getId(), ticket.getId());
            assertEquals(ticketDTO.getDay(), ticket.getDay());
            assertEquals(ticketDTO.getHour(), ticket.getHour());
            assertEquals(ticketDTO.getMonth(), ticket.getMonth());
            assertEquals(ticketDTO.getYear(), ticket.getYear());
        }
    }
}
