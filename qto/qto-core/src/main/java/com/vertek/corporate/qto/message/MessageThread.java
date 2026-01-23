package com.vertek.corporate.qto.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
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
