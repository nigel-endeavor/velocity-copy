package com.vertek.corporate.qto.activation.attempt;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.activation.requirement.ActivationAttemptRequirement;
import com.vertek.corporate.qto.activation.requirement.Requirement;
import com.vertek.corporate.qto.activation.requirement.RequirementTemplate;
import com.vertek.corporate.qto.activation.requirement.RequirementTemplateManager;
import com.vertek.corporate.qto.activation.schedule.ActivationSchedule;
import com.vertek.corporate.qto.activation.schedule.ActivationScheduleManager;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.common.lookup.LookupValueManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstance;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import com.vertek.corporate.qto.service.AbstractServiceManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.ServiceManagerFactory;
import com.vertek.corporate.qto.service.ServiceType;
import com.vertek.corporate.qto.service.TerminalServiceStatuses;
import com.vertek.corporate.qto.service.broadband.BroadbandServiceManager;
import org.apache.shiro.SecurityUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author rcasey
 * @since 3/23/2023
 */
@Stateless
public class ActivationAttemptManager extends StandardManager<ActivationAttempt> {

    /**
     * Persistence tier for ActivationAttempts.
     */
    @Inject
    private ActivationAttemptJpaDao dao;

    @Inject
    private LocationManager locationManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private ActivationScheduleManager activationScheduleManager;

    @Inject
    private ActivationAttemptManager activationAttemptManager;

    @Inject
    private ServiceMilestoneInstanceManager serviceMilestoneInstanceManager;

    @Inject
    private RequirementTemplateManager requirementTemplateManager;

    @Inject
    private LookupValueManager lookupValueManager;

    @Inject
    private BroadbandServiceManager bbManager;

    /**
     * Manager for getting the properly injected manager for a given service type.
     */
    @Inject
    private ServiceManagerFactory serviceManagerFactory;

    public ActivationAttempt createForInventory(final ActivationAttempt entity) {
        return super.create(entity);
    }

    @Override
    public ActivationAttempt create(final ActivationAttempt entity) {
        Service service;
        if (entity.getTenantId() == null) {
            service = serviceManager.retrieve(entity.getServiceId());
            entity.setTenantId(service.getTenantId());
            entity.setMasterCustomerId(service.getMasterCustomerId());
        } else {
            service = serviceManager.findByIdAndTenant(entity.getServiceId(), entity.getTenantId());
        }
        ActivationAttempt created = super.create(entity);
        created.setCreatedDate(new Date());

        //identify the previous attempt
        ActivationAttempt prevAttempt = null;
        List<ActivationAttempt> prevAttempts = dao.findByServiceId(created.getServiceId());
        if (prevAttempts.size() > 1) {
            prevAttempt = prevAttempts.get(1);
        }

        //copy forward activation test results
        if (prevAttempt != null) {
            created.setTestedDownloadSpeed(prevAttempt.getTestedDownloadSpeed());
            created.setTestedUploadSpeed(prevAttempt.getTestedUploadSpeed());
            created.setLatency(prevAttempt.getLatency());
            created.setBackupDownloadSpeed(prevAttempt.getBackupDownloadSpeed());
            created.setBackupUploadSpeed(prevAttempt.getBackupUploadSpeed());
            created.setSignalRsrp(prevAttempt.getSignalRsrp());
            created.setSinrRsrq(prevAttempt.getSinrRsrq());
            created.setPrimaryUid(prevAttempt.getPrimaryUid());
            created.setSecondaryUid(prevAttempt.getSecondaryUid());
            created.setManagedRouterSerialNumber(prevAttempt.getManagedRouterSerialNumber());
            created.setLocationDowntownForCutover(prevAttempt.getLocationDowntownForCutover());
            created.setCloseoutCode(prevAttempt.getCloseoutCode());
        }

        //copy requirements from previous attempt to new attempt
        Location location = locationManager.retrieve(service.getLocationId());
        if (location.getRequirementTemplateId() != null) {
            RequirementTemplate requirementTemplate = requirementTemplateManager.retrieve(location.getRequirementTemplateId());
            if (requirementTemplate != null) {
                for (Requirement requirement : requirementTemplate.getRequirements()) {
                    ActivationAttemptRequirement prevRequirement = null;
                    if (prevAttempt != null) {
                        prevRequirement = prevAttempt.getRequirements().stream().filter(r -> Objects.equals(r.getRequirement().getId(), requirement.getId())).findFirst().orElse(null);
                    }
                    ActivationAttemptRequirement aaRequirement = new ActivationAttemptRequirement();
                    aaRequirement.setRequirement(requirement);
                    aaRequirement.setActivationAttemptId(created.getId());
                    if (prevRequirement != null) {
                        aaRequirement.setComplete(prevRequirement.isComplete());
                    }
                    created.getRequirements().add(aaRequirement);
                }
            }
        }
        return edit(created);
    }

