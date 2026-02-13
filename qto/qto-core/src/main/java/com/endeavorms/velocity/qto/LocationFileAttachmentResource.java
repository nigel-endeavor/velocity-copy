package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.attachment.LocationFileAttachment;
import com.endeavorms.velocity.qto.attachment.LocationFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rcasey
 * @since 1/24/2023
 */
@RestController
@RequestMapping("/api/locationFileAttachments")
public class LocationFileAttachmentResource extends AbstractFileAttachmentResource<LocationFileAttachment> {

    @Autowired
    private LocationFileAttachmentManager manager;

    @Autowired
    private LocationManager locationManager;

    @Override
    protected AbstractFileAttachmentManager<LocationFileAttachment> getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/locationFileAttachments";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getAttachments(@RequestParam("locationId") final Long locationId) {
        PaginatedResult<LocationFileAttachment> result = manager.findByLocationId(locationId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> upload(@RequestParam("locationId") final Long locationId,
                                     HttpServletRequest servletRequest) {
        PreconditionsUtil.checkArgument(locationId, "Location ID is required.");
        return upload(servletRequest, () -> {
            LocationFileAttachment attachment = new LocationFileAttachment();
            Location location = locationManager.retrieve(locationId);
            attachment.setLocationId(locationId);
            attachment.setTenantId(location.getTenantId());
            attachment.setMasterCustomerId(location.getMasterCustomerId());
            return attachment;
        });
    }
}
