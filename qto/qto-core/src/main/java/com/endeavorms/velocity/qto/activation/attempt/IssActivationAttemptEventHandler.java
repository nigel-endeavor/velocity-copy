package com.endeavorms.velocity.qto.activation.attempt;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.activation.issue.ActivationIssue;
import com.endeavorms.velocity.qto.activation.issue.ActivationIssueManager;
import com.endeavorms.velocity.qto.activation.requirement.ActivationAttemptRequirement;
import com.endeavorms.velocity.qto.activation.requirement.Requirement;
import com.endeavorms.velocity.qto.activation.requirement.RequirementTemplate;
import com.endeavorms.velocity.qto.activation.requirement.RequirementTemplateManager;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import com.endeavorms.velocity.qto.jeop.ServiceJeop;
import com.endeavorms.velocity.qto.jeop.ServiceJeopManager;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstanceManager;
import com.endeavorms.velocity.qto.note.NoteUnionView;
import com.endeavorms.velocity.qto.note.ServiceNote;
import com.endeavorms.velocity.qto.note.ServiceNoteManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.ServiceType;
import com.endeavorms.velocity.qto.service._4g5g.GService;
import com.endeavorms.velocity.qto.service._4g5g.GServiceManager;
import com.endeavorms.velocity.qto.service.broadband.BroadbandService;
import com.endeavorms.velocity.qto.service.broadband.BroadbandServiceManager;
import com.endeavorms.velocity.qto.service.dia.DiaService;
import com.endeavorms.velocity.qto.service.dia.DiaServiceManager;
import com.endeavorms.velocity.qto.service.ucaas.UcaasService;
import com.endeavorms.velocity.qto.service.ucaas.UcaasServiceManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author rcasey
 * @since 4/25/2023
 */
@Component
public class IssActivationAttemptEventHandler {

    protected static final Logger LOGGER = LoggerFactory.getLogger(IssActivationAttemptEventHandler.class);

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private BroadbandServiceManager broadbandServiceManager;

    @Inject
    private DiaServiceManager diaServiceManager;

    @Inject
    private UcaasServiceManager ucaasServiceManager;

    @Inject
    private GServiceManager gServiceManager;

    @Inject
    private ActivationIssueManager activationIssueManager;

    @Inject
    private ServiceJeopManager serviceJeopManager;

    @Inject
    private ServiceNoteManager serviceNoteManager;

    @Inject
    private ServiceMilestoneInstanceManager sMilestoneInstanceManager;

    @Inject
    private LocationManager locationManager;

    /**
     * Business logic for activation attempts.
     */
    @Inject
    private ActivationAttemptManager activationAttemptManager;

    /**
     * Company Manager.
     */
    @Inject
    private CompanyManager companyManager;

    /**
     * Company Config Manager.
     */
    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> companyConfigManager;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private RequirementTemplateManager requirementTemplateManager;

    public ActivationAttempt handlePushIncomplete(final ActivationAttempt attempt) throws ActivationAttemptPushValidationException {
        //validations
        List<String> errors = new ArrayList<>();
        //At least 1 PST entered
        List<ActivationIssue> activationIssues = activationIssueManager.findByAttemptId(attempt.getId());
        if (activationIssues.isEmpty()) {
            errors.add("This activation has no activation issues");
        } else {
            for (ActivationIssue issue : activationIssues) {
                if (Strings.isNullOrEmpty(issue.getTertiaryRootCause())) {
                    errors.add("Tertiary Root Cause must be populated");
                    break;
                }
            }
        }
        //Network Complete Date and VoIP Complete Date Must be Blank
        if (attempt.getNetworkCompleteDate() != null) {
            errors.add("Network Complete Date must be blank");
        }
        if (attempt.getVoipCompleteDate() != null) {
            errors.add("UCaaS Complete Date must be blank");
        }
        //A close code must be populated
        if (Strings.isNullOrEmpty(attempt.getCloseoutCode())) {
            errors.add("Closeout Code must be populated");
        }
        //FT Check-out time must be populated
        if (attempt.getFieldTechCheckOut() == null) {
            errors.add("FT Check-out Time must be populated");
        }
        if (!errors.isEmpty()) {
            throw new ActivationAttemptPushValidationException(errors);
        }
        ActivationAttempt updated = activationAttemptManager.edit(attempt);

        performPushPartialOrIncompleteActions(updated, activationIssues);
        return updated;
    }

