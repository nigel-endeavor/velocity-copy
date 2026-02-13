package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.ServiceSearchCriteria;
import com.endeavorms.velocity.qto.service.macd.MultiMacdQueueHandler;
import com.endeavorms.velocity.qto.service.macd.MultiMacdRequestDto;
import com.endeavorms.velocity.qto.service.macd.request.MacdRequestDto;
import com.endeavorms.velocity.qto.service.multiedit.ServiceMultiEditQueueHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rcasey
 * @since 1/6/2023
 */
@RestController
@RequestMapping("/api/services")
public class ServiceResource extends AbstractResource<Service> {

    @Autowired
    private ServiceManager manager;

    @Autowired
    private ServiceMultiEditQueueHandler multiEditQueueHandler;

    @Autowired
    private MultiMacdQueueHandler multiMacdQueueHandler;

    @Override
    protected String getResourcePath() {
        return "/services";
    }

    @GetMapping
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<?> getServices(@ModelAttribute final ServiceSearchCriteria criteria) {
        PaginatedResult<Service> result = manager.findBySearchCriteria(criteria);
        return (ResponseEntity<?>) getCollectionResource(result, criteria, getLocation(ServiceResource.class));
    }

    @PostMapping("/multiEdit")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<Void> multiEdit(@RequestBody final MultiEditRequestDto multiEditRequest) {
        try {
            multiEditQueueHandler.sendMessageToQueue(multiEditRequest);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/macd")
    @PreAuthorize("hasAuthority('inventory:write')")
    public ResponseEntity<?> createMacd(@RequestBody final MacdRequestDto macdRequestDto) {
        try {
            var errors = manager.validateMacdRequest(macdRequestDto);
            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(new com.endeavorms.velocity.qto.common.BadRequestError(errors));
            }
            Service macd = manager.createMacd(macdRequestDto);
            return ResponseEntity.ok(macd);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/multiMacd")
    @PreAuthorize("hasAuthority('inventory:write')")
    public ResponseEntity<Void> createMultiMacd(@RequestBody final MultiMacdRequestDto multiMacdRequestDto) {
        try {
            multiMacdQueueHandler.sendMessageToQueue(multiMacdRequestDto);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/inventoryLocation")
    @PreAuthorize("hasAuthority('inventory:read')")
    public ResponseEntity<Location> getInventoryLocation(@PathVariable("id") final Long serviceId) {
        Service service = manager.retrieve(serviceId);
        Location inventoryLocation = manager.getInventoryLocation(service);
        return ResponseEntity.ok(inventoryLocation);
    }

    @GetMapping("/{id}/relatedMacds")
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<?> getOpenRelatedMacds(@PathVariable("id") final Long serviceId) {
        return ResponseEntity.ok(manager.findOpenByInventoryId(serviceId));
    }
}
