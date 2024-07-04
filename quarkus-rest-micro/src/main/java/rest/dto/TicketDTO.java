package rest.dto;

import java.util.UUID;

public class TicketDTO {
    private UUID id;
    private UserDTO user;
    private PublicKeyDTO publicKey;
    private short day;
    private short month;
    private short year;
    private short hour;

    public UserDTO getUser() {
        return this.user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PublicKeyDTO getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(PublicKeyDTO publicKeyDTO) {
        this.publicKey = publicKeyDTO;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public short getDay() {
        return this.day;
    }

    public void setDay(short day) {
        this.day = day;
    }

    public short getMonth() {
        return this.month;
    }

    public void setMonth(short month) {
        this.month = month;
    }

    public short getYear() {
        return this.year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public short getHour() {
        return this.hour;
    }

    public void setHour(short hour) {
        this.hour = hour;
    }

    public TicketDTO() {
    }

    public static class TicketDTOBuilder {
        private UUID id;
        private short day;
        private short month;
        private short year;
        private short hour;
        private UserDTO user;
        private PublicKeyDTO publicKey;

        public TicketDTOBuilder setUser(UserDTO user) {
            this.user = user;
            return this;
        }

        public TicketDTOBuilder setPublicKeyDTO(PublicKeyDTO publicKeyDTO) {
            this.publicKey = publicKeyDTO;
            return this;
        }

        public TicketDTOBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public TicketDTOBuilder setDay(short day) {
            this.day = day;
            return this;
        }

        public TicketDTOBuilder setMonth(short month) {
            this.month = month;
            return this;
        }

        public TicketDTOBuilder setYear(short year) {
            this.year = year;
            return this;
        }

        public TicketDTOBuilder setHour(short hour) {
            this.hour = hour;
            return this;
        }

        public TicketDTO build() {
            return new TicketDTO(this);
        }
    }

    private TicketDTO(TicketDTOBuilder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.publicKey = builder.publicKey;
        this.day = builder.day;
        this.month = builder.month;
        this.year = builder.year;
        this.hour = builder.hour;
    }
}
