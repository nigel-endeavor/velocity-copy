package com.endeavorms.velocity.qto.interceptors;

import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySet;
import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySetInclude;
import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySetManager;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstance;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Interceptor for validating Service Milestones.
 */
@Component
public class ServiceMilestoneInterceptor {

    static {
        LoggerFactory.getLogger(ServiceMilestoneInterceptor.class);
    }

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private MilestoneDisplaySetManager milestoneDisplaySetManager;

    public BadRequestError validate(ServiceMilestoneInstance mi) {
        List<ValidationError> errors = Lists.newArrayList();
        Service service = serviceManager.retrieve(mi.getServiceId());

        if ("CANCELLED".equals(mi.getMilestone().getCode())
                || "CHANGE_IN_ASSIGNMENT".equals(mi.getMilestone().getCode())) {
            if (mi.getMilestoneDate() != null) {
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
            MilestoneDisplaySet mds = milestoneDisplaySetManager.getByDisplayType(service.getType());
            for (MilestoneDisplaySetInclude mdsi : mds.getDisplaySetIncludes()) {
                if (mdsi.getMilestone().getCode().equals(mi.getMilestone().getCode()) && mdsi.isInventoryFlag()) {
                    errors.add(new ValidationError("Service Milestone",
                            "Milestone " + mi.getMilestone().getName() + " is an Inventory Milestone and can only be adjusted, not removed."));
                }
            }
        }

        return errors.isEmpty() ? null : new BadRequestError(errors);
    }
}
