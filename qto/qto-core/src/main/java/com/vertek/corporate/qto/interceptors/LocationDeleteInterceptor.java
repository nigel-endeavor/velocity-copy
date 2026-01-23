package com.vertek.corporate.qto.interceptors;

import com.google.common.collect.Lists;
import com.vertek.corporate.qto.common.BadRequestError;
import com.vertek.corporate.qto.common.ValidationError;
import com.vertek.corporate.qto.dispute.Dispute;
import com.vertek.corporate.qto.dispute.DisputeManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.TerminalServiceStatuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.List;

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
}
