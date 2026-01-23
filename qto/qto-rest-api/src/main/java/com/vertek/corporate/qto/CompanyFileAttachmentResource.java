package com.vertek.corporate.qto;

import com.vertek.corporate.qto.attachment.CompanyFileAttachment;
import com.vertek.corporate.qto.attachment.CompanyFileAttachmentManager;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractFileAttachmentManager;
import com.vertek.corporate.qto.common.AbstractFileAttachmentResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
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
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
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

