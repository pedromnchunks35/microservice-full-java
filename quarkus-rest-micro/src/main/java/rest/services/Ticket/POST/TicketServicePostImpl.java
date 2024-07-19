package rest.services.Ticket.POST;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;

import org.json.JSONObject;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import rest.dto.GeneralResponse;
import rest.dto.PublicKeyDTO;
import rest.dto.TicketDTO;
import rest.dto.UserDTO;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;
import rest.mapper.TicketMapperImpl;
import rest.repositories.PublicKeyRepository;
import rest.repositories.TicketRepository;
import rest.repositories.UserRepository;
import rest.utils.crypto.KeyUtils;
import rest.utils.date.TicketDateUtils;
@ApplicationScoped
public class TicketServicePostImpl implements TicketServicePostInterface {

    @Override
    public Response createTicket(TicketDTO ticketDTO, TicketRepository ticketRepository,
            PublicKeyRepository publicKeyRepository, UserRepository userRepository) {
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
        UserDTO checkObj = userRepository.getUserByUsername(ticketDTO.getUser().getUsername());
        if (checkObj == null) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("Error, the user you gave does not exist")
                            .build())
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        ticketDTO.setUser(checkObj);
        // ? Get the public key that is beeing used currently to hash the ticket
        PublicKeyDTO publicKeyToCreateTicket = publicKeyRepository.getMainPublicKey();
        if (publicKeyToCreateTicket == null || publicKeyToCreateTicket.getKey() == null) {
            return Response.ok(
                    new GeneralResponse.GeneralResponseBuilder<>()
                            .setMessage("There is no main publickey setted to hash the ticket")
                            .build())
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
        ticketDTO.setPublicKey(publicKeyToCreateTicket);
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
            ticketDTO = ticketRepository.createTicket(ticketDTO);
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
                            .setMessage("There is a problem with publickey or with the user to sign this ticket "
                                    + e.getMessage())
                            .build())
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
        ticketDTO.setEncryptedTicket(encryptedForm);
        return Response.ok(ticketDTO).status(Response.Status.CREATED).build();
    }

}
