package rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import rest.dto.PublicKeyDTO;
import rest.dto.TicketDTO;
import rest.dto.UserDTO;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;

@QuarkusTest
public class TicketTest {
        @Inject
        TicketService ticketService;
        @Inject
        PublicKeyService publicKeyService;
        @Inject
        UserService userService;
        @Inject
        UserTransaction userTransaction;

        @BeforeEach
        public void setUp() throws NotSupportedException, SystemException {
                userTransaction.begin();
        }

        @AfterEach
        public void clean() throws IllegalStateException, SecurityException, SystemException {
                userTransaction.rollback();
        }

        @Test
        public void createTicketTest() throws AlreadyExistsException, DoesNotExistException {
                UserDTO newUser = new UserDTO.UserDTOBuilder()
                                .setCountry("PK")
                                .setLocation("KKKKKK")
                                .setPhoneNumber("929261723")
                                .setPostalCode("4720-106")
                                .setUsername("deletedUser")
                                .build();
                UserDTO resultantUser = userService.createUser(newUser);
                PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                                .setChangedAt(new Date())
                                .setInUsage(false)
                                .setKey("Lul")
                                .build();
                PublicKeyDTO resultantPublicKey = publicKeyService.createPublicKey(newPublicKeyDTO);
                TicketDTO ticket = new TicketDTO.TicketDTOBuilder()
                                .setDay((short) 1)
                                .setHour((short) 1)
                                .setUser(resultantUser)
                                .setPublicKeyDTO(resultantPublicKey)
                                .setMonth((short) 2)
                                .setYear((short) 2000)
                                .build();
                TicketDTO resultantTicket = ticketService.createTicket(ticket);
                assertEquals(ticket.getYear(), resultantTicket.getYear());
                try {
                        ticketService.createTicket(ticket);
                } catch (Exception e) {
                        assertEquals(e.getMessage().contains("ticket"), true);
                }
                ticket.setPublicKey(null);
                ticket.setId(null);
                try {
                        ticketService.createTicket(ticket);
                } catch (Exception e) {
                        assertEquals(e.getMessage().contains("publickey"), true);
                }
        }

        @Test
        public void getTicketTest() throws AlreadyExistsException, DoesNotExistException {
                UserDTO newUser = new UserDTO.UserDTOBuilder()
                                .setCountry("PK")
                                .setLocation("KKKKKK")
                                .setPhoneNumber("929261723")
                                .setPostalCode("4720-106")
                                .setUsername("deletedUser")
                                .build();
                UserDTO resultantUser = userService.createUser(newUser);
                PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                                .setChangedAt(new Date())
                                .setInUsage(false)
                                .setKey("Lul")
                                .build();
                PublicKeyDTO resultantPublicKey = publicKeyService.createPublicKey(newPublicKeyDTO);
                TicketDTO ticket = new TicketDTO.TicketDTOBuilder()
                                .setDay((short) 1)
                                .setHour((short) 1)
                                .setUser(resultantUser)
                                .setPublicKeyDTO(resultantPublicKey)
                                .setMonth((short) 2)
                                .setYear((short) 2000)
                                .build();
                TicketDTO resultantTicket = ticketService.createTicket(ticket);
                assertEquals(ticket.getYear(), resultantTicket.getYear());
                List<TicketDTO> resultantTicketFromGet = ticketService.getTicketsByUsername("deletedUser", 0, 2);
                assertEquals(resultantTicketFromGet.size(), 1);
                assertEquals(resultantTicketFromGet.get(0).getDay(), resultantTicket.getDay());
                assertEquals(resultantTicketFromGet.get(0).getEncryptedTicket(), resultantTicket.getEncryptedTicket());
                assertEquals(resultantTicketFromGet.get(0).getHour(), resultantTicket.getHour());
                assertEquals(resultantTicketFromGet.get(0).getId(), resultantTicket.getId());
                assertEquals(resultantTicketFromGet.get(0).getMonth(), resultantTicket.getMonth());
                assertEquals(resultantTicketFromGet.get(0).getPublicKey().getChangedAt(),
                                resultantTicket.getPublicKey().getChangedAt());
                assertEquals(resultantTicketFromGet.get(0).getPublicKey().getId(),
                                resultantTicket.getPublicKey().getId());
                assertEquals(resultantTicketFromGet.get(0).getPublicKey().getKey(),
                                resultantTicket.getPublicKey().getKey());
                assertEquals(resultantTicketFromGet.get(0).getUser().getCountry(),
                                resultantTicket.getUser().getCountry());
                assertEquals(resultantTicketFromGet.get(0).getUser().getLocation(),
                                resultantTicket.getUser().getLocation());
                assertEquals(resultantTicketFromGet.get(0).getUser().getPhoneNumber(),
                                resultantTicket.getUser().getPhoneNumber());
                assertEquals(resultantTicketFromGet.get(0).getUser().getPostalCode(),
                                resultantTicket.getUser().getPostalCode());
                assertEquals(resultantTicketFromGet.get(0).getUser().getUsername(),
                                resultantTicket.getUser().getUsername());
                assertEquals(resultantTicketFromGet.get(0).getYear(), resultantTicket.getYear());
        }