    public ActivationAttempt editForInventory(final ActivationAttempt entity) {
        return super.edit(entity);
    }
    @Override
    public ActivationAttempt edit(final ActivationAttempt entity) {
        ActivationAttempt existing = retrieve(entity.getId());
        entity.setIssueNotes(existing.getIssueNotes());
        if (!Strings.isNullOrEmpty(entity.getInternalTechAssigned())
                && entity.getScheduledAttemptStatus().equals("Schedule Date Confirmed")) {
            entity.setScheduledAttemptStatus("In Progress");
        }
        if (Strings.isNullOrEmpty(entity.getInternalTechAssigned()) && entity.getScheduledAttemptStatus().equals("In Progress")) {
            entity.setScheduledAttemptStatus("Schedule Date Confirmed");
        }
        if (entity.getIssueNotesDisplay() != null) {
            entity.setIssueNotes(entity.getIssueNotesDisplay());
        }
        Service service = serviceManager.retrieve(entity.getServiceId());
        entity.setTenantId(service.getTenantId());
        entity.setMasterCustomerId(service.getMasterCustomerId());
        for (ActivationAttemptRequirement aaRequirement : entity.getRequirements()) {
            aaRequirement.getRequirement().setLookupValue(lookupValueManager.retrieve(aaRequirement.getRequirement().getLookupValue().getId()));
        }

        if (entity.getUseExistingInventoryLocationAddress() != null
                && entity.getUseExistingInventoryLocationAddress()) {
            Location location = locationManager.retrieve(service.getLocationId());
            Location inventoryLocation
                    = locationManager.findInvByClientLocIdAndTenant(location.getClientLocationId(),
                    location.getTenantId());
            location.setAddress1(inventoryLocation.getAddress1());
            location.setAddress2(inventoryLocation.getAddress2());
            location.setCity(inventoryLocation.getCity());
            location.setState(inventoryLocation.getState());
            location.setPostalCode(inventoryLocation.getPostalCode());
            location.setCountry(inventoryLocation.getCountry());
            locationManager.edit(location);
        }

        return super.edit(entity);
    }

    @Override
    protected ActivationAttemptJpaDao getDao() {
        return dao;
    }

    public List<ActivationAttempt> findByServiceId(final long serviceId) {
        List<ActivationAttempt> attempts = dao.findByServiceId(serviceId);
        boolean showInternalNotes = SecurityUtils.getSubject().isPermitted(Permissions.ORDER_WRITE);
        if (showInternalNotes) {
            attempts.forEach(attempt -> attempt.setIssueNotesDisplay(attempt.getIssueNotes()));
        }
        return attempts;
    }

    public List<ActivationAttempt> findForInventory(final long serviceId) {
        return dao.findByServiceId(serviceId);
    }

    public List<ActivationAttempt> findForDeletion(final long serviceId) {
        return dao.findByServiceId(serviceId);
    }


    /**
     * Finds an ActivationAttempt with the given ftdiAppointmentId.
     * @param ftdiAppointmentId Vendor appointment id
     * @return list of corresponding ActivationAttempts
     */
    public ActivationAttempt findByFtdiAppointmentId(final Long ftdiAppointmentId) {
        return dao.findByFtdiAppointmentId(ftdiAppointmentId);
    }

    /**
     * Finds all ActivationAttempts with the given serviceId.
     *
     * @param serviceId service id
     * @param tenantId  tenant id
     * @return list of corresponding ActivationAttempts
     */
    public List<ActivationAttempt> findByServiceIdAndTenant(final Long serviceId, final Long tenantId) {
        return dao.findByServiceIdAndTenant(serviceId, tenantId);
    }