    public ActivationAttempt handlePushPartial(final ActivationAttempt attempt) throws ActivationAttemptPushValidationException {
        //validations
        List<String> errors = new ArrayList<>();
        //At least 1 PST entered
        List<ActivationIssue> activationIssues = activationIssueManager.findByAttemptId(attempt.getId());
        if (activationIssues.isEmpty()) {
            errors.add("This activation has no activation issues");
        } else {
            for (ActivationIssue issue : activationIssues) {
                if (issue.getTertiaryRootCause() == null) {
                    errors.add("Tertiary Root Cause must be populated");
                    break;
                }
            }
        }
        //Modem mac address must be filled in for broadband
        Service service = serviceManager.retrieve(attempt.getServiceId());
        if ("Broadband".equals(service.getType()) && Strings.isNullOrEmpty(((BroadbandService) service).getMacAddress())) {
            errors.add("Modem MAC must be populated");
        }
        //Router Serial Number must be filled in for DIA
        if ("DIA".equals(service.getType()) && Strings.isNullOrEmpty(((DiaService) service).getRouterSerialNumber())) {
            errors.add("Router Serial Number must be populated");
        }
        //Tested Download Speed must be filled in
        if (Strings.isNullOrEmpty(attempt.getTestedDownloadSpeed())) {
            errors.add("Tested Download Speed must be populated");
        }
        //Tested Upload Speed must be filled in
        if (Strings.isNullOrEmpty(attempt.getTestedUploadSpeed())) {
            errors.add("Tested Upload Speed must be populated");
        }
        //A close Code Must be populated
        if (Strings.isNullOrEmpty(attempt.getCloseoutCode())) {
            errors.add("Closeout Code must be populated");
        }
        //FT Check-out time must be populated
        if (attempt.getFieldTechCheckOut() == null) {
            errors.add("FT Check-out Time must be populated");
        }
        if (!errors.isEmpty()) {
            throw new ActivationAttemptPushValidationException(errors);
        }
        ActivationAttempt updated = activationAttemptManager.edit(attempt);

        performPushPartialOrIncompleteActions(updated, activationIssues);
        return updated;
    }

