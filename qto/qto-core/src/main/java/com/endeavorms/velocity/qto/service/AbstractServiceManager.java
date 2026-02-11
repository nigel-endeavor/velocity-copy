package com.endeavorms.velocity.qto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.RecordSource;
import com.endeavorms.velocity.qto.activation.attempt.ActivationAttempt;
import com.endeavorms.velocity.qto.activation.attempt.ActivationAttemptManager;
import com.endeavorms.velocity.qto.activation.issue.ActivationIssue;
import com.endeavorms.velocity.qto.activation.issue.ActivationIssueManager;
import com.endeavorms.velocity.qto.activation.requirement.ActivationAttemptRequirement;
import com.endeavorms.velocity.qto.activation.schedule.ActivationSchedule;
import com.endeavorms.velocity.qto.activation.schedule.ActivationScheduleManager;
import com.endeavorms.velocity.qto.attachment.FileAttachment;
import com.endeavorms.velocity.qto.attachment.FileAttachmentContent;
import com.endeavorms.velocity.qto.attachment.ServiceFileAttachment;
import com.endeavorms.velocity.qto.attachment.ServiceFileAttachmentManager;
import com.endeavorms.velocity.qto.brokerage.ServiceBrokerage;
import com.endeavorms.velocity.qto.brokerage.ServiceBrokerageManager;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.company.jms.CompanyMessage;
import com.endeavorms.velocity.qto.company.jms.CompanyMessageHandler;
import com.endeavorms.velocity.qto.contact.location.LocationContact;
import com.endeavorms.velocity.qto.contact.location.LocationContactManager;
import com.endeavorms.velocity.qto.contact.order.OrderContact;
import com.endeavorms.velocity.qto.contact.order.OrderContactManager;
import com.endeavorms.velocity.qto.costhistory.CostHistory;
import com.endeavorms.velocity.qto.costhistory.CostHistoryManager;
import com.endeavorms.velocity.qto.customfield.value.CustomFieldValue;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValue;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValueManager;
import com.endeavorms.velocity.qto.dataverse.CrmIntegration;
import com.endeavorms.velocity.qto.dataverse.CrmIntegrationManager;
import com.endeavorms.velocity.qto.dataverse.Dataverse;
import com.endeavorms.velocity.qto.dispute.Dispute;
import com.endeavorms.velocity.qto.dispute.DisputeManager;
import com.endeavorms.velocity.qto.equipment.Equipment;
import com.endeavorms.velocity.qto.equipment.ServiceEquipment;
import com.endeavorms.velocity.qto.equipment.ServiceEquipmentManager;
import com.endeavorms.velocity.qto.interval.ServiceIntervalInstance;
import com.endeavorms.velocity.qto.interval.ServiceIntervalInstanceManager;
import com.endeavorms.velocity.qto.inventory.PendingDisconnect;
import com.endeavorms.velocity.qto.inventory.PendingDisconnectManager;
import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurcharge;
import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurchargeManager;
import com.endeavorms.velocity.qto.jeop.ServiceJeop;
import com.endeavorms.velocity.qto.jeop.ServiceJeopManager;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.location.TerminalLocationStatuses;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstanceManager;
import com.endeavorms.velocity.qto.milestone.MilestoneInstanceHistory;
import com.endeavorms.velocity.qto.milestone.MilestoneInstanceHistoryManager;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstanceManager;
import com.endeavorms.velocity.qto.note.DisputeNote;
import com.endeavorms.velocity.qto.note.DisputeNoteManager;
import com.endeavorms.velocity.qto.note.JeopNoteManager;
import com.endeavorms.velocity.qto.note.ServiceNote;
import com.endeavorms.velocity.qto.note.ServiceNoteManager;
import com.endeavorms.velocity.qto.notification.NotificationManager;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.service.snapshot.ServiceSnapshot;
import com.endeavorms.velocity.qto.service.snapshot.ServiceSnapshotManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Abstract manager for Service subclasses, can be extended for use in places where ServiceManager cannot be used.
 *
 * @param <T> Service model
 * @author rcasey
 * @since 2/10/2023
 */
public abstract class AbstractServiceManager<T extends Service> extends StandardManager<T> {

    /**
     * Logging Facade.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceManager.class);

    @Inject
    private OrderManager orderManager;

    @Inject
    private OrderContactManager orderContactManager;

    @Inject
    private LocationManager locationManager;

    @Inject
    private LocationContactManager locationContactManager;

    @Inject
    private LocationMilestoneInstanceManager locationMilestoneInstanceManager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private ServiceMilestoneInstanceManager serviceMilestoneInstanceManager;

    @Inject
    private ServiceJeopManager serviceJeopManager;

    @Inject
    private ServiceNoteManager serviceNoteManager;

    @Inject
    private ServiceManagerFactory serviceManagerFactory;
    /**
     * Business logic for cost history.
     */
    @Inject
    private CostHistoryManager costHistoryManager;

    /**
     * Notification manager.
     */
    @Inject
    private NotificationManager notificationManager;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private CompanyMessageHandler companyMessageHandler;

    @Inject
    private ServiceFileAttachmentManager serviceFileAttachmentManager;

    @Inject
    private ActivationScheduleManager activationScheduleManager;

    @Inject
    private ActivationAttemptManager activationAttemptManager;

    @Inject
    private ActivationIssueManager activationIssueManager;

    @Inject
    private ServiceBrokerageManager serviceBrokerageManager;

    @Inject
    private ServiceCustomFieldValueManager serviceCustomFieldValueManager;

    @Inject
    private ServiceEquipmentManager serviceEquipmentManager;

    @Inject
    private Dataverse dataverse;

    @Inject private CrmIntegrationManager crmIntegrationManager;

    @Inject
    private ServiceSurchargeManager surchargeManager;

    @Inject
    private DisputeManager disputeManager;

    @Inject
    private DisputeNoteManager disputeNoteManager;

    @Inject
    private ServiceIntervalInstanceManager serviceIntervalInstanceManager;

    @Inject
    private MilestoneInstanceHistoryManager milestoneHistoryManager;

    @Inject
    private ServiceSnapshotManager snapshotManager;

    @Inject
    private ServiceBrokerageManager brokerageManager;

    @Inject
    private PendingDisconnectManager pendingDisconnectManager;

    @Inject
    private JeopNoteManager jeopNoteManager;

    @Override
    public T create(final T entity) {
        T persisted;
        Order order = orderManager.retrieve(entity.getOrderId());
        Company company = companyManager.retrieve(order.getCompany().getId());
        Long tenantId = company.getTenantId();
        entity.setTenantId(tenantId);
        entity.setMasterCustomerId(company.getMasterCustomerId());
        if (Strings.isNullOrEmpty(entity.getRecordSource())) {
            entity.setRecordSource(RecordSource.UNKNOWN.getName());
        }
        if (Strings.isNullOrEmpty(entity.getOrderType())) {
            entity.setOrderType(OrderType.NEW.getOrderType());
        }
        if (!Strings.isNullOrEmpty(entity.getDownloadSpeed()) || !Strings.isNullOrEmpty(entity.getUploadSpeed())) {
            entity.setSpeed(entity.getDownloadSpeed() + "/" + entity.getUploadSpeed());
        }
        if (entity.isCurrentInventory()) {
            entity.setActive(true);
            entity.setBillable(true);
            persisted = super.create(entity);
            if (persisted.getOriginalMrc() != null) {
                costHistoryManager.create(null, persisted, "MRC", "Original MRC");
            }
            if (persisted.getOriginalMrr() != null) {
                costHistoryManager.create(null, persisted, "MRR", "Original MRR");
            }
            if (persisted.getOriginalNrr() != null) {
                costHistoryManager.create(null, persisted, "NRR", "Original NRR");
            }
            costHistoryManager.create(null, persisted, "MRC", "Initial Cost");
            costHistoryManager.create(null, persisted, "MRR", "Initial Cost");
            costHistoryManager.create(null, persisted, "NRR", "Initial Cost");
            costHistoryManager.create(null, persisted, "NRC", "Initial Cost");
            costHistoryManager.create(null, persisted, "Annual Recurring Cost", "Initial Cost");
            // only do this if the service is created from inventory
            if (persisted.getProvisioningServiceId() == null && !serviceMilestoneInstanceManager.doesMilestoneExist(persisted.getId(), "COMPLETE")) {
                serviceMilestoneInstanceManager.create(persisted.getId(), "COMPLETE", new Date());
            }
        } else {
            entity.setUpdateClient(true);
            persisted = super.create(entity);
        }

        //assign a client service id to the service
        if (Strings.isNullOrEmpty(entity.getClientServiceId())) {
            String newCsid = persisted.getId() + entity.getType();
            List<Service> services = serviceManager.findByClientServiceIdAndTenant(newCsid, tenantId);
            if (services.size() > 0) {
                int i = services.size();
                do {
                    services = serviceManager.findByClientServiceIdAndTenant(newCsid + "-" + i, tenantId);
                    if (!services.isEmpty()) {
                        i++;
                    }
                } while (!services.isEmpty());
                newCsid = newCsid + "-" + i;
            }
            newCsid = newCsid.replace(" ", "");
            entity.setClientServiceId(newCsid);
            persisted = super.edit(entity);
        }

        //don't do this if the call is coming from the inventory create function that is triggered from the provisioning service
        if (entity.getProvisioningServiceId() == null) {
            serviceMilestoneInstanceManager.create(persisted.getId(), "CREATED", new Date());
            serviceMilestoneInstanceManager.create(persisted.getId(), "RECEIVED", new Date());
            if (!"Order Received".equals(order.getStatus())) {
                serviceMilestoneInstanceManager.create(persisted.getId(), "ENGINEER_ASSIGNED", new Date());
            }
        }

        companyMessageHandler.sendMessageToQueue(new CompanyMessage(company.getId(), "updateCounts"));
        return persisted;
    }

