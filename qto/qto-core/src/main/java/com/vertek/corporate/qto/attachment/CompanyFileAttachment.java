package com.vertek.corporate.qto.attachment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "company_file_attachment")
public class CompanyFileAttachment extends FileAttachment {
    @Column(name = "company_id")
    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }
}
