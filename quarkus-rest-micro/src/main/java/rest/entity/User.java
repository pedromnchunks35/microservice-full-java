package rest.entity;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "USER_APP")
public class User extends PanacheEntityBase {
    @Id
    @NotBlank
    @Column(name = "USERNAME", nullable = false, updatable = false)
    private String username;
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

    public List<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    private User(UserBuilder builder) {
        this.username = builder.username;
        this.location = builder.location;
        this.postalCode = builder.postalCode;
        this.phoneNumber = builder.phoneNumber;
        this.country = builder.country;
        this.tickets = builder.tickets;
    }

    public static class UserBuilder {
        private String username;
        private String location;
        private String postalCode;
        private String phoneNumber;
        private String country;
        private List<Ticket> tickets;

        public UserBuilder setUsername(String username) {
            this.username = username;
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