    @Override
    public T edit(final T entity) {

        T existing = retrieve(entity.getId());
        String existingProvider = existing.getProvider();
        boolean locationHasNoMoreServices = false;
        // if this service has been relocated, update it's service order ID
        Location oldLocation = null;
        Location newLocation = null;
        if (!existing.getLocationId().equals(entity.getLocationId())) {
            // check to see if this location has any other services under it
            newLocation = locationManager.retrieve(entity.getLocationId());
            entity.setOrderId(newLocation.getOrderId());
            oldLocation = locationManager.retrieve(existing.getLocationId());

            if (oldLocation.getServices().stream().filter(s -> s.getId() != entity.getId()).count() == 0) {
                locationHasNoMoreServices = true;
                oldLocation.setActive(false);
                oldLocation = locationManager.edit(oldLocation);
            }
            //create audit note for the service indicating that it has been relocated
            String build = "Service relocated from Order #" + oldLocation.getOrderId()
                    + " to Order #" + newLocation.getOrderId();
            serviceNoteManager.create(entity.getId(), build, entity.getType());
        }
        //cost history
        //using BigDecimal::compareTo instead of equals here because BigDecimal::equals considers scale. i.e. 0 != 0.00
        if (Boolean.TRUE.equals(entity.isCurrentInventory()) && entity.getMrc().compareTo(existing.getMrc()) != 0) {
            costHistoryManager.create(existing, entity, "MRC");
        }
        if (Boolean.TRUE.equals(entity.isCurrentInventory()) && entity.getNrc().compareTo(existing.getNrc()) != 0) {
            costHistoryManager.create(existing, entity, "NRC");
        }
        if (Boolean.TRUE.equals(entity.isCurrentInventory()) && entity.getMrr().compareTo(existing.getMrr()) != 0) {
            costHistoryManager.create(existing, entity, "MRR");
        }
        if (Boolean.TRUE.equals(entity.isCurrentInventory()) && entity.getNrr().compareTo(existing.getNrr()) != 0) {
            costHistoryManager.create(existing, entity, "NRR");
        }
        if (Boolean.TRUE.equals(entity.isCurrentInventory()) && entity.getAnnualRecurringCost().compareTo(existing.getAnnualRecurringCost()) != 0) {
            costHistoryManager.create(existing, entity, "Annual Recurring Cost");
        }
        Order order = orderManager.retrieve(entity.getOrderId());
        Company company = companyManager.retrieve(order.getCompany().getId());

        Long tenantId = company.getTenantId();
        entity.setTenantId(tenantId);
        entity.setMasterCustomerId(company.getMasterCustomerId());
        if (entity.getContractTerm() != null && entity.getContractSignedDate() != null) {
            entity.setCircuitTermEndDate(serviceManager.dateAdd(
                    entity.getContractSignedDate(), entity.getContractTerm()));
        } else {
            entity.setCircuitTermEndDate(null);
        }
        entity.setUpdateClient(true);

        //if all services under this services location are inactive, set the location to inactive
        Location location = locationManager.retrieve(entity.getLocationId());
        location.setActive(location.getServices().stream().filter(s -> s.getId() != entity.getId()).anyMatch(Service::isActive) || entity.isActive());
        locationManager.edit(location);

        T updated = super.edit(entity);

        //check link/bundle associations
        Long parentId = updated.getLinkedBundledParentId();
        boolean parent = updated.getLinkedBundledParent();
        if (updated.getLinked() == false && updated.getBundled() == false
                && parentId != null) {
            //reset parent association for entity
            updated.setLinkedBundledParentId(null);
            if (parent) {
                updated.setLinkedBundledParent(false);
            }
            updated = super.edit(updated);
            List<T> services = (List<T>) serviceManager.findAssociatedByParentId(parentId);
            if (parent) {
                //remove association for all linked/bundled services
                for (T service : services) {
                    service.setLinkedBundledParentId(null);
                    service.setLinkedBundledParent(false);
                    service.setLinked(false);
                    service.setBundled(false);
                    super.edit(service);
                }
            } else if (services.size() == 1) {
                //Parent only has one child, remove association on parent
                T parentService = services.get(0);
                parentService.setLinkedBundledParentId(null);
                parentService.setLinkedBundledParent(false);
                parentService.setLinked(false);
                parentService.setBundled(false);
                super.edit(parentService);
            }
        } else if (Boolean.TRUE.equals(updated.getBundled())
                && !Strings.isNullOrEmpty(existingProvider)
                && !existingProvider.equals(updated.getProvider())) {
            //if a bundled service's provider changes then update all other services in the bundle
            List<T> services = (List<T>) serviceManager.findAssociatedByParentId(parentId);
            for (T service : services) {
                if (!service.getId().equals(updated.getId())) {
                    service.setProvider(entity.getProvider());
                    super.edit(service);
                }
            }
        }

        if (oldLocation != null) {
            if (locationHasNoMoreServices && !locationMilestoneInstanceManager.doesMilestoneExist(oldLocation.getId(), "CHANGE_IN_ASSIGNMENT")) {
                locationMilestoneInstanceManager.create(oldLocation.getId(), "CHANGE_IN_ASSIGNMENT", new Date());
            }

            ServiceMilestoneInstance engineerAssigned = serviceMilestoneInstanceManager.retrieveCurrentMilestoneByCode(entity.getId(), "ENGINEER_ASSIGNED");
            if ("Pending Assignment".equals(newLocation.getStatus()) && engineerAssigned != null) {
                engineerAssigned.setMilestoneDate(null);
                serviceMilestoneInstanceManager.edit(engineerAssigned);
            } else if (!"Pending Assignment".equals(newLocation.getStatus()) && engineerAssigned == null) {
                serviceMilestoneInstanceManager.create(entity.getId(), "ENGINEER_ASSIGNED", new Date());
            }
        }

        //If the service has an inventory record call the update inventory function
        updated = updateInventory(entity, null);

        companyMessageHandler.sendMessageToQueue(new CompanyMessage(company.getId(), "updateCounts"));
        return updated;
    }

    /**
     * Clones the provided service of type X to a new Service of type T.
     *
     * @param source the Service of type X to clone.
     * @param <X>    the type of the service we're cloning from.
     * @return the Service clone of type T.
     */
    public <X extends Service> T cloneAndCancel(final X source) {
        T clone = clone(source);
        cancel(source, clone);
        return clone;
    }

    /**
     * Handles copying of properties etc. from an existing entity of type X to a new entity of type T,
     * both of which are types of Service.
     *
     * @param source the Service we're cloning.
     * @return the created and populated target.
     */
    public <X extends Service> T clone(final X source) {
        try {
            //service
            // Get the class object for T
            Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            // Create a new instance of T
            T target = clazz.getDeclaredConstructor().newInstance();
            target.setClientServiceId(source.getClientServiceId());
            target.setAlternateId(source.getAlternateId());
            target.setProjectName(source.getProjectName());
            target.setOrderType(source.getOrderType());
            target.setDmarc(source.getDmarc());
            target.setLocationHours(source.getLocationHours());
            target.setLocationId(source.getLocationId());
            target.setOrderId(source.getOrderId());
            target.setTicketNumber(source.getTicketNumber());
            target.setServiceBilledTo(source.getServiceBilledTo());
            target.setPoNumber(source.getPoNumber());
            target.setJobNumber(source.getJobNumber());
            target.setClientServiceInfo(source.getClientServiceInfo());
            target.setClientServiceType(source.getClientServiceType());
            target.setRecordSource(RecordSource.CLONE.getName());
            target.setDescription(source.getDescription());
            target.setLocationHours(source.getLocationHours());
            if (source.getTicketNumber() != null) {
                target.setEligibleForUpdate(true);
                target.setUpdateClient(true);
            }
            target.setLegacyId(source.getLegacyId());
            target.setLegacyDiaId(source.getLegacyDiaId());
            if (source.getLinked()) {
                target.setLinked(source.getLinked());
                target.setLinkedBundledParentId(source.getLinkedBundledParentId());
                target.setLinkedBundledParent(source.getLinkedBundledParent());
            }

            target.setStatus("");
            T created = create(target);
            //service milestones
            ServiceMilestoneInstance sourceReceivedMilestoneInstance
                    = serviceMilestoneInstanceManager
                    .retrieveCurrentMilestoneByCode(source.getId(), "RECEIVED");
            ServiceMilestoneInstance targetReceivedMilestoneInstance
                    = serviceMilestoneInstanceManager
                    .retrieveCurrentMilestoneByCode(created.getId(), "RECEIVED");
            targetReceivedMilestoneInstance.setMilestoneDate(sourceReceivedMilestoneInstance.getMilestoneDate());
            ServiceMilestoneInstance sourceCustomerRequestedInstallMilestoneInstance
                    = serviceMilestoneInstanceManager
                    .retrieveCurrentMilestoneByCode(source.getId(), "CUSTOMER_REQUESTED_INSTALL");
            if (sourceCustomerRequestedInstallMilestoneInstance != null) {
                serviceMilestoneInstanceManager
                        .create(target.getId(),
                                "CUSTOMER_REQUESTED_INSTALL",
                                sourceCustomerRequestedInstallMilestoneInstance.getMilestoneDate());
            }
            //note
            String cloningUser = SecurityUtils.getLoggedInUser();
            ServiceNote note = new ServiceNote();
            note.setServiceId(created.getId());
            note.setCategory(created.getType());
            Subject subject = subjectManager.findByUsername(cloningUser);
            note.setCreatedBy(subject == null ? cloningUser : subject.getDisplayName());
            note.setCreatedById(subject.getId());
            note.setCreatedDate(new Date());
            String build = cloningUser +
                    " cancelled service order " +
                    source.getId() +
                    " and new ordering continues on this service order.";
            note.setNote(build);
            serviceNoteManager.create(note);
            return created;
        } catch (Exception e) {
            throw new RuntimeException("Unable to clone to the target service type.", e);
        }
    }