        @Test
        public void deleteTicketById() throws AlreadyExistsException, DoesNotExistException {
                UserDTO newUser = new UserDTO.UserDTOBuilder()
                                .setCountry("PK")
                                .setLocation("KKKKKK")
                                .setPhoneNumber("929261723")
                                .setPostalCode("4720-106")
                                .setUsername("deletedUser")
                                .build();
                UserDTO resultantUser = userService.createUser(newUser);
                PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                                .setChangedAt(new Date())
                                .setInUsage(false)
                                .setKey("Lul")
                                .build();
                PublicKeyDTO resultantPublicKey = publicKeyService.createPublicKey(newPublicKeyDTO);
                TicketDTO ticket = new TicketDTO.TicketDTOBuilder()
                                .setDay((short) 1)
                                .setHour((short) 1)
                                .setUser(resultantUser)
                                .setPublicKeyDTO(resultantPublicKey)
                                .setMonth((short) 2)
                                .setYear((short) 2000)
                                .build();
                TicketDTO resultantTicket = ticketService.createTicket(ticket);
                Boolean gotDeleted = ticketService.deleteById(resultantTicket.getId());
                assertEquals(gotDeleted, true);
                List<TicketDTO> result = ticketService.getTicketsByUsername(newUser.getUsername(), 0, 1);
                assertEquals(result.size(), 0);
                Boolean falseDelete = ticketService.deleteById(UUID.randomUUID());
                assertEquals(falseDelete, false);
        }

        @Test
        public void getLastInsertedTicketByUsernameTest() throws AlreadyExistsException, DoesNotExistException {
                TicketDTO lastInsertedEmptyTicket = ticketService.getLastInsertedTicketByUsername("deletedUser");
                assertEquals(lastInsertedEmptyTicket, null);
                UserDTO newUser1 = new UserDTO.UserDTOBuilder()
                                .setCountry("PK")
                                .setLocation("KKKKKK")
                                .setPhoneNumber("929261723")
                                .setPostalCode("4720-106")
                                .setUsername("deletedUser")
                                .build();
                UserDTO resultantUser1 = userService.createUser(newUser1);
                PublicKeyDTO newPublicKeyDTO1 = new PublicKeyDTO.PublicKeyDTOBuilder()
                                .setChangedAt(new Date())
                                .setInUsage(false)
                                .setKey("Lul")
                                .build();
                PublicKeyDTO resultantPublicKey1 = publicKeyService.createPublicKey(newPublicKeyDTO1);
                TicketDTO ticket1 = new TicketDTO.TicketDTOBuilder()
                                .setDay((short) 1)
                                .setHour((short) 1)
                                .setUser(resultantUser1)
                                .setPublicKeyDTO(resultantPublicKey1)
                                .setMonth((short) 2)
                                .setYear((short) 2000)
                                .build();
                ticketService.createTicket(ticket1);

                // ? Putting the secound
                UserDTO newUser = userService.getUserByUsername("deletedUser");
                PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                                .setChangedAt(new Date())
                                .setInUsage(false)
                                .setKey("lul")
                                .build();
                PublicKeyDTO resultantPublicKey = publicKeyService.createPublicKey(newPublicKeyDTO);
                TicketDTO ticket = new TicketDTO.TicketDTOBuilder()
                                .setDay((short) 1)
                                .setHour((short) 1)
                                .setUser(newUser)
                                .setPublicKeyDTO(resultantPublicKey)
                                .setMonth((short) 2)
                                .setYear((short) 2000)
                                .build();
                TicketDTO resultantTicket = ticketService.createTicket(ticket);
                TicketDTO lastInsertedTicket = ticketService.getLastInsertedTicketByUsername("deletedUser");
                assertEquals(resultantTicket.getId(), lastInsertedTicket.getId());
        }