    public ActivationAttempt handlePushComplete(final ActivationAttempt attempt) throws ActivationAttemptPushValidationException {
        if (sMilestoneInstanceManager.doesMilestoneExist(attempt.getServiceId(), "ACTIVATION_COMPLETE")) {
            return attempt;
        }
        //validations
        List<String> errors = new ArrayList<>();
        //"Replace 4G/5G with BB / DIA" Field must be filled in
        if (Strings.isNullOrEmpty(attempt.getReplace4g5g())) {
            errors.add("Replace 4G/5G with Broadband/DIA must be populated");
        }
        //Modem MAC address must be filled in
        Service service = serviceManager.retrieve(attempt.getServiceId());
        Location location = locationManager.retrieve(service.getLocationId());
        if ("Broadband".equals(service.getType()) && Strings.isNullOrEmpty(((BroadbandService) service).getMacAddress())) {
            errors.add("Modem MAC Address must be populated");
        }
        if ("DIA".equals(service.getType()) && Strings.isNullOrEmpty(((DiaService) service).getRouterMacAddress())) {
            errors.add("Router MAC Address must be populated");
        }
        //Tested Download Speed must be filled in
        if (Strings.isNullOrEmpty(attempt.getTestedDownloadSpeed())) {
            errors.add("Tested Download Speed must be populated");
        }
        //Tested Upload Speed must be filled in
        if (Strings.isNullOrEmpty(attempt.getTestedUploadSpeed())) {
            errors.add("Tested Upload Speed must be populated");
        }
        //A close Code must be populated
        if (Strings.isNullOrEmpty(attempt.getCloseoutCode())) {
            errors.add("Closeout Code must be populated");
        }
        //"Network Complete" and "UCaaS Complete" (property is called voipCompleteDate) must be filled in
        if (attempt.getNetworkCompleteDate() == null) {
            errors.add("Network Complete Date must populated");
        }
        boolean serviceHasUcaas = location.getServices().stream().anyMatch(s -> ServiceType.UCAAS.getServiceName().equals(s.getType()));
        if (attempt.getVoipCompleteDate() == null && serviceHasUcaas) {
            errors.add("UCaaS Complete Date must be populated");
        }
        //FT Check-out time must be populated
        if (attempt.getFieldTechCheckOut() == null) {
            errors.add("FT Check-out Time must be populated");
        }
        //If requirements exist, all must be set to "complete"
        for (ActivationAttemptRequirement requirement : attempt.getRequirements()) {
            if (!requirement.isComplete()) {
                errors.add("This activation has incomplete requirements");
                break;
            }
        }
        //location level of effort must be set
        if (Strings.isNullOrEmpty(location.getLevelOfEffort())) {
            errors.add("Level of Effort must be populated");
        }
        if (!errors.isEmpty()) {
            throw new ActivationAttemptPushValidationException(errors);
        }
        ActivationAttempt updated = activationAttemptManager.edit(attempt);

        //Create a Journal Note Based on Root causes, Jeops, TTU Requirements, close code, and entered notes
        List<ActivationIssue> issues = activationIssueManager.findByAttemptId(updated.getId());
        createNote(updated, issues, service.getTenantId());

        //Closes all open jeops with today's date
        List<ServiceJeop> jeops = serviceJeopManager.getOpen(updated.getServiceId());
        for (ServiceJeop jeop : jeops) {
            jeop.setEndDate(new Date());
            serviceJeopManager.edit(jeop);
        }


        //actions
        //Set service activation complete date to today
        //Sets service complete milestone and status to complete (driven by event handler when this milestone is set)
        sMilestoneInstanceManager.create(updated.getServiceId(), "ACTIVATION_COMPLETE", new Date());
        return updated;
    }