    /**
     * Handles canceling of a given Service.
     *
     * @param toBeCancelled the Service to be cancelled.
     * @param clone         the Service clone to be used for constructing a note on the cancelled Service pointing users to the
     *                      clone for future work.
     * @param <X>           the type of the Service we're cancelling.
     */
    public <X extends Service> void cancel(final X toBeCancelled, final T clone) {
        //service
        //get the manager of the service we're cancelling (might be the same as T, might be different).
        AbstractServiceManager<X> manager
                = (AbstractServiceManager<X>) serviceManagerFactory
                .getManager(ServiceType.fromServiceName(toBeCancelled.getType()));
        X retrieved = manager.retrieve(toBeCancelled.getId());
        retrieved.setActive(false);
        retrieved.setEligibleForUpdate(false);
        manager.edit(retrieved);
        //jeops
        //close all of them
        for (ServiceJeop serviceJeop : serviceJeopManager.getOpen(toBeCancelled.getId())) {
            serviceJeop.setEndDate(new Date());
            serviceJeopManager.edit(serviceJeop);
        }
        //note
        serviceMilestoneInstanceManager.create(toBeCancelled.getId(), "CANCELLED", new Date());
        ServiceNote note = new ServiceNote();
        note.setServiceId(retrieved.getId());
        note.setCategory(toBeCancelled.getType());
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        note.setCreatedBy(subject == null ? username : subject.getDisplayName());
        note.setCreatedById(subject.getId());
        note.setCreatedDate(new Date());
        String build = "Service cancelled and service has been continued on " +
                clone.getType() +
                " service " +
                clone.getId() +
                ".";
        note.setNote(build);
        serviceNoteManager.create(note);
    }

    /**
     * Handles copying of properties etc. from an existing entity of type X to a new MACD of type T,
     * both of which are types of Service.
     *
     * @param inventoryService the Service we're cloning.
     * @param orderType        the order type.
     * @param macdNote         the MACD note.
     * @param subjectId        the subject ID.
     * @return the created and populated target.
     */
    public T createMacd(final Service inventoryService, final List<String> orderType,
                        final List<String> subOrderType, final List<String> disconnectReason,
                        final List<String> projectName, final String macdNote, final Long subjectId) {
        try {
            boolean newService = subOrderType.stream().anyMatch(s -> s.contains("New Service"));
            boolean rateChange = subOrderType.stream().anyMatch(s -> s.contains("Same Service - Rate or term change"));
            Location inventoryLocation = locationManager.retrieve(inventoryService.getLocationId());
            Location provLocation = null;
            if (inventoryLocation.getProvisioningLocationId() != null) {
                provLocation = locationManager.retrieve(inventoryLocation.getProvisioningLocationId());
            }
            Location disconnectLocation = null;
            Location macLocation = null;

            if (provLocation != null) {
                List<Location> existingLocations
                        = locationManager.findByClientLocIdAndTenant(provLocation.getClientLocationId(),
                        provLocation.getTenantId());
                for (Location existingLocation : existingLocations) {
                    if (!existingLocation.isCurrentInventory()
                            && !TerminalLocationStatuses.getStatuses().contains(existingLocation.getStatus())) {
                        List<Service> existingServices = serviceManager.findByLocationId(existingLocation.getId());
                        for (Service existingService : existingServices) {
                            if (!Strings.isNullOrEmpty(existingService.getOrderType())
                                    && existingService.getOrderType().equals(OrderType.DISCONNECT.getOrderType())) {
                                disconnectLocation = existingLocation;
                            } else {
                                macLocation = existingLocation;
                            }
                        }
                    }
                }
            }

            Location newLocation = null;
            Order newOrder = new Order();
            if (orderType.contains(OrderType.DISCONNECT.getOrderType()) && disconnectLocation != null) {
                newLocation = disconnectLocation;
            } else if (macLocation != null && !orderType.contains(OrderType.DISCONNECT.getOrderType())) {
                //put this in because there was some old data in demo tenant that did not have the id's correctly set
                if (macLocation.getInventoryLocationId() == null) {
                    macLocation.setInventoryLocationId(inventoryLocation.getId());
                    macLocation = locationManager.editSuper(macLocation);
                }
                newLocation = macLocation;
            }

            if (newLocation != null) {
                newOrder = orderManager.retrieve(newLocation.getOrderId());
                if (newOrder.getProvisioner() != null) {
                    String header = "New MACD";
                    String body = "A new MACD has been added to Location #" + newLocation.getId();
                    notificationManager.create(newOrder.getProvisioner(), header, body, "priority_high", null);
                }
            } else {
                newLocation = new Location();
                Order inventoryOrder = orderManager.retrieve(inventoryLocation.getOrderId());
                if (inventoryOrder.getCompany() != null && inventoryOrder.getCompany().getParentCompany() != null) {
                    Company masterCompany = companyManager.retrieve(inventoryOrder.getCompany().getParentCompany().getId());
                    if (masterCompany.getProvisioner() != null) {
                        newOrder.setProvisioner(masterCompany.getProvisioner());
                    }
                    if (masterCompany.getI90ProjectManager() != null) {
                        newOrder.setVertekProjectManager(masterCompany.getI90ProjectManager());
                    }
                }
                newOrder.setCompany(inventoryOrder.getCompany());
                newOrder.setClientOrderId(inventoryOrder.getClientOrderId());
                newOrder.setStatus("Order Received");
                newOrder.setInventoryOrderId(inventoryOrder.getId());
                newOrder = orderManager.createMacd(newOrder);
                List<OrderContact> oldOrderContacts = orderContactManager.findByOrderId(inventoryOrder.getId());
                for (OrderContact oldOrderContact : oldOrderContacts) {
                    OrderContact newContact = new OrderContact();
                    newContact.setOrderId(newOrder.getId());
                    newContact.setEmail(oldOrderContact.getEmail());
                    newContact.setPhone(oldOrderContact.getPhone());
                    newContact.setType(oldOrderContact.getType());
                    newContact.setFirstName(oldOrderContact.getFirstName());
                    newContact.setLastName(oldOrderContact.getLastName());
                    newContact.setCompanyId(oldOrderContact.getCompanyId());
                    newContact.setActive(true);
                    newContact.setRole(oldOrderContact.getRole());
                    newContact.setTenantId(oldOrderContact.getTenantId());
                    newContact.setMasterCustomerId(oldOrderContact.getMasterCustomerId());
                    orderContactManager.create(newContact);
                }
                newLocation.setAddress1(inventoryLocation.getAddress1());
                if (!orderType.contains(OrderType.MOVE.getOrderType())
                        && !subOrderType.contains("Same Service - Change in floor/suite/MPOE/campus building")) {
                    newLocation.setAddress2(inventoryLocation.getAddress2());
                }
                newLocation.setCity(inventoryLocation.getCity());
                newLocation.setState(inventoryLocation.getState());
                newLocation.setPostalCode(inventoryLocation.getPostalCode());
                newLocation.setCountry(inventoryLocation.getCountry());
                newLocation.setClientLocationId(inventoryLocation.getClientLocationId());
                newLocation.setName(inventoryLocation.getName());
                newLocation.setBuildingType(inventoryLocation.getBuildingType());
                newLocation.setTimezone(inventoryLocation.getTimezone());
                newLocation.setPhoneNumber(inventoryLocation.getPhoneNumber());
                newLocation.setActive(false);
                newLocation.setProgressPercentage(0);
                newLocation.setLevelOfEffort("MACD");
                newLocation.setIcb(new BigDecimal(0));
                newLocation.setMrc(new BigDecimal(0));
                newLocation.setNrc(new BigDecimal(0));
                newLocation.setOsp(new BigDecimal(0));
                newLocation.setTenantId(inventoryLocation.getTenantId());
                newLocation.setParentLocationId(inventoryLocation.getId());
                newLocation.setMasterCustomerId(inventoryLocation.getMasterCustomerId());
                newLocation.setRecordSource(RecordSource.MANUAL_MACD.getName());
                newLocation.setInventoryLocationId(inventoryLocation.getId());

                inventoryOrder.setProvisioningOrderId(newOrder.getId());
                orderManager.editSuper(inventoryOrder);
                newLocation.setOrderId(newOrder.getId());
                newLocation = locationManager.create(newLocation);
                inventoryLocation.setProvisioningLocationId(newLocation.getId());
                locationManager.editSuper(inventoryLocation);

                List<LocationContact> oldLocationContacts = locationContactManager.retrieveByLocationId(inventoryLocation.getId());
                for (LocationContact oldContact : oldLocationContacts) {
                    LocationContact newContact = new LocationContact();
                    newContact.setLocationId(newLocation.getId());
                    newContact.setEmail(oldContact.getEmail());
                    newContact.setPhone(oldContact.getPhone());
                    newContact.setType(oldContact.getType());
                    newContact.setFirstName(oldContact.getFirstName());
                    newContact.setLastName(oldContact.getLastName());
                    newContact.setCompanyId(oldContact.getCompanyId());
                    newContact.setActive(true);
                    newContact.setRole(oldContact.getRole());
                    newContact.setTenantId(oldContact.getTenantId());
                    newContact.setMasterCustomerId(oldContact.getMasterCustomerId());
                    newContact.setParentContactId(oldContact.getId());
                    newContact.setNotes(oldContact.getNotes());
                    locationContactManager.create(newContact);
                }
            }

            //service
            // Get the class object for T
            Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            // Create a new instance of T
            T target = clazz.getDeclaredConstructor().newInstance();
            target.setLocationId(newLocation.getId());
            target.setOrderId(newOrder.getId());
            target.setOrderType(orderType.stream().collect(Collectors.joining(", ")));
            target.setSubOrderType(subOrderType.stream().collect(Collectors.joining(", ")));
            if (projectName != null && !projectName.isEmpty()) {
                target.setProjectName(projectName.stream().filter(Objects::nonNull).collect(Collectors.joining(", ")));
            }
            if (!newService) {
                target.setClientServiceId(inventoryService.getClientServiceId());
                target.setSubProductType(inventoryService.getSubProductType());
                if (!rateChange) {
                    target.setContractTerm(inventoryService.getContractTerm());
                }
                target.setProvider(inventoryService.getProvider());
                target.setSummaryBill(inventoryService.getSummaryBill());
                target.setProviderCircuitId(inventoryService.getProviderCircuitId());
                target.setInsideWiringRequired(inventoryService.getInsideWiringRequired());
                target.setMediaType(inventoryService.getMediaType());
                target.setNetStatus(inventoryService.getNetStatus());
                target.setLocationHours(inventoryService.getLocationHours());
                target.setProductInstallInterval(inventoryService.getProductInstallInterval());
                target.setTrunkGroup(inventoryService.getTrunkGroup());
                target.setConnectionHandoffType(inventoryService.getConnectionHandoffType());
                target.setTieDownInfo(inventoryService.getTieDownInfo());
                target.setType(inventoryService.getType());
                target.setCurrency(inventoryService.getCurrency());
                target.setBuildingStatus(inventoryService.getBuildingStatus());
                target.setDescription(inventoryService.getDescription());

                if (projectName != null && !projectName.isEmpty()) {
                    target.setProjectName(projectName.get(0));
                }

                setConditionalFields((T) inventoryService, target, orderType, subOrderType, disconnectReason);

                //set the service type specific fields
                macdServiceMapping((T) inventoryService, target, orderType, subOrderType);
                target.setInventoryServiceId(inventoryService.getId());
            }
            target.setActive(false);
            target.setFinalUpdate(false);
            target.setBillable(false);
            target.setProgressPercentage(0);
            target.setEligibleForUpdate(false);
            target.setUpdateClient(false);
            target.setParentServiceId(inventoryService.getId());
            target.setTenantId(inventoryService.getTenantId());
            target.setMasterCustomerId(inventoryService.getMasterCustomerId());
            target.setRecordSource(RecordSource.MANUAL_MACD.getName());
//            target.setLinkedBundledParentId(inventoryService.getLinkedBundledParentId());
//            target.setBundled(inventoryService.getBundled());
//            target.setLinked(inventoryService.getLinked());
//            target.setLinkedBundledParent(inventoryService.getLinkedBundledParent());

            target.setStatus("");
            T created = create(target);
            // the services are reversed here because the inventory service is creating the macd service
            // and wherever else this is called the ordering service is creating the inventory service
            duplicateBrokerage(inventoryService, created);
            duplicateCustomFields(inventoryService, created);
            inventoryService.setProvisioningServiceId(created.getId());
            duplicateEquipment(inventoryService, created);
            super.edit((T) inventoryService);

            if (!Strings.isNullOrEmpty(macdNote)) {
                //note
                ServiceNote note = new ServiceNote();
                note.setServiceId(created.getId());
                note.setCategory(created.getType());

                String username = null;
                Subject subject = null;
                if (subjectId == null) {
                    String loggedInUser = SecurityUtils.getLoggedInUser();
                    if (!SecurityUtils.SCHEDULER.equals(loggedInUser)) {
                        subject = subjectManager.findByUsername(loggedInUser);
                        loggedInUser = subject.getDisplayName();
                    }
                    username = loggedInUser;
                } else {
                    subject = subjectManager.retrieve(subjectId);
                    username = subject.getDisplayName();
                }

                note.setCreatedBy(username);
                note.setCreatedById(subject.getId());
                note.setCreatedDate(new Date());
                note.setNote(macdNote);
                serviceNoteManager.create(note);
            }

            bundleLinkMacd(inventoryService);
            return created;
        } catch (Exception e) {
            LOGGER.debug("Unable to create a MACD. " + e.getMessage());
            throw new RuntimeException("Unable to create a MACD for this reason: " + e.getMessage(), e);
        }

    }

