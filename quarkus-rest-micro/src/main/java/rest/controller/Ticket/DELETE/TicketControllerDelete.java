package rest.controller.Ticket.DELETE;

import java.util.UUID;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("ticket")
public class TicketControllerDelete {

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteTicket(@PathParam("id") UUID id) {
        return Response.ok(id.toString()).status(Response.Status.OK).build();
    }
}
