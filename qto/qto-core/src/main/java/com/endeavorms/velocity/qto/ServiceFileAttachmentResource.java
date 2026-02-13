package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.attachment.ServiceFileAttachment;
import com.endeavorms.velocity.qto.attachment.ServiceFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
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
@RequestMapping("/api/serviceFileAttachments")
public class ServiceFileAttachmentResource extends AbstractFileAttachmentResource<ServiceFileAttachment> {

    @Autowired
    private ServiceFileAttachmentManager manager;

    @Autowired
    private ServiceManager serviceManager;

    @Override
    protected AbstractFileAttachmentManager<ServiceFileAttachment> getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/serviceFileAttachments";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getAttachments(@RequestParam(value = "serviceId", required = false) final Long serviceId,
                                             @RequestParam(value = "locationId", required = false) final Long locationId) {
        PaginatedResult<ServiceFileAttachment> result;
        if (locationId != null) {
            result = manager.findByLocationId(locationId);
        } else {
            result = manager.findByServiceIdPaginated(serviceId);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> upload(@RequestParam("serviceId") final Long serviceId,
                                    HttpServletRequest servletRequest) {
        PreconditionsUtil.checkArgument(serviceId, "Service ID is required.");
        return upload(servletRequest, () -> {
            ServiceFileAttachment attachment = new ServiceFileAttachment();
            Service service = serviceManager.retrieve(serviceId);
            attachment.setServiceId(serviceId);
            attachment.setTenantId(service.getTenantId());
            attachment.setMasterCustomerId(service.getMasterCustomerId());
            return attachment;
        });
    }
}
