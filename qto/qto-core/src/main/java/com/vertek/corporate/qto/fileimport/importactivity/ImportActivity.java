package com.vertek.corporate.qto.fileimport.importactivity;

import com.vertek.corporate.qto.attachment.FileAttachment;
import com.vertek.corporate.qto.common.AbstractTenantOwnedEntity;
import com.vertek.corporate.qto.fileimport.ImportActivityStatus;
import com.vertek.corporate.qto.order.dto.OrderCreateDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@Entity
@Table(name = "import_activity")
public class ImportActivity extends AbstractTenantOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "import_activity_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "file_attachment_id", referencedColumnName = "file_attachment_id")
    private FileAttachment fileAttachment;

    @OneToOne
    @JoinColumn(name = "error_file_attachment_id", referencedColumnName = "file_attachment_id")
    private FileAttachment errorFileAttachment;

    @Column(name = "import_start_date")
    private Date importStartDate;

    @Column(name = "import_end_date")
    private Date importEndDate;

    @Column(name = "status")
    private ImportActivityStatus status = ImportActivityStatus.PENDING_PROCESSING;

    @Column(name = "status_details")
    private String statusDetails;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "import_type")
    private String importType;

    @Column(name = "num_successful")
    private Long numSuccessful = 0L;

    @Column(name = "num_failed")
    private Long numFailed = 0L;

    @Transient
    private Long numProcessed = 0L;

    //supports the OrderImporter
    @Transient
    private List<OrderCreateDto> dtoList = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    public FileAttachment getFileAttachment() {
        return fileAttachment;
    }

    public void setFileAttachment(final FileAttachment fileAttachment) {
        this.fileAttachment = fileAttachment;
    }

    public FileAttachment getErrorFileAttachment() {
        return errorFileAttachment;
    }

    public void setErrorFileAttachment(final FileAttachment errorFileAttachment) {
        this.errorFileAttachment = errorFileAttachment;
    }

    public Date getImportStartDate() {
        return importStartDate;
    }

    public void setImportStartDate(final Date importStartDate) {
        this.importStartDate = importStartDate;
    }

    public Date getImportEndDate() {
        return importEndDate;
    }

    public void setImportEndDate(final Date importEndDate) {
        this.importEndDate = importEndDate;
    }

    public String getStatus() {
        return status.getDisplayName();
    }

    public void setStatus(final ImportActivityStatus status) {
        this.status = status;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(final String statusDetails) {
        this.statusDetails = statusDetails;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(final String importType) {
        this.importType = importType;
    }

    public Long getNumSuccessful() {
        return numSuccessful;
    }

    public void setNumSuccessful(final Long numSuccessful) {
        this.numSuccessful = numSuccessful;
    }

    public Long getNumFailed() {
        return numFailed;
    }

    public void setNumFailed(final Long numFailed) {
        this.numFailed = numFailed;
    }

    public Long getNumProcessed() {
        return numProcessed;
    }

    public void setNumProcessed(final Long numProcessed) {
        this.numProcessed = numProcessed;
    }

    public List<OrderCreateDto> getDtoList() {
        return dtoList;
    }

    public void setDtoList(final List<OrderCreateDto> dtoList) {
        this.dtoList = dtoList;
    }
}
