package rest.service;

import java.util.List;
import java.util.UUID;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import rest.dto.TicketDTO;
import rest.entity.Ticket;
import rest.mapper.TicketMapperImpl;

@ApplicationScoped
public class TicketService implements PanacheRepositoryBase<Ticket, UUID> {
    /**
     * @param id, the id of a given ticket
     * @return a ticket with a given id
     */
    public TicketDTO getTicketsById(UUID id) {
        Ticket ticket = findById(id);
        return TicketMapperImpl.ticketToTicketDTO(ticket);
    }

    /**
     * @param username, the username of the user that retains certain tickets
     * @return The list of tickets that belong to a certain user
     */
    public List<TicketDTO> getTicketsByUsername(String username, int page, int size) {
        List<Ticket> ticket = getEntityManager()
                .createQuery("SELECT * FROM Ticket t JOIN t.user u JOIN t.publicKey", Ticket.class)
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
    public TicketDTO getLastInsertedTicketByUsername(String username) {
        Ticket ticket = find("order by createdAt desc").firstResult();
        return TicketMapperImpl.ticketToTicketDTO(ticket);
    }

    public boolean deleteTicketById(UUID id) {
        TicketDTO checkObj = getTicketsById(id);
        if (checkObj == null) {
            return false;
        }
        delete(TicketMapperImpl.ticketDTOtoTicket(checkObj));
        return true;
    }
}
