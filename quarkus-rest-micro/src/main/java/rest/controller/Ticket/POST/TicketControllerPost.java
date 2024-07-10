package rest.controller.Ticket.POST;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.TicketDTO;

@Path("ticket")
public class TicketControllerPost {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTicket(@RequestBody TicketDTO ticketDTO) {
        return Response.ok(ticketDTO).status(Response.Status.CREATED).build();
    }
}
