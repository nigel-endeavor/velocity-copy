package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.report.DashboardSearchCriteria;
import com.vertek.corporate.qto.report.WipLocationJeopView;
import com.vertek.corporate.qto.report.WipServiceJeopView;
import com.vertek.corporate.qto.report.WipServiceView;
import com.vertek.corporate.qto.report.WipViewManager;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @since 3/10/2023
 */
@Path("/wipViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class WipViewResource extends AbstractResource<WipServiceView> {

    @Inject
    private WipViewManager manager;

    @GET
    @Path("/wipServices")
    public Response getWipServices(@Form final DashboardSearchCriteria criteria,
                                   @QueryParam("allStatuses") final boolean allStatuses) {
        List<WipServiceView> wipServices = manager.getWipServices(criteria, allStatuses);
        return Response.ok(wipServices).build();
    }

    @GET
    @Path("/wipServiceJeops")
    public Response getWipServiceJeops(@Form final DashboardSearchCriteria criteria) {
        List<WipServiceJeopView> wipServiceJeops = manager.getWipServiceJeops(criteria);
        return Response.ok(wipServiceJeops).build();
    }

    @GET
    @Path("/wipLocationJeops")
    public Response getWipLocationJeops(@Form final DashboardSearchCriteria criteria) {
        List<WipLocationJeopView> wipServiceJeops = manager.getWipLocationJeops(criteria);
        return Response.ok(wipServiceJeops).build();
    }

    /**
     * Returns services that meet the conditions of the monthly spend dashboard.
     * @param criteria the search criteria to filter services by.
     * @return matching services.
     */
    @GET
    @Path("/monthlySpend")
    public Response getMonthlySpend(@Form final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = manager.getServicesForMonthlySpend(criteria);
        return Response.ok(wipServices).build();
    }

    /**
     * Returns services that meet the conditions of the incremental network spend dashboard.
     * @param criteria the search criteria to filter services by.
     * @return matching services.
     */
    @GET
    @Path("/incrementalNetworkSpend")
    public Response getServicesForIncrementalNetworkSpend(@Form final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = manager.getServicesForIncrementalNetworkSpend(criteria);
        return Response.ok(wipServices).build();
    }
    /**
     * Returns services that meet the conditions of the unbillable network expense accrual dashboard.
     * @param criteria the search criteria to filter services by.
     * @return matching services.
     */
    @GET
    @Path("/unbillableNetworkExpenseAccrual")
    public Response getServicesForUnbillableNetworkExpenseAccrual(@Form final DashboardSearchCriteria criteria) {
        List<WipServiceView> wipServices = manager.getServicesForUnbillableNetworkExpenseAccrual(criteria);
        return Response.ok(wipServices).build();
    }
}
