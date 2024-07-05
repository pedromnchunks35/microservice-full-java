package rest.dto;

import java.util.Date;
import java.util.List;

import rest.entity.Ticket;

public class PublicKeyDTO {
    private Long id;
    private Date changedAt;
    private byte[] createdBy;
    private List<Ticket> tickets;

    public PublicKeyDTO() {
    }

    public static class PublicKeyDTOBuilder {
        private Long id;
        private Date changedAt;
        private byte[] createdBy;
        private List<Ticket> tickets;

        public List<Ticket> getTicket() {
            return tickets;
        }

        public PublicKeyDTOBuilder setTickets(List<Ticket> tickets) {
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

        public byte[] getCreatedBy() {
            return this.createdBy;
        }

        public PublicKeyDTOBuilder setCreatedBy(byte[] createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public PublicKeyDTO build() {
            return new PublicKeyDTO(this);
        }
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    private PublicKeyDTO(PublicKeyDTOBuilder builder) {
        this.id = builder.id;
        this.changedAt = builder.changedAt;
        this.createdBy = builder.createdBy;
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

    public byte[] getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(byte[] createdBy) {
        this.createdBy = createdBy;
    }

}
