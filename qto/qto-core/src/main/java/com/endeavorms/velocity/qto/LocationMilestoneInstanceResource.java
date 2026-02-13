package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.interceptors.LocationMilestoneInterceptor;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstanceManager;
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
 * @since 2/16/2023
 */
@RestController
@RequestMapping("/api/locationMilestoneInstances")
public class LocationMilestoneInstanceResource extends AbstractResource<LocationMilestoneInstance> {

    @Override
    protected String getResourcePath() {
        return "/locationMilestoneInstances";
    }

    @Autowired
    private LocationMilestoneInstanceManager manager;

    @Autowired
    private LocationMilestoneInterceptor locationMilestoneInterceptor;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getMilestoneInstances(@RequestParam("locationId") final Long locationId) {
        List<LocationMilestoneInstance> milestoneInstances = manager.listByRecord(locationId);
        return ResponseEntity.ok(milestoneInstances);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final LocationMilestoneInstance milestoneInstance) {
        try {
            BadRequestError validationError = locationMilestoneInterceptor.validate(milestoneInstance);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            LocationMilestoneInstance created = manager.create(milestoneInstance);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final LocationMilestoneInstance milestoneInstance) {
        try {
            BadRequestError validationError = locationMilestoneInterceptor.validate(milestoneInstance);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            if (!id.equals(milestoneInstance.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            LocationMilestoneInstance updated = manager.edit(milestoneInstance);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
