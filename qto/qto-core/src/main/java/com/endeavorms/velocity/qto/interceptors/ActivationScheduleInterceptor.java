package com.endeavorms.velocity.qto.interceptors;

import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.activation.schedule.ActivationSchedule;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Interceptor for validating FTDI Schedule Items.
 */
@Component
public class ActivationScheduleInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivationScheduleInterceptor.class);

    @Autowired
    private LocationManager locationManager;

    @Autowired
    private ServiceManager serviceManager;

    public BadRequestError validate(ActivationSchedule schedule) {
        List<ValidationError> errors = Lists.newArrayList();
        Service service = serviceManager.retrieve(schedule.getServiceId());
        Location location = locationManager.retrieve(service.getLocationId());

        if (service.getPoNumber() == null) {
            errors.add(new ValidationError("Service", "The PO number is missing."));
        }
        if (service.getClientServiceId() == null) {
            errors.add(new ValidationError("Service", "The Client Service ID is missing."));
        }
        if (location.getAddress1() == null || location.getCity() == null
                || location.getState() == null || location.getPostalCode() == null) {
            errors.add(new ValidationError("Location", "The Address is incomplete."));
        }
        if (location.getName() == null) {
            errors.add(new ValidationError("Location", "The Location Name is missing."));
        }
        if (location.getClientLocationId() == null) {
            errors.add(new ValidationError("Location", "The Client Location ID is missing."));
        }
        if (schedule.getFtdiOrderTypeId() == null) {
            errors.add(new ValidationError("Schedule", "The Order Type is missing."));
        }
        if (schedule.getRequestedDate() == null) {
            errors.add(new ValidationError("Schedule", "The Requested Date is missing."));
        }
        if (schedule.getTechnicalNote() == null) {
            errors.add(new ValidationError("Schedule", "The Technical Note is missing."));
        }

        return errors.isEmpty() ? null : new BadRequestError(errors);
    }
}
