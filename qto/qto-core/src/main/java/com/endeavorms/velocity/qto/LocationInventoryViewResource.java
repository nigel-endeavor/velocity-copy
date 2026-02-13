package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.location.inventoryview.InventoryWorklistMeta;
import com.endeavorms.velocity.qto.location.inventoryview.LocationInventoryView;
import com.endeavorms.velocity.qto.location.inventoryview.LocationInventoryViewManager;
import com.endeavorms.velocity.qto.location.inventoryview.LocationInventoryViewSearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/locationInventoryViews")
public class LocationInventoryViewResource extends AbstractResource<LocationInventoryView> {

    @Override
    protected String getResourcePath() {
        return "/locationInventoryViews";
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationInventoryViewResource.class);

    @Autowired
    private LocationInventoryViewManager manager;

    @GetMapping("/serviceTypes")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return ResponseEntity.ok(serviceType);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getInventoryLocationViews(@ModelAttribute final LocationInventoryViewSearchCriteria criteria) {
        Long start = System.currentTimeMillis();
        LOGGER.debug("getInventoryLocationViews called");
        LocationInventoryViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<LocationInventoryView> result = manager.findInventoryBySearchCriteria(crit);
        LOGGER.debug("getInventoryLocationViews took " + (System.currentTimeMillis() - start) + "ms");
        return getCollectionResource(result, crit, getLocation(LocationInventoryViewResource.class));
    }

    @GetMapping("/link")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getInventoryLocationViewsForLink(@ModelAttribute final LocationInventoryViewSearchCriteria criteria) {
        PaginatedResult<LocationInventoryView> result = manager.findBySearchCriteriaForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationInventoryViewResource.class));
    }

    @GetMapping("/meta")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getInventoryLocationWorklistMeta(@ModelAttribute final LocationInventoryViewSearchCriteria criteria) {
        InventoryWorklistMeta meta = manager.getInventoryWorklistMeta(criteria);
        return ResponseEntity.ok(meta);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('inventory:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final LocationInventoryView locationView) {
        try {
            if (!id.equals(locationView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            LocationInventoryView updated = manager.edit(locationView);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/relocate")
    @PreAuthorize("hasAuthority('inventory:write')")
    public ResponseEntity<?> getLocationViewsForRelocate(@ModelAttribute final LocationInventoryViewSearchCriteria criteria) {
        PaginatedResult<LocationInventoryView> result = manager.findBySearchCriteriaForServiceRelocate(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationInventoryViewResource.class));
    }
}
