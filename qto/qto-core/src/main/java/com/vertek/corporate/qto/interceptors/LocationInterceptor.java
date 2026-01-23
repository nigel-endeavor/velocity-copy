package com.vertek.corporate.qto.interceptors;

import com.google.common.collect.Lists;
import com.vertek.corporate.qto.common.BadRequestError;
import com.vertek.corporate.qto.common.ValidationError;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.config.CompanyConfigPropertiesDto;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.location.TerminalLocationStatuses;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Intercepts calls to Order Manager.
 */
public class LocationInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationInterceptor.class);

    /**
     * Location Manager.
     */
    @Inject
    private LocationManager locationManager;

    /**
     * Order Manager.
     */
    @Inject
    private OrderManager orderManager;

    /**
     * Company Manager.
     */
    @Inject
    private CompanyManager companyManager;

    /**
     * Validates the incoming Locations.
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
            if (param instanceof Location) {
                Location location = (Location) param;
                Order order = orderManager.retrieve(location.getOrderId());
                CompanyConfigPropertiesDto config = companyManager.getConfigDto(order.getTenantId());
                if (config.getClientIdUniqueConstraint() == null || !config.getClientIdUniqueConstraint()) {
                    return context.proceed();
                }

                if (location.getId() == null) {
                    //check Client Location ID for uniqueness on create
                    List<Location> locations = locationManager.findByClientLocIdAndTenant(location.getClientLocationId(), order.getTenantId());
                    Location invLocation = locationManager.findInvByClientLocIdAndTenant(location.getClientLocationId(), order.getTenantId());
                    List<Location> openLocations = locations.stream()
                            .filter(l -> !TerminalLocationStatuses.getStatuses().contains(l.getStatus()))
                            .collect(Collectors.toList());

                    if (!openLocations.isEmpty() || invLocation != null) {
                        errors.add(new ValidationError("Location", "Client Location ID is associated with another open location."));
                    }
                } else {
                    //check Client Location ID for uniqueness on edit
                    Location existing = locationManager.retrieve(location.getId());
                    if (location.getClientLocationId() != null && !location.getClientLocationId().equals(existing.getClientLocationId())) {
                        List<Location> leafLocations = locationManager.getLocationTreeList(location.getId());
                        //checks to see if the client location id matches any of the leaf locations
                        //I put in this check because I changed the client location ID on a macd and when I changed it back
                        //it was no longer in the list of locations with that client location id
                        Location matchLeaf = leafLocations.stream().filter(l -> l.getClientLocationId().equals(location.getClientLocationId())).findFirst().orElse(null);
                        if (matchLeaf == null) {
                            List<Location> locations = locationManager.findByClientLocIdAndTenant(
                                    location.getClientLocationId(), existing.getTenantId());
                            for (Location leafLocation : leafLocations) {
                                Location location1 = locations.stream().filter(l -> l.getId().equals(leafLocation.getId())).findFirst().orElse(null);
                                if (location1 == null && !locations.isEmpty()) {
                                    errors.add(new ValidationError("Location", "Client Location ID is associated with another location."));
                                    break;
                                }
                            }
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
