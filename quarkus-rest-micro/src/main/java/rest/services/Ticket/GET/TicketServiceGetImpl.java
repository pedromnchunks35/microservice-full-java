package rest.services.Ticket.GET;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.TicketDTO;
import rest.repositories.TicketRepository;
import java.util.List;
@ApplicationScoped
public class TicketServiceGetImpl implements TicketServiceGetInterface {

    @Override
    public Response getAllTickets(int page, int size, TicketRepository ticketRepository) {
        List<TicketDTO> result = ticketRepository.getAllTickets(page, size);
        return Response.ok(result).status(Response.Status.OK).build();
    }

    @Override
    public Response getTicketById(UUID id, TicketRepository ticketRepository) {
        TicketDTO response = ticketRepository.getTicketsById(id);
        if (response == null) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error, no ticket with that id got found")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response.ok(response).status(Response.Status.OK).build();
    }

    @Override
    public Response getTicketByUsername(String username, int page, int size, TicketRepository ticketRepository) {
        if (username.length() == 0 || page < 0 || size <= 0) {
            return Response.ok(new GeneralResponse.GeneralResponseBuilder<Object>()
                    .setMessage("Bad request")
                    .build()).status(Response.Status.BAD_REQUEST).build();
        }
        List<TicketDTO> response = ticketRepository.getTicketsByUsername(username, page, size);
        return Response.ok(response).status(Response.Status.OK).build();
    }

}
