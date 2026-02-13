package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.activation.requirement.RequirementTemplate;
import com.endeavorms.velocity.qto.activation.requirement.RequirementTemplateManager;
import com.endeavorms.velocity.qto.common.AbstractResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author rcasey
 * @since 3/3/2023
 */
@RestController
@RequestMapping("/api/requirementTemplates")
public class RequirementTemplateResource extends AbstractResource {

    @Override
    protected String getResourcePath() {
        return "/requirementTemplates";
    }

    @Autowired
    private RequirementTemplateManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getTemplates(@RequestParam("companyId") final Long companyId,
                                          @RequestParam(value = "showInactive", defaultValue = "false") final boolean showInactive) {
        List<RequirementTemplate> templates = manager.findByCompanyId(companyId, showInactive);
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> retrieve(@PathVariable("id") final Long id) {
        RequirementTemplate retrieved = manager.retrieve(id);
        return ResponseEntity.ok(retrieved);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final RequirementTemplate template) {
        try {
            RequirementTemplate created = manager.create(template);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final RequirementTemplate template) {
        try {
            if (!id.equals(template.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            RequirementTemplate updated = manager.edit(template);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
