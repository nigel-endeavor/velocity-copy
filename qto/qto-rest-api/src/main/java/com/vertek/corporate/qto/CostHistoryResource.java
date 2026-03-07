package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.costhistory.CostHistory;
import com.vertek.corporate.qto.costhistory.CostHistoryManager;
import com.vertek.corporate.qto.costhistory.CostHistorySearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import static com.vertek.corporate.qto.authentication.Permissions.INVENTORY_READ;

/**
 * @author rcasey
 * @since 11/1/2023
 */
@Path("/costHistory")
@Consumes("application/json")
@Produces("application/json")
@RequiresPermissions(INVENTORY_READ)
public class CostHistoryResource extends AbstractResource<CostHistory> {

    @Inject
    private CostHistoryManager manager;

    @GET
    public Response getCostHistory(@Form final CostHistorySearchCriteria criteria) {
        try {
            return Response.ok(manager.findBySearchCriteria(criteria)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/meta")
    public Response getCostHistoryMeta(@Form final CostHistorySearchCriteria criteria) {
        try {
            return Response.ok(manager.getCostHistoryMeta(criteria)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
