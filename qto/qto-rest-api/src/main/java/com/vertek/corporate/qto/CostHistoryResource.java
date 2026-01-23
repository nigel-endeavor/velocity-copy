package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.costhistory.CostHistory;
import com.vertek.corporate.qto.costhistory.CostHistoryManager;
import com.vertek.corporate.qto.costhistory.CostHistorySearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
