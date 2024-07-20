package rest.controllers.Ticket.POST;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.TicketDTO;
import rest.repositories.PublicKeyRepository;
import rest.repositories.TicketRepository;
import rest.repositories.UserRepository;
import rest.services.Ticket.POST.TicketServicePostImpl;
import rest.utils.headers.HeadersOperations;
import rest.utils.keycloak.KeycloakOperationsImpl;

@Path("ticket")
public class TicketControllerPost {
        @Inject
        TicketRepository ticketRepository;
        @Inject
        PublicKeyRepository publicKeyRepository;
        @Inject
        UserRepository userRepository;
        @Inject
        TicketServicePostImpl ticketServicePostImpl;
        @Inject
        HeadersOperations headersOperations;
        @Inject
        KeycloakOperationsImpl keycloakOperationsImpl;

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response createTicket(@RequestBody TicketDTO ticketDTO, @Context HttpHeaders headers) {
                if (!headersOperations.verifyRole(headers, "admin", keycloakOperationsImpl)
                                && !headersOperations.verifyRole(headers, "user", keycloakOperationsImpl)) {
                        return Response
                                        .ok(new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("You should present a valid token or have the role of admin or user")
                                                        .build())
                                        .status(401).build();
                }
                return ticketServicePostImpl.createTicket(ticketDTO, ticketRepository, publicKeyRepository,
                                userRepository);
        }
}