    private void setConditionalFields(T source, T target, List<String> orderType,
                                      List<String> subOrderType, List<String> disconnectReason) {
        if (orderType.contains(OrderType.DISCONNECT.getOrderType())) {
            target.setDisconnectReason(disconnectReason.stream().collect(Collectors.joining(", ")));
            target.setMrc(BigDecimal.valueOf(0));
            target.setNrc(BigDecimal.valueOf(0));
            target.setAnnualRecurringCost(BigDecimal.valueOf(0));
            target.setHasIcb(false);
            target.setIcb(BigDecimal.valueOf(0));
            target.setHasOsp(false);
            target.setOsp(BigDecimal.valueOf(0));
            target.setOspConstIntervalEst(null);
            target.setMacdCostChange(BigDecimal.ZERO.subtract(source.getMrc()));
            target.setMacdRevenueChange(BigDecimal.ZERO.subtract(source.getMrr()));
        } else {
            target.setProviderOrderNum(source.getProviderOrderNum());
        }

        if ((!orderType.contains(OrderType.DISCONNECT.getOrderType()))
                && !subOrderType.contains("Same Service - Change in floor/suite/MPOE/campus building")
                && !subOrderType.contains("Static IP Add")
                && !subOrderType.contains("Billing Party Change - Outbound")
                && !subOrderType.contains("Billing Party Change - Inbound")
                && !subOrderType.contains("Same Service - Speed change")) {
            if (!subOrderType.contains("Same Service - Rate or Term Change")) {
                target.setPoNumber(source.getPoNumber());
            }
            target.setProviderOrderNum(source.getProviderOrderNum());
        }

        if (!subOrderType.contains("Same Service - Change in floor/suite/MPOE/campus building")) {
            target.setDmarc(source.getDmarc());
        }

        if (!subOrderType.contains("Billing Party Change - Inbound")
                && !subOrderType.contains("Billing Party Change - Outbound")) {
            target.setServiceBilledTo(source.getServiceBilledTo());
        }

        if (!orderType.contains(OrderType.DISCONNECT.getOrderType())
                && !subOrderType.contains("Static IP Add")
                && !subOrderType.contains("Same Service - Speed change")
                && !subOrderType.contains("Same Service - Rate or term change")) {
            target.setMrc(source.getMrc());
            target.setNrc(source.getNrc());
            target.setAnnualRecurringCost(source.getAnnualRecurringCost());
            target.setHasIcb(source.isHasIcb());
            target.setIcb(source.getIcb());
            target.setHasOsp(source.isHasOsp());
            target.setOsp(source.getOsp());
            target.setOspConstIntervalEst(source.getOspConstIntervalEst());
        }

        if (!subOrderType.contains("Same Service - Rate or term change")) {
            target.setContractSignedDate(source.getContractSignedDate());
            target.setCircuitTermEndDate(source.getCircuitTermEndDate());
        }


        if (!subOrderType.contains("Same Service - Speed change")) {
            target.setSpeed(source.getSpeed());
            target.setDownloadSpeed(source.getDownloadSpeed());
            target.setUploadSpeed(source.getUploadSpeed());
        }

        if (!subOrderType.contains("Static IP Add")) {
            target.setIpFormat(source.getIpFormat());
            target.setAdditionalIpBlockRequired(source.getAdditionalIpBlockRequired());
            target.setAdditionalIpBlock(source.getAdditionalIpBlock());
            target.setWanIps(source.getWanIps());
            target.setWanGateway(source.getWanGateway());
            target.setWanSubnet(source.getWanSubnet());
            target.setLanBlock(source.getLanBlock());
            target.setLanIps(source.getLanIps());
            target.setLanGateway(source.getLanGateway());
            target.setLanSubnet(source.getLanSubnet());
            target.setDns1(source.getDns1());
            target.setDns2(source.getDns2());
        }
    }

    protected abstract void macdServiceMapping(final T source, final T target,
                                               final List<String> orderType, final List<String> subOrderType);


