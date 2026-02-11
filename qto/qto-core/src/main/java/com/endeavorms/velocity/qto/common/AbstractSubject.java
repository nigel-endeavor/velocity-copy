package com.endeavorms.velocity.qto.common;

import com.google.common.base.MoreObjects;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 * Base class for Subject entity implementations.
 * @author <a href="mailto:rconnolly@vertek.com">rconnolly</a>
 * @since 1.11.0 - 11/14/13 12:27 PM
 */
@MappedSuperclass
public abstract class AbstractSubject extends StandardVersionedBaseEntity  {


    /** System Identifier.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    /** Stores the objectGUID for Active Directory subjects.*/
    @Column(name = "subject_uuid")
    private String uuid;

    /** The Subject's display name - used in UIs, etc.*/
    @Size(min = 3, max = 100)
    @Column(name = "display_name")
    private String displayName;

    /** The Subject's email address - subjects for background processes may be directed to a mailing list for example.*/
    @NotNull
    @Column(name = "email_address")
    private String emailAddress;

    /**
     * The Subject's username.
     * NOTE: this is only being maintained so that apps depending on platform versions pre-1.7.0 can still authenticate
     * as they use the username field and not the email address.
     * */
    @SuppressWarnings("unused")
    @Column(name = "username")
    private String username;

    /**
     * Flag indicating whether the entity is considered "active" or not.
     * An inactive Subject should not be able authenticate, for example.
     */
    @Column(name = "active")
    private boolean active = true;

    /** The number of consecutive failed login attempts.*/
    @Column(name = "bad_password_count")
    private int badPasswordCount = 0;

    /** The date of the last failed authentication attempt.*/
    @Column(name = "bad_password_time")
    private Date badPasswordTime;




    /**
     * We need to copy the email address into the username field for apps that use this field pre-1.7.0!
     */
    @PrePersist
    void prePersist() {
        this.username = this.emailAddress;
    }
    /**
     * We need to copy the email address into the username field for apps that use this field pre-1.7.0!
     */
    @PreUpdate
    void preUpdate() {
        this.username = this.emailAddress;
    }




    @Override
    public Long getId() {
        return id;
    }
    private void setId(final Long id) {
        this.id = id;
    }


    public String getUuid() {
        return uuid;
    }
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }


    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }


    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public boolean isActive() {
        return active;
    }
    public void setActive(final boolean active) {
        this.active = active;
    }



    public int getBadPasswordCount() {
        return badPasswordCount;
    }


    public Date getBadPasswordTime() {
        return badPasswordTime;
    }



    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("uuid", uuid)
                .add("displayName", displayName)
                .add("emailAddress", emailAddress)
                .add("active", active)
                .add("badPasswordCount", badPasswordCount)
                .add("badPasswordTime", badPasswordTime)
                .toString();
    }

}
