package com.vertek.corporate.qto;

import com.vertek.corporate.qto.attachment.ServiceFileAttachment;
import com.vertek.corporate.qto.attachment.ServiceFileAttachmentManager;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractFileAttachmentManager;
import com.vertek.corporate.qto.common.AbstractFileAttachmentResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
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

/**
 * @author rcasey
 * @since 1/24/2023
 */
@Path("/serviceFileAttachments")
@Produces("application/json")
public class ServiceFileAttachmentResource extends AbstractFileAttachmentResource<ServiceFileAttachment> {

    /** Business methods for ServiceFileAttachment. */
    @Inject
    private ServiceFileAttachmentManager manager;

    @Inject
    private ServiceManager serviceManager;

    @Override
    protected AbstractFileAttachmentManager<ServiceFileAttachment> getManager() {
        return manager;
    }

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getAttachments(@QueryParam("serviceId") final Long serviceId, @QueryParam("locationId") final Long locationId) {
        PaginatedResult<ServiceFileAttachment> result;
        if (locationId != null) {
            result = manager.findByLocationId(locationId);
        } else {
            result = manager.findByServiceIdPaginated(serviceId);
        }
        return Response.ok(result).build();
    }

    @POST
    @Path("/upload")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response upload(@QueryParam("serviceId") final Long serviceId,
                           @Context final HttpServletRequest servletRequest) {
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
