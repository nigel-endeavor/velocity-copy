package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.attachment.ServiceFileAttachment;
import com.endeavorms.velocity.qto.attachment.ServiceFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
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

    @Override
    protected String getResourcePath() {
        return "/serviceFileAttachments";
    }

    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
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
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
