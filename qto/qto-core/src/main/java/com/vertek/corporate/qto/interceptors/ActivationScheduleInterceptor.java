package com.vertek.corporate.qto.interceptors;

import com.google.common.collect.Lists;
import com.vertek.corporate.qto.activation.schedule.ActivationSchedule;
import com.vertek.corporate.qto.common.BadRequestError;
import com.vertek.corporate.qto.common.ValidationError;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Interceptor for validating FTDI Schedule Items.
 *
 * @author mwelicka
 * @since 5/19/2023
 */
public class ActivationScheduleInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivationScheduleInterceptor.class);

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
     * Service Manager.
     */
    @Inject
    private ServiceManager serviceManager;

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
            if (param instanceof ActivationSchedule) {
                ActivationSchedule schedule = (ActivationSchedule) param;
                Service service = serviceManager.retrieve(schedule.getServiceId());
                Location location = locationManager.retrieve(service.getLocationId());
                if (service.getPoNumber() == null) {
                    errors.add(new ValidationError("Service",
                            "The PO number is missing."));
                }

                if (service.getClientServiceId() == null) {
                    errors.add(new ValidationError("Service",
                            "The Client Service ID is missing."));
                }

                if (location.getAddress1() == null
                        || location.getCity() == null
                        || location.getState() == null
                        || location.getPostalCode() == null) {
                    errors.add(new ValidationError("Location",
                            "The Address is incomplete."));
                }

                if (location.getName() == null) {
                    errors.add(new ValidationError("Location",
                            "The Location Name is missing."));
                }

                if (location.getClientLocationId() == null) {
                    errors.add(new ValidationError("Location",
                            "The Client Location ID is missing."));
                }

                if (schedule.getFtdiOrderTypeId() == null) {
                    errors.add(new ValidationError("Schedule",
                            "The Order Type is missing."));
                }

                if (schedule.getRequestedDate() == null) {
                    errors.add(new ValidationError("Schedule",
                            "The Requested Date is missing."));
                }

                if (schedule.getTechnicalNote() == null) {
                    errors.add(new ValidationError("Schedule",
                            "The Technical Note is missing."));
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


}
