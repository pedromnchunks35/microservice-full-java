package rest.controller.Ticket.DELETE;

import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import rest.dto.GeneralResponse;
import rest.service.TicketService;

@Path("ticket")
public class TicketControllerDelete {
    @Inject
    TicketService ticketService;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteTicket(@PathParam("id") UUID id) {
        boolean isDeleted = ticketService.deleteById(id);
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
