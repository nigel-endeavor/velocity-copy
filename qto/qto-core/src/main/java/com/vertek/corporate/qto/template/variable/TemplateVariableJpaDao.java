package com.vertek.corporate.qto.template.variable;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.template.TemplateType;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.vertek.corporate.qto.template.variable.QTemplateVariable.templateVariable;

/**
 * Persistence layer for Email Template Variables.
 */
@Stateless
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
