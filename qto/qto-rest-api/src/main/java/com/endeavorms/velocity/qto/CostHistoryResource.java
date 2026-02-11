package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.costhistory.CostHistory;
import com.endeavorms.velocity.qto.costhistory.CostHistoryManager;
import com.endeavorms.velocity.qto.costhistory.CostHistorySearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 11/1/2023
 */
@RestController
@RequestMapping("/api/costHistory")
@PreAuthorize("hasAuthority('inventory:read')")
public class CostHistoryResource extends AbstractResource<CostHistory> {

    @Override
    protected String getResourcePath() {
        return "/costHistory";
    }

    @Inject
    private CostHistoryManager manager;

    @GetMapping
    public ResponseEntity<?> getCostHistory(@ModelAttribute final CostHistorySearchCriteria criteria) {
        try {
            return ResponseEntity.ok(manager.findBySearchCriteria(criteria));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/meta")
    public ResponseEntity<?> getCostHistoryMeta(@ModelAttribute final CostHistorySearchCriteria criteria) {
        try {
            return ResponseEntity.ok(manager.getCostHistoryMeta(criteria));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
