package com.endeavorms.velocity.qto.dispute;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author fcurran
 * @since 9/18/2023
 */
@RestController
@RequestMapping("/api/disputeViews")
public class DisputeViewResource extends AbstractResource<DisputeView> {

    @Override
    protected String getResourcePath() {
        return "/disputeViews";
    }

    @Autowired
    private DisputeViewManager manager;

    @GetMapping
    public ResponseEntity<?> getDisputeViews(@ModelAttribute final DisputeViewSearchCriteria criteria) {
        DisputeViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<DisputeView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(DisputeViewResource.class));
    }

    @GetMapping("/meta")
    public ResponseEntity<?> getDisputeWorklistMeta(@ModelAttribute final DisputeViewSearchCriteria criteria) {
        DisputeWorklistMeta meta = manager.getDisputeWorklistMeta(criteria);
        return ResponseEntity.ok(meta);
    }

    @GetMapping("/serviceTypes")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return ResponseEntity.ok(serviceType);
    }
}
