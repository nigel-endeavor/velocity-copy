package com.endeavorms.velocity.qto.contact.location;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import org.springframework.http.ResponseEntity;
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

/**
 * Endpoints related to location contacts.
 * @author fcurran
 * @since 3/31/2023
 */
@RestController
@RequestMapping("/api/locationContacts")
public class LocationContactResource extends AbstractResource<LocationContact> {

    @Override
    protected String getResourcePath() {
        return "/locationContacts";
    }

    @Autowired
    private LocationContactManager manager;

    @GetMapping
    public ResponseEntity<?> getLocationContacts(@ModelAttribute final LocationContactSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria.getLocationId(), "A locationId is required");
        PaginatedResult<LocationContact> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationContactResource.class));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody final LocationContact locationContact) {
        try {
            PreconditionsUtil.checkArgument(locationContact.getLocationId(), "A locationId is required");
            LocationContact createLocationContact = manager.create(locationContact);
            return ResponseEntity.ok(createLocationContact);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final LocationContact locationContact) {
        try {
            if (!id.equals(locationContact.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            PreconditionsUtil.checkArgument(locationContact.getLocationId(), "A locationId is required");
            LocationContact updatedLocationContact = manager.edit(locationContact);
            return ResponseEntity.ok(updatedLocationContact);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") final Long id) {
        manager.remove(id);
        return ResponseEntity.noContent().build();
    }
}
