package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.interceptors.LocationDeleteInterceptor;
import com.endeavorms.velocity.qto.interceptors.LocationInterceptor;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.location.LocationSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Optional;

/**
 * @author rcasey
 * @since 1/10/2023
 */
@RestController
@RequestMapping("/api/locations")
public class LocationResource extends AbstractResource<Location> {

    @Override
    protected String getResourcePath() {
        return "/locations";
    }

    @Autowired
    private LocationManager manager;

    @Autowired
    private LocationInterceptor locationInterceptor;

    @Autowired
    private LocationDeleteInterceptor locationDeleteInterceptor;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getLocations(@ModelAttribute final LocationSearchCriteria criteria) {
        PaginatedResult<Location> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationResource.class));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> create(@RequestBody final Location location) {
        Optional<BadRequestError> validationError = locationInterceptor.validateLocation(location);
        if (validationError.isPresent()) {
            return ResponseEntity.badRequest().body(validationError.get());
        }
        try {
            Location created = manager.create(location);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final Location location) {
        Optional<BadRequestError> validationError = locationInterceptor.validateLocation(location);
        if (validationError.isPresent()) {
            return ResponseEntity.badRequest().body(validationError.get());
        }
        try {
            if (!id.equals(location.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            Location edited = manager.edit(location);
            return ResponseEntity.ok(edited);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        Optional<BadRequestError> validationError = locationDeleteInterceptor.validateLocationDelete(id);
        if (validationError.isPresent()) {
            return ResponseEntity.badRequest().body(validationError.get());
        }
        manager.markForDeletion(id);
        return ResponseEntity.ok().build();
    }
}
