package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.cyberView.ServiceCyberView;
import com.endeavorms.velocity.qto.service.cyberView.ServiceCyberViewManager;
import com.endeavorms.velocity.qto.service.cyberView.ServiceCyberViewSearchCriteria;
import com.endeavorms.velocity.qto.service.multiedit.ServiceMultiEditQueueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/serviceCyberViews")
public class ServiceCyberViewResource extends AbstractResource<ServiceCyberView> {

    @Override
    protected String getResourcePath() {
        return "/serviceCyberViews";
    }

    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceCyberViewResource.class);

    @Autowired
    private ServiceCyberViewManager manager;

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private ServiceMultiEditQueueHandler multiEditQueueHandler;

    @GetMapping
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<?> getServiceCyberViews(@ModelAttribute final ServiceCyberViewSearchCriteria criteria) {
        ServiceCyberViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<ServiceCyberView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ServiceCyberViewResource.class));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final ServiceCyberView serviceView) {
        try {
            if (!id.equals(serviceView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ServiceCyberView updated = manager.edit(serviceView);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/link")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> getServiceViewsForLink(@ModelAttribute final ServiceCyberViewSearchCriteria criteria) {
        PaginatedResult<ServiceCyberView> result = manager.getServiceViewsForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceCyberViewResource.class));
    }

    @GetMapping("/bundle")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> getServiceViewsForBundle(@ModelAttribute final ServiceCyberViewSearchCriteria criteria) {
        PaginatedResult<ServiceCyberView> result = manager.getServiceViewsForBundle(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceCyberViewResource.class));
    }

    @PutMapping("/link")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> link(@RequestParam("incomingServiceId") final Long incomingServiceId,
                                  @RequestParam("selectedItems") final String selectedItems,
                                  @RequestParam("linkType") final String linkType) {
        serviceManager.link(incomingServiceId, selectedItems, linkType);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/multiEdit")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> multiEdit(@RequestBody final MultiEditRequestDto multiEditRequest) {
        try {
            multiEditQueueHandler.sendMessageToQueue(multiEditRequest);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
