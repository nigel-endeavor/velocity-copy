package com.vertek.corporate.qto.notification;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author rcasey
 * @since 6/15/2023
 */
@Entity
@Table(name = "notification")
public class Notification extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "header")
    private String header;

    @Column(name = "body")
    private String body;

    @Column(name = "icon")
    private String icon;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "dismissed")
    private boolean dismissed;

    @Column(name = "link_to")
    private String linkTo;

    @Override
    public Long getId() {
        return id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(final String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(final boolean dismissed) {
        this.dismissed = dismissed;
    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(final String linkTo) {
        this.linkTo = linkTo;
    }
}
