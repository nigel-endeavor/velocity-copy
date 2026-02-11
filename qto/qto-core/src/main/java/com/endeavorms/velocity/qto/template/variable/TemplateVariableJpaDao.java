package com.endeavorms.velocity.qto.template.variable;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.template.TemplateType;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.template.variable.QTemplateVariable.templateVariable;

/**
 * Persistence layer for Email Template Variables.
 */
@Component
public class TemplateVariableJpaDao extends AbstractJpaDao<TemplateVariable, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Retrieve all template variables by type.
     * @param templateType the type of template variables to retrieve.
     * @return a list of template variables.
     */
    public PaginatedResult<TemplateVariable> getTemplateVariablesByType(final String templateType) {
        //retrieve all template variables by type
        return new PaginatedResult<>(new JPAQuery<TemplateVariable>(entityManager).from(templateVariable)
                .where(templateVariable.templateType.eq(TemplateType.valueOf(templateType.toUpperCase())))
                .orderBy(templateVariable.label.asc())
                .fetchResults());
    }
}
