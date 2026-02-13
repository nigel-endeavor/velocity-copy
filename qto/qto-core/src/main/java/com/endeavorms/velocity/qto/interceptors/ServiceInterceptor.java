package com.endeavorms.velocity.qto.interceptors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.authentication.Permissions;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertiesDto;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.note.ServiceNoteManager;
import com.endeavorms.velocity.qto.service.AbstractServiceManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.ServiceManagerFactory;
import com.endeavorms.velocity.qto.service.ServiceType;
import com.endeavorms.velocity.qto.service.TerminalServiceStatuses;
import com.endeavorms.velocity.qto.service.historyview.ServiceHistoryView;
import com.endeavorms.velocity.qto.service.historyview.ServiceHistoryViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

/**
 * Intercepts calls to ServiceManagers.
 */
@Component
public class ServiceInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInterceptor.class);

    /**
     * the error list.
     */
    private List<ValidationError> errors;

    @Autowired
    private ServiceManagerFactory serviceManagerFactory;

    @Autowired
    private ServiceNoteManager serviceNoteManager;

    @Autowired
    private ServiceHistoryViewManager serviceHistoryViewManager;

    @Autowired
    private LocationManager locationManager;

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private CompanyManager companyManager;

    /**
     * Validates the incoming Service for Spring MVC controllers.
     * @param entity the service to validate.
     * @return Optional containing BadRequestError if validation fails, empty if valid.
     */
    public Optional<BadRequestError> validateService(final Service entity) {
        errors = Lists.newArrayList();
        try {
            Location location = locationManager.retrieve(entity.getLocationId());
            CompanyConfigPropertiesDto config = companyManager.getConfigDto(location.getTenantId());
            AbstractServiceManager<?> manager = serviceManagerFactory.getManager(ServiceType.fromServiceName(entity.getType()));

            if (entity.getId() == null) {
                if (Boolean.FALSE.equals(config.getAutoCreateClientServiceId())
                        && Strings.isNullOrEmpty(entity.getClientServiceId())) {
                    errors.add(new ValidationError("Service", "Client Service ID is required."));
                } else if (Boolean.TRUE.equals(config.getClientIdUniqueConstraint())) {
                    if (entity.getClientServiceId() != null) {
                        List<Service> services = serviceManager.findByClientServiceIdAndTenant(
                                entity.getClientServiceId(), location.getTenantId());
                        if (!services.isEmpty()) {
                            errors.add(new ValidationError("Service", "Client Service ID is associated with another service."));
                        }
                    }
                }
            } else {
                Service existing = manager.retrieve(entity.getId());
                if (TerminalServiceStatuses.getStatuses().contains(entity.getStatus())) {
                    if (SecurityUtils.hasAuthority(Permissions.ADMIN)
                            || SecurityUtils.hasAuthority(Permissions.ORDER_WRITE_TERMINAL)
                            || SecurityUtils.hasAuthority(Permissions.INVENTORY_WRITE)) {
                        LOGGER.debug("service {} is in terminal status: {}, service is being updated by admin user {}",
                                entity.getId(), entity.getStatus(), SecurityUtils.getLoggedInUser());
                        String auditString = serviceNoteManager.getAuditString(entity, existing);
                        if (auditString.length() > 0) {
                            serviceNoteManager.create(entity.getId(), auditString, "Audit");
                        }
                    } else {
                        LOGGER.debug("service {} is in terminal status: {}, service will not be updated",
                                entity.getId(), entity.getStatus());
                        throw new IllegalStateException("Service is in terminal status: " + entity.getStatus());
                    }
                }
                if (Boolean.TRUE.equals(config.getClientIdUniqueConstraint())
                        && entity.getClientServiceId() != null
                        && !entity.getClientServiceId().equals(existing.getClientServiceId())) {
                    List<ServiceHistoryView> leafServices = serviceHistoryViewManager.getServiceTreeList(entity.getId());
                    ServiceHistoryView matchLeaf = leafServices.stream()
                            .filter(s -> s.getClientServiceId() != null && s.getClientServiceId().equals(entity.getClientServiceId()))
                            .findFirst().orElse(null);
                    if (matchLeaf == null) {
                        List<Service> services = serviceManager.findByClientServiceIdAndTenant(
                                entity.getClientServiceId(), location.getTenantId());
                        for (ServiceHistoryView leafService : leafServices) {
                            Service service = services.stream()
                                    .filter(s -> s.getId().equals(leafService.getId()))
                                    .findFirst().orElse(null);
                            if (service == null && !services.isEmpty()) {
                                errors.add(new ValidationError("Service", "Client Service ID is associated with another service."));
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Service validation error", e);
            errors.add(new ValidationError("Service", e.getMessage()));
        }
        return errors.isEmpty() ? Optional.empty() : Optional.of(new BadRequestError(errors));
    }

}
