package rest.entity;

import java.util.Date;

import org.hibernate.annotations.Generated;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private byte[] createdBy;

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

    public byte[] getcreatedBy() {
        return this.createdBy;
    }

    public void setcreatedBy(byte[] createdBy) {
        this.createdBy = createdBy;
    }

    private PublicKey(PublicKeyBuilder builder) {
        this.id = builder.id;
        this.changedAt = builder.changedAt;
        this.createdBy = builder.createdBy;
    }

    public static class PublicKeyBuilder {
        private Long id;
        private Date changedAt;
        private byte[] createdBy;

        public PublicKeyBuilder() {
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

        public byte[] getcreatedBy() {
            return this.createdBy;
        }

        public PublicKeyBuilder setcreatedBy(byte[] createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public PublicKey build() {
            return new PublicKey(this);
        }
    }
}
