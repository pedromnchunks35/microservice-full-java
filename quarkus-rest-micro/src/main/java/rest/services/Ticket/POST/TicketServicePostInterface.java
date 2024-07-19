package rest.services.Ticket.POST;

import jakarta.ws.rs.core.Response;
import rest.dto.TicketDTO;
import rest.repositories.PublicKeyRepository;
import rest.repositories.TicketRepository;
import rest.repositories.UserRepository;

public interface TicketServicePostInterface {
    public Response createTicket(TicketDTO ticketDTO, TicketRepository ticketRepository,PublicKeyRepository publicKeyRepository,UserRepository userRepository);
}