    public <X extends Service> X updateInventory(final X existingService, Boolean setActive) {
        //only proccess if eligible for update (has passed the first inventory milestone), has not had a final update and is not inventory
        if (!existingService.isEligibleForInventory()
                || existingService.getFinalUpdate()
                || existingService.isCurrentInventory()) {
            return existingService;
        }

        AbstractServiceManager<Service> manager = (AbstractServiceManager<Service>) serviceManagerFactory
                .getManager(ServiceType.fromServiceName(existingService.getType()));
        T inventoryService = null;
        T updateService = (T) manager.retrieve(existingService.getId());


        if (updateService.getInventoryServiceId() != null) {
            try {
                //don't update inventory Location or Order if the service is a disconnect and was created from a New Service MAC
                //only update if the disconnect is a standalone and there is only one service for that location
                boolean updateLocInventory = true;
                Location existingLocation = locationManager.retrieve(updateService.getLocationId());
                if (OrderType.DISCONNECT.getOrderType().equals(updateService.getOrderType())) {
                    List<Service> inventoryServices = serviceManager.findByLocationId(existingLocation.getInventoryLocationId());
                    //check to see if the disconnect is a stand alone service
                    if (inventoryServices.size() > 1) {
                        updateLocInventory = false;
                    }
                    //set the active and billable flags to false
                    setActive = false;
                }
                if (updateLocInventory) {
                    existingLocation.setEligibleForInventory(true);
                    Order existingOrder = orderManager.retrieve(updateService.getOrderId());
                    existingOrder.setEligibleForInventory(true);
                    locationManager.updateInventory(existingLocation);

                    //because on the provisioning side there could be multiple Locations on an Order and on the
                    //inventory side there can only be one Location on an Order there could be multiple Inventory Orders
                    //with the same provisioning order id.  We need to update all of them.
                    List<Order> inventoryOrders = orderManager.findInvByProvisioningId(existingOrder.getId());
                    for (Order inventoryOrder : inventoryOrders) {
                        orderManager.updateInventory(existingOrder, inventoryOrder);
                    }
                }

                inventoryService = (T) manager.retrieve(updateService.getInventoryServiceId());

                if (inventoryService != null) {
                    //do the Service table first then the Service Type specific fields
                    for (Field field : Service.class.getDeclaredFields()) {
                        if (!field.getName().equalsIgnoreCase("isCurrentInventory")
                                && !field.getName().equalsIgnoreCase("provisioningServiceId")
                                && !field.getName().equalsIgnoreCase("inventoryServiceId")
                                && !field.getName().equalsIgnoreCase("masterCustomerId")
                                && !field.getName().equalsIgnoreCase("id")
                                && !field.getName().equalsIgnoreCase("locationId")
                                && !field.getName().equalsIgnoreCase("orderId")
                                && !field.getName().equalsIgnoreCase("parentServiceId")
                                && !field.getName().equalsIgnoreCase("finalUpdate")
                                && !field.getName().equalsIgnoreCase("eligibleForInventory")
                                && !field.getName().equalsIgnoreCase("updateClient")
                                && !field.getName().equalsIgnoreCase("elgibleForUpdate")
                                && !field.getName().equalsIgnoreCase("billable")
                                && !field.getName().equalsIgnoreCase("active")
                                && !field.getName().equalsIgnoreCase("linked")
                                && !field.getName().equalsIgnoreCase("bundled")
                                && !field.getName().equalsIgnoreCase("linkedBundledParent")
                                && !field.getName().equalsIgnoreCase("linkedBundledParentId")
                                && !field.getName().equalsIgnoreCase("version")) {
                            field.setAccessible(true);
                            field.set(inventoryService, field.get(updateService));
                        }
                    }

                    if (setActive != null) {
                        inventoryService.setActive(setActive);
                        inventoryService.setBillable(setActive);
                        if (!setActive) {
                            List<Service> invServices = serviceManager.findByLocationId(inventoryService.getLocationId());
                            boolean locActive = false;
                            for (Service invService : invServices) {
                                if (invService.isActive()) {
                                    locActive = true;
                                    break;
                                }
                            }
                            if (!locActive) {
                                Location location = locationManager.retrieve(inventoryService.getLocationId());
                                location.setActive(false);
                                locationManager.editSuper(location);
                                LocationMilestoneInstance lrfi = locationMilestoneInstanceManager.retrieveCurrentMilestoneByCode(location.getId(), "REMOVED_FROM_INVENTORY");
                                if (lrfi == null) {
                                    locationMilestoneInstanceManager.create(location.getId(), "REMOVED_FROM_INVENTORY", new Date());
                                }
                            }
                        }
                    }
                    //do the Service Type specific fields
                    for (Field field : inventoryService.getClass().getDeclaredFields()) {
                        if (!field.getName().equalsIgnoreCase("zAddress")
                                && !field.getName().equalsIgnoreCase("version")) {
                            field.setAccessible(true);
                            field.set(inventoryService, field.get(updateService));
                        }
                    }

                    inventoryService = super.edit(inventoryService);

                    deleteSupportingData(inventoryService);

                    duplicateMilestones(updateService, inventoryService);
                    copyNotesAndAttachments(updateService, inventoryService);
                    duplicateActivationData(updateService, inventoryService);
                    duplicateBrokerage(updateService, inventoryService);
                    duplicateCustomFields(updateService, inventoryService);
                    duplicateEquipment(updateService, inventoryService);

                    //only set final update if the call is coming from the milestone being set
                    // service edit sets it to null, the milestone sets it to true on complete or false if the milestone is being removed
                    if (setActive != null) {
                        updateService.setFinalUpdate(TerminalServiceStatuses.getStatuses().contains(existingService.getStatus()));
                    }
                    updateService.setInventoryServiceId(inventoryService.getId());
                    updateService = super.edit(updateService);

                    bundleLinkInventory(updateService);

                }
            } catch (Exception e) {
                LOGGER.debug("Unable to update an Inventory record.", e);
                throw new RuntimeException("Unable to update an Inventory record.", e);
            }
            return (X) updateService;
        } else {
            return existingService;
        }
    }

    private void deleteSupportingData(T inventoryService) {
        List<ServiceMilestoneInstance> milestones = serviceMilestoneInstanceManager.findByServiceId(inventoryService.getId());
        for (ServiceMilestoneInstance milestone : milestones) {
            if (!milestone.getMilestone().getCode().equalsIgnoreCase("CREATED")) {
                serviceMilestoneInstanceManager.remove(milestone.getId());
            }
        }

        List<ServiceNote> notes = serviceNoteManager.findByServiceId(inventoryService.getId());
        for (ServiceNote note : notes) {
            if (note.getParentNoteId() != null) {
                serviceNoteManager.remove(note.getId());
            }
        }

        //we don't delete file attachments from inventory, we synchronize them with the provisioning service
        //because the inventory is going to be the repository of all the files attachments for the service


        List<ActivationAttempt> attempts = activationAttemptManager.findForInventory(inventoryService.getId());
        for (ActivationAttempt attempt : attempts) {
            List<ActivationIssue> issues = activationIssueManager.findByAttemptId(attempt.getId());
            for (ActivationIssue issue : issues) {
                activationIssueManager.remove(issue.getId());
            }
            activationAttemptManager.remove(attempt.getId());
        }

        List<ActivationSchedule> schedules = activationScheduleManager.findByServiceId(inventoryService.getId());
        for (ActivationSchedule schedule : schedules) {
            activationScheduleManager.remove(schedule.getId());
        }
        ServiceBrokerage serviceBrokerage = serviceBrokerageManager.findByServiceId(inventoryService.getId());
        if (serviceBrokerage != null) {
            serviceBrokerageManager.remove(serviceBrokerage.getId());
        }
        List<ServiceCustomFieldValue> customFields = serviceCustomFieldValueManager.findByRecordId(inventoryService.getId());
        for (ServiceCustomFieldValue customField : customFields) {
            serviceCustomFieldValueManager.remove(customField.getId());
        }
        List<ServiceEquipment> equipment = serviceEquipmentManager.findByServiceId(inventoryService.getId());
        for (ServiceEquipment equip : equipment) {
            serviceEquipmentManager.remove(equip.getId());
        }
    }

