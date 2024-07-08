package rest.dto;

import java.util.List;

public class UserDTO {
    private String username;
    private String location;
    private String postalCode;
    private String phoneNumber;
    private String country;
    private List<TicketDTO> tickets;

    public List<TicketDTO> getTickets() {
        return this.tickets;
    }

    public void setTickets(List<TicketDTO> tickets) {
        this.tickets = tickets;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private UserDTO(UserDTOBuilder builder) {
        this.username = builder.username;
        this.location = builder.location;
        this.postalCode = builder.postalCode;
        this.phoneNumber = builder.phoneNumber;
        this.country = builder.country;
        this.tickets = builder.tickets;
    }

    public static class UserDTOBuilder {
        private String username;
        private String location;
        private String postalCode;
        private String phoneNumber;
        private String country;
        private List<TicketDTO> tickets;

        public UserDTOBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public UserDTOBuilder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public UserDTOBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserDTOBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public UserDTOBuilder setTickets(List<TicketDTO> tickets) {
            this.tickets = tickets;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }
}
