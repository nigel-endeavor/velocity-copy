package com.vertek.corporate.qto.template.email;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMultitenantJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.template.TemplateType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.vertek.corporate.qto.template.email.QEmailTemplate.emailTemplate;

/**
 * Persistence layer for Email Templates.
 */
@Stateless
public class EmailTemplateJpaDao extends AbstractMultitenantJpaDao<EmailTemplate, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }

    /**
     * Retrieve all email templates by type.
     *
     * @param templateType the type of email templates to retrieve.
     * @param tenantId the tenant id to use.
     * @return a list of email templates.
     */
    public PaginatedResult<EmailTemplate> getEmailTemplatesByType(final String templateType, final Long tenantId) {
        return new PaginatedResult<>(new JPAQuery<EmailTemplate>(entityManager).from(emailTemplate)
                .where(emailTemplate.templateType.eq(TemplateType.valueOf(templateType.toUpperCase()))
                        .and(emailTemplate.tenantId.eq(tenantId)))
                .orderBy(emailTemplate.name.asc())
                .fetchResults());
    }
}