    public T createInventory(final Service existingService) {
        Order existingOrder = orderManager.retrieve(existingService.getOrderId());
        Location existingLocation = locationManager.retrieve(existingService.getLocationId());
        Location inventoryLocation = null;
        T inventoryService = null;
        //Check to see if there is an inventory item for this location
        if (existingLocation.getInventoryLocationId() == null) {
            inventoryLocation = locationManager.findInvByClientLocIdAndTenant(
                    existingLocation.getClientLocationId(), existingService.getTenantId());
        } else {
            inventoryLocation = locationManager.retrieve(existingLocation.getInventoryLocationId());
        }
        try {
            if (inventoryLocation == null) {
                //Create Order
                Order inventoryOrder = orderManager.createInventoryOrder(existingOrder);

                //create Location
                inventoryLocation = locationManager.createInventoryLocation(existingLocation, inventoryOrder.getId());
            } else {
                existingLocation.setInventoryLocationId(inventoryLocation.getId());
                existingLocation.setEligibleForInventory(true);
                existingLocation.setFinalUpdate(false);
                locationManager.updateInventory(existingLocation);
                existingOrder.setInventoryOrderId(inventoryLocation.getOrderId());
                existingOrder.setEligibleForInventory(true);
                //because on the provisioning side there could be multiple Locations on an Order and on the
                //inventory side there can only be one Location on an Order there could be multiple Inventory Orders
                //with the same provisioning order id.  We need to update all of them.
                List<Order> inventoryOrders = orderManager.findInvByProvisioningId(existingOrder.getId());
                for (Order inventoryOrder : inventoryOrders) {
                    orderManager.updateInventory(existingOrder, inventoryOrder);
                }
            }

            AbstractServiceManager<Service> manager = (AbstractServiceManager<Service>) serviceManagerFactory
                    .getManager(ServiceType.fromServiceName(existingService.getType()));

            Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            // Create a new instance of T
            inventoryService = clazz.getDeclaredConstructor().newInstance();
            //do the Service table first then the Service Type specific fields
            for (Field field : Service.class.getDeclaredFields()) {
                if (!field.getName().equalsIgnoreCase("isCurrentInventory")
                        && !field.getName().equalsIgnoreCase("provisioningServiceId")
                        && !field.getName().equalsIgnoreCase("inventoryServiceId")
                        && !field.getName().equalsIgnoreCase("id")
                        && !field.getName().equalsIgnoreCase("locationId")
                        && !field.getName().equalsIgnoreCase("orderId")
                        && !field.getName().equalsIgnoreCase("parentServiceId")
                        && !field.getName().equalsIgnoreCase("eligibleForInventory")
                        && !field.getName().equalsIgnoreCase("updateClient")
                        && !field.getName().equalsIgnoreCase("elgibleForUpdate")
                        && !field.getName().equalsIgnoreCase("billable")
                        && !field.getName().equalsIgnoreCase("active")
                        && !field.getName().equalsIgnoreCase("linked")
                        && !field.getName().equalsIgnoreCase("bundled")
                        && !field.getName().equalsIgnoreCase("linkedBundledParent")
                        && !field.getName().equalsIgnoreCase("linkedBundledParentId")
                        && !field.getName().equalsIgnoreCase("finalUpdate")
                        && !field.getName().equalsIgnoreCase("version")) {
                    field.setAccessible(true);
                    field.set(inventoryService, field.get(existingService));
                }
            }
            //do the Service Type specific fields
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getName().equalsIgnoreCase("zAddress")
                        && !field.getName().equalsIgnoreCase("version")) {
                    field.setAccessible(true);
                    field.set(inventoryService, field.get(existingService));
                }
            }

            inventoryService.setProvisioningServiceId(existingService.getId());
            inventoryService.setParentServiceId(existingService.getId());
            inventoryService.setCurrentInventory(true);
            inventoryService.setActive(true);
            inventoryService.setBillable(true);
            inventoryService.setUpdateClient(false);
            inventoryService.setEligibleForUpdate(false);
            inventoryService.setTenantId(inventoryLocation.getTenantId());
            inventoryService.setLocationId(inventoryLocation.getId());
            inventoryService.setOrderId(inventoryLocation.getOrderId());
            inventoryService = create(inventoryService);
            serviceMilestoneInstanceManager.createNoEventHandler(inventoryService.getId(),
                    "CREATED", new Date());

            duplicateMilestones(existingService, inventoryService);
            copyNotesAndAttachments(existingService, inventoryService);
            duplicateActivationData(existingService, inventoryService);
            duplicateBrokerage(existingService, inventoryService);
            duplicateCustomFields(existingService, inventoryService);
            duplicateEquipment(existingService, inventoryService);

            //set the inventory service id on the existing service
            existingService.setInventoryServiceId(inventoryService.getId());
            //this flag is for MACDs so the updates don't start happening until the inventory milestone is set
            //but also needs to be set for new orders
            existingService.setEligibleForInventory(true);
            super.edit((T) existingService);

