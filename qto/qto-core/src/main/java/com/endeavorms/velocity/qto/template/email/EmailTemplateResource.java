package com.endeavorms.velocity.qto.template.email;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/emailTemplates")
public class EmailTemplateResource extends AbstractResource<EmailTemplate> {

    @Override
    protected String getResourcePath() {
        return "/emailTemplates";
    }

    @Autowired
    private EmailTemplateManager manager;

    @GetMapping("/{templateType}")
    public ResponseEntity<?> getEmailTemplates(@PathVariable("templateType") final String templateType,
                                                @ModelAttribute final EmailTemplateSearchCriteria criteria) {
        if (criteria.getEntityId() == null && criteria.getCompanyId() == null) {
            throw new IllegalArgumentException("Either a company ID or an entity ID is required");
        }
        PaginatedResult<EmailTemplate> result = manager.getEmailTemplatesByType(templateType, criteria);
        return getCollectionResource(result, criteria, getLocation(EmailTemplateResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('order:write-terminal')")
    public ResponseEntity<?> create(@RequestBody final EmailTemplate emailTemplate) {
        try {
            PreconditionsUtil.checkArgument(emailTemplate.getTemplateType(), "An email template type is required");
            PreconditionsUtil.checkArgument(emailTemplate.getCompanyId(), "A related company ID is required");
            EmailTemplate createdEmailTemplate = manager.create(emailTemplate);
            return ResponseEntity.ok(createdEmailTemplate);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('order:write-terminal')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final EmailTemplate emailTemplate) {
        try {
            if (!id.equals(emailTemplate.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            PreconditionsUtil.checkArgument(emailTemplate.getTemplateType(), "An email template type is required");
            PreconditionsUtil.checkArgument(emailTemplate.getCompanyId(), "A related company ID is required");
            EmailTemplate updatedEmailTemplate = manager.edit(emailTemplate);
            return ResponseEntity.ok(updatedEmailTemplate);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('order:write-terminal')")
    public ResponseEntity<?> remove(@PathVariable("id") final Long id) {
        manager.remove(id);
        return ResponseEntity.noContent().build();
    }
}
