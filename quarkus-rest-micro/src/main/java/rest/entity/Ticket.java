package rest.entity;

import org.hibernate.Hibernate;
import org.hibernate.annotations.UuidGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "TICKET")
public class Ticket extends PanacheEntityBase {
    @Id
    @UuidGenerator
    @Column(name = "ID", nullable = false, updatable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;
    @ManyToOne
    @JoinColumn(name = "ID_PUBLIC_KEY")
    private PublicKey publicKey;
    @Column(name = "DAY", nullable = false)
    private short day;
    @Column(name = "MONTH", nullable = false)
    private short month;
    @Column(name = "YEAR", nullable = false)
    private short year;
    @Column(name = "HOUR", nullable = false)
    private short hour;
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public static Ticket findById(UUID id) {
        Ticket ticket = find("id", id).firstResult();
        Hibernate.initialize(ticket.getUser());
        Hibernate.initialize(ticket.getPublicKey());
        return ticket;
    }

    // ? To init with the current date
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public Ticket() {
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public static class TicketBuilder {
        private UUID id;
        private User user;
        private short day;
        private short month;
        private short year;
        private short hour;
        private PublicKey publicKey;

        public TicketBuilder setPublicKey(PublicKey publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public TicketBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public TicketBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public TicketBuilder setDay(short day) {
            this.day = day;
            return this;
        }

        public TicketBuilder setMonth(short month) {
            this.month = month;
            return this;
        }

        public TicketBuilder setYear(short year) {
            this.year = year;
            return this;
        }

        public TicketBuilder setHour(short hour) {
            this.hour = hour;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }

    private Ticket(TicketBuilder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.day = builder.day;
        this.month = builder.month;
        this.year = builder.year;
        this.hour = builder.hour;
        this.publicKey = builder.publicKey;
    }

}
