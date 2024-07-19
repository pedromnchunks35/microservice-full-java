package rest.services.Ticket.GET;

import java.util.UUID;

import jakarta.ws.rs.core.Response;
import rest.repositories.TicketRepository;

public interface TicketServiceGetInterface {
    public Response getAllTickets(int page, int size, TicketRepository ticketRepository);

    public Response getTicketById(UUID id, TicketRepository ticketRepository);

    public Response getTicketByUsername(String username,int page, int size,TicketRepository ticketRepository);
}
