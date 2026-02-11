package com.endeavorms.velocity.qto.template.variable;

import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Business logic layer for Template Variables.
 */
@Component
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
