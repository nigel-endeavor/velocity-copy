package com.vertek.corporate.qto.interceptors;

import com.google.common.collect.Lists;
import com.vertek.corporate.qto.common.BadRequestError;
import com.vertek.corporate.qto.common.ValidationError;
import com.vertek.corporate.qto.jeop.LocationJeop;
import com.vertek.corporate.qto.jeop.LocationJeopManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.milestone.LocationMilestoneInstance;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import com.vertek.corporate.qto.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Interceptor for validating Location Milestones.
 *
 * @author mwelicka
 * @since 3/10/2023
 */
public class LocationMilestoneInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationMilestoneInterceptor.class);

        /**
     * the error list.
     */
    private List<ValidationError> errors;

    /**
     * Location Manager.
     */
    @Inject
    private LocationManager locationManager;

    /**
     * Service Milestone Instance Manager.
     */
    @Inject
    private ServiceMilestoneInstanceManager smiManager;

    /**
     * Location Jeop Manager.
     */
    @Inject
    private LocationJeopManager locationJeopManager;

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
            if (param instanceof LocationMilestoneInstance) {
                LocationMilestoneInstance mi = (LocationMilestoneInstance) param;
                Location location = locationManager.retrieve(mi.getLocationId());

                if ("COMPLETE".equals(mi.getMilestone().getCode()) && mi.getMilestoneDate() == null
                        && location.isCurrentInventory()) {
                    errors.add(new ValidationError("Location Milestones",
                            "You can only adjust the Complete Milestone on an Inventory Location"));
                }

                 if (("CANCELLED".equals(mi.getMilestone().getCode())
                         || "CHANGE_IN_ASSIGNMENT".equals(mi.getMilestone().getCode()))
                         && mi.getMilestoneDate() == null && location.getInventoryLocationId() != null) {
                     errors.add(new ValidationError("Location Milestones",
                             "This Location was previously linked to an inventory item so you cannot remove the Cancelled or Change in Assignment Milestone date."));
                 }


                if ("ON_HOLD".equals(mi.getMilestone().getCode()) && mi.getMilestoneDate() != null) {
                    validateOpenJeop(location.getId());

                }

                 if (("CANCELLED".equals(mi.getMilestone().getCode())
                         || "CHANGE_IN_ASSIGNMENT".equals(mi.getMilestone().getCode()))
                         && mi.getMilestoneDate() != null) {
                    checkServices(mi.getMilestone().getCode(), location);
                    if (location.isCurrentInventory()) {
                        errors.add(new ValidationError("Location Milestones",
                                "You cannont set the Cancelled or Change in Assignment Milestone on an Inventory Location"));
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

    /**
     * Checks to see if there are any Complete or Canceled milestones on the Services.
     *
     * @param code
     * @param location Location being updated
     */
    private void checkServices(final String code, final Location location) {
        for (Service service : location.getServices()) {
            if (smiManager.doesMilestoneExist(service.getId(), "COMPLETE")
                    && ("CHANGE_IN_ASSIGNMENT".equals(code) || "CANCELLED".equals(code))) {
                errors.add(new ValidationError("Location Milestones",
                       "There is a Service with a Complete Milestone. Changes will have to be Service Level"));
                break;
            }
            if (service.getInventoryServiceId() != null && service.isEligibleForInventory() && ("CHANGE_IN_ASSIGNMENT".equals(code) || "CANCELLED".equals(code))) {
                errors.add(new ValidationError("Location Milestones",
                        "There is a Service that is in Inventory. Changes will have to be Service Level"));
                break;
            }
        }
    }

    /**
     * Checks to see if there is an open jeop for the On Hold status.
     * @param id Location ID.
     */
    private void validateOpenJeop(final Long id) {
        List<LocationJeop> jeops = locationJeopManager.getOpen(id);
        if (jeops.isEmpty()) {
            String msg = "An open Jeopardy is required before entering the On Hold Milestone";
            errors.add(new ValidationError("Location Milestones", msg));
        }
    }


}
