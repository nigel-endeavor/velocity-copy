package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.report.ActivationAttemptView;
import com.endeavorms.velocity.qto.report.ActivationAttemptViewManager;
import com.endeavorms.velocity.qto.report.DashboardSearchCriteria;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
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

    @Override
    protected String getResourcePath() {
        return "/activationViews";
    }

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
