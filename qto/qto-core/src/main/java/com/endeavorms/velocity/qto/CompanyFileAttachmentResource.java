package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.attachment.CompanyFileAttachment;
import com.endeavorms.velocity.qto.attachment.CompanyFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
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

@RestController
@RequestMapping("/api/companyFileAttachments")
public class CompanyFileAttachmentResource extends AbstractFileAttachmentResource<CompanyFileAttachment> {
    @Autowired
    private CompanyFileAttachmentManager manager;

    @Autowired
    private CompanyManager companyManager;

    @Autowired
    private LocationManager locationManager;

    @Override
    protected AbstractFileAttachmentManager<CompanyFileAttachment> getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/companyFileAttachments";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> getAttachments(@RequestParam(value = "companyId", required = false) final Long companyId,
                                            @RequestParam(value = "locationId", required = false) final Long locationId) {
        PaginatedResult<CompanyFileAttachment> result;
        Long id;
        if (locationId != null && companyId == null) {
            Location location = locationManager.retrieve(locationId);
            id = location.getMasterCustomerId();
        } else {
            id = companyId;
        }
        result = manager.findByCompanyId(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> upload(@RequestParam("companyId") final Long companyId,
                                     HttpServletRequest servletRequest) {
        PreconditionsUtil.checkArgument(companyId, "Company ID is required.");
        return upload(servletRequest, () -> {
            CompanyFileAttachment attachment = new CompanyFileAttachment();
            Company company = companyManager.retrieve(companyId);
            attachment.setCompanyId(companyId);
            attachment.setTenantId(company.getTenantId());
            if ("Master Customer".equals(company.getType())) {
                attachment.setMasterCustomerId(company.getId());
            } else {
                attachment.setMasterCustomerId(company.getMasterCustomerId());
            }
            return attachment;
        });
    }
}
