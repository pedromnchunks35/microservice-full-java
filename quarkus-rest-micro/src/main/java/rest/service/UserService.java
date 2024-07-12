package rest.service;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import rest.dto.UserDTO;
import rest.entity.User;
import rest.exceptions.AlreadyExistsException;
import rest.exceptions.DoesNotExistException;
import rest.mapper.UserMapperImpl;

@ApplicationScoped
public class UserService implements PanacheRepository<User> {
    /**
     * @param username
     * @return a UserDTO object with this given username
     */
    public UserDTO getUserByUsername(String username) {
        User result = find("username", username).firstResult();
        return UserMapperImpl.userToUserDTO(result);
    }

    /**
     * @param page, the page number
     * @param size, the size of this page
     * @return a list with all the users in according to this parameters
     */
    public List<UserDTO> getAllUsers(int page, int size) {
        List<User> result = findAll().page(page, size).list();
        return UserMapperImpl.userListToUserDTOList(result);
    }

    /**
     * @param newUser, the new user
     * @return the user back because it went successfull
     * @throws AlreadyExistsException
     */
    public UserDTO createUser(UserDTO newUser) throws AlreadyExistsException {
        User user = find("username", newUser.getUsername()).firstResult();
        if (user == null) {
            user = UserMapperImpl.userDTOtoUser(newUser);
            persist(user);
        } else {
            throw new AlreadyExistsException("user");
        }
        return UserMapperImpl.userToUserDTO(user);
    }

    /**
     * @param newUser,user to update
     * @return the user back in case it goes well
     * @throws DoesNotExistException
     */
    public UserDTO updateUser(UserDTO newUser) throws DoesNotExistException {
        User result = find("username", newUser.getUsername()).firstResult();
        if (newUser.getCountry() != null) {
            result.setCountry(newUser.getCountry());
        }
        if (newUser.getLocation() != null) {
            result.setLocation(newUser.getLocation());
        }
        if (newUser.getPhoneNumber() != null) {
            result.setPhoneNumber(newUser.getPhoneNumber());
        }
        if (newUser.getPostalCode() != null) {
            result.setPostalCode(newUser.getPostalCode());
        }
        if (result != null) {
            persist(result);
        } else {
            throw new DoesNotExistException("user");
        }
        return newUser;
    }

    /**
     * @param username, username of the user we wich to delete
     * @return true in case it goes ok
     * @throws DoesNotExistException
     */
    public boolean deleteUser(String username) throws DoesNotExistException {
        User checkUser = find("username", username).firstResult();
        if (checkUser != null) {
            delete(checkUser);
        } else {
            throw new DoesNotExistException("user");
        }
        return true;
    }
}
