package rest.mapper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

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
                .setPublicKey(PublicKeyMapperImpl.publicKeyDTOtoPublicKeyTail(ticketDTO.getPublicKey()))
                .setUser(UserMapperImpl.userDTOtoUserTail(ticketDTO.getUser()))
                .setMonth(ticketDTO.getMonth())
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
                .setPublicKeyDTO(PublicKeyMapperImpl.publicKeyToPublicKeyDTOTail(ticket.getPublicKey()))
                .setUser(UserMapperImpl.userToUserDTOTail(ticket.getUser()))
                .setMonth(ticket.getMonth())
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

    public static JSONObject ticketDTOtoJsonObject(TicketDTO ticketDTO) {
        JSONObject jsonForm = new JSONObject();
        jsonForm.put("id", ticketDTO.getId() == null ? null : ticketDTO.getId().toString());
        jsonForm.put("day", ticketDTO.getDay());
        jsonForm.put("hour", ticketDTO.getHour());
        jsonForm.put("month", ticketDTO.getMonth());
        jsonForm.put("year", ticketDTO.getYear());
        jsonForm.put("username", ticketDTO.getUser().getUsername());
        return jsonForm;
    }
}
