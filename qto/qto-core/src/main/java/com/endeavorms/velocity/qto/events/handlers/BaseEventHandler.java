package com.endeavorms.velocity.qto.events.handlers;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.quartz.ScheduledJobUtil;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertiesDto;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import com.endeavorms.velocity.qto.costhistory.CostHistoryManager;
import com.endeavorms.velocity.qto.dispute.DisputeManager;
import com.endeavorms.velocity.qto.interval.IntervalType;
import com.endeavorms.velocity.qto.interval.IntervalTypeManager;
import com.endeavorms.velocity.qto.interval.ServiceIntervalInstanceManager;
import com.endeavorms.velocity.qto.interval.jms.IntervalMessage;
import com.endeavorms.velocity.qto.interval.jms.IntervalQueueHandler;
import com.endeavorms.velocity.qto.inventory.PendingDisconnect;
import com.endeavorms.velocity.qto.inventory.PendingDisconnectManager;
import com.endeavorms.velocity.qto.jeop.LocationJeop;
import com.endeavorms.velocity.qto.jeop.LocationJeopManager;
import com.endeavorms.velocity.qto.jeop.OrderJeopManager;
import com.endeavorms.velocity.qto.jeop.ServiceJeop;
import com.endeavorms.velocity.qto.jeop.ServiceJeopManager;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.location.TerminalLocationStatuses;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstanceManager;
import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySet;
import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySetInclude;
import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySetManager;
import com.endeavorms.velocity.qto.milestone.MilestoneInstance;
import com.endeavorms.velocity.qto.milestone.OrderMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.OrderMilestoneInstanceManager;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstanceManager;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.order.TerminalOrderStatuses;
import com.endeavorms.velocity.qto.service.AbstractServiceManager;
import com.endeavorms.velocity.qto.service.OrderType;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.ServiceManagerFactory;
import com.endeavorms.velocity.qto.service.ServiceType;
import com.endeavorms.velocity.qto.service.TerminalServiceStatuses;
import com.endeavorms.velocity.qto.service.macd.request.MacdDto;
import com.endeavorms.velocity.qto.service.macd.request.MacdRequestDto;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Event Handler.
 *
 * @author fcurran
 * @since 1/31/2023
 */
