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

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceDeleteInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDeleteInterceptor.class);

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
    private DisputeManager disputeManager;

    /**
     * Validates the incoming Service.
     *
     * @param context the context of the invocation.
     * @param <X>     the type of Service.
     * @return the result of the invocation.
     * @throws Exception if the invocation fails.
     */
    @AroundInvoke
    public <X extends Service> Object validate(final InvocationContext context) throws Exception {
        LOGGER.info("ServiceDeleteInterceptor.validate() called");
        errors = Lists.newArrayList();

        for (Object param : context.getParameters()) {
            if (param instanceof Long) {
                Long serviceId = (long) param;
                Service existing = serviceManager.retrieve(serviceId);
//                Location location = locationManager.retrieve(existing.getLocationId());
//                AbstractServiceManager<X> manager = (AbstractServiceManager<X>) serviceManagerFactory
//                        .getManager(ServiceType.fromServiceName(existing.getType()));
                //check for open macd's and disputes
                if (existing.isCurrentInventory()) {
                    List<Service> macd = serviceManager.findMacdByParentId(existing.getId());
                    if (!macd.isEmpty()) {
                        errors.add(new ValidationError(
                                "Service", "This inventory item has an open MACD and cannot be deleted."));
                    }

                    List<Dispute> disputes = disputeManager.findOpenDisputesByServiceId(existing.getId());
                    if (disputes != null && disputes.size() > 0) {
                        errors.add(new ValidationError(
                                "Service", "This inventory item has an open dispute and cannot be deleted."));
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
