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
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/companyFileAttachments")
@Produces("application/json")
public class CompanyFileAttachmentResource extends AbstractFileAttachmentResource<CompanyFileAttachment> {
    @Inject
    private CompanyFileAttachmentManager manager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private LocationManager locationManager;


    @Override
    protected AbstractFileAttachmentManager<CompanyFileAttachment> getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/companyFileAttachments";
    }

    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getAttachments(@QueryParam("companyId") final Long companyId, @QueryParam("locationId") final Long locationId) {
        PaginatedResult<CompanyFileAttachment> result;
        Long id;
        if (locationId != null && companyId == null) {
            Location location = locationManager.retrieve(locationId);
            id = location.getMasterCustomerId();
        } else {
            id = companyId;
        }
        result = manager.findByCompanyId(id);

        return Response.ok(result).build();
    }

    @POST
    @Path("/upload")

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response upload(@QueryParam("companyId") final Long companyId,
                           @Context final HttpServletRequest servletRequest) {
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

