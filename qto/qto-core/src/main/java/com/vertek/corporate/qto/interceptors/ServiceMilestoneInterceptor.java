package com.vertek.corporate.qto.interceptors;

import com.google.common.collect.Lists;
import com.vertek.corporate.qto.common.BadRequestError;
import com.vertek.corporate.qto.common.ValidationError;
import com.vertek.corporate.qto.milestone.MilestoneDisplaySet;
import com.vertek.corporate.qto.milestone.MilestoneDisplaySetInclude;
import com.vertek.corporate.qto.milestone.MilestoneDisplaySetManager;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstance;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Interceptor for validating Service Milestones.
 *
 * @author mwelicka
 * @since 3/10/2023
 */
public class ServiceMilestoneInterceptor {

    static {
        LoggerFactory.getLogger(ServiceMilestoneInterceptor.class);
    }

    /**
     * the error list.
     */
    private List<ValidationError> errors;

    /**
     * Service Manager.
     */
    @Inject
    private ServiceManager serviceManager;

    @Inject
    private MilestoneDisplaySetManager milestoneDisplaySetManager;

    /**
     * Validates milestones that may be edited.
     *
     * @param context the intercepted InvocationContext.
     * @return an Object.
     * @throws Exception should invocation fail for any reason.
     */
    @AroundInvoke
    public Object validate(final InvocationContext context) throws Exception {
       errors = Lists.newArrayList();

        for (Object param : context.getParameters()) {
            if (param instanceof ServiceMilestoneInstance) {
                ServiceMilestoneInstance mi = (ServiceMilestoneInstance) param;
                Service service = serviceManager.retrieve(mi.getServiceId());

                if ("CANCELLED".equals(mi.getMilestone().getCode())
                        || "CHANGE_IN_ASSIGNMENT".equals(mi.getMilestone().getCode())) {
                    if (mi.getMilestoneDate() != null){
                        if (service.isCurrentInventory()) {
                            errors.add(new ValidationError("Service Milestone",
                                    "Inventory Services cannot be Cancelled or have a Change in Assignment."));
                        }
                        if (service.getInventoryServiceId() != null && service.isEligibleForInventory()) {
                            errors.add(new ValidationError("Service Milestone",
                                    "This Service is already in Inventory and cannot be Cancelled or have a Change in Assignment."));
                        }
                    } else {
                        if (service.getOrderType() != null && !"New".equals(service.getOrderType())) {
                            errors.add(new ValidationError("Service Milestone",
                                    "Cancelled or Change in Assignment Milestone dates cannot be removed from a MACD Services."));
                        }
                    }
                }

                if (mi.getMilestoneDate() == null) {
                     // get the inventory flag from the Milestone Display Set Include table
                    MilestoneDisplaySet mds = milestoneDisplaySetManager.getByDisplayType(service.getType());
                    for (MilestoneDisplaySetInclude mdsi : mds.getDisplaySetIncludes()) {
                        if (mdsi.getMilestone().getCode().equals(mi.getMilestone().getCode()) && mdsi.isInventoryFlag()) {
                           errors.add(new ValidationError("Service Milestone",
                                   "Milestone " + mi.getMilestone().getName() + " is an Inventory Milestone and can only be adjusted, not removed."));
                        }
                    }
                }
            }
        }

        if (errors.size() > 0) {
            BadRequestError error = new BadRequestError(errors);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }

        return context.proceed();
    }
}