@Component
public class BaseEventHandler {

    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEventHandler.class);

    /**
     * Service Milestone manager.
     */
    @Inject
    private ServiceMilestoneInstanceManager sMilestoneManager;

    /**
     * Location Milestone Manager.
     */
    @Inject
    private LocationMilestoneInstanceManager lMilestoneManager;

    /**
     * Order Milestone Manager.
     */
    @Inject
    private OrderMilestoneInstanceManager oMilestoneManager;

    @Inject
    private MilestoneDisplaySetManager milestoneDisplaySetManager;

    /**
     * Service Jeop manager.
     */
    @Inject
    private ServiceJeopManager sJeopManager;

    /**
     * Location Jeop Manager.
     */
    @Inject
    private LocationJeopManager lJeopManager;


    /**
     * Order Jeop Manager.
     */
    @Inject
    private OrderJeopManager oJeopManager;

    @Inject
    private ServiceManager serviceManager;

    /**
     * Manager for getting the properly injected manager for a given service type.
     */
    @Inject
    private ServiceManagerFactory serviceManagerFactory;

    /**
     * Service Manager.
     */
    @Inject
    private LocationManager locationManager;

    /**
     * Service Manager.
     */
    @Inject
    private OrderManager orderManager;

    /**
     * Interval Type manager.
     */
    @Inject
    private IntervalTypeManager intervalTypeManager;

    /**
     * Interval Instance manager.
     */
    @Inject
    private ServiceIntervalInstanceManager sIntervalInstanceManager;

    /**
     * Handles sending messages to interval queue.
     */
    @Inject
    private IntervalQueueHandler intervalQueueHandler;

    /**
     * Subject Manager.
     */
    @Inject
    private SubjectManager subjectManager;

    /**
     * Pending Disconnect Manager.
     */
    @Inject
    private PendingDisconnectManager pendingDisconnectManager;

    /**
     * Cost History Manager.
     */
    @Inject
    private CostHistoryManager costHistoryManager;

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> configPropertyManager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private DisputeManager disputeManager;

    /**
     * Method to handle milestone event by code.
     *
     * @param milestoneInstance the milestone instance.
     * @param order             the order.
     */
    public void handleMilestoneEvent(final OrderMilestoneInstance milestoneInstance, final Order order) {
        if (milestoneInstance.getMilestone().getCode().equalsIgnoreCase("CREATED")) {
            order.setStatus("Order Received");
        } else if (milestoneInstance.getMilestone().getCode().equalsIgnoreCase("ON_HOLD")) {
            order.setStatus("On Hold");
            List<Location> locations = locationManager.findbyOrderId(order.getId());
            for (Location location : locations) {
                if (!lMilestoneManager.doesMilestoneExist(location.getId(), "ON_HOLD")) {
                    lMilestoneManager.create(location.getId(), "ON_HOLD", milestoneInstance.getMilestoneDate());
                }
                List<Service> services = serviceManager.findByLocationId(location.getId());
                for (Service service : services) {
                    if (!sMilestoneManager.doesMilestoneExist(service.getId(), "ON_HOLD")) {
                        sMilestoneManager.create(service.getId(), "ON_HOLD", milestoneInstance.getMilestoneDate());
                    }
                }
            }
        }
    }

    /**
     * Method to handle milestone event by code.
     *
     * @param milestoneInstance the milestone instance.
     * @param location          the location.
     */
    public <X extends Service> void handleMilestoneEvent(final LocationMilestoneInstance milestoneInstance, final Location location) {
        //if the incoming milestone is a terminal milestone, and there is an existing ON_HOLD milestone - remove the ON_HOLD milestone
        List<String> terminalMilestones = Arrays.asList("CHANGE_IN_ASSIGNMENT", "COMPLETED", "CANCELLED");
        if (terminalMilestones.contains(milestoneInstance.getMilestone().getCode()) && lMilestoneManager.doesMilestoneExist(location.getId(), "ON_HOLD")) {
            LocationMilestoneInstance onHold = lMilestoneManager.retrieveCurrentMilestoneByCode(location.getId(), "ON_HOLD");
            lMilestoneManager.remove(onHold.getId());
        }
        String status = getLocationStatus(location.getId());
        String globalServiceStatus = "";
        boolean updateServices = false;
        String code = milestoneInstance.getMilestone().getCode();
        if ("ENGINEER_ASSIGNED".equals(code)) {
            updateServices = true;
        } else if ("ON_HOLD".equals(code)) {
            if (milestoneInstance.getMilestoneDate() == null) {
                updateServices = true;
            } else {
                globalServiceStatus = "On Hold";
                List<LocationJeop> jeops = lJeopManager.getOpen(location.getId());
                for (LocationJeop jeop : jeops) {
                    if ("customer requested delay".equalsIgnoreCase(jeop.getDescription())) {
                        status = "Customer Requested Delay";
                        break;
                    } else if ("customer unresponsive".equalsIgnoreCase(jeop.getDescription())) {
                        status = "Customer Unresponsive";
                        break;
                    }
                }
            }
        } else if ("CHANGE_IN_ASSIGNMENT".equals(milestoneInstance.getMilestone().getCode())) {
            if (milestoneInstance.getMilestoneDate() == null) {
                updateServices = true;
            } else {
                globalServiceStatus = "Change In Assignment";
            }
        } else if ("CANCELLED".equals(milestoneInstance.getMilestone().getCode())) {
            if (milestoneInstance.getMilestoneDate() == null) {
                updateServices = true;
            } else {
                globalServiceStatus = "Service Cancelled";
            }
        }
        // if a milestone was cleared and we need to update related services
        // or if the milestone being set impacts service status and progress
        if (updateServices || !Strings.isNullOrEmpty(globalServiceStatus)) {
            Map<Long, String> serviceTypeMap = location.getServices().stream()
                    .collect(Collectors.toMap(Service::getId, Service::getType));
            for (Long serviceId : serviceTypeMap.keySet()) {
                AbstractServiceManager<X> serviceManager
                        = (AbstractServiceManager<X>) serviceManagerFactory.getManager(
                        ServiceType.fromServiceName(serviceTypeMap.get(serviceId)));
                X typedService = serviceManager.retrieve(serviceId);
                if (typedService.getLocationId().equals(location.getId())) {
                    if ("ON_HOLD".equals(code) || "CHANGE_IN_ASSIGNMENT".equals(code) || "CANCELLED".equals(code)
                            || "ENGINEER_ASSIGNED".equals(code)) {
                        if (!TerminalServiceStatuses.getStatuses().contains(typedService.getStatus())) {
                            ServiceMilestoneInstance smi = sMilestoneManager.retrieveCurrentMilestoneByCode(typedService.getId(), code);
                            if (milestoneInstance.getMilestoneDate() == null && smi != null) {
                                sMilestoneManager.remove(smi.getId());
                            } else if (milestoneInstance.getMilestoneDate() != null && smi == null) {
                                sMilestoneManager.create(typedService.getId(), code, milestoneInstance.getMilestoneDate());
                            }
                        }
                    }
                    if (updateServices) {
                        setServiceStatus(typedService);
                    } else {
                        if ("On Hold".equals(globalServiceStatus)) {
                            if (!TerminalServiceStatuses.getStatuses().contains(typedService.getStatus())) {
                                typedService.setStatus(globalServiceStatus);
                            }
                        } else {
                            typedService.setStatus(globalServiceStatus);
                        }
                        if (!"On Hold".equals(globalServiceStatus)) {
                            typedService.setProgressPercentage(100);
                        }
                    }
                    serviceManager.edit(typedService);
                }
            }
        }

//        location.setStatus(status);
//        Location updatedLocation = locationManager.edit(location);
//        setOrderStatus(updatedLocation, (milestoneInstance == null) ? null : milestoneInstance.getMilestoneDate());
    }


    /**
     * Method to handle milestone event by code.
     *
     * @param milestoneInstance the milestone instance.
     * @param incomingService   the service.
     */
    public <X extends Service> void handleMilestoneEvent(final ServiceMilestoneInstance milestoneInstance,
                                                         final Service incomingService) {

        String code = milestoneInstance.getMilestone().getCode();

        AbstractServiceManager<X> serviceManager
                = (AbstractServiceManager<X>) serviceManagerFactory.getManager(
                ServiceType.fromServiceName(incomingService.getType()));
        X service = serviceManager.retrieve(incomingService.getId());

        //validation - location level of effort must set before billable milestones can be processed. NETWORK_PROVIDER_FOC is not a billable milestone but it could set the PROVIDER_ORDER_SUBMITTED which is.
        Location location = locationManager.retrieve(service.getLocationId());
        List<String> billableMilestones = Arrays.asList("SITE_SURVEY_SUBMIT", "NETWORK_PROVIDER_FOC", "PROVIDER_ORDER_SUBMITTED", "NETWORK_PROVIDER_CONSTRUCTION_START", "COMPLETE");
        if (billableMilestones.contains(code) && Strings.isNullOrEmpty(location.getLevelOfEffort()) && !location.isCurrentInventory()) {
            throw new RuntimeException("Location Level of Effort must be set before " + milestoneInstance.getMilestone().getName());
        }

        //if the incoming milestone is a terminal milestone, and there is an existing ON_HOLD milestone - remove the ON_HOLD milestone
        List<String> terminalMilestones = Arrays.asList("CHANGE_IN_ASSIGNMENT", "COMPLETE", "CANCELLED");
        if (terminalMilestones.contains(code) && sMilestoneManager.doesMilestoneExist(service.getId(), "ON_HOLD")) {
            ServiceMilestoneInstance onHold = sMilestoneManager.retrieveCurrentMilestoneByCode(service.getId(), "ON_HOLD");
            sMilestoneManager.remove(onHold.getId());
        }

        //if the incoming service is Linked or Bundled and the status is Change In Assignment or Cancelled, remove the linked and bundled flags
        //also reset the provisioning service id on the inventory service if necessary
        List<String> deadMilestones = Arrays.asList("CHANGE_IN_ASSIGNMENT", "CANCELLED");
         if (deadMilestones.contains(code)) {
            service.setLinked(false);
            service.setBundled(false);
        }

        if ("PROVIDER_ORDER_SUBMITTED".equals(code)
                && OrderType.DISCONNECT.getOrderType().equalsIgnoreCase(service.getOrderType())) {
            if (ServiceType.CROSSCONNECT.getServiceName().equalsIgnoreCase(service.getType()) && milestoneInstance.getMilestoneDate() != null && service.getCrossConnectId()== null) {
                throw new RuntimeException("A Cross Connect ID must be populated before submitting the disconnect request");
            }
            if (!ServiceType.CROSSCONNECT.getServiceName().equalsIgnoreCase(service.getType()) && milestoneInstance.getMilestoneDate() != null && service.getProviderCircuitId() == null) {
                throw new RuntimeException("A Provider Circuit ID must be populated before submitting the disconnect request");
            }
        } else if ("PROVIDER_ORDER_SUBMITTED".equals(code)) {
            // if setting the milestone
            // else if clearing the milestone
            if (milestoneInstance.getMilestoneDate() != null) {
                if (!lMilestoneManager.doesMilestoneExist(service.getLocationId(), "PROVISIONING_START")) {
                    lMilestoneManager.create(service.getLocationId(), "PROVISIONING_START",
                            milestoneInstance.getMilestoneDate());
                }
                // if contract signed date is null, set contract signed date to provider order submitted date
                if (service.getContractSignedDate() == null) {
                    service.setContractSignedDate(milestoneInstance.getMilestoneDate());
                }
            } else {
                sMilestoneManager.retrieveCurrentMilestoneByCode(service.getId(), "PROVIDER_ORDER_SUBMITTED");
                LocationMilestoneInstance lmi = lMilestoneManager.retrieveCurrentMilestoneByCode(
                        service.getLocationId(), "PROVISIONING_START");
                if (lmi != null) {
                    lMilestoneManager.remove(lmi.getId());
                }
                // if contract signed date is not null
                if (service.getContractSignedDate() != null) {
                    // set contract signed date to data provisioning complete date if it exists, else set to null
                    ServiceMilestoneInstance dataProvisioningComplete
                            = sMilestoneManager.retrieveCurrentMilestoneByCode(service.getId(),
                            "DATA_PROVISIONING_COMPLETE");
                    if (dataProvisioningComplete != null) {
                        service.setContractSignedDate(dataProvisioningComplete.getMilestoneDate());
                    } else {
                        service.setContractSignedDate(milestoneInstance.getMilestoneDate());
                    }
                }
            }
        } else if ("NETWORK_PROVIDER_FOC".equals(code) && milestoneInstance.getMilestoneDate() != null) {
            Date milestoneDate = setCalDate(milestoneInstance.getMilestoneDate());;
            Date today = setCalDate(new Date());
            // If the FOC date isn't blank and is today or in the future, set the provider Order Submit date
            if (milestoneDate.compareTo(today) >= 0) {
                if (!sMilestoneManager.doesMilestoneExist(service.getId(), "PROVIDER_ORDER_SUBMITTED")) {
                    sMilestoneManager.create(service.getId(), "PROVIDER_ORDER_SUBMITTED", milestoneInstance.getMilestoneDate());
                }
            }
        } else if ("DATA_PROVISIONING_COMPLETE".equals(code)) {
            // if clearing the milestone and contract signed date is not null,
            // else set contract signed date to data provisioning complete date if contract signed date is null
            if (milestoneInstance.getMilestoneDate() == null && service.getContractSignedDate() != null) {
                // set contract signed date to provider order submitted date if it exists, else set to null
                ServiceMilestoneInstance providerOrderSubmitted
                        = sMilestoneManager.retrieveCurrentMilestoneByCode(service.getId(),
                        "PROVIDER_ORDER_SUBMITTED");
                if (providerOrderSubmitted != null) {
                    service.setContractSignedDate(providerOrderSubmitted.getMilestoneDate());
                } else {
                    service.setContractSignedDate(null);
                }
            } else if (service.getContractSignedDate() == null) {
                service.setContractSignedDate(milestoneInstance.getMilestoneDate());
            }
        } else if ("NETWORK_PROVIDER_CONSTRUCTION_START".equals(code)
                && milestoneInstance.getMilestoneDate() != null) {
            // get all Open Network construction jeops
            List<ServiceJeop> openJeops = sJeopManager.findOpenByDescription(service.getId(), "Network Provider Construction");
            // if no jeops, create a new one
            if (openJeops.isEmpty()) {
                openJeop(service.getId(), "Network Provider Construction",
                        milestoneInstance.getMilestoneDate(), service.getType());
            } else {
                // otherwise, edit the existing date
                for (ServiceJeop jeop : openJeops) {
                    if (jeop.getEndDate() == null) {
                        jeop.setStartDate(milestoneInstance.getMilestoneDate());
                        sJeopManager.edit(jeop);
                    }
                }
            }

        } else if ("ACTIVATION_COMPLETE".equals(code)) {
            if (milestoneInstance.getMilestoneDate() != null) {
                if (!sMilestoneManager.doesMilestoneExist(service.getId(), "COMPLETE")) {
                    sMilestoneManager.create(service.getId(), "COMPLETE", milestoneInstance.getMilestoneDate());
                }
            }
        } else if ("BILLING_REVIEW_COMPLETE".equals(code) && OrderType.DISCONNECT.getOrderType().equalsIgnoreCase(service.getOrderType())) {
            if (!sMilestoneManager.doesMilestoneExist(service.getId(), "PROVIDER_DISCONNECT_COMPLETE")) {
                throw new RuntimeException("The Provider Disconnect Complete date must be populated first");
            }
        } else if ("COMPLETE".equals(code) && OrderType.DISCONNECT.getOrderType().equalsIgnoreCase(service.getOrderType())) {
            if (milestoneInstance.getMilestoneDate() != null) {
                String msg = "";
                if (!sMilestoneManager.doesMilestoneExist(service.getId(), "PROVIDER_DISCONNECT_COMPLETE")) {
                    msg = "The Provider Disconnect Complete date must be populated";
                }
                if (!sMilestoneManager.doesMilestoneExist(service.getId(), "BILLING_REVIEW_COMPLETE")) {
                    if (!Strings.isNullOrEmpty(msg)) {
                        msg += "\n";
                    }
                    msg += "The Billing Review Complete date must be populated";
                }
                if (!Strings.isNullOrEmpty(msg)) {
                    throw new RuntimeException(msg);
                }
            }
        }

        // get the status, progress percentage, and inventory flag from the Milestone Display Set Include table
        setServiceStatus(service);
        X updateService = serviceManager.edit(service);

        setLocationStatusFromService(updateService, milestoneInstance.getMilestoneDate());
        if (!service.isCurrentInventory()) {
            processInventoryAndMacd(updateService, milestoneInstance, code);

            // No intervals are created for UCaaS and 4G/5G services
            List<String> noIntervalServiceTypes = Arrays.asList(ServiceType.UCAAS.getServiceName(), ServiceType.G.getServiceName());
            if (milestoneInstance.getMilestoneDate() != null && !noIntervalServiceTypes.contains(service.getType())) {
                createIntervalMessage(milestoneInstance.getId(), service.getId());
            }
        }
    }

    private <X extends Service> void processInventoryAndMacd(X service, ServiceMilestoneInstance milestoneInstance, String code) {

        // get the inventory flag from the Milestone Display Set Include table and process the inventory/MACD
        MilestoneDisplaySet mds = milestoneDisplaySetManager.getByDisplayType(service.getType());
        boolean inventoryFlag = false;
        String provisionerMilestone = null;
        for (MilestoneDisplaySetInclude mdsi : mds.getDisplaySetIncludes()) {
            if (mdsi.getMilestone().getCode().equals(code)) {
                inventoryFlag = mdsi.isInventoryFlag();
                if (!"COMPLETE".equals(mdsi.getMilestone().getCode()) && mdsi.isInventoryFlag()) {
                    provisionerMilestone = mdsi.getMilestone().getCode();
                }
                break;
            }
        }

        if (inventoryFlag && milestoneInstance.getMilestoneDate() != null) {

            if (service.getParentServiceId() != null && (("COMPLETE".equals(code) && provisionerMilestone == null)
                    || (provisionerMilestone != null && provisionerMilestone.equals(code)))) {
                //update cost history
                Service parent = serviceManager.retrieve(service.getParentServiceId());
                if (parent != null && (service.getMrc().compareTo(parent.getMrc()) != 0
                        || service.getNrc().compareTo(parent.getNrc()) != 0
                        || service.getAnnualRecurringCost().compareTo(parent.getAnnualRecurringCost()) != 0)) {
                    service.setCostChangeReason("MACD");
                    if (service.getMrc().compareTo(parent.getMrc()) != 0) {
                        costHistoryManager.create(parent, service, "MRC");
                    }
                    if (service.getNrc().compareTo(parent.getNrc()) != 0) {
                        costHistoryManager.create(parent, service, "NRC");
                    }
                    if (service.getMrr().compareTo(parent.getMrr()) != 0) {
                        costHistoryManager.create(parent, service, "MRR");
                    }
                    if (service.getNrr().compareTo(parent.getNrr()) != 0) {
                        costHistoryManager.create(parent, service, "NRR");
                    }
                    if (service.getAnnualRecurringCost().compareTo(parent.getAnnualRecurringCost()) != 0) {
                        costHistoryManager.create(parent, service, "Annual Recurring Cost");
                    }
                }
            }

            handleInventory(service);
            if (!Strings.isNullOrEmpty(service.getOrderType())
                    && !OrderType.NEW.getOrderType().equals(service.getOrderType())
                    && "COMPLETE".equals(code)) {
                processDisconnect(service);
            }
        } else if (TerminalServiceStatuses.getStatuses().contains(service.getStatus()) && milestoneInstance.getMilestoneDate() != null) {
            resetInventory(service, code);
        }
    }


    private <X extends Service> void setServiceStatus(X service) {
        //get the status, progress percentage from the Milestone Display Set Include table by looping through the
        //milestone instances backwards and find the last one entered to pull the status and percentage
        String displayType = service.getType();
        if ("Disconnect".equalsIgnoreCase(service.getOrderType())) {
            displayType = "Disconnect";
        } else if ("Move".equalsIgnoreCase(service.getOrderType())
                || "Add".equalsIgnoreCase(service.getOrderType())
                || "Change".equalsIgnoreCase(service.getOrderType())) {
            displayType = "MAC";
        }
        MilestoneDisplaySet mds = milestoneDisplaySetManager
                .getByDisplayType(displayType);
        List<MilestoneDisplaySetInclude> mdsis = mds.getDisplaySetIncludes();
        ListIterator<MilestoneDisplaySetInclude> iterator = mdsis.listIterator(mdsis.size());
        while (iterator.hasPrevious()) {
            MilestoneDisplaySetInclude mdsi = iterator.previous();
            ServiceMilestoneInstance smi = sMilestoneManager.retrieveCurrentMilestoneByCode(service.getId(), mdsi.getMilestone().getCode());
            if (smi != null) {
                if (mdsi.getProgressPercentage() != null) {
                    service.setProgressPercentage(mdsi.getProgressPercentage().intValue());
                }
                if (mdsi.getStatus() != null) {
                    service.setStatus(mdsi.getStatus());
                    break;
                } else if (("Television").equals(displayType) && ("CUSTOMER_REQUESTED_INSTALL").equals(mdsi.getMilestone().getCode())) {
                    if (sMilestoneManager.doesMilestoneExist(service.getId(), "ENGINEER_ASSIGNED")) {
                        service.setStatus("Engineer Assigned");
                    } else {
                        service.setStatus("Pending Assignment");
                    }
                    break;
                }
            }
        }
    }

    private Date setCalDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * Gets the Location status based on latest milestone.
     *
     * @param locationId location Id.
     */
    private String getLocationStatus(final Long locationId) {
        String status = "";
        if (lMilestoneManager.doesMilestoneExist(locationId, "ON_HOLD")) {
            status = "On Hold";
        } else if (lMilestoneManager.doesMilestoneExist(locationId, "CHANGE_IN_ASSIGNMENT")) {
            status = "Change In Assignment";
        } else if (lMilestoneManager.doesMilestoneExist(locationId, "CANCELLED")) {
            status = "Location Cancelled";
        } else if (lMilestoneManager.doesMilestoneExist(locationId, "COMPLETE")) {
            status = "Location Complete";
        } else if (lMilestoneManager.doesMilestoneExist(locationId, "PROVISIONING_START")) {
            status = "Provisioning In Progress";
        } else if (lMilestoneManager.doesMilestoneExist(locationId, "TDG_COMPLETE")) {
            status = "TDG Complete";
        } else if (lMilestoneManager.doesMilestoneExist(locationId, "INITIAL_CONTACT_WITH_CUSTOMER")) {
            status = "Customer Contacted";
        } else if (lMilestoneManager.doesMilestoneExist(locationId, "ENGINEER_ASSIGNED")) {
            status = "Engineer Assigned";
        } else {
            status = "Pending Assignment";
        }
        return status;
    }



    /**
     * Sets the location status based off the services.
     *
     * @param updatedService updated service.
     * @param miDate         milestone date.
     */
    private void setLocationStatusFromService(Service updatedService, Date miDate) {
        Location location = locationManager.retrieve(updatedService.getLocationId());
        List<Service> services = serviceManager.findByLocationId(location.getId());
        String locStatus = location.getStatus();

        Integer progress = 0;

        boolean hasCompleted = false;
        boolean allTerminal = (!services.isEmpty());
        int terminalsCount = 0;
        boolean allCancelled = (!services.isEmpty());
        boolean allChangeInAssignment = (!services.isEmpty());
        int onHoldsCount = 0;
        int servicesCount = services.size();
        boolean hasActive = false;
        boolean hasDisconnect = false;
        boolean hasEngAssigned = false;
        boolean hasTdg = false;
        boolean hasConfig = false;

        for (Service service : services) {
            String status = service.getStatus();
            progress += service.getProgressPercentage();
            if (service.equals(updatedService)
                    && !service.getStatus().equals(updatedService.getStatus())) {
                status = updatedService.getStatus();
            }
            if ("Service Complete".equals(service.getStatus())) {
                hasCompleted = true;
            }

            if ("Technical Data Gathering".equals(service.getStatus()) || "Technical Data Gathering Complete".equals(service.getStatus())) {
                hasTdg = true;
            }

            if ("Engineer Assigned".equals(service.getStatus())) {
                hasEngAssigned = true;
            }

            if ("Configuration and Onboarding".equals(service.getStatus())
                    || "Policy & Compliance Configuration".equals(service.getStatus())
                    || "License  Provisioning".equals(service.getStatus())) {
                hasConfig = true;
            }

            if ("Disconnect Complete".equals(service.getStatus())) {
                hasDisconnect = true;
            }

            if (!"Service Cancelled".equals(status) && !"Disconnect Cancelled".equals(status)) {
                allCancelled = false;
            }
            if ("On Hold".equals(status)) {
                onHoldsCount++;
            }
            if (!"Change In Assignment".equals(status)) {
                allChangeInAssignment = false;
            }
            if (!"Service Cancelled".equals(status)
                    && !"Service Complete".equals(status)
                    && !"Change In Assignment".equals(status)
                    && !"Disconnect Complete".equals(status)) {
                allTerminal = false;
            } else {
                terminalsCount++;
            }
        }

        String status = location.getStatus();
        if (allTerminal && (hasCompleted || hasDisconnect)) {
            status = "Location Complete";
            if (!lMilestoneManager.doesMilestoneExist(location.getId(), "COMPLETE")) {
                lMilestoneManager.create(location.getId(), "COMPLETE", miDate);
            }
        } else if (allCancelled) {
            status = "Location Cancelled";
            if (!lMilestoneManager.doesMilestoneExist(location.getId(), "CANCELLED")) {
                lMilestoneManager.create(location.getId(), "CANCELLED", miDate);
            }
        } else if (allChangeInAssignment) {
            status = "Change In Assignment";
            if (!lMilestoneManager.doesMilestoneExist(location.getId(), "CHANGE_IN_ASSIGNMENT")) {
                lMilestoneManager.create(location.getId(), "CHANGE_IN_ASSIGNMENT", miDate);
            }

        } else if ((terminalsCount + onHoldsCount) == servicesCount) {
            status = "On Hold";
            if (!lMilestoneManager.doesMilestoneExist(location.getId(), "ON_HOLD")) {
                lMilestoneManager.create(location.getId(), "ON_HOLD", miDate);
            }
        } else if (hasEngAssigned && locStatus.equals("Pending Assignment")) {
            status = "Engineer Assigned";
        } else if (hasTdg && (locStatus.equals("Engineer Assigned") || locStatus.equals("Pending Assignment"))) {
            status = "Technical Data Gathering";
        } else if (hasConfig && (locStatus.equals("Engineer Assigned") || locStatus.equals("Pending Assignment")
                || locStatus.equals("Technical Data Gathering"))) {
            status = "Configuration and Onboarding";
        } else if (location.getStatus() == null) {
            status = "Pending Assignment";
        } else if (!allTerminal && TerminalLocationStatuses.getStatuses().contains(location.getStatus())) {
            LocationMilestoneInstance complete = lMilestoneManager.retrieveCurrentMilestoneByCode(location.getId(), "COMPLETE");
            LocationMilestoneInstance cancelled = lMilestoneManager.retrieveCurrentMilestoneByCode(location.getId(), "CANCELLED");
            LocationMilestoneInstance cia = lMilestoneManager.retrieveCurrentMilestoneByCode(location.getId(), "CHANGE_IN_ASSIGNMENT");
            if (complete != null) {
                complete.setMilestoneDate(null);
                lMilestoneManager.edit(complete);
            }
            if (cancelled != null) {
                cancelled.setMilestoneDate(null);
                lMilestoneManager.edit(cancelled);
            }
            if (cia != null) {
                cia.setMilestoneDate(null);
                lMilestoneManager.edit(cia);
            }
//            status = getLocationStatus(location.getId());
        } else {
            status = locStatus;
        }

        if (allTerminal && !hasCompleted && !hasDisconnect) {
            //If the location has been cancelled or changed in assignment, and the location is not eligible for inventory yet but has an inventory location id,
            // reset the inventory linking ids
            if (location.getInventoryLocationId() != null && !location.isEligibleForInventory()){
                Location inventoryLocation = locationManager.retrieve(location.getInventoryLocationId());
                inventoryLocation.setProvisioningLocationId(location.getParentLocationId());
                inventoryLocation.setParentLocationId(location.getParentLocationId());
                locationManager.editSuper(inventoryLocation);
            }
       }


        Integer progressPercentage = progress / services.size();
        location.setProgressPercentage(progressPercentage);
        location.setStatus(status);
        Location updatedLocation = locationManager.edit(location);

        if ("On Hold".equals(location.getStatus()) && (terminalsCount + onHoldsCount) != servicesCount) {
            LocationMilestoneInstance lmi = lMilestoneManager.
                    retrieveCurrentMilestoneByCode(location.getId(), "ON_HOLD");
            if (lmi != null) {
                lMilestoneManager.remove(lmi.getId());
                updatedLocation.setStatus(getLocationStatus(updatedLocation.getId()));
                updatedLocation = locationManager.edit(updatedLocation);
            }
        }
        setOrderStatus(updatedLocation, miDate);

    }


    /**
     * Sets the Order status based of the locations.
     *
     * @param updatedLocation updated location.
     */
    private void setOrderStatus(final Location updatedLocation, final Date miDate) {
        Order order = orderManager.retrieve(updatedLocation.getOrderId());
        List<Location> locations = locationManager.findbyOrderId(order.getId());
        String orderStatus = order.getStatus();

        boolean hasChangeInAssignment = false;
        boolean hasCompleted = false;
        boolean allTerminal = (locations.size() > 0);
        boolean allCancelled = (locations.size() > 0);
        boolean allChangInAssignment = (locations.size() > 0);
        boolean allOnHold = (locations.size() > 0);
        boolean allPendingAssignment = (locations.size() > 0);
        boolean hasEngAssigned = false;
        boolean hasTdg = false;
        boolean hasConfig = false;

        for (Location location : locations) {
            String status = location.getStatus();
            status = ((status == null) ? "Pending Assignment" : status);
            if (location.equals(updatedLocation)
                    && !location.getStatus().equals(updatedLocation.getStatus())) {
                status = updatedLocation.getStatus();
            }
            if ("Location Complete".equals(location.getStatus())) {
                hasCompleted = true;
            }
            if ("Engineer Assigned".equals(location.getStatus())) {
                hasEngAssigned = true;
            }
            if ("Technical Data Gathering".equals(location.getStatus())) {
                hasTdg = true;
            }
            if ("Configuration and Onboarding".equals(location.getStatus())) {
                hasConfig = true;
            }
            if (!"Location Cancelled".equals(status)) {
                allCancelled = false;
            }
            if (!"On Hold".equals(status) && !"Location Complete".equals(status) && !"Location Cancelled".equals(status) && !"Change In Assignment".equals(status)) {
                allOnHold = false;
            }
            if (!"Change In Assignment".equals(status)) {
                allChangInAssignment = false;
            }
            if ("Change In Assignment".equals(status)) {
                hasChangeInAssignment = true;
            }
            if (!"Location Cancelled".equals(status)
                    && !"Location Complete".equals(status)
                    && !"Change In Assignment".equals(status)) {
                allTerminal = false;
            }
            if (!"Pending Assignment".equals(status)) {
                allPendingAssignment = false;
            }
        }

        String status;
        if (allTerminal && hasCompleted) {
            status = "Order Complete";
            if (!oMilestoneManager.doesMilestoneExist(order.getId(), "COMPLETE")) {
                oMilestoneManager.create(order.getId(), "COMPLETE", miDate);

            }
        } else if (allCancelled) {
            status = "Order Cancelled";
            if (!oMilestoneManager.doesMilestoneExist(order.getId(), "CANCELLED")) {
                oMilestoneManager.create(order.getId(), "CANCELLED", miDate);

            }

        } else if (allChangInAssignment || (allTerminal && hasChangeInAssignment && !hasCompleted)) {
            status = "Change In Assignment";
            if (!oMilestoneManager.doesMilestoneExist(order.getId(), "CHANGE_IN_ASSIGNMENT")) {
                oMilestoneManager.create(order.getId(), "CHANGE_IN_ASSIGNMENT", miDate);

            }
        } else if (allOnHold) {
            status = "On Hold";
            if (!oMilestoneManager.doesMilestoneExist(order.getId(), "ON_HOLD")) {
                oMilestoneManager.create(order.getId(), "ON_HOLD", miDate);

            }
        } else if (hasEngAssigned && order.getStatus().equals("Order Received")) {
            status = "Engineer Assigned";
        } else if (hasTdg && (order.getStatus().equals("Engineer Assigned") || order.getStatus().equals("Order Received"))) {
            status = "Technical Data Gathering";
        } else if (hasConfig && (order.getStatus().equals("Engineer Assigned") || order.getStatus().equals("Order Received")
                || order.getStatus().equals("Technical Data Gathering"))) {
            status = "Configuration and Onboarding";
        } else if (hasCompleted && !allTerminal) {
            status = "Configuration and Onboarding";
        } else if (allPendingAssignment || (!hasEngAssigned && !hasTdg && !hasConfig)) {
            status = "Order Received";
        } else {
            status = order.getStatus();
        }
        if (!"".equals(status) && !status.equals(order.getStatus())) {
            order.setStatus(status);
            orderManager.edit(order);
        }



//        //reset Inventory Order Id if all locations are Cancelled or Change in Assigment
//        if (allTerminal && !hasCompleted ) {
//            //get the inventory Location
//            Location inventoryLocation = locationManager.retrieve(updatedLocation.getInventoryLocationId());
//            //get the inventory Order based on inventory location order id
//            Order inventoryOrder = orderManager.retrieve(inventoryLocation.getOrderId());
//            //get the parent location
//            Location parentLocation = locationManager.retrieve(updatedLocation.getParentLocationId());
//            //set the privisioning order id to the parent location order id
//            inventoryOrder.setProvisioningOrderId(parentLocation.getOrderId());
//            orderManager.editSuper(inventoryOrder);
//        }

    }


    /**
     * Open a specific Service Jeop.
     *
     * @param id        Service ID.
     * @param jeopDesc  Description.
     * @param startDate Start Date.
     * @param level     Service Type.
     */
    private void openJeop(final Long id, final String jeopDesc, final Date startDate, final String level) {

        ServiceJeop jeop = new ServiceJeop();
        jeop.setServiceId(id);
        jeop.setDescription(jeopDesc);
        jeop.setStartDate(startDate);
        jeop.setResponsibility("Provider");
        Subject subject = subjectManager.findByUsername(SecurityUtils.getLoggedInUser());
        jeop.setOriginator(subject.getDisplayName());
        sJeopManager.create(jeop);

    }


    /**
     * Returns the appropriate event handler by its class name.
     *
     * @param className the name of said class.
     * @return the oms event handler
     */
    public static BaseEventHandler omsEventHandlerFactory(final String className) {

        try {
            Class eventHandlerClass = Class.forName(className);
            return (BaseEventHandler) ScheduledJobUtil.findEjb(eventHandlerClass);
        } catch (ClassNotFoundException cnfe) {
            LOGGER.error("Class {} not found.", className);
            throw new RuntimeException(cnfe);
        } catch (NamingException ne) {
            LOGGER.error(ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    /**
     * Creates an IntervalMessage and sends it to the interval queue.
     *
     * @param milestoneInstanceId milestone instance id
     * @param serviceId           service id
     */
    private void createIntervalMessage(final Long milestoneInstanceId, final Long serviceId) {
        IntervalMessage message = new IntervalMessage(serviceId, milestoneInstanceId, "service");
        intervalQueueHandler.sendMessageToQueue(message);
    }

    /**
     * Handles Service milestone instances.
     *
     * @param milestoneInstanceId milestone instance id
     * @param serviceId           service id
     */
    public void createServiceIntervals(final Long milestoneInstanceId, final Long serviceId) {
        MilestoneInstance milestoneInstance = sMilestoneManager.retrieve(milestoneInstanceId);

        List<IntervalType> intervalTypes = intervalTypeManager.findByMilestoneId(milestoneInstance.getMilestone().getId());
        for (IntervalType intervalType : intervalTypes) {
            if (milestoneInstance.getMilestone().getId().equals(intervalType.getOpenMilestoneId())) {
                openServiceInterval(serviceId, milestoneInstanceId, intervalType);
            } else if (milestoneInstance.getMilestone().getId().equals(intervalType.getCloseMilestoneId())) {
                sIntervalInstanceManager.closeServiceInterval(serviceId, milestoneInstanceId, intervalType.getId());
            }
        }
    }

    /**
     * Opens a service interval.
     *
     * @param serviceId           service id
     * @param milestoneInstanceId milestone instance id
     * @param intervalType        interval type
     */
    private void openServiceInterval(final Long serviceId, final Long milestoneInstanceId,
                                     final IntervalType intervalType) {
        MilestoneInstance closeMilestone;
        sIntervalInstanceManager.openServiceInterval(serviceId, milestoneInstanceId, intervalType.getId());
        closeMilestone = sMilestoneManager.retrieveCurrentMilestone(serviceId, intervalType.getCloseMilestoneId());
        if (closeMilestone != null) {
            sIntervalInstanceManager.closeServiceInterval(serviceId, closeMilestone.getId(), intervalType.getId());
        }
    }

    private <X extends Service> void handleInventory(final X service) {
        AbstractServiceManager<X> manager
                = (AbstractServiceManager<X>) serviceManagerFactory.getManager(
                ServiceType.fromServiceName(service.getType()));

        if (!service.isCurrentInventory() && service.getInventoryServiceId() == null) {
            manager.createInventory(service);
        } else if (!service.isCurrentInventory() && service.getInventoryServiceId() != null) {
            service.setEligibleForInventory(true);
            manager.updateInventory(service, true);
        }
    }

    public <X extends Service> void processDisconnect(final X source) {
        AbstractServiceManager<X> svcManager = (AbstractServiceManager<X>) serviceManagerFactory
                .getManager(ServiceType.fromServiceName(source.getType()));

        X parent = null;
        if (source.getParentServiceId() != null) {
            parent = (X) serviceManager.retrieve(source.getParentServiceId());
        }

        if ((!Strings.isNullOrEmpty(source.getSubOrderType()) && source.getSubOrderType().contains("New Service"))
                && sMilestoneManager.doesMilestoneExist(source.getId(), "COMPLETE")
                && parent != null) {
            //check the pending disconnect table to see if there is a pending disconnect and update
            createPendingDisconnect(source, parent, svcManager);
        }
    }

    private <X extends Service> void createPendingDisconnect(final X source, final X parent,
                                                             final AbstractServiceManager<X> svcManager) {
        PendingDisconnect pendingDisconnect = pendingDisconnectManager.findByParent(source.getParentServiceId());
        Service inventoryService = serviceManager.retrieve(parent.getId());
        if (pendingDisconnect != null) {
            pendingDisconnect.setNewServiceCompleteDate(new Date());
            if (pendingDisconnect.getNewService() == null) {
                pendingDisconnect.setNewService(source);
            }
            Service newDisconnect = serviceManager.findDisconnectByParentId(source.getParentServiceId());
            //Disconnect service already exists so just set update the pending disconnect table
            if (newDisconnect != null && pendingDisconnect.getChildService() == null) {
                pendingDisconnect.setChildService(newDisconnect);
            } else {
                //create disconnect if there is no delay
                CompanyConfigPropertiesDto config = companyManager.getConfigDto(source.getTenantId());
                if (config.getDisconnectDelay() == null || config.getDisconnectDelay() < 1) {
                    List<String> orderTypes = new ArrayList<>();
                    orderTypes.add(OrderType.DISCONNECT.getOrderType());
                    List<String> disconnectReasons = new ArrayList<>();
                    disconnectReasons.add(pendingDisconnect.getDisconnectReason());
                    List<String> subOrderTypes = new ArrayList<>();
                    subOrderTypes.add(pendingDisconnect.getDisconnectReason());
                    MacdRequestDto macdRequestDto = new MacdRequestDto();
                    macdRequestDto.setServiceId(inventoryService.getId());

                    MacdDto macdDto = new MacdDto();
                    macdDto.setOrderType(OrderType.DISCONNECT.getOrderType());
                    macdDto.setSubOrderType(pendingDisconnect.getDisconnectReason());
                    macdDto.setCreateDisconnectUponCompletion(false);
                    macdDto.setServiceType(inventoryService.getType());
                    macdDto.setProjectName(inventoryService.getProjectName());
                    macdDto.setDisconnectReason(pendingDisconnect.getDisconnectReason());

                    macdRequestDto.setMacds(Arrays.asList(macdDto));

                    X disconnect = serviceManager.createMacd(macdRequestDto);
                    pendingDisconnect.setChildService(disconnect);
                }
            }
            pendingDisconnectManager.edit(pendingDisconnect);
        }
    }


    private <X extends Service> void resetInventory(final X service, final String code) {
        AbstractServiceManager<X> svcManager = (AbstractServiceManager<X>) serviceManagerFactory
                .getManager(ServiceType.fromServiceName(service.getType()));

        // set the service complete date in the pending disconnect table
        if (!OrderType.DISCONNECT.getOrderType().equals(service.getOrderType())) {
            if ("COMPLETE".equals(code)) {
                if (service.getParentServiceId() != null) {
                    PendingDisconnect pendingDisconnect = pendingDisconnectManager.findByParent(service.getParentServiceId());
                    if (pendingDisconnect != null) {
                        pendingDisconnect.setNewServiceCompleteDate(null);
                        pendingDisconnectManager.edit(pendingDisconnect);
                    }
                }
            }
        }

        // If the complete date is taken out check to see if there is another inventory milestone
        //and if there is don't make the inventory item inactive
        String provisionerMilestone = "";
        MilestoneDisplaySet mds = milestoneDisplaySetManager.getByDisplayType(service.getType());
        for (MilestoneDisplaySetInclude mdsi : mds.getDisplaySetIncludes()) {
            if (!"COMPLETE".equals(mdsi.getMilestone().getCode()) && mdsi.isInventoryFlag()) {
                provisionerMilestone = mdsi.getMilestone().getCode();
            }
        }

        Location location = locationManager.retrieve(service.getLocationId());
        if ("COMPLETE".equals(code)) {
            service.setFinalUpdate(false);
            svcManager.edit(service);

            location.setFinalUpdate(false);
            locationManager.edit(location);

            Order order = orderManager.retrieve(location.getOrderId());
            order.setFinalUpdate(false);
            orderManager.edit(order);

            //If the provisionerMilestone is empty then complete is the only inventory milestone and the inventory needs to be reset
            if (provisionerMilestone.equals(code) || Strings.isNullOrEmpty(provisionerMilestone)) {
                svcManager.updateInventory(service, false);
            }
        } else if (("CANCELLED".equals(code) || "CHANGE_IN_ASSIGNMENT".equals(code))
                && (service.getOrderType() != null && !OrderType.NEW.getOrderType().equals(service.getOrderType()))
                && !service.getSubOrderType().contains("New Service")) {
            //reset cancelled macd's
            Service inventoryService = serviceManager.retrieve(service.getInventoryServiceId());
            Service provService = serviceManager.findProvisioningByInventoryId(service.getInventoryServiceId());
            inventoryService.setProvisioningServiceId(provService.getId());
            svcManager.editSuper(inventoryService);
            if (TerminalLocationStatuses.CANCELLED.getStatus().equals(location.getStatus())
                    || TerminalLocationStatuses.CHANGE_IN_ASSIGNMENT.getStatus().equals(location.getStatus())) {
                Location inventoryLocation = locationManager.retrieve(location.getInventoryLocationId());
                inventoryLocation.setProvisioningLocationId(provService.getLocationId());
                locationManager.editSuper(inventoryLocation);
                Order order = orderManager.retrieve(service.getOrderId());
                if (TerminalOrderStatuses.CANCELLED.getStatus().equals(order.getStatus())
                        || TerminalOrderStatuses.CHANGE_IN_ASSIGNMENT.getStatus().equals(order.getStatus())) {
                    Order inventoryOrder = orderManager.retrieve(order.getInventoryOrderId());
                    inventoryOrder.setProvisioningOrderId(provService.getOrderId());
                    orderManager.editSuper(inventoryOrder);
                }
            }
        }
    }


}
