package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.attachment.LocationFileAttachment;
import com.endeavorms.velocity.qto.attachment.LocationFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
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

/**
 * @author rcasey
 * @since 1/24/2023
 */
@Path("/locationFileAttachments")
@Produces("application/json")
public class LocationFileAttachmentResource extends AbstractFileAttachmentResource<LocationFileAttachment> {

    /** Business methods for LocationFileAttachment. */
    @Inject
    private LocationFileAttachmentManager manager;

    @Inject
    private LocationManager locationManager;

    @Override
    protected AbstractFileAttachmentManager<LocationFileAttachment> getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/locationFileAttachments";
    }

    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getAttachments(@QueryParam("locationId") final Long locationId) {
        PaginatedResult<LocationFileAttachment> result = manager.findByLocationId(locationId);
        return Response.ok(result).build();
    }

    @POST
    @Path("/upload")

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response upload(@QueryParam("locationId") final Long locationId,
                           @Context final HttpServletRequest servletRequest) {
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
