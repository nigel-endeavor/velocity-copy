package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.location.inventoryview.InventoryWorklistMeta;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.inventoryview.ServiceInventoryView;
import com.endeavorms.velocity.qto.service.inventoryview.ServiceInventoryViewManager;
import com.endeavorms.velocity.qto.service.inventoryview.ServiceInventoryViewSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author rcasey
 * @since 2/23/2023
 */
@RestController
@RequestMapping("/api/serviceInventoryViews")
public class ServiceInventoryViewResource extends AbstractResource<ServiceInventoryView> {

    @Override
    protected String getResourcePath() {
        return "/serviceInventoryViews";
    }

    @Autowired
    private ServiceInventoryViewManager manager;

    @Autowired
    private ServiceManager serviceManager;

    @GetMapping
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getInventoryServiceViews(@ModelAttribute final ServiceInventoryViewSearchCriteria criteria) {
        ServiceInventoryViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<ServiceInventoryView> result = manager.findInventoryBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ServiceInventoryViewResource.class));
    }

    @GetMapping("/inventory")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getInventoryServiceViewsForLink(@ModelAttribute final ServiceInventoryViewSearchCriteria criteria) {
        PaginatedResult<ServiceInventoryView> result = manager.findBySearchCriteriaForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceInventoryViewResource.class));
    }

    @PutMapping("/inventory/link")
    @PreAuthorize("hasAuthority('inventory:write')")
    public ResponseEntity<?> linkInventory(@RequestParam("incomingServiceId") final Long incomingServiceId,
                                           @RequestParam("selectedItems") final String selectedItems,
                                           @RequestParam("linkType") final String linkType) {
        serviceManager.link(incomingServiceId, selectedItems, linkType);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/meta")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getInventoryServiceWorklistMeta(@ModelAttribute final ServiceInventoryViewSearchCriteria criteria) {
        InventoryWorklistMeta meta = manager.getInventoryWorklistMeta(criteria);
        return ResponseEntity.ok(meta);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('inventory:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ServiceInventoryView serviceView) {
        try {
            if (!id.equals(serviceView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ServiceInventoryView updated = manager.edit(serviceView);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/link")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> getServiceViewsForLink(@ModelAttribute final ServiceInventoryViewSearchCriteria criteria) {
        PaginatedResult<ServiceInventoryView> result = manager.getServiceInventoryViewsForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceInventoryViewResource.class));
    }

    @GetMapping("/bundle")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> getServiceViewsForBundle(@ModelAttribute final ServiceInventoryViewSearchCriteria criteria) {
        PaginatedResult<ServiceInventoryView> result = manager.getServiceInventoryViewsForBundle(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceInventoryViewResource.class));
    }

    @PutMapping("/link")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> link(@RequestParam("incomingServiceId") final Long incomingServiceId,
                                  @RequestParam("selectedItems") final String selectedItems,
                                  @RequestParam("linkType") final String linkType) {
        serviceManager.link(incomingServiceId, selectedItems, linkType);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/serviceTypes")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return ResponseEntity.ok(serviceType);
    }
}
