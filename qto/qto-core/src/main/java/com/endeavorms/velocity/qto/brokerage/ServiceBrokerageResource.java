package com.endeavorms.velocity.qto.brokerage;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.interceptors.ServiceBrokerageInterceptor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fcurran
 * @since 6/19/2024
 */
@RestController
@RequestMapping("/api/serviceBrokerages")
public class ServiceBrokerageResource extends AbstractResource<ServiceBrokerage> {

    @Override
    protected String getResourcePath() {
        return "/serviceBrokerages";
    }

    @Autowired
    private ServiceBrokerageManager manager;

    @Autowired
    private ServiceBrokerageInterceptor serviceBrokerageInterceptor;

    @GetMapping("/{serviceId}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> retrieve(@PathVariable("serviceId") final Long serviceId) {
        ServiceBrokerage retrieved = manager.findByServiceId(serviceId);
        return ResponseEntity.ok(retrieved);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final ServiceBrokerage entity) {
        serviceBrokerageInterceptor.validateBeforeSave(entity);
        ServiceBrokerage created = manager.create(entity);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ServiceBrokerage entity) {
        serviceBrokerageInterceptor.validateBeforeSave(entity);
        ServiceBrokerage updated = manager.edit(entity);
        return ResponseEntity.ok(updated);
    }
}
