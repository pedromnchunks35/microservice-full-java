package rest.controllers.Ticket.DELETE;

import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.repositories.TicketRepository;
import rest.services.Ticket.DELETE.TicketServiceDeleteImpl;
import rest.utils.headers.HeadersOperations;
import rest.utils.keycloak.KeycloakOperationsImpl;

@Path("ticket")
public class TicketControllerDelete {
    @Inject
    TicketRepository ticketRepository;
    @Inject
    TicketServiceDeleteImpl ticketServiceDeleteImpl;
    @Inject
    HeadersOperations headersOperations;
    @Inject
    KeycloakOperationsImpl keycloakOperationsImpl;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteTicket(@PathParam("id") UUID id, @Context HttpHeaders headers) {
        if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)) {
            return Response
                    .ok(new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("You should present a valid token or have the role of admin").build())
                    .status(401).build();
        }
        return ticketServiceDeleteImpl.deleteTicket(id, ticketRepository);
    }
}
