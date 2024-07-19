package rest.dto;

import java.util.Date;
import java.util.List;

public class PublicKeyDTO {
    private Long id;
    private Date changedAt;
    private String key;
    private List<TicketDTO> tickets;
    private boolean inUsage;

    public PublicKeyDTO() {
    }

    public static class PublicKeyDTOBuilder {
        private Long id;
        private Date changedAt;
        private String key;
        private List<TicketDTO> tickets;
        private boolean inUsage;

        public boolean isInUsage() {
            return this.inUsage;
        }

        public PublicKeyDTOBuilder setInUsage(boolean inUsage) {
            this.inUsage = inUsage;
            return this;
        }

        public List<TicketDTO> getTicket() {
            return tickets;
        }

        public PublicKeyDTOBuilder setTickets(List<TicketDTO> tickets) {
            this.tickets = tickets;
            return this;
        }

        public Long getId() {
            return this.id;
        }

        public PublicKeyDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public Date getChangedAt() {
            return this.changedAt;
        }

        public PublicKeyDTOBuilder setChangedAt(Date changedAt) {
            this.changedAt = changedAt;
            return this;
        }

        public String getKey() {
            return this.key;
        }

        public PublicKeyDTOBuilder setKey(String key) {
            this.key = key;
            return this;
        }

        public PublicKeyDTO build() {
            return new PublicKeyDTO(this);
        }
    }

    public List<TicketDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDTO> tickets) {
        this.tickets = tickets;
    }

    private PublicKeyDTO(PublicKeyDTOBuilder builder) {
        this.id = builder.id;
        this.changedAt = builder.changedAt;
        this.key = builder.key;
        this.inUsage = builder.inUsage;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getChangedAt() {
        return this.changedAt;
    }

    public void setChangedAt(Date changedAt) {
        this.changedAt = changedAt;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String Key) {
        this.key = Key;
    }

    public boolean isInUsage() {
        return this.inUsage;
    }

    public void setInUsage(boolean inUsage) {
        this.inUsage = inUsage;
    }
}
