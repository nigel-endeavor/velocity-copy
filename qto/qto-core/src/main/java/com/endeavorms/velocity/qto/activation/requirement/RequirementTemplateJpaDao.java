package com.endeavorms.velocity.qto.activation.requirement;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.activation.requirement.QRequirementTemplate.requirementTemplate;
import static com.endeavorms.velocity.qto.company.QCompany.company;

/**
 * @author rcasey
 * @since 3/3/2023
 */
@Component
public class RequirementTemplateJpaDao extends AbstractMasterCustomerJpaDao<RequirementTemplate> {
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

    public List<RequirementTemplate> findByCompanyId(final Long companyId, final boolean showInactive) {
        BooleanExpression expression = requirementTemplate.companyId.eq(companyId)
                .or(requirementTemplate.global.eq(true)
                        .and(company.tenantId.eq(
                                JPAExpressions.select(company.tenantId)
                                        .from(company)
                                        .where(company.id.eq(companyId))

                        )));
        if (!showInactive) {
            expression = expression.and(requirementTemplate.active.eq(true));
        }
        expression = addMasterCustomerAndTenantFilter(expression, requirementTemplate.masterCustomerId, requirementTemplate.tenantId, true);
        return new JPAQuery<RequirementTemplate>(entityManager)
                .from(requirementTemplate)
                .join(company).on(requirementTemplate.companyId.eq(company.id))
                .where(expression).fetch();
    }

    public RequirementTemplate getDefaultTemplate(final Long companyId) {
        return new JPAQuery<RequirementTemplate>(entityManager)
                .from(requirementTemplate)
                .where(requirementTemplate.isDefault.eq(true)
                        .and(requirementTemplate.companyId.eq(companyId)))
                .fetchOne();
    }
}
