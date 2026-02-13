package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.view.ServiceView;
import com.endeavorms.velocity.qto.service.view.ServiceViewManager;
import com.endeavorms.velocity.qto.service.view.ServiceViewSearchCriteria;
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
@RequestMapping("/api/serviceViews")
public class ServiceViewResource extends AbstractResource<ServiceView> {

    @Override
    protected String getResourcePath() {
        return "/serviceViews";
    }

    @Autowired
    private ServiceViewManager manager;

    @Autowired
    private ServiceManager serviceManager;

    @GetMapping
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<?> getServiceViews(@ModelAttribute final ServiceViewSearchCriteria criteria) {
        ServiceViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<ServiceView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ServiceViewResource.class));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ServiceView serviceView) {
        try {
            if (!id.equals(serviceView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ServiceView updated = manager.edit(serviceView);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/link")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> getServiceViewsForLink(@ModelAttribute final ServiceViewSearchCriteria criteria) {
        PaginatedResult<ServiceView> result = manager.getServiceViewsForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceViewResource.class));
    }

    @GetMapping("/bundle")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> getServiceViewsForBundle(@ModelAttribute final ServiceViewSearchCriteria criteria) {
        PaginatedResult<ServiceView> result = manager.getServiceViewsForBundle(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceViewResource.class));
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
