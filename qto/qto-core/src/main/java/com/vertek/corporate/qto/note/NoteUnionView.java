package com.vertek.corporate.qto.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * A view containing a union of both location and service notes.
 * @author llevit
 * @since 1/27/2023
 */
@Entity
@Table(name = "v_note_union")
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteUnionView extends AbstractMasterCustomerOwnedEntity {

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "service_id")
    private Long serviceId;

    @Id
    @Column(name = "note_id")
    private Long id;

    @Column(name = "note")
    private String note;

    @Column(name = "category")
    private String category;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "edited_date")
    private Date editedDate;

    @Column(name = "edited_by")
    private String editedBy;

    @Column(name = "internal_only")
    private Boolean internalOnly = false;

    @Column(name = "parent_note_id")
    private Long parentNoteId;

    @JsonIgnore
    @Column(name = "created_by_id")
    private Long createdById;

    @Transient
    private boolean isEditable = false;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getInternalOnly() {
        return internalOnly;
    }

    public void setInternalOnly(final Boolean internalOnly) {
        this.internalOnly = internalOnly;
    }

    public Long getParentNoteId() {
        return parentNoteId;
    }

    public void setParentNoteId(final Long parentNoteId) {
        this.parentNoteId = parentNoteId;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(final boolean editable) {
        isEditable = editable;
    }

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(final Date editedDate) {
        this.editedDate = editedDate;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(final String editedBy) {
        this.editedBy = editedBy;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(final Long createdById) {
        this.createdById = createdById;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }
}
