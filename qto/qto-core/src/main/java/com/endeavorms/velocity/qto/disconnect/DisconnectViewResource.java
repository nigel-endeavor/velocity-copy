package com.endeavorms.velocity.qto.disconnect;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.disconnect.multiedit.DisconnectMultiEditQueueHandler;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
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

import java.util.List;

/**
 * @author fcurran
 * @since 1.3.0
 */
@RestController
@RequestMapping("/api/disconnectViews")
public class DisconnectViewResource extends AbstractResource<DisconnectView> {

    @Override
    protected String getResourcePath() {
        return "/disconnectViews";
    }

    @Autowired
    private DisconnectViewManager manager;

    @Autowired
    private DisconnectMultiEditQueueHandler multiEditQueueHandler;

    @GetMapping
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<?> getDisconnectViews(@ModelAttribute final DisconnectViewSearchCriteria criteria) {
        DisconnectViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<DisconnectView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(DisconnectViewResource.class));
    }

    @GetMapping("/meta")
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<?> getDisconnectWorklistMeta(@ModelAttribute final DisconnectViewSearchCriteria criteria) {
        DisconnectWorklistMeta meta = manager.getWorklistMeta(criteria);
        return ResponseEntity.ok(meta);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final DisconnectView disconnectView) {
        try {
            if (!id.equals(disconnectView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            DisconnectView updated = manager.edit(disconnectView);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/multiEdit")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> multiEdit(@RequestBody final MultiEditRequestDto multiEditRequest) {
        try {
            multiEditQueueHandler.sendMessageToQueue(multiEditRequest);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/serviceTypes")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<?> getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return ResponseEntity.ok(serviceType);
    }
}
