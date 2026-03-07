package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.DashboardDataset;
import com.vertek.corporate.qto.report.DashboardSearchCriteria;
import com.vertek.corporate.qto.service.snapshot.ServiceSnapshotManager;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author rcasey
 * @since 3/25/2024
 */
@Path("/serviceSnapshots")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class ServiceSnapshotResource extends AbstractResource {

    @Inject
    private ServiceSnapshotManager manager;

    @GET
    @Path("/inventoryValuation")
    public Response getInventoryValuation(@Form final DashboardSearchCriteria criteria,
                                          @DefaultValue("6") @QueryParam("numOfMonths") final int numOfMonths) {
        DashboardDataset<BigDecimal> dataset = manager.getInventoryValuation(criteria, numOfMonths);
        return Response.ok().entity(dataset).build();
    }

    @GET
    @Path("/inventoryCounts")
    public Response getInventoryCounts(@Form final DashboardSearchCriteria criteria,
                                       @DefaultValue("6") @QueryParam("numOfMonths") final int numOfMonths) {
        DashboardDataset<BigInteger> dataset = manager.getInventoryCounts(criteria, numOfMonths);
        return Response.ok().entity(dataset).build();
    }

    @GET
    @Path("/newInventory")
    public Response getNewInventory(@Form final DashboardSearchCriteria criteria,
                                    @DefaultValue("6") @QueryParam("numOfMonths") final int numOfMonths) {
        DashboardDataset<BigInteger> dataset = manager.getNewInventory(criteria, numOfMonths);
        return Response.ok().entity(dataset).build();
    }

    @GET
    @Path("/serviceTypes")
    public Response getServiceTypes() {
       List<String> serviceType = manager.findServiceTypes();
        return Response.ok(serviceType).build();
    }
}
