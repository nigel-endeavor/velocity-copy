package com.endeavorms.velocity.qto.attachment;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.attachment.QCompanyFileAttachment.companyFileAttachment;


@Component
public class CompanyFileAttachmentJpaDao extends AbstractMasterCustomerJpaDao<CompanyFileAttachment> {

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

    public PaginatedResult<CompanyFileAttachment> findByCompanyId(final Long companyId) {
        BooleanExpression expression = companyFileAttachment.companyId.eq(companyId);
        expression = addMasterCustomerAndTenantFilter(expression, companyFileAttachment.masterCustomerId, companyFileAttachment.tenantId, true);

        return new PaginatedResult<>(new JPAQuery<CompanyFileAttachment>(entityManager)
                .from(companyFileAttachment)
                .where(expression)
                .fetchResults());
    }

}
