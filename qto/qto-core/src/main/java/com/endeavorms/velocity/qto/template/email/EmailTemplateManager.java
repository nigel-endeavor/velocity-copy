package com.endeavorms.velocity.qto.template.email;

import com.endeavorms.velocity.qto.activation.attempt.ActivationAttemptManager;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.company.CompanyManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

/**
 * Business logic layer for Email Templates.
 */
@Component
public class EmailTemplateManager extends StandardManager<EmailTemplate> {
    /** Business logic tier for companies. */
    @Inject
    private CompanyManager companyManager;
    /** Business logic tier for activation attempts. */
    @Inject
    private ActivationAttemptManager activationAttemptManager;

    /**
     * Persistence tier for Email Templates.
     */
    @Inject
    private EmailTemplateJpaDao dao;

    @Override
    protected EmailTemplateJpaDao getDao() {
        return dao;
    }

    @Override
    public EmailTemplate edit(final EmailTemplate template) {
        template.setTenantId(companyManager.retrieve(template.getCompanyId()).getTenantId());
        return super.edit(template);
    }

    @Override
    public EmailTemplate create(final EmailTemplate template) {
        template.setTenantId(companyManager.retrieve(template.getCompanyId()).getTenantId());
        return super.create(template);
    }

    /**
     * Get Email Templates by type.
     *
     * @param templateType the type of template to get.
     * @param criteria the search criteria to use.
     * @return a list of email templates.
     */
    public PaginatedResult<EmailTemplate> getEmailTemplatesByType(final String templateType,
                                                                  final EmailTemplateSearchCriteria criteria) {
        Long tenantId;
        if (templateType.equalsIgnoreCase("ACTIVATION") && criteria.getEntityId() != null) {
            tenantId = activationAttemptManager.retrieve(criteria.getEntityId()).getTenantId();
        } else {
            tenantId = companyManager.retrieve(criteria.getCompanyId()).getTenantId();
        }
        return dao.getEmailTemplatesByType(templateType, tenantId);
    }
}
