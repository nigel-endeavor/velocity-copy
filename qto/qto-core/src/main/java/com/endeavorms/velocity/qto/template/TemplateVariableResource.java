package com.endeavorms.velocity.qto.template;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.template.variable.TemplateVariable;
import com.endeavorms.velocity.qto.template.variable.TemplateVariableManager;
import com.endeavorms.velocity.qto.template.variable.TemplateVariableSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * REST API for template variables.
 */
@RestController
@RequestMapping("/api/templateVariables")
public class TemplateVariableResource extends AbstractResource<TemplateVariable> {

    @Override
    protected String getResourcePath() {
        return "/templateVariables";
    }

    @Autowired
    private TemplateVariableManager manager;

    @GetMapping("/{templateType}")
    public ResponseEntity<?> getTemplateVariables(@PathVariable("templateType") final String templateType,
                                                 @ModelAttribute final TemplateVariableSearchCriteria criteria) {
        PaginatedResult<TemplateVariable> result = manager.getTemplateVariablesByType(templateType);
        return getCollectionResource(result, criteria, getLocation(TemplateVariableResource.class));
    }
}
