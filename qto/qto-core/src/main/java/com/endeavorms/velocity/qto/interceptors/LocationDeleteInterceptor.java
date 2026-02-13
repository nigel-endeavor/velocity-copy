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

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Component
public class LocationDeleteInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationDeleteInterceptor.class);


    /**
     * Service Manager.
     */
    @Inject
    private ServiceManager serviceManager;

    @Inject
    private LocationManager locationManager;

    @Inject
    private DisputeManager disputeManager;

    /**
     * Validates the incoming Service.
     *
     * @param context the context of the invocation.
     * @return the result of the invocation.
     * @throws Exception if the invocation fails.
     */
    @AroundInvoke
    public Object validate(final InvocationContext context) throws Exception {
        LOGGER.info("LocationInterceptor.validate() called");
        List<ValidationError> errors = Lists.newArrayList();
        for (Object param : context.getParameters()) {
            if (param instanceof Long) {
                Long locationId = (long) param;
                Location location = locationManager.retrieve(locationId);
                List <Service> services = serviceManager.findByLocationId(locationId);
                //check for open macd's and disputes
                if (location.isCurrentInventory()) {
                    for (Service service : services) {
                        List<Service> macd = serviceManager.findMacdByParentId(service.getId());
                        if (macd != null && !TerminalServiceStatuses.getStatuses().contains(service.getStatus())) {
                            errors.add(new ValidationError(
                                    "Location", "A service on this inventory item has open MACD and cannot be deleted."));
                        }

                        List<Dispute> disputes = disputeManager.findOpenDisputesByServiceId(service.getId());
                        if (disputes != null && disputes.size() > 0) {
                            errors.add(new ValidationError(
                                    "Location", "A service on this inventory item has an open dispute and cannot be deleted."));
//                            throw new IllegalStateException("A service on this inventory item has an open dispute and cannot be deleted.");
                        }
                    }
                }
                //If inventory Check to see if the ordering location has any open services
                if (location.isCurrentInventory()) {
                    Location orderingLocation = locationManager.retrieve(location.getProvisioningLocationId());
                    for (Service service : orderingLocation.getServices()) {
                        if (!TerminalServiceStatuses.getStatuses().contains(service.getStatus())) {
                            errors.add(new ValidationError(
                                    "Location", "The ordering location has open services and cannot be deleted."));
                            break;
                        }
                    }
                }
            }
        }

        if (!errors.isEmpty()) {
            BadRequestError error = new BadRequestError(errors);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }

        return context.proceed();
    }

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
