package rest.controllers.Ticket.GET;

import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.repositories.TicketRepository;
import rest.services.Ticket.GET.TicketServiceGetImpl;
import rest.utils.headers.HeadersOperations;
import rest.utils.keycloak.KeycloakOperationsImpl;

@Path("ticket")
public class TicketControllerGet {
    @Inject
    TicketRepository ticketRepository;
    @Inject
    TicketServiceGetImpl ticketServiceGetImpl;
    @Inject
    HeadersOperations headersOperations;
    @Inject
    KeycloakOperationsImpl keycloakOperationsImpl;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{page}/{size}")
    public Response getAllTickets(@PathParam("page") int page, @PathParam("size") int size,
            @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return ticketServiceGetImpl.getAllTickets(page, size, ticketRepository);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getById/{id}")
    public Response getTicketById(@PathParam("id") UUID id, @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return ticketServiceGetImpl.getTicketById(id, ticketRepository);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getByUsername/{username}/{page}/{size}")
    public Response getTicketByUsername(@PathParam("username") String username, @PathParam("page") int page,
            @PathParam("size") int size, @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return ticketServiceGetImpl.getTicketByUsername(username, page, size, ticketRepository);
    }

}
