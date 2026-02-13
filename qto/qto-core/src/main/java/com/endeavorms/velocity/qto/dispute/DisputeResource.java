package com.endeavorms.velocity.qto.dispute;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.dispute.multidispute.MultiDisputeQueueHandler;
import com.endeavorms.velocity.qto.dispute.multidispute.MultiDisputeRequestDto;
import com.endeavorms.velocity.qto.dispute.multiedit.DisputeMultiEditQueueHandler;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;
import java.util.List;

/**
 * @author llevit
 */
@RestController
@RequestMapping("/api/disputes")
public class DisputeResource extends AbstractResource<Dispute> {

    @Override
    protected String getResourcePath() {
        return "/disputes";
    }

    @Inject
    private DisputeManager manager;

    @Inject
    private DisputeMultiEditQueueHandler multiEditQueueHandler;

    @Inject
    private MultiDisputeQueueHandler multiDisputeQueueHandler;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getDisputes(@ModelAttribute final DisputeSearchCriteria criteria) {
        PaginatedResult<Dispute> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(DisputeResource.class));
    }

    @GetMapping("/service/{serviceId}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<List<Dispute>> getDisputesByService(@PathVariable("serviceId") final Long serviceId) {
        List<Dispute> disputes = manager.findByServiceId(serviceId);
        return ResponseEntity.ok(disputes);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final Dispute dispute) {
        try {
            if (dispute.getServiceId() == null) {
                throw new IllegalArgumentException("Missing serviceId.");
            }
            Dispute returnedDispute = manager.create(dispute);
            return ResponseEntity.ok(returnedDispute);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final Dispute dispute) {
        try {
            if (!id.equals(dispute.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            if (dispute.getServiceId() == null) {
                throw new IllegalArgumentException("Missing serviceId.");
            }
            Dispute returnedDispute = manager.edit(dispute);
            return ResponseEntity.ok(returnedDispute);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/multiEdit")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> multiEdit(@RequestBody final MultiEditRequestDto multiEditRequest) {
        try {
            multiEditQueueHandler.sendMessageToQueue(multiEditRequest);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/meta")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<DisputeGridMeta> getDisputesGridMeta(@ModelAttribute final DisputeSearchCriteria criteria) {
        DisputeGridMeta meta = manager.getDisputesGridMeta(criteria);
        return ResponseEntity.ok(meta);
    }

    @PostMapping("/multiDispute")
    @PreAuthorize("hasAuthority('inventory:write')")
    public ResponseEntity<?> multiDispute(@RequestBody final MultiDisputeRequestDto dto) {
        try {
            multiDisputeQueueHandler.sendMessageToQueue(dto);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
