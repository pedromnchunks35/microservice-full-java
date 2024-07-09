package rest.controller.Ticket.GET;

import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.TicketDTO;
import rest.service.TicketService;

@Path("ticket")
public class TicketControllerGet {
    @Inject
    TicketService ticketService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{page}/{size}")
    public Response getAllTickets(@PathParam("page") int page, @PathParam("size") int size) {
        List<TicketDTO> result = ticketService.getAllTickets(page, size);
        return Response.ok(result).status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getById/{id}")
    public Response getTicketById(@PathParam("id") UUID id) {
        TicketDTO response = ticketService.getTicketsById(id);
        return Response.ok(response).status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getByUsername/{username}/{page}/{size}")
    public Response getTicketByUsername(@PathParam("username") String username, @PathParam("page") int page,
            @PathParam("size") int size) {
        if (username.length() == 0 || page < 0 || size <= 0) {
            return Response.ok(new GeneralResponse.GeneralResponseBuilder<Object>()
                    .setMessage("Bad request")
                    .build()).status(Response.Status.BAD_REQUEST).build();
        }
        List<TicketDTO> response = ticketService.getTicketsByUsername(username, page, size);
        return Response.ok(response).status(Response.Status.OK).build();
    }

}
