package com.vertek.corporate.qto.message;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;

/**
 * @author rcasey
 * @since 4/26/2023
 */
@Entity
@Table(name = "message")
public class Message extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(name = "message_thread_id")
    private Long messageThreadId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "body")
    private String body;

    @Transient
    private String createdByDisplayName;

    @Override
    public Long getId() {
        return id;
    }

    public Long getMessageThreadId() {
        return messageThreadId;
    }

    public void setMessageThreadId(final Long messageThreadId) {
        this.messageThreadId = messageThreadId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public String getCreatedByDisplayName() {
        return createdByDisplayName;
    }

    public void setCreatedByDisplayName(final String createdByDisplayName) {
        this.createdByDisplayName = createdByDisplayName;
    }
}
