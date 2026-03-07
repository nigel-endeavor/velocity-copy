package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.report.DashboardSearchCriteria;
import com.vertek.corporate.qto.report.ProviderIntervalsView;
import com.vertek.corporate.qto.report.ProviderViewManager;
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
 * @author llevit
 */
@Path("/providerViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class ProviderViewResource extends AbstractResource<ProviderIntervalsView> {

    @Inject
    private ProviderViewManager providerManager;

    @Inject
    private WipViewManager wipManager;

    @GET
    @Path("/providerIntervals")
    public Response getProviderIntervals(@Form final DashboardSearchCriteria criteria,
                                        @QueryParam("intervalTypeCode") final String intervalTypeCode,
                                        @QueryParam("numOfMonths") final int numOfMonths) {
        PreconditionsUtil.checkArgument(intervalTypeCode, "Interval Type Code is required.");
        List<ProviderIntervalsView> providerIntervals = providerManager.getProviderIntervals(criteria, intervalTypeCode, numOfMonths);
        return Response.ok(providerIntervals).build();
    }

    @GET
    @Path("/providerReliance")
    public Response getProviderReliance(@Form final DashboardSearchCriteria criteria) {
        List<WipServiceView> services = wipManager.getProviderReliance(criteria);
        return Response.ok(services).build();
    }
}
