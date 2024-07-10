package rest.controller.Ticket.PUT;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.TicketDTO;
import rest.service.TicketService;

@Path("ticket")
public class TicketControllerPut {
    @Inject
    TicketService ticketService;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTicket(@RequestBody TicketDTO ticketDTO) {
        return Response.ok(ticketDTO).status(Response.Status.OK).build();
    }
}
