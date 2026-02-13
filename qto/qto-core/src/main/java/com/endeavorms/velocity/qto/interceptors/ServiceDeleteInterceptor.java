package com.endeavorms.velocity.qto.interceptors;

import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.dispute.Dispute;
import com.endeavorms.velocity.qto.dispute.DisputeManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceDeleteInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDeleteInterceptor.class);

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private DisputeManager disputeManager;

    /**
     * Validates service deletion for Spring MVC controllers.
     */
    public Optional<BadRequestError> validateServiceDelete(final Long serviceId) {
        List<ValidationError> errors = Lists.newArrayList();
        try {
            Service existing = serviceManager.retrieve(serviceId);
            if (existing.isCurrentInventory()) {
                List<Service> macd = serviceManager.findMacdByParentId(existing.getId());
                if (!macd.isEmpty()) {
                    errors.add(new ValidationError("Service", "This inventory item has an open MACD and cannot be deleted."));
                }
                List<Dispute> disputes = disputeManager.findOpenDisputesByServiceId(existing.getId());
                if (disputes != null && !disputes.isEmpty()) {
                    errors.add(new ValidationError("Service", "This inventory item has an open dispute and cannot be deleted."));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Service delete validation error", e);
            errors.add(new ValidationError("Service", e.getMessage()));
        }
        return errors.isEmpty() ? Optional.empty() : Optional.of(new BadRequestError(errors));
    }
}