            bundleLinkInventory(existingService);

        } catch (Exception e) {
            LOGGER.debug("Unable to create an Inventory record.", e);
            throw new RuntimeException("Unable to create an Inventory record.", e);
        }
        return inventoryService;
    }

    private void duplicateEquipment(final Service existingService, final T inventoryService)
            throws IllegalAccessException {
        List<ServiceEquipment> equipment = serviceEquipmentManager.findByServiceId(existingService.getId());
        for (ServiceEquipment equip : equipment) {
            ServiceEquipment newEquip = new ServiceEquipment();
            for (Field equipField : Equipment.class.getDeclaredFields()) {
                if (!equipField.getName().equalsIgnoreCase("id")
                        && !equipField.getName().equalsIgnoreCase("serviceId")) {
                    equipField.setAccessible(true);
                    equipField.set(newEquip, equipField.get(equip));
                }
                newEquip.setServiceId(inventoryService.getId());
                serviceEquipmentManager.create(newEquip);
            }
        }
    }

    private void duplicateCustomFields(final Service existingService, final T inventoryService)
            throws IllegalAccessException {
        List<ServiceCustomFieldValue> customFields
                = serviceCustomFieldValueManager.findByRecordId(existingService.getId());
        for (ServiceCustomFieldValue customField : customFields) {
            ServiceCustomFieldValue newCustomField = new ServiceCustomFieldValue();
            for (Field customFieldField : CustomFieldValue.class.getDeclaredFields()) {
                if (!customFieldField.getName().equalsIgnoreCase("id")
                        && !customFieldField.getName().equalsIgnoreCase("serviceId")) {
                    customFieldField.setAccessible(true);
                    customFieldField.set(newCustomField, customFieldField.get(customField));
                }
            }
            newCustomField.setServiceId(inventoryService.getId());
            serviceCustomFieldValueManager.create(newCustomField);
        }
    }

    /**
     * Handles finding and copying of service brokerage info.
     *
     * @param existingService  the existing service.
     * @param inventoryService the inventory service to create brokerage information for.
     * @throws IllegalAccessException if the fields can't be accessed.
     */
    private void duplicateBrokerage(final Service existingService, final T inventoryService)
            throws IllegalAccessException {
        ServiceBrokerage serviceBrokerage = serviceBrokerageManager.findByServiceId(existingService.getId());
        if (serviceBrokerage != null) {
            ServiceBrokerage newBrokerage = new ServiceBrokerage();
            for (Field brokerageField : ServiceBrokerage.class.getDeclaredFields()) {
                if (!brokerageField.getName().equalsIgnoreCase("id")
                        && !brokerageField.getName().equalsIgnoreCase("serviceId")
                        && !brokerageField.getName().equalsIgnoreCase("masterCustomerId")
                        && !brokerageField.getName().equalsIgnoreCase("version")) {
                    brokerageField.setAccessible(true);
                    brokerageField.set(newBrokerage, brokerageField.get(serviceBrokerage));
                }
            }
            newBrokerage.setServiceId(inventoryService.getId());
            newBrokerage.setMasterCustomerId(inventoryService.getMasterCustomerId());
            serviceBrokerageManager.create(newBrokerage);
        }
    }

    private void bundleLinkInventory(Service existingService) {
        //check to see if the incoming service is linked or bundled and if so check to see if any other services
        //from that association are in inventory and if so update link or bundle them
        if (existingService.getLinked() || existingService.getBundled()) {
            List<Service> associatedServices = serviceManager.findAssociatedByParentId(existingService.getLinkedBundledParentId());
            boolean doBundle = false;
            boolean doLink = false;
            //loop through them to see if any besides existingService are in inventory
            for (Service associatedService : associatedServices) {
                if (!Objects.equals(associatedService.getId(), existingService.getId())
                        && associatedService.getInventoryServiceId() != null) {
                    doBundle = associatedService.getBundled();
                    doLink = associatedService.getLinked();
                }
            }
            if (doLink || doBundle) {
                // get the parent from the Provisioning side and use the inventory id to get the inventory record
                // and that will be the parent for the linked or bundled services
                Service parentProvisioning = serviceManager.retrieve(existingService.getLinkedBundledParentId());
                // only do if the parent is in Inventory
                if (parentProvisioning.getInventoryServiceId() != null) {
                    Service parentInventory = serviceManager.retrieve(parentProvisioning.getInventoryServiceId());
                    //get the associated services from the inventory side using the associated services inventory ids and update
                    for (Service associatedService : associatedServices) {
                        if (associatedService.getInventoryServiceId() != null) {
                            Service associatedInventory = serviceManager.retrieve(associatedService.getInventoryServiceId());
                            associatedInventory.setLinked(doLink);
                            associatedInventory.setBundled(doBundle);
                            associatedInventory.setLinkedBundledParentId(parentInventory.getId());
                            associatedInventory.setLinkedBundledParent(associatedService.getLinkedBundledParent());
                            super.edit((T) associatedInventory);
                        }
                    }
                }
            }
        }
    }

    private void bundleLinkMacd(Service inventoryService) {
        //check to see if the incoming service is linked or bundled and if so check to see if any other services
        //from that association are on the same location in provisioning and if so link or bundle them
        if (inventoryService.getLinked() || inventoryService.getBundled()) {
            List<Service> associatedInventoryServices = serviceManager.findAssociatedByParentId(inventoryService.getLinkedBundledParentId());
            //get the provisioning service to get the Location ID
            Service provisioningService = serviceManager.retrieve(inventoryService.getProvisioningServiceId());
            boolean doBundle = false;
            boolean doLink = false;
            List<Service> servicesToUpdate = new ArrayList<>();
            Long parentId = null;
            for (Service associatedService : associatedInventoryServices) {
                if (associatedService.getProvisioningServiceId() != null) {
                    Service service = serviceManager.retrieve(associatedService.getProvisioningServiceId());
                    if (Objects.equals(service.getLocationId(), provisioningService.getLocationId())) {
                        servicesToUpdate.add(service);
                        if (associatedService.getLinkedBundledParent()) {
                            parentId = service.getId();
                        }
                        doBundle = associatedService.getBundled();
                        doLink = associatedService.getLinked();
                    }
                }
            }
            if (servicesToUpdate.size() > 1) {
                for (Service serviceToUpdate : servicesToUpdate) {
                    serviceToUpdate.setLinked(doLink);
                    serviceToUpdate.setBundled(doBundle);
                    serviceToUpdate.setLinkedBundledParentId(parentId);
                    serviceToUpdate.setLinkedBundledParent(Objects.equals(serviceToUpdate.getId(), parentId));
                    super.edit((T) serviceToUpdate);
                }
            }
        }
    }

    private void duplicateActivationData(Service existingService, T inventoryService) {
        //for this one we need to start with the schedule and for each schedule then do the activation,
        // not doing custom fields and equipment and dispatch for now
        // and then for each activation do its issues and requirements
        try {
            List<ActivationSchedule> schedules = activationScheduleManager.findByServiceId(existingService.getId());
            for (ActivationSchedule schedule : schedules) {
                ActivationSchedule newSchedule = new ActivationSchedule();
                for (Field schedField : ActivationSchedule.class.getDeclaredFields()) {
                    if (!schedField.getName().equalsIgnoreCase("id")
                            && !schedField.getName().equalsIgnoreCase("serviceId")
                            && !schedField.getName().equalsIgnoreCase("version")
                            && !schedField.getName().equalsIgnoreCase("customFields")
                            && !schedField.getName().equalsIgnoreCase("equipment")
                            && !schedField.getName().equalsIgnoreCase("masterCustomerId")
                            && !schedField.getName().equalsIgnoreCase("dispatches")) {
                        schedField.setAccessible(true);
                        schedField.set(newSchedule, schedField.get(schedule));
                    }
                }
                newSchedule.setServiceId(inventoryService.getId());
                newSchedule.setTenantId(inventoryService.getTenantId());
                newSchedule.setMasterCustomerId(inventoryService.getMasterCustomerId());
                newSchedule = activationScheduleManager.createForInventory(newSchedule);

                //Do activation attempts
                List<ActivationAttempt> attempts = activationAttemptManager.findByAllByScheduleId(schedule.getId());
                for (ActivationAttempt attempt : attempts) {
                    ActivationAttempt newAttempt = new ActivationAttempt();
                    for (Field attemptField : ActivationAttempt.class.getDeclaredFields()) {
                        if (!attemptField.getName().equalsIgnoreCase("id")
                                && !attemptField.getName().equalsIgnoreCase("scheduleId")
                                && !attemptField.getName().equalsIgnoreCase("version")
                                && !attemptField.getName().equalsIgnoreCase("ActivationAttemptRequirement")
                                && !attemptField.getName().equalsIgnoreCase("masterCustomerId")
                                && !attemptField.getName().equalsIgnoreCase("requirements")) {
                            attemptField.setAccessible(true);
                            attemptField.set(newAttempt, attemptField.get(attempt));
                        }
                    }
                    newAttempt.setActivationScheduleId(newSchedule.getId());
                    newAttempt.setServiceId(inventoryService.getId());
                    newAttempt.setTenantId(inventoryService.getTenantId());
                    newAttempt.setMasterCustomerId(inventoryService.getMasterCustomerId());
                    newAttempt = activationAttemptManager.createForInventory(newAttempt);
                    //get the requirements
                    if (attempt.getRequirements() != null) {
                        List<ActivationAttemptRequirement> requirements = attempt.getRequirements();
                        for (ActivationAttemptRequirement requirement : requirements) {
                            ActivationAttemptRequirement newRequirement = new ActivationAttemptRequirement();
                            newRequirement.setActivationAttemptId(newAttempt.getId());
                            newRequirement.setRequirement(requirement.getRequirement());
                            newRequirement.setComplete(requirement.isComplete());
                            newRequirement.setComment(requirement.getComment());
                            newAttempt.getRequirements().add(newRequirement);
                        }
                        newAttempt = activationAttemptManager.editForInventory(newAttempt);
                    }
                    //do the issues
                    List<ActivationIssue> issues = activationIssueManager.findByAttemptId(attempt.getId());
                    for (ActivationIssue issue : issues) {
                        ActivationIssue newIssue = new ActivationIssue();
                        for (Field issueField : ActivationIssue.class.getDeclaredFields()) {
                            if (!issueField.getName().equalsIgnoreCase("id")
                                    && !issueField.getName().equalsIgnoreCase("activationAttemptId")
                                    && !issueField.getName().equalsIgnoreCase("masterCustomerId")
                                    && !issueField.getName().equalsIgnoreCase("version")) {
                                issueField.setAccessible(true);
                                issueField.set(newIssue, issueField.get(issue));
                            }
                        }
                        newIssue.setActivationAttemptId(newAttempt.getId());
                        newIssue.setMasterCustomerId(inventoryService.getMasterCustomerId());
                        activationIssueManager.create(newIssue);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.debug("Unable to create an Inventory record activation attempts. " + e.getMessage());
            throw new RuntimeException("Unable to create an Inventory record." + e.getMessage());
        }
    }

    private void copyNotesAndAttachments(Service existingService, T inventoryService) {
        try {
            List<ServiceNote> notes = serviceNoteManager.findByServiceId(existingService.getId());
            for (ServiceNote note : notes) {
                ServiceNote newNote = new ServiceNote();
                newNote.setNote(note.getNote());
                newNote.setCategory(note.getCategory());
                newNote.setCreatedDate(note.getCreatedDate());
                newNote.setCreatedBy(note.getCreatedBy());
                newNote.setCreatedById(note.getCreatedById());
                newNote.setInternalOnly(note.getInternalOnly());
                newNote.setTenantId(inventoryService.getTenantId());
                newNote.setUpdateClient(false);
                newNote.setServiceId(inventoryService.getId());
                newNote.setMasterCustomerId(inventoryService.getMasterCustomerId());
                newNote.setParentNoteId(note.getId());
                serviceNoteManager.create(newNote);
            }

            //for file attachments check to synchronize the data with the service
            //duplicate the attachment if it doesn't exist, update it if it does
            List<ServiceFileAttachment> attachments = serviceFileAttachmentManager.findByServiceId(existingService.getId());
            for (ServiceFileAttachment attachment : attachments) {
                ServiceFileAttachment inventoryAttachment = serviceFileAttachmentManager.findByParentId(attachment.getId());
                if (inventoryAttachment == null) {
                    inventoryAttachment = new ServiceFileAttachment();
                }
                for (Field attachmentField : FileAttachment.class.getDeclaredFields()) {
                    if (!attachmentField.getName().equalsIgnoreCase("id")
                            && !attachmentField.getName().equalsIgnoreCase("serviceId")
                            && !attachmentField.getName().equalsIgnoreCase("parentFileAttachmentId")
                            && !attachmentField.getName().equalsIgnoreCase("content")
                            && !attachmentField.getName().equalsIgnoreCase("version")
                            && !attachmentField.getName().equalsIgnoreCase("tenantId")
                            && !attachmentField.getName().equalsIgnoreCase("masterCustomerId")
                            && !attachmentField.getName().equalsIgnoreCase("parentOwnerId")) {
                        attachmentField.setAccessible(true);
                        attachmentField.set(inventoryAttachment, attachmentField.get(attachment));
                    }
                }
                inventoryAttachment.setTenantId(inventoryService.getTenantId());
                inventoryAttachment.setServiceId(inventoryService.getId());
                inventoryAttachment.setParentFileAttachmentId(attachment.getId());
                inventoryAttachment.setParentOwnerId(attachment.getServiceId());
                inventoryAttachment.setMasterCustomerId(inventoryService.getMasterCustomerId());
                FileAttachmentContent content = new FileAttachmentContent(attachment.getContent().getData());
                inventoryAttachment.setContent(content);
                if (inventoryAttachment.getId() == null) {
                    serviceFileAttachmentManager.create(inventoryAttachment);
                } else {
                    serviceFileAttachmentManager.edit(inventoryAttachment);
                }
            }


            //check the inventory for deleted file attachments from the existing provisioning order and remove from inventory
            //we only check the existing location and not any of the historical locations because at some point we may purge
            //the file attachments from completed locations after a period of time
            List<ServiceFileAttachment> inventoryServiceFileAttachments =
                    serviceFileAttachmentManager.findByServiceId(inventoryService.getId());
            for (ServiceFileAttachment inventoryServiceFileAttachment : inventoryServiceFileAttachments) {
                if (inventoryServiceFileAttachment.getParentOwnerId() != null && inventoryServiceFileAttachment.getParentFileAttachmentId() != null) {
                    ServiceFileAttachment existingFileAttachment = serviceFileAttachmentManager.findByInventoryRecordId(inventoryServiceFileAttachment.getParentOwnerId(), inventoryServiceFileAttachment.getParentFileAttachmentId());
                    if (existingFileAttachment == null) {
                        serviceFileAttachmentManager.remove(inventoryServiceFileAttachment.getId());
                    }
                } else {
                    serviceFileAttachmentManager.remove(inventoryServiceFileAttachment.getId());
                }
            }

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void duplicateMilestones(Service existingService, T inventoryService) {
        //duplicate milestones
        List<ServiceMilestoneInstance> milestones = serviceMilestoneInstanceManager.findByServiceId(existingService.getId());
        for (ServiceMilestoneInstance milestone : milestones) {
            if (!milestone.getMilestone().getCode().equalsIgnoreCase("CREATED")) {
                new ServiceMilestoneInstance();
                ServiceMilestoneInstance newMilestone;
                List<ServiceMilestoneInstance> invMilestones = serviceMilestoneInstanceManager.findByServiceId(existingService.getId());
                newMilestone = serviceMilestoneInstanceManager.createNoEventHandler(inventoryService.getId(),
                        milestone.getMilestone().getCode(), milestone.getMilestoneDate());
                newMilestone.setParentMilestoneInstanceId(milestone.getId());
                serviceMilestoneInstanceManager.editNoEventHandler(newMilestone);
            }
        }
    }

    public void editSuper(Service inventoryService) {
        super.edit((T) inventoryService);
    }

    /**
     * Pulls data from CRM for the given service and updates the service with the new data.
     * @param existing the existing service
     * @return
     */
    public T pullFromCrm(final T existing) {
        LOGGER.debug("Pulling from CRM for service: {}", existing.getId());
        String response = dataverse.pullFromCrm(existing.getOpportunityNum().trim());
        CrmIntegration crmIntegration = new CrmIntegration();
        crmIntegration.setOpportunityNum(existing.getOpportunityNum());
        crmIntegration.setResponse(response);
        crmIntegration.setRequestDate(new Date());
        crmIntegrationManager.create(crmIntegration);
        if (response != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response);
                if (!jsonNode.get("new_pricingmrr").isNull()) {
                    existing.setMrr(jsonNode.get("new_pricingmrr").decimalValue());
                } else {
                    existing.setMrr(BigDecimal.valueOf(0));
                }
                if (!jsonNode.get("new_pricingnrc").isNull()) {
                    existing.setNrr(jsonNode.get("new_pricingnrc").decimalValue());
                } else {
                    existing.setNrr(BigDecimal.valueOf(0));
                }
                if (!jsonNode.get("new_contractterminmonths").isNull()) {
                    int months = jsonNode.get("new_contractterminmonths").asInt();
                    if (months == 1) {
                        existing.setContractTerm("MTM");
                    } else {
                        existing.setContractTerm(months + " Month");
                    }
                } else {
                    existing.setContractTerm(null);
                }
                if (!jsonNode.get("actualclosedate").isNull()) {
                    existing.setContractSignedDate(new SimpleDateFormat("yyyy-MM-dd").parse(jsonNode.get("actualclosedate").asText()));
                } else {
                    existing.setContractSignedDate(null);
                }

            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return edit(existing);
    }




    /**
     * Delete a service
     * @param id serviceId.
     */
    public <T extends Service> void deleteService(final Long id) {
        //service_surcharge
        //surcharge
        List<ServiceSurcharge> surcharges = surchargeManager.findByServiceId(id);
        surcharges.forEach(surcharge -> {
            surchargeManager.remove(surcharge.getId());
        });

        //activation_attempt_requirement
        //activation_issue
        //activation_attempt
        List<ActivationAttempt> attempts = activationAttemptManager.findForDeletion(id);
        for (ActivationAttempt attempt : attempts) {
            List<ActivationIssue> issues = activationIssueManager.findByAttemptId(attempt.getId());
            issues.forEach(issue -> {
                activationIssueManager.remove(issue.getId());
            });
            activationAttemptManager.remove(attempt.getId());
        }

        //activation_schedule_custom
        //activation_schedule_equipment
        //activation_schedule
        List<ActivationSchedule> schedules = activationScheduleManager.findByServiceId(id);
        schedules.forEach(schedule -> {
            activationScheduleManager.remove(schedule.getId());
        });

        //service_custom_field_value
        //custom_field_value
        List<ServiceCustomFieldValue> serviceCustomFieldValues = serviceCustomFieldValueManager.findByRecordId(id);
        serviceCustomFieldValues.forEach(serviceCustomFieldValue -> {
            serviceCustomFieldValueManager.remove(serviceCustomFieldValue.getId());
        });


        //dispute_note
        //service_dispute
        //dispute
        List<Dispute> disputes = disputeManager.findByServiceId(id);
        for (Dispute dispute : disputes) {
            List<DisputeNote> notes = disputeNoteManager.findByDisputeId(dispute.getId());
            notes.forEach(note -> {
                disputeNoteManager.remove(note.getId());
            });
            disputeManager.remove(dispute.getId());
        }

        //jeop_instance_note
        //service_jeop_instance
        //jeop_instance
        List<ServiceJeop> jeops = serviceJeopManager.findByServiceId(id);
        for (ServiceJeop jeop : jeops) {
            serviceJeopManager.remove(jeop.getId());
        }

        //service_note
        //note
        List<ServiceNote> notes = serviceNoteManager.findByServiceId(id);
        notes.forEach(note -> {
            serviceNoteManager.remove(note.getId());
        });

        //service_file_attachment
        //file_attachment
        //file_attachment_content
        List<ServiceFileAttachment> attachments = serviceFileAttachmentManager.findByServiceId(id);
        attachments.forEach(attachment -> {
            serviceFileAttachmentManager.remove(attachment.getId());
        });

        //service_interval_instance
        //interval_instance
        List<ServiceIntervalInstance> intervalInstances = serviceIntervalInstanceManager.findByServiceId(id);
        intervalInstances.forEach(intervalInstance -> {
            serviceIntervalInstanceManager.remove(intervalInstance.getId());
        });


        //service_equipment
        //equipment
        List<ServiceEquipment> equipments = serviceEquipmentManager.findByServiceId(id);
        equipments.forEach(equipment -> {
            serviceEquipmentManager.remove(equipment.getId());
        });

        //service_milestone_instance
        //milestone_instance_history
        //milestone_instance
        List<ServiceMilestoneInstance> milestones = serviceMilestoneInstanceManager.findByServiceId(id);
        milestones.forEach(milestone -> {
            List<MilestoneInstanceHistory> history = milestoneHistoryManager.findByMilestoneInstanceId(milestone.getId());
            history.forEach(h -> {
                serviceMilestoneInstanceManager.remove(h.getId());
            });
            serviceMilestoneInstanceManager.remove(milestone.getId());
        });

        //service_shipment_tracking
        //shipment_tracking
        //not used

        //cost_history
        List<CostHistory> costHistories = costHistoryManager.findByServiceId(id);
        costHistories.forEach(costHistory -> {
            costHistoryManager.remove(costHistory.getId());
        });

        //pending_disconnect (will need to do something with the foreign key)
        PendingDisconnect pendingDisconnect = pendingDisconnectManager.findByParent(id);
        if (pendingDisconnect != null) {
            pendingDisconnectManager.remove(pendingDisconnect.getId());
        }

        //service_brokerage
        ServiceBrokerage brokerage = brokerageManager.findByServiceId(id);
        if (brokerage != null) {
            brokerageManager.remove(brokerage.getId());
        }

        //service_snapshot
        List<ServiceSnapshot> snapshots = snapshotManager.findByServiceId(id);
        snapshots.forEach(snapshot -> {
            snapshotManager.remove(snapshot.getId());
        });


        serviceManager.remove(id);

    }

    public void markForDeletion(Long id) {
        Service service = retrieve(id);
        String orderType = service.getOrderType();
        Long inventoryServiceId = service.getInventoryServiceId();
        String type = service.getType();
        Long locationId = service.getLocationId();

        //check to see if MACD and if inventory item still exists reset the provisioning id on the inventory item
        if (OrderType.getMacTypes().contains(orderType) && !orderType.equals(OrderType.NEW.getOrderType())) {
            List<Service> provServices = serviceManager.findAllProvisioningByInventoryId(inventoryServiceId);
            Optional<Service> latestService = provServices.stream()
                    .filter(s -> !s.getStatus().equals(TerminalServiceStatuses.CANCELLED.getStatus())
                            && !s.getStatus().equals(TerminalServiceStatuses.CHANGE_IN_ASSIGNMENT.getStatus())
                            && !s.getId().equals(id))
                    .sorted(Comparator.comparing(Service::getId).reversed()) // Sort by id descending
                    .findFirst(); // Get the first element

            T inventoryService = (T) retrieve(inventoryServiceId);
            inventoryService.setProvisioningServiceId(latestService.map(Service::getId).orElse(null));
            editSuper(inventoryService);
        }

        if (service.isCurrentInventory()) {
            List<Service> provServices = serviceManager.findAllProvisioningByInventoryId(id);
            provServices.forEach(provService -> {
                provService.setInventoryServiceId(null);
                editSuper(provService);
            });
        }
        service.setMarkedForDeletion(true);
        service.setDeletionDate(new Date());
        service.setStatus("Deleted");
        edit((T) service);

        List<Service> services = serviceManager.findByLocationId(locationId);
        Boolean onHold = true;
        for (Service s : services) {
            if (!s.getStatus().equals("On Hold")) {
                onHold = false;
                break;
            }
        }
        if (onHold) {
            if (!locationMilestoneInstanceManager.doesMilestoneExist(locationId, "ON_HOLD")) {
                locationMilestoneInstanceManager.create(locationId, "ON_HOLD", new Date());
            }
            Location location = locationManager.retrieve(locationId);
            location.setStatus("On Hold");
            locationManager.editSuper(location);
            onHold = true;
            List<Location> locations = locationManager.findbyOrderId(location.getOrderId());
            for (Location loc : locations) {
                if (!loc.getStatus().equals("On Hold")) {
                    onHold = false;
                    break;
                }
            }
            if (onHold) {
                Order order = orderManager.retrieve(location.getOrderId());
                order.setStatus("On Hold");
                orderManager.editSuper(order);
            }
        }
    }
}