        @Test
        public void getAllTicketsTest() throws AlreadyExistsException, DoesNotExistException {
                List<TicketDTO> allTicketsEmpty = ticketService.getAllTickets(0, 10);
                assertEquals(allTicketsEmpty.size(), 0);
                UserDTO newUser = new UserDTO.UserDTOBuilder()
                                .setCountry("PK")
                                .setLocation("KKKKKK")
                                .setPhoneNumber("929261723")
                                .setPostalCode("4720-106")
                                .setUsername("deletedUser")
                                .build();
                UserDTO resultantUser = userService.createUser(newUser);
                for (int i = 0; i < 6; i++) {
                        PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                                        .setChangedAt(new Date())
                                        .setInUsage(false)
                                        .setKey("Lul")
                                        .build();
                        PublicKeyDTO resultantPublicKey = publicKeyService.createPublicKey(newPublicKeyDTO);
                        TicketDTO ticket = new TicketDTO.TicketDTOBuilder()
                                        .setDay((short) 1)
                                        .setHour((short) 1)
                                        .setUser(resultantUser)
                                        .setPublicKeyDTO(resultantPublicKey)
                                        .setMonth((short) 2)
                                        .setYear((short) 2000)
                                        .build();
                        ticketService.createTicket(ticket);
                }
                List<TicketDTO> allTicketsList = ticketService.getAllTickets(0, 10);
                assertEquals(allTicketsList.size(), 6);
        }

        @Test
        public void getAllTicketsByUsernameTest() throws AlreadyExistsException, DoesNotExistException {
                List<TicketDTO> allTicketsEmpty = ticketService.getTicketsByUsername("deletedUser", 0, 10);
                assertEquals(allTicketsEmpty.size(), 0);
                UserDTO newUser = new UserDTO.UserDTOBuilder()
                                .setCountry("PK")
                                .setLocation("KKKKKK")
                                .setPhoneNumber("929261723")
                                .setPostalCode("4720-106")
                                .setUsername("deletedUser")
                                .build();
                UserDTO resultantUser = userService.createUser(newUser);
                for (int i = 0; i < 6; i++) {
                        PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                                        .setChangedAt(new Date())
                                        .setInUsage(false)
                                        .setKey("Lul")
                                        .build();
                        PublicKeyDTO resultantPublicKey = publicKeyService.createPublicKey(newPublicKeyDTO);
                        TicketDTO ticket = new TicketDTO.TicketDTOBuilder()
                                        .setDay((short) 1)
                                        .setHour((short) 1)
                                        .setUser(resultantUser)
                                        .setPublicKeyDTO(resultantPublicKey)
                                        .setMonth((short) 2)
                                        .setYear((short) 2000)
                                        .build();
                        ticketService.createTicket(ticket);
                }
                List<TicketDTO> allTicketsList = ticketService.getTicketsByUsername("deletedUser", 0, 10);
                assertEquals(allTicketsList.size(), 6);
        }

        @Test
        public void getTicketsByIdTest() throws AlreadyExistsException, DoesNotExistException {
                TicketDTO emptyTicket = ticketService.getTicketsById(UUID.randomUUID());
                assertEquals(emptyTicket, null);
                UserDTO newUser = new UserDTO.UserDTOBuilder()
                                .setCountry("PK")
                                .setLocation("KKKKKK")
                                .setPhoneNumber("929261723")
                                .setPostalCode("4720-106")
                                .setUsername("deletedUser")
                                .build();
                UserDTO resultantUser = userService.createUser(newUser);
                PublicKeyDTO newPublicKeyDTO = new PublicKeyDTO.PublicKeyDTOBuilder()
                                .setChangedAt(new Date())
                                .setInUsage(false)
                                .setKey("Lul")
                                .build();
                PublicKeyDTO resultantPublicKey = publicKeyService.createPublicKey(newPublicKeyDTO);
                TicketDTO ticket = new TicketDTO.TicketDTOBuilder()
                                .setDay((short) 1)
                                .setHour((short) 1)
                                .setUser(resultantUser)
                                .setPublicKeyDTO(resultantPublicKey)
                                .setMonth((short) 2)
                                .setYear((short) 2000)
                                .build();
                TicketDTO resultantTicket = ticketService.createTicket(ticket);
                TicketDTO ticketGetById = ticketService.getTicketsById(resultantTicket.getId());
                assertEquals(resultantTicket.getId(), ticketGetById.getId());
        }
}
