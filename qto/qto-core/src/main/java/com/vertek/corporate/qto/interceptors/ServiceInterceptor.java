package com.vertek.corporate.qto.interceptors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.BadRequestError;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.ValidationError;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.config.CompanyConfigPropertiesDto;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.note.ServiceNoteManager;
import com.vertek.corporate.qto.service.AbstractServiceManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.ServiceManagerFactory;
import com.vertek.corporate.qto.service.ServiceType;
import com.vertek.corporate.qto.service.TerminalServiceStatuses;
import com.vertek.corporate.qto.service.historyview.ServiceHistoryView;
import com.vertek.corporate.qto.service.historyview.ServiceHistoryViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Intercepts calls to ServiceManagers.
 */
public class ServiceInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInterceptor.class);

    /**
     * the error list.
     */
    private List<ValidationError> errors;

    /**
     * The factory for ServiceManagers.
     */
    @Inject
    private ServiceManagerFactory serviceManagerFactory;
    /**
     * The manager for ServiceNotes.
     */
    @Inject
    private ServiceNoteManager serviceNoteManager;
    /**
     * Service History View Manager.
     */
    @Inject
    private ServiceHistoryViewManager serviceHistoryViewManager;

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

    @Inject
    private CompanyManager companyManager;

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
        LOGGER.info("ServiceInterceptor.validate() called");
        errors = Lists.newArrayList();

        for (Object param : context.getParameters()) {
            if (param instanceof Service) {
                Service entity = (Service) param;
                Location location = locationManager.retrieve(entity.getLocationId());
                CompanyConfigPropertiesDto config = companyManager.getConfigDto(location.getTenantId());
                AbstractServiceManager<X> manager = (AbstractServiceManager<X>) serviceManagerFactory
                        .getManager(ServiceType.fromServiceName(entity.getType()));

                if (entity.getId() == null) {
                    if (Boolean.FALSE.equals(config.getAutoCreateClientServiceId())
                            && Strings.isNullOrEmpty(entity.getClientServiceId())) {
                        errors.add(new ValidationError("Service", "Client Service ID is required."));
                    } else if (Boolean.TRUE.equals(config.getClientIdUniqueConstraint())) {
                        //check Client Service ID for uniqueness for new services
                        if (entity.getClientServiceId() != null) {
                            List<Service> services = serviceManager.findByClientServiceIdAndTenant(
                                    entity.getClientServiceId(), location.getTenantId());
                            if (!services.isEmpty()) {
                                errors.add(new ValidationError("Service", "Client Service ID is associated with another service."));
                            }
                        }
                    }
                } else if (entity.getId() != null) {
                    X existing = manager.retrieve(entity.getId());
                    if (TerminalServiceStatuses.getStatuses().contains(entity.getStatus())) {
                        if (org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.ADMIN)
                                || org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.ORDER_WRITE_TERMINAL)
                                || org.apache.shiro.SecurityUtils.getSubject().isPermitted(Permissions.INVENTORY_WRITE)) {
                            LOGGER.debug(
                                    "service {} is in terminal status: {}, service is being updated by admin user {}",
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
                    //check Client Service ID for uniqueness on edit
                    if (config.getClientIdUniqueConstraint() == null
                        || config.getClientIdUniqueConstraint() == false){
                        return context.proceed();
                    }
                    if (entity.getClientServiceId() != null
                            && !entity.getClientServiceId().
                            equals(existing.getClientServiceId())) {
                        List<ServiceHistoryView> leafServices = serviceHistoryViewManager.getServiceTreeList(entity.getId());
                        //checks to see if the client location id matches any of the leaf locations
                        //I put in this check because I changed the client location ID on a macd and when I changed it back
                        //it was no longer in the list of locations with that client location id
                        ServiceHistoryView matchLeaf = leafServices.stream().filter(s -> s.getClientServiceId() != null && s.getClientServiceId().
                                equals(entity.getClientServiceId())).findFirst().orElse(null);
                        if (matchLeaf == null) {
                            List<Service> services = serviceManager.findByClientServiceIdAndTenant(
                                    entity.getClientServiceId(), location.getTenantId());
                            for (ServiceHistoryView leafService : leafServices) {
                                Service service = services.stream().filter(s -> s.getId().
                                        equals(leafService.getId())).findFirst().orElse(null);
                                if (service == null && !services.isEmpty()) {
                                    errors.add(new ValidationError(
                                            "Service", "Client Service ID is associated with another service."));
                                    break;
                                }
                            }
                        }
                    }
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
