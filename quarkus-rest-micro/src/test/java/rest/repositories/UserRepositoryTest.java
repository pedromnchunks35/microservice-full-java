package rest.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import rest.dto.UserDTO;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@QuarkusTest
public class UserRepositoryTest {
    @Inject
    UserRepository userRepository;
    @Inject
    UserTransaction userTransaction;

    @BeforeEach
    public void setUp() throws Exception {
        userTransaction.begin();
    }

    @AfterEach
    public void tearDown() throws Exception {
        userTransaction.rollback();
    }

    @Test
    public void createUserTest() throws AlreadyExistsException {
        UserDTO newUser = new UserDTO.UserDTOBuilder()
                .setCountry("PT")
                .setLocation("Braga")
                .setPhoneNumber("929261723")
                .setPostalCode("4720-106")
                .setUsername("pedrocas")
                .build();
        UserDTO userReturned = userRepository.createUser(newUser);
        assertEquals(newUser.getCountry(), userReturned.getCountry());
        assertEquals(newUser.getLocation(), userReturned.getLocation());
        assertEquals(newUser.getPhoneNumber(), userReturned.getPhoneNumber());
        assertEquals(newUser.getPostalCode(), userReturned.getPostalCode());
        assertEquals(newUser.getUsername(), userReturned.getUsername());
        try {
            userRepository.createUser(newUser);
        } catch (AlreadyExistsException e) {
            assertEquals(e.getMessage().contains("user"), true);
        }
    }

    @Test
    public void getUserByUsernameTest() throws AlreadyExistsException {
        UserDTO newUser = new UserDTO.UserDTOBuilder()
                .setCountry("PT")
                .setLocation("Braga")
                .setPhoneNumber("929261723")
                .setPostalCode("4720-106")
                .setUsername("pedrocas")
                .build();
        userRepository.createUser(newUser);
        UserDTO userReturned = userRepository.getUserByUsername("pedrocas");
        assertEquals(newUser.getCountry(), userReturned.getCountry());
        assertEquals(newUser.getLocation(), userReturned.getLocation());
        assertEquals(newUser.getPhoneNumber(), userReturned.getPhoneNumber());
        assertEquals(newUser.getPostalCode(), userReturned.getPostalCode());
        assertEquals(newUser.getUsername(), userReturned.getUsername());
        UserDTO nonexistingUser = userRepository.getUserByUsername("pedrocas231313");
        assertEquals(null, nonexistingUser);
    }

    @Test
    public void getAllUsersTest() throws AlreadyExistsException {
        List<UserDTO> firstQuery = userRepository.getAllUsers(0, 30);
        List<UserDTO> transactionsToInsert = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserDTO newUser = new UserDTO.UserDTOBuilder()
                    .setCountry("PT")
                    .setLocation("Braga")
                    .setPhoneNumber("929261723")
                    .setPostalCode("4720-106")
                    .setUsername(UUID.randomUUID().toString())
                    .build();
            userRepository.createUser(newUser);
            transactionsToInsert.add(newUser);
        }
        List<UserDTO> result = userRepository.getAllUsers(2, 2);
        assertEquals(result.size(), 2);
        for (int j = 0; j < result.size(); j++) {
            boolean exists = false;
            for (int i = 0; i < transactionsToInsert.size(); i++) {
                if (result.get(j).getUsername() == transactionsToInsert.get(i).getUsername()) {
                    exists = true;
                    break;
                }
            }
            assertEquals(true, exists);
        }
        result = userRepository.getAllUsers(0, 30);
        assertEquals(10 + firstQuery.size(), result.size());
    }

    @Test
    public void updateUserTest()
            throws AlreadyExistsException, DoesNotExistException, NotSupportedException, SystemException, Exception {
        try {
            UserDTO newUser = new UserDTO.UserDTOBuilder()
                    .setCountry("PK")
                    .setLocation("KKKKKK")
                    .setPhoneNumber("929261723")
                    .setPostalCode("4720-106")
                    .setUsername("testeuser1234")
                    .build();
            userRepository.createUser(newUser);
        } catch (Exception e) {
        }
        UserDTO newUser = new UserDTO.UserDTOBuilder()
                .setCountry("PK")
                .setLocation("KKKKKK2344")
                .setPhoneNumber("929261723313131")
                .setPostalCode("4720-106")
                .setUsername("testeuser1234")
                .build();
        UserDTO updatedUser = userRepository.updateUser(newUser);
        assertEquals(newUser.getCountry(), updatedUser.getCountry());
        assertEquals(newUser.getLocation(), updatedUser.getLocation());
        assertEquals(newUser.getPhoneNumber(), updatedUser.getPhoneNumber());
        assertEquals(newUser.getPostalCode(), updatedUser.getPostalCode());
        assertEquals(newUser.getUsername(), updatedUser.getUsername());
    }

    @Test
    public void deleteUserTest() throws AlreadyExistsException, DoesNotExistException {
        UserDTO newUser = new UserDTO.UserDTOBuilder()
                .setCountry("PK")
                .setLocation("KKKKKK")
                .setPhoneNumber("929261723")
                .setPostalCode("4720-106")
                .setUsername("deletedUser")
                .build();
        userRepository.createUser(newUser);
        boolean result = userRepository.deleteUser("deletedUser");
        assertEquals(result, true);
        try {
            userRepository.deleteUser("nonameboys");
        } catch (DoesNotExistException e) {
            assertEquals(e.getMessage().contains("user"), true);
        }
    }

}