    /**
     * Finds the ActivationAttempt with the given activationScheduleId.
     *
     * @param activationScheduleId activation schedule id
     * @return ActivationAttempt
     */
    public ActivationAttempt findByActivationScheduleId(final Long activationScheduleId) {
        return dao.findByActivationScheduleId(activationScheduleId);
    }

    /**
     * Finds all Activation Attempts scheduled today for the FTDI Checkin Quartz job.
     * @param tenantId
     * @return
     */
    public List<ActivationAttempt> findTodaysUnstartedAppointments(Long tenantId) {
        return dao.findTodaysUnstartedAppointments(tenantId);
    }

    public <X extends Service> ActivationAttempt cancel(final Long activationAttemptId, final boolean applySameDayCancelSurcharge) {
        ActivationAttempt attempt = retrieve(activationAttemptId);
        attempt.setCancelledDate(new Date());
        attempt.setCancelledBy("Vertek");
        attempt.setScheduledAttemptStatus("Cancelled");

        //update milestones of the related service
        List<ActivationAttempt> activationAttempts = findByServiceId(attempt.getServiceId());
        ActivationAttempt latestNonCancelledAttempt = null;
        if (activationAttempts.size() > 1) {
            // to get the last non-cancelled attempt
            latestNonCancelledAttempt
                    = activationAttempts.stream()
                    .filter(a -> (!a.getId().equals(attempt.getId())
                            && !a.getScheduledAttemptStatus().equals("Cancelled")))
                    .max(Comparator.comparing(ActivationAttempt::getScheduledCheckInTime))
                    .orElse(null);
        }

        // If the latest non-cancelled attempt is complete, do nothing but return the attempt
        if (latestNonCancelledAttempt != null && "Complete".equals(latestNonCancelledAttempt.getScheduledAttemptStatus())) {
            return edit(attempt);
        }

        //If the latest non-cancelled attempt is pending re-schedule, we need to reset the service status back to Installation Issue
        if (latestNonCancelledAttempt != null && latestNonCancelledAttempt.getScheduledAttemptStatus().contains("Pending Re-Schedule")) {
            Service s = serviceManager.retrieve(attempt.getServiceId());
            AbstractServiceManager<X> serviceManager
                    = (AbstractServiceManager<X>) serviceManagerFactory.getManager(
                    ServiceType.fromServiceName(s.getType()));
            X typedService = serviceManager.retrieve(s.getId());
            typedService.setStatus("Installation Issue");
            serviceManager.edit(typedService);
            ServiceMilestoneInstance activationRequested
                    = serviceMilestoneInstanceManager.retrieveCurrentMilestoneByCode(attempt.getServiceId(),
                    "ACTIVATION_REQUESTED");
            if (activationRequested != null) {
                activationRequested.setMilestoneDate(null);
                serviceMilestoneInstanceManager.editNoEventHandler(activationRequested);
            }
            ServiceMilestoneInstance activationScheduled
                = serviceMilestoneInstanceManager.retrieveCurrentMilestoneByCode(attempt.getServiceId(),
                "ACTIVATION_SCHEDULED");
            if (activationScheduled != null) {
                activationScheduled.setMilestoneDate(null);
                serviceMilestoneInstanceManager.editNoEventHandler(activationScheduled);
            }
            return edit(attempt);
        }

        String activationRequestedCode = "ACTIVATION_REQUESTED";
        ServiceMilestoneInstance activationRequested
                = serviceMilestoneInstanceManager.retrieveCurrentMilestoneByCode(attempt.getServiceId(),
                activationRequestedCode);
        if (activationRequested != null) {
            if (latestNonCancelledAttempt != null) {
                //set the date to the date of the latest non-cancelled attempt
                activationRequested.setMilestoneDate(latestNonCancelledAttempt.getCreatedDate());
            } else {
                //remove the milestone if there are no non-cancelled attempts
                activationRequested.setMilestoneDate(null);
            }
            serviceMilestoneInstanceManager.editNoEventHandler(activationRequested);
        } else if (latestNonCancelledAttempt != null && latestNonCancelledAttempt.getCreatedDate() != null) {
            //attempt records created before release 1.7.0 won't have a created date
            serviceMilestoneInstanceManager.create(
                    attempt.getServiceId(),
                    activationRequestedCode,
                    latestNonCancelledAttempt.getCreatedDate());
        }
        String activationScheduledCode = "ACTIVATION_SCHEDULED";
        ServiceMilestoneInstance activationScheduled
                = serviceMilestoneInstanceManager.retrieveCurrentMilestoneByCode(attempt.getServiceId(),
                activationScheduledCode);
        if (activationScheduled != null) {
            if (latestNonCancelledAttempt != null) {
                //set the date to the date of the latest non-cancelled attempt
                activationScheduled.setMilestoneDate(latestNonCancelledAttempt.getScheduledCheckInTime());
            } else {
                activationScheduled.setMilestoneDate(null);
            }
            serviceMilestoneInstanceManager.editNoEventHandler(activationScheduled);
        } else if (latestNonCancelledAttempt != null) {
            serviceMilestoneInstanceManager.create(
                    attempt.getServiceId(),
                    activationScheduledCode,
                    latestNonCancelledAttempt.getScheduledCheckInTime());
        }

        return edit(attempt);
    }