    /**
     * Push logic for partial and incomplete ActivationAttempts.
     *
     * @param attempt          ActivationAttempt.
     * @param activationIssues List of ActivationIssues.
     */
    private void performPushPartialOrIncompleteActions(final ActivationAttempt attempt, final List<ActivationIssue> activationIssues) {
        //Create service Jeop(s) & Jeop Note(s)  based on Root Cause Mapping
        createJeops(attempt.getServiceId(), activationIssues);

        //check to see if there are any Scheduled activation attempts
         //update milestones of the related service
        List<ActivationAttempt> activationAttempts = activationAttemptManager.findByServiceId(attempt.getServiceId());
        ActivationAttempt latestScheduledAttempt = null;
        if (activationAttempts.size() > 1) {
            // to get the last non-cancelled attempt
            latestScheduledAttempt
                    = activationAttempts.stream()
                    .filter(a -> (!a.getId().equals(attempt.getId())
                            && a.getScheduledAttemptStatus().equals("Schedule Date Confirmed")))
                    .max(Comparator.comparing(ActivationAttempt::getScheduledCheckInTime))
                    .orElse(null);
        }

        ServiceMilestoneInstance activationRequested = sMilestoneInstanceManager.retrieveCurrentMilestoneByCode(attempt.getServiceId(), "ACTIVATION_REQUESTED");
        ServiceMilestoneInstance activationScheduled = sMilestoneInstanceManager.retrieveCurrentMilestoneByCode(attempt.getServiceId(), "ACTIVATION_SCHEDULED");

        if (latestScheduledAttempt != null) {
            //update the milestones to the latest scheduled attempt dates
            //attempt records created before release 1.7.0 won't have a created date
            if (latestScheduledAttempt.getCreatedDate() != null) {
                if (activationRequested != null) {
                    activationRequested.setMilestoneDate(latestScheduledAttempt.getCreatedDate());
                    sMilestoneInstanceManager.editNoEventHandler(activationRequested);
                } else {
                    sMilestoneInstanceManager.create(
                            attempt.getServiceId(),
                            "ACTIVATION_REQUESTED",
                            latestScheduledAttempt.getCreatedDate());
                }
            }
            if (activationScheduled != null) {
                activationScheduled.setMilestoneDate(latestScheduledAttempt.getScheduledCheckInTime());
                sMilestoneInstanceManager.editNoEventHandler(activationScheduled);
            } else {
                sMilestoneInstanceManager.create(
                        attempt.getServiceId(),
                        "ACTIVATION_SCHEDULED",
                        latestScheduledAttempt.getCreatedDate());
            }

        } else {
            //Set Activation Requested and Activation scheduled fields to Blank
            if (activationScheduled != null) {
                activationScheduled.setMilestoneDate(null);
                sMilestoneInstanceManager.editNoEventHandler(activationScheduled);
            }

            if (activationRequested != null) {
                activationRequested.setMilestoneDate(null);
                sMilestoneInstanceManager.editNoEventHandler(activationRequested);
            }
        }

        //Set service status to "Installation issue"
        Service service = serviceManager.retrieve(attempt.getServiceId());
        service.setStatus("Installation Issue");

        //Create a Journal Note Based on Root causes, Jeops, TTU Requirements, close code, and entered notes
        createNote(attempt, activationIssues, service.getTenantId());
        if ("Broadband".equals(service.getType())) {
            broadbandServiceManager.edit((BroadbandService) service);
        } else if ("DIA".equals(service.getType())) {
            diaServiceManager.edit((DiaService) service);
        } else if ("UCaaS".equals(service.getType())) {
            ucaasServiceManager.edit((UcaasService) service);
        } else if ("4G/5G".equals(service.getType())) {
            gServiceManager.edit((GService) service);
        } else {
            LOGGER.error("Unexpected Service Type: {}, cannot set status on service: {}", service.getType(), service.getId());
        }

        //Copy activation test results to future attempts
        List<ActivationAttempt> attempts = activationAttemptManager.findByServiceId(attempt.getServiceId());
        for (ActivationAttempt a : attempts) {
            if (Objects.equals(a.getId(), attempt.getId())) {
                break;
            }
            a.setTestedDownloadSpeed(attempt.getTestedDownloadSpeed());
            a.setTestedUploadSpeed(attempt.getTestedUploadSpeed());
            a.setLatency(attempt.getLatency());
            a.setBackupDownloadSpeed(attempt.getBackupDownloadSpeed());
            a.setBackupUploadSpeed(attempt.getBackupUploadSpeed());
            a.setSignalRsrp(attempt.getSignalRsrp());
            a.setSinrRsrq(attempt.getSinrRsrq());
            a.setPrimaryUid(attempt.getPrimaryUid());
            a.setSecondaryUid(attempt.getSecondaryUid());
            a.setManagedRouterSerialNumber(attempt.getManagedRouterSerialNumber());
            a.setLocationDowntownForCutover(attempt.getLocationDowntownForCutover());
            a.setCloseoutCode(attempt.getCloseoutCode());
            activationAttemptManager.edit(a);

            //copy requirements to future attempts
            Location location = locationManager.retrieve(service.getLocationId());
            if (location.getRequirementTemplateId() != null) {
                RequirementTemplate requirementTemplate = requirementTemplateManager.retrieve(location.getRequirementTemplateId());
                if (requirementTemplate != null) {
                    for (Requirement requirement : requirementTemplate.getRequirements()) {
                        ActivationAttemptRequirement existingRequirement = a.getRequirements().stream().filter(r -> Objects.equals(r.getRequirement().getId(), requirement.getId())).findFirst().orElse(null);
                        if (existingRequirement == null) {
                            ActivationAttemptRequirement prevRequirement = null;
                            if (attempt.getRequirements() != null) {
                                prevRequirement = attempt.getRequirements().stream().filter(r -> Objects.equals(r.getRequirement().getId(), requirement.getId())).findFirst().orElse(null);
                            }
                            ActivationAttemptRequirement aaRequirement = new ActivationAttemptRequirement();
                            aaRequirement.setRequirement(requirement);
                            aaRequirement.setActivationAttemptId(a.getId());
                            if (prevRequirement != null) {
                                aaRequirement.setComplete(prevRequirement.isComplete());
                            }
                            a.getRequirements().add(aaRequirement);
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates ServiceJeops based on list of ActivationIssues.
     * Contains Activation to jeop mapping.
     *
     * @param serviceId Service Id.
     * @param issues    List of ActivationIssues.
     */
    private void createJeops(final Long serviceId, final List<ActivationIssue> issues) {
        ServiceJeop jeop = new ServiceJeop();
        jeop.setServiceId(serviceId);
        jeop.setStartDate(new Date());
        jeop.setOriginator(SecurityUtils.getLoggedInUser());

        for (ActivationIssue issue : issues) {
            switch (issue.getPrimaryRootCause()) {
                case "Customer":
                    jeop.setResponsibility("End Customer");
                    switch (issue.getSecondaryRootCause()) {
                        case "Access":
                            jeop.setDescription("No Access");
                            break;
                        case "Other":
                            jeop.setDescription("Other");
                            break;
                        case "Infrastructure":
                            switch (issue.getTertiaryRootCause()) {
                                case "Inside Wiring":
                                    jeop.setDescription("Inside Wiring");
                                    break;
                                case "Power Supply":
                                    jeop.setDescription("Electrical Power");
                                    break;
                                case "Site Not Ready":
                                    jeop.setDescription("Customer Not Ready");
                                    break;
                                default:
                                    LOGGER.error("No jeop mapping exists for activation issue tertiary root cause: {}. activationIssueId: {}, activationId: {}, serviceId: {}",
                                            issue.getTertiaryRootCause(), issue.getId(), issue.getActivationAttemptId(), serviceId);
                            }
                            break;
                        default:
                            LOGGER.error("No jeop mapping exists for activation issue secondary root cause: {}. activationIssueId: {}, activationId: {}, serviceId: {}",
                                    issue.getSecondaryRootCause(), issue.getId(), issue.getActivationAttemptId(), serviceId);
                    }
                    break;
                case "Equipment":
                    jeop.setResponsibility("Client");
                    //secondary root cause does not need to be checked here
                    switch (issue.getTertiaryRootCause()) {
                        case "Defective":
                        case "Broken":
                            jeop.setDescription("Equipment Defective");
                            break;
                        case "Not On Site":
                            jeop.setDescription("Equipment Missing");
                            break;
                        default:
                            LOGGER.error("No jeop mapping exists for activation issue tertiary root cause: {}. activationIssueId: {}, activationId: {}, serviceId: {}",
                                    issue.getTertiaryRootCause(), issue.getId(), issue.getActivationAttemptId(), serviceId);
                    }
                    break;
                case "Provider":
                    jeop.setResponsibility("Provider");
                    switch (issue.getSecondaryRootCause()) {
                        case "Circuit":
                            switch (issue.getTertiaryRootCause()) {
                                case "Bouncing":
                                case "Incorrectly Installed":
                                case "No Sync":
                                    jeop.setDescription("Circuit Test Failure");
                                    break;
                                case "Not Installed":
                                    jeop.setDescription("Service not Installed");
                                    break;
                                case "Tag & Locate":
                                    jeop.setDescription("Facilities Issue");
                                    break;
                                default:
                                    LOGGER.error("No jeop mapping exists for activation issue tertiary root cause: {}. activationIssueId: {}, activationId: {}, serviceId: {}",
                                            issue.getTertiaryRootCause(), issue.getId(), issue.getActivationAttemptId(), serviceId);
                            }
                            break;
                        case "Modem":
                            switch (issue.getTertiaryRootCause()) {
                                case "Config":
                                    jeop.setDescription("Equipment Configuration");
                                    break;
                                case "Defective":
                                    jeop.setDescription("Equipment Defective");
                                    break;
                                case "Surf":
                                    jeop.setDescription("Circuit Test Failure");
                                    break;
                                default:
                                    LOGGER.error("No jeop mapping exists for activation issue tertiary root cause: {}. activationIssueId: {}, activationId: {}, serviceId: {}",
                                            issue.getTertiaryRootCause(), issue.getId(), issue.getActivationAttemptId(), serviceId);
                            }
                            break;
                        case "Other":
                            jeop.setDescription("Provider Other");
                            break;
                        default:
                            LOGGER.error("No jeop mapping exists for activation issue secondary root cause: {}. activationIssueId: {}, activationId: {}, serviceId: {}",
                                    issue.getSecondaryRootCause(), issue.getId(), issue.getActivationAttemptId(), serviceId);
                    }
                    break;
                case "Systems":
                    jeop.setResponsibility("Project Manager");
                    jeop.setDescription("Equipment Configuration");
                    break;
                case "Tech":
                    jeop.setResponsibility("Project Manager");
                    switch (issue.getSecondaryRootCause()) {
                        case "Competency":
                            jeop.setDescription("Technical Abilities");
                            break;
                        case "Tech Tools":
                            jeop.setDescription("Technician Tools");
                            break;
                        case "Other":
                            jeop.setDescription("Technician Other");
                            break;
                        default:
                            LOGGER.error("No jeop mapping exists for activation issue secondary root cause: {}. activationIssueId: {}, activationId: {}, serviceId: {}",
                                    issue.getSecondaryRootCause(), issue.getId(), issue.getActivationAttemptId(), serviceId);
                    }
                    break;
                default:
                    LOGGER.error("No jeop mapping exists for activation issue primary root cause: {}. activationIssueId: {}, activationId: {}, serviceId: {}",
                            issue.getPrimaryRootCause(), issue.getId(), issue.getActivationAttemptId(), serviceId);
            }
            if (!Strings.isNullOrEmpty(jeop.getDescription())) {
                serviceJeopManager.create(jeop);
            }
        }
    }

    private void createNote(final ActivationAttempt attempt, final List<ActivationIssue> issues, final Long tenantId) {
        StringBuilder rootCauses = new StringBuilder();
        for (ActivationIssue issue : issues) {
            rootCauses.append(issue.getPrimaryRootCause()).append("/").append(issue.getSecondaryRootCause()).append("/").append(issue.getTertiaryRootCause()).append("\n");
        }
        StringBuilder jeopSting = new StringBuilder();
        List<ServiceJeop> jeops = serviceJeopManager.getOpen(attempt.getServiceId());
        for (ServiceJeop jeop : jeops) {
            jeopSting.append(jeop.getDescription()).append("\n");
        }
        StringBuilder requirementString = new StringBuilder();
        for (ActivationAttemptRequirement requirement : attempt.getRequirements()) {
            requirementString.append(requirement.getRequirement().getLookupValue().getValue()).append(": ").append(requirement.isComplete() ? "Complete" : "Incomplete").append("\n");
        }
        StringBuilder activationNotes = new StringBuilder();
        List<NoteUnionView> serviceNotes = serviceNoteManager.getActivationNotes(attempt.getServiceId());
        for (NoteUnionView note : serviceNotes) {
            activationNotes.append(note.getNote()).append("\n");
        }
        String noteBody = "Timeline & Turnup Comments:\n"
                + "Closeout Code: " + attempt.getCloseoutCode() + "\n"
                + "Status: " + attempt.getScheduledAttemptStatus() + "\n"
                + "Root Causes: \n" + rootCauses
                + "Jeopardies:" + "\n" + jeopSting
                + "Requirements:" + "\n" + requirementString
                + "Activation Notes:" + "\n" + activationNotes;

        ServiceNote note = new ServiceNote();
        note.setServiceId(attempt.getServiceId());
        note.setNote(noteBody);
        Service service = serviceManager.retrieve(attempt.getServiceId());
        note.setCategory(service.getType());
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        note.setCreatedBy(subject == null ? username : subject.getDisplayName());
        note.setCreatedById(subject.getId());
        note.setCreatedDate(new Date());
        serviceNoteManager.create(note);

//        if (!Strings.isNullOrEmpty(attempt.getFtdiVendorId())) {
//            sendNoteToFtdi(noteBody, attempt.getFtdiDispatchId(), tenantId);
//        }
    }

    public ActivationAttempt rollback(final Long activationAttemptId) {
        ActivationAttempt attempt = activationAttemptManager.retrieve(activationAttemptId);
        ServiceMilestoneInstance activationRequested = sMilestoneInstanceManager
                .retrieveCurrentMilestoneByCode(attempt.getServiceId(), "ACTIVATION_REQUESTED");
        ServiceMilestoneInstance activationScheduled = sMilestoneInstanceManager
                .retrieveCurrentMilestoneByCode(attempt.getServiceId(), "ACTIVATION_SCHEDULED");
        ServiceMilestoneInstance activationComplete = sMilestoneInstanceManager
                .retrieveCurrentMilestoneByCode(attempt.getServiceId(), "ACTIVATION_COMPLETE");
        ServiceMilestoneInstance complete = sMilestoneInstanceManager
                .retrieveCurrentMilestoneByCode(attempt.getServiceId(), "COMPLETE");


        //refactor to only roll back items that were completed by this attempt.
        //this logic will roll back items with the same complete date as the attempt
        if (attempt.isDuplicateToRelated()
                && ("Complete".equals(attempt.getScheduledAttemptStatus()))
                && (complete != null)) {
            Service aaService = serviceManager.retrieve(attempt.getServiceId());
            List<Service> services = serviceManager.findByLocationId(aaService.getLocationId());
            if (services.size() > 1) {
                for (Service rService : services) {
                    //skip for the current service and any service that is terminal
                    if (rService.getId().equals(aaService.getId())
                            || !"Service Complete".equalsIgnoreCase(rService.getStatus())) {
                        continue;
                    }

                    //only rollback services that have the same date as the attempt
                    ServiceMilestoneInstance rComplete = sMilestoneInstanceManager
                            .retrieveCurrentMilestoneByCode(rService.getId(), "COMPLETE");

                    ServiceMilestoneInstance rActivationRequested = sMilestoneInstanceManager
                            .retrieveCurrentMilestoneByCode(rService.getId(), "ACTIVATION_REQUESTED");
                    if (rActivationRequested != null
                            && rActivationRequested.getMilestoneDate().compareTo(activationRequested.getMilestoneDate()) == 0
                            && rComplete.getMilestoneDate().compareTo(complete.getMilestoneDate()) == 0) {
                        rActivationRequested.setMilestoneDate(null);
                        sMilestoneInstanceManager.edit(rActivationRequested);
                    }

                    ServiceMilestoneInstance rActivationScheduled = sMilestoneInstanceManager
                            .retrieveCurrentMilestoneByCode(rService.getId(), "ACTIVATION_SCHEDULED");
                    if (rActivationScheduled != null
                            && rActivationScheduled.getMilestoneDate().compareTo(activationScheduled.getMilestoneDate()) == 0
                            && rComplete.getMilestoneDate().compareTo(complete.getMilestoneDate()) == 0) {
                        rActivationScheduled.setMilestoneDate(null);
                        sMilestoneInstanceManager.edit(rActivationScheduled);
                    }

                    ServiceMilestoneInstance rActivationComplete = sMilestoneInstanceManager
                            .retrieveCurrentMilestoneByCode(rService.getId(), "ACTIVATION_COMPLETE");
                    if (rActivationComplete != null
                            && rActivationComplete.getMilestoneDate().compareTo(activationComplete.getMilestoneDate()) == 0
                            && rComplete.getMilestoneDate().compareTo(complete.getMilestoneDate()) == 0) {
                        rActivationComplete.setMilestoneDate(null);
                        sMilestoneInstanceManager.edit(rActivationComplete);
                    }

                    if (rComplete != null
                            && rComplete.getMilestoneDate().compareTo(complete.getMilestoneDate()) == 0) {
                        rComplete.setMilestoneDate(null);
                        sMilestoneInstanceManager.edit(rComplete);
                        //find the activation attempt and roll back the status
                        activationAttemptManager.findByServiceId(rService.getId()).stream()
                            .filter(aa -> Arrays.asList("Complete")
                                    .contains(aa.getScheduledAttemptStatus()))
                            .forEach(aa -> {
                                aa.setScheduledAttemptStatus("Schedule Date Confirmed");
                                activationAttemptManager.edit(aa);
                            });
                    }
                }
            }
        }

        //create rollback note
        String noteBody = "Rollback of Activation Attempt from status of " + attempt.getScheduledAttemptStatus();
        ServiceNote note = new ServiceNote();
        note.setNote(noteBody);
        note.setServiceId(attempt.getServiceId());
        note.setCategory("Activation");
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        note.setCreatedBy(subject == null ? username : subject.getDisplayName());
        note.setCreatedById(subject.getId());
        note.setCreatedDate(new Date());
        serviceNoteManager.create(note);

//        if (attempt.getFtdiVendorId() != null && complete != null) {
//            sendNoteToFtdi(noteBody, attempt.getFtdiDispatchId(), attempt.getTenantId());
//        }

        //remove the dates from the attempt
        if (activationComplete != null) {
            activationComplete.setMilestoneDate(null);
            sMilestoneInstanceManager.edit(activationComplete);
        }
        if (complete != null) {
            complete.setMilestoneDate(null);
            sMilestoneInstanceManager.edit(complete);
        }


        attempt.setScheduledAttemptStatus("In Progress");
        attempt =  activationAttemptManager.edit(attempt);
        return attempt;
    }

//    public String sendNoteToFtdi(final String note, final Long dispatchId, final Long tenantId) {
//
//        Company company = companyManager.getCompanyIdForTenant(tenantId);
//        String url = companyConfigManager.getString(company.getId(), CompanyConfigKey.ENDEAVOR_PROXY_URL);
//        url = url + "notes/?dispatchId=" + dispatchId + "&tenantId=" + tenantId;
//
//
//        SSLContext sslContext = null;
//        try {
//            sslContext = SSLContexts.custom().loadTrustMaterial(null,
//                            (TrustStrategy) (chain, authType) -> true)
//                    .build();
//            HttpPost request = new HttpPost(url);
//            request.setEntity(new StringEntity(note, ContentType.APPLICATION_JSON));
//            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
//
//
//            CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(sslsf).build();
//            CloseableHttpResponse response = httpClient.execute(request);
//            System.out.println(response.getStatusLine().getStatusCode());
//
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                // return it as a String
//                String result = EntityUtils.toString(entity);
//                System.out.println(result);
//            }
//
//        } catch (ClientProtocolException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (KeyStoreException e) {
//            throw new RuntimeException(e);
//        } catch (KeyManagementException e) {
//            throw new RuntimeException(e);
//        }
//
//        return "Done";
//    }
}
