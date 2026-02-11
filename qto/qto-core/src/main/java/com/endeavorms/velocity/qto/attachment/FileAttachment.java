package com.endeavorms.velocity.qto.attachment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * @author rcasey 
 * @since 1/24/2023
 */
@Entity
@Table(name = "file_attachment")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileAttachment extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_attachment_id")
    private Long id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private Long size;

    @Column(name = "upload_date")
    private Date uploadDate;

    @Column(name = "file_modified_date")
    private Date fileModifiedDate;

    @Column(name = "uploaded_by_username")
    private String uploadedByUserName;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "file_attachment_content_id", referencedColumnName = "file_attachment_content_id")
    private FileAttachmentContent content;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "parent_file_attachment_id")
    private Long parentFileAttachmentId;

    @Column(name = "parent_owner_id")
    private Long parentOwnerId;


    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(final Long size) {
        this.size = size;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(final Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getFileModifiedDate() {
        return fileModifiedDate;
    }

    public void setFileModifiedDate(final Date fileModifiedDate) {
        this.fileModifiedDate = fileModifiedDate;
    }

    public String getUploadedByUserName() {
        return uploadedByUserName;
    }

    public void setUploadedByUserName(final String uploadedByUserName) {
        this.uploadedByUserName = uploadedByUserName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public FileAttachmentContent getContent() {
        return content;
    }

    public void setContent(final FileAttachmentContent content) {
        this.content = content;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(final Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getParentFileAttachmentId() {
        return parentFileAttachmentId;
    }

    public void setParentFileAttachmentId(final Long parentFileAttachmentId) {
        this.parentFileAttachmentId = parentFileAttachmentId;
    }

    public Long getParentOwnerId() {
        return parentOwnerId;
    }

    public void setParentOwnerId(final Long parentOwnerId) {
        this.parentOwnerId = parentOwnerId;
    }
}
