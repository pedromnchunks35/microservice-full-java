package rest.services.Ticket.DELETE;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import rest.dto.GeneralResponse;
import rest.repositories.TicketRepository;
@ApplicationScoped
public class TicketServiceDeleteImpl implements TicketServiceDeleteInterface {

    @Override
    public Response deleteTicket(UUID id, TicketRepository ticketRepository) {
        boolean isDeleted = ticketRepository.deleteById(id);
        GeneralResponse<Boolean> res;
        Status status;
        if (isDeleted) {
            res = new GeneralResponse.GeneralResponseBuilder<Boolean>()
                    .setMessage("Success")
                    .setResponse(isDeleted)
                    .build();
            status = Response.Status.ACCEPTED;
        } else {
            res = new GeneralResponse.GeneralResponseBuilder<Boolean>()
                    .setMessage("Error")
                    .setResponse(isDeleted)
                    .build();
            status = Response.Status.BAD_REQUEST;
        }
        return Response.ok(res).status(status).build();
    }

}