    public List<ActivationAttempt> findByAllByScheduleId(Long scheduleId) {
        return dao.findByAllByScheduleId(scheduleId);
    }

    public void checkDuplicateToRelated(ActivationAttempt attempt) {
        Service aaService = serviceManager.retrieve(attempt.getServiceId());
        List<Service> services = serviceManager.findByLocationId(aaService.getLocationId());
        if (services.size() > 1) {
            String activationRequestedCode = "ACTIVATION_REQUESTED";
            ServiceMilestoneInstance sourceActivationRequested = serviceMilestoneInstanceManager
                    .retrieveCurrentMilestoneByCode(aaService.getId(), activationRequestedCode);

            String activationCompleteCode = "ACTIVATION_COMPLETE";
            ServiceMilestoneInstance sourceActivationComplete = serviceMilestoneInstanceManager
                    .retrieveCurrentMilestoneByCode(aaService.getId(), activationCompleteCode);
            //get the activation schedule for the current service (the one that was just completed
            ActivationSchedule schedule = activationScheduleManager.retrieve(attempt.getActivationScheduleId());
            for (Service aService : services) {
                //skip for the current service and any service that is terminal
                if (aService.getId().equals(aaService.getId())
                        || TerminalServiceStatuses.getStatuses().contains(aService.getStatus())) {
                    continue;
                }

                //mark any activation attempts with the status of "Schedule Date Confirmed" or "In Progress" as complete - this skips validations and surcharges for these
                activationAttemptManager.findByServiceId(aService.getId()).stream()
                        .filter(aa -> Arrays.asList("Schedule Date Confirmed", "In Progress")
                                .contains(aa.getScheduledAttemptStatus()))
                        .forEach(aa -> {
                            aa.setScheduledAttemptStatus("Complete");
                            activationAttemptManager.edit(aa);
                        });

                //only add milestones for services that are not already requested/scheduled/complete
                ServiceMilestoneInstance activationRequested = serviceMilestoneInstanceManager
                        .retrieveCurrentMilestoneByCode(aService.getId(), activationRequestedCode);
                if (activationRequested == null && sourceActivationRequested != null) {
                    serviceMilestoneInstanceManager
                            .create(aService.getId(),
                                    activationRequestedCode,
                                    sourceActivationRequested.getMilestoneDate());
                }

                String activationScheduledCode = "ACTIVATION_SCHEDULED";
                ServiceMilestoneInstance activationScheduled = serviceMilestoneInstanceManager
                        .retrieveCurrentMilestoneByCode(aService.getId(), activationScheduledCode);
                if (activationScheduled == null) {
                    serviceMilestoneInstanceManager
                            .create(aService.getId(), activationScheduledCode, schedule.getRequestedDate());
                }

                ServiceMilestoneInstance activationComplete = serviceMilestoneInstanceManager
                        .retrieveCurrentMilestoneByCode(aService.getId(), activationCompleteCode);
                if (activationComplete == null) {
                    serviceMilestoneInstanceManager
                            .create(aService.getId(),
                                    activationCompleteCode,
                                    sourceActivationComplete.getMilestoneDate());
                }


            }
        }

    }
}
