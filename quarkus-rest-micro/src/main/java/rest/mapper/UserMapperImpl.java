package rest.mapper;

import java.util.ArrayList;
import java.util.List;

import rest.dto.UserDTO;
import rest.entity.User;

public class UserMapperImpl {
    public static User userDTOtoUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return new User.UserBuilder()
                .setCountry(userDTO.getCountry())
                .setLocation(userDTO.getLocation())
                .setPhoneNumber(userDTO.getPhoneNumber())
                .setPostalCode(userDTO.getPostalCode())
                .setTickets(TicketMapperImpl.ticketDTOlistToTicketList(userDTO.getTickets()))
                .setUsername(userDTO.getUsername())
                .build();
    }

    public static UserDTO userToUserDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO.UserDTOBuilder()
                .setCountry(user.getCountry())
                .setLocation(user.getLocation())
                .setPhoneNumber(user.getPhoneNumber())
                .setPostalCode(user.getPostalCode())
                .setTickets(TicketMapperImpl.ticketListToTicketDTOList(user.getTickets()))
                .setUsername(user.getUsername())
                .build();
    }

    public static List<User> userDTOListToUserList(List<UserDTO> userDTOList) {
        if (userDTOList == null) {
            return null;
        }
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < userDTOList.size(); i++) {
            UserDTO userDTO = userDTOList.get(i);
            userList.add(userDTOtoUser(userDTO));
        }
        return userList;
    }

    public static List<UserDTO> userListToUserDTOList(List<User> userList) {
        if (userList == null) {
            return null;
        }
        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            userDTOList.add(userToUserDTO(user));
        }
        return userDTOList;
    }
}
