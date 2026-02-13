package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.location.view.LocationView;
import com.endeavorms.velocity.qto.location.view.LocationViewManager;
import com.endeavorms.velocity.qto.location.view.LocationViewSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author rcasey
 * @since 1/9/2023
 */
@RestController
@RequestMapping("/api/locationViews")
public class LocationViewResource extends AbstractResource<LocationView> {

    @Override
    protected String getResourcePath() {
        return "/locationViews";
    }

    @Autowired
    private LocationViewManager manager;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getLocationViews(@ModelAttribute final LocationViewSearchCriteria criteria) {
        LocationViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<LocationView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(LocationViewResource.class));
    }

    @GetMapping("/relocate")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> getLocationViewsForRelocate(@ModelAttribute final LocationViewSearchCriteria criteria) {
        PaginatedResult<LocationView> result = manager.findBySearchCriteriaForServiceRelocate(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationViewResource.class));
    }

    @GetMapping("/serviceTypes")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return ResponseEntity.ok(serviceType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final LocationView locationView) {
        try {
            if (!id.equals(locationView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            LocationView updated = manager.edit(locationView);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
