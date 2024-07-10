package rest.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Generated;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "PUBLIC_KEY")
public class PublicKey extends PanacheEntityBase {
    @Id
    @Generated
    private Long id;
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date changedAt;
    @Column(name = "KEY", nullable = false, updatable = false)
    private byte[] key;
    @OneToMany(mappedBy = "publicKey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;
    @Column(name = "IN_USAGE", nullable = false, updatable = false)
    private boolean inUsage;

    public PublicKey() {
    }

    // ? To init with the current date
    @PrePersist
    protected void onCreate() {
        changedAt = new Date();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getchangedAt() {
        return this.changedAt;
    }

    public void setchangedAt(Date changedAt) {
        this.changedAt = changedAt;
    }

    public byte[] getKey() {
        return this.key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    private PublicKey(PublicKeyBuilder builder) {
        this.id = builder.id;
        this.changedAt = builder.changedAt;
        this.key = builder.key;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public boolean isInUsage() {
        return this.inUsage;
    }

    public void setInUsage(boolean inUsage) {
        this.inUsage = inUsage;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public static class PublicKeyBuilder {
        private Long id;
        private Date changedAt;
        private byte[] key;
        private List<Ticket> tickets;
        private boolean inUsage;

        public Object getInUsage() {
            return this.inUsage;
        }

        public PublicKeyBuilder setInUsage(boolean inUsage) {
            this.inUsage = inUsage;
            return this;
        }

        public PublicKeyBuilder() {
        }

        public List<Ticket> getTickets() {
            return tickets;
        }

        public PublicKeyBuilder setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
            return this;
        }

        public Long getId() {
            return this.id;
        }

        public PublicKeyBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public Date getchangedAt() {
            return this.changedAt;
        }

        public PublicKeyBuilder setchangedAt(Date changedAt) {
            this.changedAt = changedAt;
            return this;
        }

        public byte[] getKey() {
            return this.key;
        }

        public PublicKeyBuilder setKey(byte[] key) {
            this.key = key;
            return this;
        }

        public PublicKey build() {
            return new PublicKey(this);
        }
    }
}
