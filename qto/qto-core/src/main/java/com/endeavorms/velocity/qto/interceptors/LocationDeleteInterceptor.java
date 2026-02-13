package com.endeavorms.velocity.qto.interceptors;

import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.dispute.Dispute;
import com.endeavorms.velocity.qto.dispute.DisputeManager;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.TerminalServiceStatuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Component
public class LocationDeleteInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationDeleteInterceptor.class);


    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private LocationManager locationManager;

    @Autowired
    private DisputeManager disputeManager;

    /**
     * Validates location deletion for Spring MVC controllers.
     */
    public Optional<BadRequestError> validateLocationDelete(final Long locationId) {
        List<ValidationError> errors = Lists.newArrayList();
        try {
            Location location = locationManager.retrieve(locationId);
            List<Service> services = serviceManager.findByLocationId(locationId);
            if (location.isCurrentInventory()) {
                for (Service service : services) {
                    List<Service> macd = serviceManager.findMacdByParentId(service.getId());
                    if (macd != null && !TerminalServiceStatuses.getStatuses().contains(service.getStatus())) {
                        errors.add(new ValidationError("Location", "A service on this inventory item has open MACD and cannot be deleted."));
                    }
                    List<Dispute> disputes = disputeManager.findOpenDisputesByServiceId(service.getId());
                    if (disputes != null && !disputes.isEmpty()) {
                        errors.add(new ValidationError("Location", "A service on this inventory item has an open dispute and cannot be deleted."));
                    }
                }
                Location orderingLocation = locationManager.retrieve(location.getProvisioningLocationId());
                for (Service service : orderingLocation.getServices()) {
                    if (!TerminalServiceStatuses.getStatuses().contains(service.getStatus())) {
                        errors.add(new ValidationError("Location", "The ordering location has open services and cannot be deleted."));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Location delete validation error", e);
            errors.add(new ValidationError("Location", e.getMessage()));
        }
        return errors.isEmpty() ? Optional.empty() : Optional.of(new BadRequestError(errors));
    }
}
