package com.vertek.corporate.qto.template.email;

import com.vertek.corporate.qto.activation.attempt.ActivationAttemptManager;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.company.CompanyManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Business logic layer for Email Templates.
 */
@Stateless
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
