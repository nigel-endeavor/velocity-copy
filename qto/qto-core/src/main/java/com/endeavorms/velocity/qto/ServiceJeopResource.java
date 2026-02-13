package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.jeop.JeopUnionView;
import com.endeavorms.velocity.qto.jeop.JeopUnionViewSearchCriteria;
import com.endeavorms.velocity.qto.jeop.ServiceJeop;
import com.endeavorms.velocity.qto.jeop.ServiceJeopManager;
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

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author llevit
 */
@RestController
@RequestMapping("/api/serviceJeops")
public class ServiceJeopResource extends AbstractResource<JeopUnionView> {

    @Override
    protected String getResourcePath() {
        return "/serviceJeops";
    }

    @Autowired
    private ServiceJeopManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getServiceJeops(@ModelAttribute final JeopUnionViewSearchCriteria criteria) {
        PaginatedResult<JeopUnionView> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceJeopResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> create(@RequestBody final ServiceJeop jeop) {
        try {
            if (jeop.getServiceId() == null) {
                throw new IllegalArgumentException("Missing serviceId.");
            }
            ServiceJeop returnedJeop = manager.create(jeop);
            return ResponseEntity.ok(returnedJeop);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ServiceJeop jeop) {
        try {
            if (!id.equals(jeop.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            if (jeop.getServiceId() == null) {
                throw new IllegalArgumentException("Missing serviceId.");
            }
            ServiceJeop returnedJeop = manager.edit(jeop);
            return ResponseEntity.ok(returnedJeop);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
