package com.vertek.corporate.qto;

import com.vertek.corporate.qto.attachment.LocationFileAttachment;
import com.vertek.corporate.qto.attachment.LocationFileAttachmentManager;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractFileAttachmentManager;
import com.vertek.corporate.qto.common.AbstractFileAttachmentResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
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

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getAttachments(@QueryParam("locationId") final Long locationId) {
        PaginatedResult<LocationFileAttachment> result = manager.findByLocationId(locationId);
        return Response.ok(result).build();
    }

    @POST
    @Path("/upload")

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
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
