package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.service.historyview.ServiceHistoryView;
import com.vertek.corporate.qto.service.historyview.ServiceHistoryViewManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 10/27/2023
 */
@Path("/serviceHistory")
@Consumes("application/json")
@Produces("application/json")
@RequiresPermissions(value = {
        Permissions.INVENTORY_WRITE,
        Permissions.ORDER_WRITE}, logical = Logical.OR)
public class ServiceHistoryViewResource extends AbstractResource<ServiceHistoryView> {

    @Inject
    private ServiceHistoryViewManager manager;

    @GET
    public Response getServiceHistory(@QueryParam("serviceId") final Long serviceId,
                                      @QueryParam("sortDir") final String sortDir,
                                      @QueryParam("sortField") final String sortField) {
        try {
            return Response.ok(manager.getServiceTreePaginatedResult(serviceId, sortDir, sortField)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
