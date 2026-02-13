package com.endeavorms.velocity.qto.interceptors;

import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertiesDto;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.location.TerminalLocationStatuses;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Intercepts calls to Order Manager.
 */
@Component
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

    /**
     * Validates the incoming Location for Spring MVC controllers.
     */
    public Optional<BadRequestError> validateLocation(final Location location) {
        List<ValidationError> errors = Lists.newArrayList();
        try {
            Order order = orderManager.retrieve(location.getOrderId());
            CompanyConfigPropertiesDto config = companyManager.getConfigDto(order.getTenantId());
            if (config.getClientIdUniqueConstraint() == null || !config.getClientIdUniqueConstraint()) {
                return Optional.empty();
            }
            if (location.getId() == null) {
                List<Location> locations = locationManager.findByClientLocIdAndTenant(location.getClientLocationId(), order.getTenantId());
                Location invLocation = locationManager.findInvByClientLocIdAndTenant(location.getClientLocationId(), order.getTenantId());
                List<Location> openLocations = locations.stream()
                        .filter(l -> !TerminalLocationStatuses.getStatuses().contains(l.getStatus()))
                        .collect(Collectors.toList());
                if (!openLocations.isEmpty() || invLocation != null) {
                    errors.add(new ValidationError("Location", "Client Location ID is associated with another open location."));
                }
            } else {
                Location existing = locationManager.retrieve(location.getId());
                if (location.getClientLocationId() != null && !location.getClientLocationId().equals(existing.getClientLocationId())) {
                    List<Location> leafLocations = locationManager.getLocationTreeList(location.getId());
                    Location matchLeaf = leafLocations.stream()
                            .filter(l -> location.getClientLocationId().equals(l.getClientLocationId()))
                            .findFirst().orElse(null);
                    if (matchLeaf == null) {
                        List<Location> locations = locationManager.findByClientLocIdAndTenant(
                                location.getClientLocationId(), existing.getTenantId());
                        for (Location leafLocation : leafLocations) {
                            Location loc = locations.stream().filter(l -> l.getId().equals(leafLocation.getId())).findFirst().orElse(null);
                            if (loc == null && !locations.isEmpty()) {
                                errors.add(new ValidationError("Location", "Client Location ID is associated with another location."));
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Location validation error", e);
            errors.add(new ValidationError("Location", e.getMessage()));
        }
        return errors.isEmpty() ? Optional.empty() : Optional.of(new BadRequestError(errors));
    }

}
