package rest.controller.Ticket.POST;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.json.JSONObject;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.PublicKeyDTO;
import rest.dto.TicketDTO;
import rest.dto.UserDTO;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;
import rest.mapper.TicketMapperImpl;
import rest.service.PublicKeyService;
import rest.service.TicketService;
import rest.service.UserService;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;

import rest.utils.crypto.KeyUtils;
import rest.utils.date.TicketDateUtils;;

@Path("ticket")
public class TicketControllerPost {
        @Inject
        TicketService ticketService;
        @Inject
        PublicKeyService publicKeyService;
        @Inject
        UserService userService;

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response createTicket(@RequestBody TicketDTO ticketDTO) {
                // ? Check if the user was provided
                if (ticketDTO.getUser() == null || ticketDTO.getUser().getUsername() == null
                                || ticketDTO.getUser().getUsername().length() == 0) {
                        return Response.ok(
                                        new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("Error, you should provide the username of a user")
                                                        .build())
                                        .status(Response.Status.BAD_REQUEST).build();
                }
                // ? Check if the user exists
                UserDTO checkObj = userService.getUserByUsername(ticketDTO.getUser().getUsername());
                if (checkObj == null) {
                        return Response.ok(
                                        new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("Error, the user you gave does not exist")
                                                        .build())
                                        .status(Response.Status.BAD_REQUEST)
                                        .build();
                }
                // ? Get the public key that is beeing used currently to hash the ticket
                PublicKeyDTO publicKeyToCreateTicket = publicKeyService.getMainPublicKey();
                if (publicKeyToCreateTicket == null || publicKeyToCreateTicket.getKey() == null) {
                        return Response.ok(
                                        new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("There is no main publickey setted to hash the ticket")
                                                        .build())
                                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .build();
                }
                // ? Get current time
                Calendar c = Calendar.getInstance();
                // ? Set the ticket with the necessary data
                TicketDateUtils.generateTicketDTO(ticketDTO, c);
                // ? Try to make the hash of the ticket
                JSONObject jsonFormTicket = TicketMapperImpl.ticketDTOtoJsonObject(ticketDTO);
                // ? Get public key
                String publicKeyStringForm = new String(publicKeyToCreateTicket.getKey());
                String publicKeyWithoutHeaders = KeyUtils.removeX509Headers(publicKeyStringForm);
                PublicKey publicKey;
                try {
                        publicKey = KeyUtils.getPublicKeyFromKeyWithNoHeaders(publicKeyWithoutHeaders);
                } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                        return Response.ok(
                                        new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("Error using the given publickey "
                                                                        + e.getMessage())
                                                        .build())
                                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .build();
                }
                // ? Encrypt the ticket
                String encryptedForm;
                try {
                        encryptedForm = KeyUtils.encryptTicket(jsonFormTicket.toString(), publicKey);
                } catch (InvalidKeyException e) {
                        return Response.ok(
                                        new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("Error using the given publickey "
                                                                        + e.getMessage())
                                                        .build())
                                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .build();
                } catch (Exception e) {
                        return Response.ok(
                                        new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("Error using the given publickey "
                                                                        + e.getMessage())
                                                        .build())
                                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .build();
                }
                // ? Save ticket
                try {
                        ticketService.createTicket(ticketDTO);
                } catch (AlreadyExistsException e) {
                        return Response.ok(
                                        new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("Given ticket already exists "
                                                                        + e.getMessage())
                                                        .build())
                                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .build();
                } catch (DoesNotExistException e) {
                        return Response.ok(
                                        new GeneralResponse.GeneralResponseBuilder<>()
                                                        .setMessage("There is no publickey to sign this ticket "
                                                                        + e.getMessage())
                                                        .build())
                                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .build();
                }
                ticketDTO.setEncryptedTicket(encryptedForm);
                return Response.ok(ticketDTO).status(Response.Status.CREATED).build();
        }
}
