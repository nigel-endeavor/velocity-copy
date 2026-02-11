package com.endeavorms.velocity.qto.template.email;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMultitenantJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PlatformDatabase;
import com.endeavorms.velocity.qto.template.TemplateType;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.template.email.QEmailTemplate.emailTemplate;

/**
 * Persistence layer for Email Templates.
 */
@Component
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
