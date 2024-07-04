package rest.mapper;

import java.util.ArrayList;
import java.util.List;

import rest.dto.TicketDTO;
import rest.entity.Ticket;

public class TicketMapperImpl {
    public static Ticket ticketDTOtoTicket(TicketDTO ticketDTO) {
        if (ticketDTO == null) {
            return null;
        }
        return new Ticket.TicketBuilder()
                .setDay(ticketDTO.getDay())
                .setHour(ticketDTO.getHour())
                .setId(ticketDTO.getId())
                .setMonth(ticketDTO.getMonth())
                .setUser(UserMapperImpl.userDTOtoUser(ticketDTO.getUser()))
                .setPublicKey(PublicKeyMapperImpl.publicKeyDTOtoPublicKey(ticketDTO.getPublicKey()))
                .setYear(ticketDTO.getYear())
                .build();
    }

    public static TicketDTO ticketToTicketDTO(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        return new TicketDTO.TicketDTOBuilder()
                .setDay(ticket.getDay())
                .setHour(ticket.getHour())
                .setId(ticket.getId())
                .setMonth(ticket.getMonth())
                .setUser(UserMapperImpl.userToUserDTO(ticket.getUser()))
                .setPublicKeyDTO(PublicKeyMapperImpl.publicKeyToPublicKeyDTO(ticket.getPublicKey()))
                .setYear(ticket.getYear())
                .build();
    }

    public static List<Ticket> ticketDTOlistToTicketList(List<TicketDTO> ticketDTOList) {
        if (ticketDTOList == null) {
            return null;
        }
        List<Ticket> newList = new ArrayList<Ticket>();
        for (int i = 0; i < ticketDTOList.size(); i++) {
            newList.add(ticketDTOtoTicket(ticketDTOList.get(i)));
        }
        return newList;
    }

    public static List<TicketDTO> ticketListToTicketDTOList(List<Ticket> ticketList) {
        if (ticketList == null) {
            return null;
        }
        List<TicketDTO> newList = new ArrayList<TicketDTO>();
        for (int i = 0; i < ticketList.size(); i++) {
            newList.add(ticketToTicketDTO(ticketList.get(i)));
        }
        return newList;
    }
}
