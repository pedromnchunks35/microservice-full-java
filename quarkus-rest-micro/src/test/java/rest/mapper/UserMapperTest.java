package rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import rest.dto.UserDTO;
import rest.entity.User;

@QuarkusTest
public class UserMapperTest {
    @Test
    public void userToUserDTOTest() {
        User user = new User.UserBuilder()
                .setCountry("PT")
                .setId(Long.valueOf(1))
                .setLocation("Braga")
                .setPhoneNumber("929251962")
                .setPostalCode("Posta do bacalhau")
                .build();
        UserDTO userDTO = UserMapperImpl.userToUserDTO(user);
        assertEquals(user.getCountry(), userDTO.getCountry());
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getLocation(), userDTO.getLocation());
        assertEquals(user.getPhoneNumber(), userDTO.getPhoneNumber());
        assertEquals(user.getPostalCode(), userDTO.getPostalCode());
    }

    @Test
    public void userDTOtoUserTest() {
        UserDTO userDTO = new UserDTO.UserDTOBuilder()
                .setCountry("PT")
                .setId(Long.valueOf(1))
                .setLocation("Braga")
                .setPhoneNumber("929251962")
                .setPostalCode("Posta do bacalhau")
                .build();
        User user = UserMapperImpl.userDTOtoUser(userDTO);
        assertEquals(user.getCountry(), userDTO.getCountry());
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getLocation(), userDTO.getLocation());
        assertEquals(user.getPhoneNumber(), userDTO.getPhoneNumber());
        assertEquals(user.getPostalCode(), userDTO.getPostalCode());
    }

    @Test
    public void userListToUserDTOList() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User.UserBuilder()
                    .setCountry("PT")
                    .setId(Long.valueOf(1))
                    .setLocation("Braga")
                    .setPhoneNumber("929251962")
                    .setPostalCode("Posta do bacalhau")
                    .build();
            userList.add(user);
        }
        List<UserDTO> userDTOList = UserMapperImpl.userListToUserDTOList(userList);
        for (int i = 0; i < 10; i++) {
            assertEquals(userList.get(i).getCountry(), userDTOList.get(i).getCountry());
            assertEquals(userList.get(i).getId(), userDTOList.get(i).getId());
            assertEquals(userList.get(i).getLocation(), userDTOList.get(i).getLocation());
            assertEquals(userList.get(i).getPhoneNumber(), userDTOList.get(i).getPhoneNumber());
            assertEquals(userList.get(i).getPostalCode(), userDTOList.get(i).getPostalCode());
        }
    }

    @Test
    public void userDTOListToUserList() {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserDTO userDTO = new UserDTO.UserDTOBuilder()
                    .setCountry("PT")
                    .setId(Long.valueOf(1))
                    .setLocation("Braga")
                    .setPhoneNumber("929251962")
                    .setPostalCode("Posta do bacalhau")
                    .build();
            userDTOList.add(userDTO);
        }
        List<User> userList = UserMapperImpl.userDTOListToUserList(userDTOList);
        for (int i = 0; i < 10; i++) {
            assertEquals(userList.get(i).getCountry(), userDTOList.get(i).getCountry());
            assertEquals(userList.get(i).getId(), userDTOList.get(i).getId());
            assertEquals(userList.get(i).getLocation(), userDTOList.get(i).getLocation());
            assertEquals(userList.get(i).getPhoneNumber(), userDTOList.get(i).getPhoneNumber());
            assertEquals(userList.get(i).getPostalCode(), userDTOList.get(i).getPostalCode());
        }
    }
}
