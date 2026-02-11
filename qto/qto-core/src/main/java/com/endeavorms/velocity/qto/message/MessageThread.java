package com.endeavorms.velocity.qto.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rcasey 
 * @since 4/26/2023
 */
@Entity
@Table(name = "message_thread")
public class MessageThread extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_thread_id")
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "message_thread_id", referencedColumnName = "message_thread_id")
    private List<Message> messages;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "message_thread_id", referencedColumnName = "message_thread_id")
    private List<MessageThreadWatcher> subjects = new ArrayList<>();

    @Transient
    private List<Integer> subjectIds = new ArrayList<>();

    @Transient
    private String createdByDisplayName;

    @PostLoad
    public void init() {
        if (subjects != null) {
            subjectIds = subjects.stream().map(s -> s.getSubjectId().intValue()).collect(Collectors.toList());
        }
        if (createdBy != null) {

            createdByDisplayName = createdBy.toString();
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(final List<Message> messages) {
        this.messages = messages;
    }

    public List<MessageThreadWatcher> getSubjects() {
        return subjects;
    }

    public void setSubjects(final List<MessageThreadWatcher> subjects) {
        this.subjects = subjects;
    }

    public List<Integer> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(final List<Integer> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public String getCreatedByDisplayName() {
        return createdByDisplayName;
    }

    public void setCreatedByDisplayName(final String createdByDisplayName) {
        this.createdByDisplayName = createdByDisplayName;
    }
}
