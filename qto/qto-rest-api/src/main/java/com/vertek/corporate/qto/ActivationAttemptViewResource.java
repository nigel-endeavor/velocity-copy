package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.report.ActivationAttemptView;
import com.vertek.corporate.qto.report.ActivationAttemptViewManager;
import com.vertek.corporate.qto.report.DashboardSearchCriteria;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author bmccormick
 */
@Path("/activationViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class ActivationAttemptViewResource extends AbstractResource<ActivationAttemptView> {

    @Inject
    private ActivationAttemptViewManager activationAttemptManager;


    /**
     * Returns activation attempts that meet the conditions of the Activation Event length dashboard.
     * @return matching services.
     */
    @GET
    @Path("/activationIntervals")
    public Response getServiceIntervals(@Form final DashboardSearchCriteria criteria,
                                        @QueryParam("numOfMonths") final int numOfMonths) {
//        List<String> companyNamesList = companyNames != null ? Arrays.asList(companyNames.split(", ")) : new ArrayList<>();
//        List<String> masterCompanyNamesList = masterCompanyNames != null ? Arrays.asList(masterCompanyNames.split(", ")) : new ArrayList<>();
//        List<String> tenantNamesList = tenantNames != null ? Arrays.asList(tenantNames.split(", ")) : new ArrayList<>();
        List<ActivationAttemptView> activationAttempts = activationAttemptManager.getServiceIntervals(criteria, numOfMonths);
        return Response.ok(activationAttempts).build();
    }
}
