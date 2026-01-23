package com.vertek.corporate.qto.subject;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "subject")
public class Subject extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "enc_key")
    private String encKey;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "bad_password_time")
    private Date badPasswordTime;

    @Column(name = "bad_password_count")
    private Long badPasswordCount;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Override
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
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

    public String getEncKey() {
        return encKey;
    }

    public void setEncKey(final String encKey) {
        this.encKey = encKey;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Date getBadPasswordTime() {
        return badPasswordTime;
    }

    public void setBadPasswordTime(final Date badPasswordTime) {
        this.badPasswordTime = badPasswordTime;
    }

    public Long getBadPasswordCount() {
        return badPasswordCount;
    }

    public void setBadPasswordCount(final Long badPasswordCount) {
        this.badPasswordCount = badPasswordCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(final Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
