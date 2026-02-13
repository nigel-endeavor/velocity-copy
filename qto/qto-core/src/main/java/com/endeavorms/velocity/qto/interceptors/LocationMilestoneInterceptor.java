package com.endeavorms.velocity.qto.interceptors;

import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.jeop.LocationJeop;
import com.endeavorms.velocity.qto.jeop.LocationJeopManager;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstanceManager;
import com.endeavorms.velocity.qto.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Interceptor for validating Location Milestones.
 */
@Component
public class LocationMilestoneInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationMilestoneInterceptor.class);

    @Autowired
    private LocationManager locationManager;

    @Autowired
    private ServiceMilestoneInstanceManager smiManager;

    @Autowired
    private LocationJeopManager locationJeopManager;

    public BadRequestError validate(LocationMilestoneInstance mi) {
        List<ValidationError> errors = Lists.newArrayList();
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
            validateOpenJeop(location.getId(), errors);
        }

        if (("CANCELLED".equals(mi.getMilestone().getCode())
                || "CHANGE_IN_ASSIGNMENT".equals(mi.getMilestone().getCode()))
                && mi.getMilestoneDate() != null) {
            checkServices(mi.getMilestone().getCode(), location, errors);
            if (location.isCurrentInventory()) {
                errors.add(new ValidationError("Location Milestones",
                        "You cannont set the Cancelled or Change in Assignment Milestone on an Inventory Location"));
            }
        }

        return errors.isEmpty() ? null : new BadRequestError(errors);
    }

    private void checkServices(String code, Location location, List<ValidationError> errors) {
        for (Service service : location.getServices()) {
            if (smiManager.doesMilestoneExist(service.getId(), "COMPLETE")
                    && ("CHANGE_IN_ASSIGNMENT".equals(code) || "CANCELLED".equals(code))) {
                errors.add(new ValidationError("Location Milestones",
                        "There is a Service with a Complete Milestone. Changes will have to be Service Level"));
                break;
            }
            if (service.getInventoryServiceId() != null && service.isEligibleForInventory()
                    && ("CHANGE_IN_ASSIGNMENT".equals(code) || "CANCELLED".equals(code))) {
                errors.add(new ValidationError("Location Milestones",
                        "There is a Service that is in Inventory. Changes will have to be Service Level"));
                break;
            }
        }
    }

    private void validateOpenJeop(Long id, List<ValidationError> errors) {
        List<LocationJeop> jeops = locationJeopManager.getOpen(id);
        if (jeops.isEmpty()) {
            errors.add(new ValidationError("Location Milestones",
                    "An open Jeopardy is required before entering the On Hold Milestone"));
        }
    }
}
