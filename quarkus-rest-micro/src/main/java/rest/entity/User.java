package rest.entity;

import java.util.List;

import org.hibernate.annotations.Generated;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER")
public class User extends PanacheEntityBase {
    @Id
    @Generated
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;
    @Column(name = "LOCATION", nullable = false, length = 200)
    private String location;
    @Column(name = "POSTAL_CODE", nullable = false, length = 200)
    private String postalCode;
    @Column(name = "PHONE_NUMBER", nullable = false, length = 200)
    private String phoneNumber;
    @Column(name = "COUNTRY", nullable = false, length = 200)
    private String country;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.location = builder.location;
        this.postalCode = builder.postalCode;
        this.phoneNumber = builder.phoneNumber;
        this.country = builder.country;
        this.tickets = builder.tickets;
    }

    public static class UserBuilder {
        private Long id;
        private String location;
        private String postalCode;
        private String phoneNumber;
        private String country;
        private List<Ticket> tickets;

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public UserBuilder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public UserBuilder setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
