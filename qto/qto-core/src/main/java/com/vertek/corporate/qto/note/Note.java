package com.vertek.corporate.qto.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Entity
@Table(name = "note")
@Inheritance(strategy = InheritanceType.JOINED)
public class Note extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @Column(name="legacy_id")
    private Long legacyId;

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

    @Column(name = "update_client")
    private Boolean updateClient = false;

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

    public Boolean getUpdateClient() {
        return updateClient;
    }

    public void setUpdateClient(final Boolean updateClient) {
        this.updateClient = updateClient;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }

    public Long getParentNoteId() {
        return parentNoteId;
    }

    public void setParentNoteId(final Long parentNoteId) {
        this.parentNoteId = parentNoteId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(final Long createdById) {
        this.createdById = createdById;
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

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(final boolean editable) {
        isEditable = editable;
    }
}
