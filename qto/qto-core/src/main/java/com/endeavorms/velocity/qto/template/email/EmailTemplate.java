package com.endeavorms.velocity.qto.template.email;

import com.endeavorms.velocity.qto.common.AbstractTenantOwnedEntity;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.template.TemplateType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;

/**
 * Defines an email template.
 */
@Entity
@Table(name = "email_template")
public class EmailTemplate extends AbstractTenantOwnedEntity {
    /** The ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_template_id")
    private Long id;
    /** The name of the template. */
    @Column(name = "name")
    private String name;
    /** The subject of the email. */
    @Column(name = "subject")
    private String subject;
    /** The body of the email. */
    @Column(name = "body")
    private String body;
    /** The user who last updated the template. */
    @Column(name = "last_update_by")
    private String lastUpdateBy;
    /** The date the template was last updated. */
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    /** The type of template. */
    @Enumerated(EnumType.STRING)
    @Column(name = "template_type")
    private TemplateType templateType;

    /** Company ID. Used to communicated current tenant without indicating Tenant. */
    @Transient
    private Long companyId;

    @Override
    public Long getId() {
        return id;
    }

    @PrePersist
    @PreUpdate
    void preUpdate() {
        setLastUpdateDate(new Date());
        setLastUpdateBy(SecurityUtils.getLoggedInUser());
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(final String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(final Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public TemplateType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(final TemplateType templateType) {
        this.templateType = templateType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }
}
