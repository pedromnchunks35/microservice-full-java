package rest.controller.Ticket.GET;

import java.util.UUID;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.entity.Ticket;

@Path("ticket")
public class TicketControllerGet {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTickets() {
        Ticket ticket = new Ticket.TicketBuilder()
                .setDay((short) 1)
                .setHour((short) 1)
                .setId(UUID.randomUUID())
                .setMonth((short) 1)
                .setYear((short) 2000)
                .build();
        return Response.ok(ticket).status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getById/{id}")
    public Response getTicketById(@PathParam("id") UUID id) {
        return Response.ok(id.toString()).status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getByUsername/{username}")
    public Response getTicketByUsername(@PathParam("username") String username) {
        return Response.ok(username).status(Response.Status.OK).build();
    }

}
