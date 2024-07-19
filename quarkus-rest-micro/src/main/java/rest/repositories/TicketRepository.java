package rest.repositories;

import java.util.List;
import java.util.UUID;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import rest.dto.TicketDTO;
import rest.entity.PublicKey;
import rest.entity.Ticket;
import rest.entity.User;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;
import rest.mapper.TicketMapperImpl;

@ApplicationScoped
public class TicketRepository implements PanacheRepositoryBase<Ticket, UUID> {
    @Inject
    UserRepository userRepository;
    @Inject
    PublicKeyRepository publicKeyRepository;

    /**
     * @param id, the id of a given ticket
     * @return a ticket with a given id
     */
    @Transactional
    public TicketDTO getTicketsById(UUID id) {
        Ticket ticket = findById(id);
        return TicketMapperImpl.ticketToTicketDTO(ticket);
    }

    /**
     * @param username, the username of the user that retains certain tickets
     * @return The list of tickets that belong to a certain user
     */
    @Transactional
    public List<TicketDTO> getTicketsByUsername(String username, int page, int size) {
        List<Ticket> ticket = getEntityManager()
                .createQuery("FROM Ticket t JOIN t.user u JOIN t.publicKey", Ticket.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return TicketMapperImpl.ticketListToTicketDTOList(ticket);
    }

    /**
     * @param page, the number of the page according to its size
     * @param size, the size that a page must have
     * @return the DTO of a list of tickets inside of those parameters
     */
    @Transactional
    public List<TicketDTO> getAllTickets(int page, int size) {
        List<Ticket> ticket = findAll()
                .page(Page.of(page, size))
                .list();
        return TicketMapperImpl.ticketListToTicketDTOList(ticket);
    }

    /**
     * @param username, the username of the username that we wish to get the last
     *                  ticket
     * @return The last inserted ticket for a user
     */
    @Transactional
    public TicketDTO getLastInsertedTicketByUsername(String username) {
        Ticket ticket = find("order by createdAt desc").firstResult();
        return TicketMapperImpl.ticketToTicketDTO(ticket);
    }

    /**
     * @param id, id of the ticket to delete
     * @return
     */
    @Transactional
    public boolean deleteTicketById(UUID id) {
        TicketDTO checkObj = getTicketsById(id);
        if (checkObj == null) {
            return false;
        }
        delete(TicketMapperImpl.ticketDTOtoTicket(checkObj));
        return true;
    }

    /**
     * @param newTicket, the ticket to save
     * @return
     * @throws AlreadyExistsException, case it already exists because we have an id
     * @throws DoesNotExistException,  the public key does not exist
     */
    @Transactional
    public TicketDTO createTicket(TicketDTO newTicket) throws AlreadyExistsException, DoesNotExistException {
        if (newTicket.getId() != null) {
            throw new AlreadyExistsException("ticket");
        }
        if (newTicket.getPublicKey() == null || newTicket.getPublicKey().getId() == null) {
            throw new DoesNotExistException("publickey");
        }
        if (newTicket.getUser() == null || newTicket.getUser().getUsername() == null
                || newTicket.getUser().getUsername() == "") {
            throw new DoesNotExistException("user");
        }
        Ticket ticketToSave = TicketMapperImpl.ticketDTOtoTicket(newTicket);
        User userThatAlreadyExists = null;
        userThatAlreadyExists = userRepository.find("username", ticketToSave.getUser().getUsername())
                .firstResult();
        PublicKey publicKeyThatAlreadyExists = null;
        publicKeyThatAlreadyExists = publicKeyRepository.findById(ticketToSave.getPublicKey().getId());
        ticketToSave.setUser(userThatAlreadyExists);
        ticketToSave.setPublicKey(publicKeyThatAlreadyExists);
        persist(ticketToSave);
        return TicketMapperImpl.ticketToTicketDTO(ticketToSave);
    }
}
