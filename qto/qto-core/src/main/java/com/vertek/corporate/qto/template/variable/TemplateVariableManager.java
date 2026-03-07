package com.vertek.corporate.qto.template.variable;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Business logic layer for Template Variables.
 */
@Stateless
public class TemplateVariableManager extends StandardManager<TemplateVariable> {

    /**
     * Persistence tier for Template Variables.
     */
    @Inject
    private TemplateVariableJpaDao dao;

    @Override
    protected TemplateVariableJpaDao getDao() {
        return dao;
    }

    /**
     * Get all template variables by type.
     * @param templateType the type of template to get variables for.
     * @return a list of template variables.
     */
    public PaginatedResult<TemplateVariable> getTemplateVariablesByType(final String templateType) {
        return dao.getTemplateVariablesByType(templateType);
    }
}
