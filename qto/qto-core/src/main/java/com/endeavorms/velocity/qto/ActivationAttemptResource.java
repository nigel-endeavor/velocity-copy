package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.activation.attempt.ActivationAttempt;
import com.endeavorms.velocity.qto.activation.attempt.ActivationAttemptManager;
import com.endeavorms.velocity.qto.activation.attempt.ActivationAttemptPushValidationException;
import com.endeavorms.velocity.qto.activation.attempt.IssActivationAttemptEventHandler;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Map;

/**
 * @author rcasey
 * @since 3/23/2023
 */
@RestController
@RequestMapping("/api/activationAttempts")
public class ActivationAttemptResource extends AbstractResource {

    @Override
    protected String getResourcePath() {
        return "/activationAttempts";
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivationAttemptResource.class);

    @Autowired
    private ActivationAttemptManager manager;

    @Autowired
    private IssActivationAttemptEventHandler issEventHandler;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getAttempts(@RequestParam("serviceId") final Long serviceId) {
        List<ActivationAttempt> attempts = manager.findByServiceId(serviceId);
        return ResponseEntity.ok(attempts);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('order:write','inventory:write')")
    public ResponseEntity<?> create(@RequestBody final ActivationAttempt attempt) {
        try {
            ActivationAttempt created = manager.create(attempt);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('order:write','inventory:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ActivationAttempt attempt) {
        try {
            if (!id.equals(attempt.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ActivationAttempt updated;
            if ("Complete".equals(attempt.getScheduledAttemptStatus())) {
                updated = issEventHandler.handlePushComplete(attempt);
            } else if ("Partial Complete - Pending Re-Schedule".equals(attempt.getScheduledAttemptStatus())) {
                updated = issEventHandler.handlePushPartial(attempt);
            } else if ("Incomplete - Pending Re-Schedule".equals(attempt.getScheduledAttemptStatus())) {
                updated = issEventHandler.handlePushIncomplete(attempt);
            } else {
                updated = manager.edit(attempt);
            }

            if (attempt.isDuplicateToRelated()
                    && ("Complete".equals(updated.getScheduledAttemptStatus()))) {
                manager.checkDuplicateToRelated(updated);
            }

            return ResponseEntity.ok(updated);
        } catch (ActivationAttemptPushValidationException e) {
            return ResponseEntity.internalServerError().body(e.getErrors());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyAuthority('order:write','inventory:write')")
    public ResponseEntity<?> cancel(@PathVariable("id") final Long id, @RequestBody final Map<String, Boolean> requestBody) {
        try {
            boolean applySameDayCancelSurcharge = requestBody.get("applySameDayCancelSurcharge");
            ActivationAttempt cancelled = manager.cancel(id, applySameDayCancelSurcharge);
            return ResponseEntity.ok(cancelled);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/rollback")
    @PreAuthorize("hasAuthority('order:write-terminal')")
    public ResponseEntity<?> rollBack(@PathVariable("id") final Long id) {
        try {
            ActivationAttempt activationAttempt = issEventHandler.rollback(id);
            return ResponseEntity.ok(activationAttempt);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/notes")
    @PreAuthorize("hasAnyAuthority('order:write','inventory:write')")
    public ResponseEntity<?> addNote(@RequestParam("dispatchId") final Long dispatchId,
                                     @RequestParam("tenantId") final Long tenantId,
                                     @RequestBody final String note) {
        PreconditionsUtil.checkArgument(dispatchId, "Dispatch ID is required to add note.");
        LOGGER.debug("Received Note: " + note);
        return ResponseEntity.ok().build();
    }
}
