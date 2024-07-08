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
        User user = UserMapperImpl.userDTOtoUser(newUser);
        UserDTO checkUser = getUserByUsername(user.getUsername());
        if (checkUser == null) {
            persist(user);
        } else {
            throw new AlreadyExistsException("user");
        }
        return newUser;
    }

    /**
     * @param newUser,user to update
     * @return the user back in case it goes well
     * @throws DoesNotExistException
     */
    public UserDTO updateUser(UserDTO newUser) throws DoesNotExistException {
        User user = UserMapperImpl.userDTOtoUser(newUser);
        UserDTO checkUser = getUserByUsername(user.getUsername());
        if (checkUser != null) {
            persist(user);
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
        UserDTO checkUser = getUserByUsername(username);
        if (checkUser != null) {
            delete(UserMapperImpl.userDTOtoUser(checkUser));
        } else {
            throw new DoesNotExistException("user");
        }
        return true;
    }
}
