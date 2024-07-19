package rest.services.Ticket.DELETE;

import java.util.UUID;
import jakarta.ws.rs.core.Response;
import rest.repositories.TicketRepository;

public interface TicketServiceDeleteInterface {
    public Response deleteTicket(UUID id, TicketRepository ticketRepository);
}
