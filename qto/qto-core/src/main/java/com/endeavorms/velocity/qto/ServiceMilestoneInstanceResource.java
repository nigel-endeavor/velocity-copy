package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.interceptors.ServiceMilestoneInterceptor;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstanceManager;
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
 * @since 2/20/2023
 */
@RestController
@RequestMapping("/api/serviceMilestoneInstances")
public class ServiceMilestoneInstanceResource extends AbstractResource<ServiceMilestoneInstance> {

    @Override
    protected String getResourcePath() {
        return "/serviceMilestoneInstances";
    }

    @Autowired
    private ServiceMilestoneInstanceManager manager;

    @Autowired
    private ServiceMilestoneInterceptor serviceMilestoneInterceptor;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getMilestoneInstances(@RequestParam("serviceId") final Long serviceId) {
        List<ServiceMilestoneInstance> milestoneInstances = manager.listByRecord(serviceId);
        return ResponseEntity.ok(milestoneInstances);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final ServiceMilestoneInstance milestoneInstance) {
        try {
            BadRequestError validationError = serviceMilestoneInterceptor.validate(milestoneInstance);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            ServiceMilestoneInstance created = manager.create(milestoneInstance);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ServiceMilestoneInstance milestoneInstance) {
        try {
            BadRequestError validationError = serviceMilestoneInterceptor.validate(milestoneInstance);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            if (!id.equals(milestoneInstance.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ServiceMilestoneInstance updated = manager.edit(milestoneInstance);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
