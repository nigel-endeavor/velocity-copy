package com.endeavorms.velocity.qto.equipment;

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
@RequestMapping("/api/serviceEquipment")
public class ServiceEquipmentResource extends AbstractResource<ServiceEquipment> {

    @Override
    protected String getResourcePath() {
        return "/serviceEquipment";
    }

    @Autowired
    private ServiceEquipmentManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getServiceEquipment(@ModelAttribute final ServiceEquipmentSearchCriteria criteria) {
        PaginatedResult<ServiceEquipment> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceEquipmentResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final ServiceEquipment equipment) {
        manager.create(equipment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getServiceEquipment(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(manager.retrieve(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> update(@PathVariable("id") final Long id, @RequestBody final ServiceEquipment equipment) {
        PreconditionsUtil.checkArgument(id, "Customer Task ID is required");
        manager.edit(equipment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        PreconditionsUtil.checkArgument(id, "Customer Task ID is required");
        manager.remove(id);
        return ResponseEntity.ok().build();
    }
}
