package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.service.historyview.ServiceHistoryView;
import com.endeavorms.velocity.qto.service.historyview.ServiceHistoryViewManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rcasey
 * @since 10/27/2023
 */
@RestController
@RequestMapping("/api/serviceHistory")
@PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
public class ServiceHistoryViewResource extends AbstractResource<ServiceHistoryView> {

    @Override
    protected String getResourcePath() {
        return "/serviceHistory";
    }

    @Autowired
    private ServiceHistoryViewManager manager;

    @GetMapping
    public ResponseEntity<?> getServiceHistory(@RequestParam("serviceId") final Long serviceId,
                                               @RequestParam(value = "sortDir", required = false) final String sortDir,
                                               @RequestParam(value = "sortField", required = false) final String sortField) {
        try {
            return ResponseEntity.ok(manager.getServiceTreePaginatedResult(serviceId, sortDir, sortField));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
