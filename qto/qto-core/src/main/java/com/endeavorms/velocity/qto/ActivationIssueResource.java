package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.activation.issue.ActivationIssue;
import com.endeavorms.velocity.qto.activation.issue.ActivationIssueManager;
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

import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 3/29/2023
 */
@RestController
@RequestMapping("/api/activationIssues")
public class ActivationIssueResource extends AbstractResource {

    @Override
    protected String getResourcePath() {
        return "/activationIssues";
    }

    @Inject
    private ActivationIssueManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<List<ActivationIssue>> getActivationIssues(
            @RequestParam(required = false) final Long activationAttemptId) {
        List<ActivationIssue> issues = manager.findByAttemptId(activationAttemptId);
        return ResponseEntity.ok(issues);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final ActivationIssue issue) {
        try {
            ActivationIssue created = manager.create(issue);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ActivationIssue issue) {
        try {
            if (!id.equals(issue.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ActivationIssue updated = manager.edit(issue);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
