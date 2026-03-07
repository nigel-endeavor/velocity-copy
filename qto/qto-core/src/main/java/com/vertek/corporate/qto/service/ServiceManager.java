package com.vertek.corporate.qto.service;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.querydsl.codegen.utils.StringUtils;
import com.vertek.corporate.qto.activation.attempt.ActivationAttempt;
import com.vertek.corporate.qto.activation.attempt.ActivationAttemptManager;
import com.vertek.corporate.qto.activation.issue.ActivationIssue;
import com.vertek.corporate.qto.activation.issue.ActivationIssueManager;
import com.vertek.corporate.qto.activation.schedule.ActivationSchedule;
import com.vertek.corporate.qto.activation.schedule.ActivationScheduleManager;
import com.vertek.corporate.qto.attachment.FileAttachment;
import com.vertek.corporate.qto.attachment.FileAttachmentContent;
import com.vertek.corporate.qto.attachment.FileAttachmentManager;
import com.vertek.corporate.qto.attachment.ServiceFileAttachment;
import com.vertek.corporate.qto.attachment.ServiceFileAttachmentManager;
import com.vertek.corporate.qto.brokerage.ServiceBrokerage;
import com.vertek.corporate.qto.brokerage.ServiceBrokerageManager;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.common.ValidationError;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.costhistory.CostHistory;
import com.vertek.corporate.qto.costhistory.CostHistoryManager;
import com.vertek.corporate.qto.dispute.Dispute;
import com.vertek.corporate.qto.dispute.DisputeManager;
import com.vertek.corporate.qto.equipment.ServiceEquipment;
import com.vertek.corporate.qto.equipment.ServiceEquipmentManager;
import com.vertek.corporate.qto.inventory.PendingDisconnect;
import com.vertek.corporate.qto.inventory.PendingDisconnectManager;
import com.vertek.corporate.qto.invoicing.surcharge.service.ServiceSurcharge;
import com.vertek.corporate.qto.invoicing.surcharge.service.ServiceSurchargeManager;
import com.vertek.corporate.qto.jeop.ServiceJeop;
import com.vertek.corporate.qto.jeop.ServiceJeopManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.milestone.Milestone;
import com.vertek.corporate.qto.milestone.MilestoneDisplaySet;
import com.vertek.corporate.qto.milestone.MilestoneDisplaySetManager;
import com.vertek.corporate.qto.milestone.MilestoneManager;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstance;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import com.vertek.corporate.qto.multiedit.request.MilestoneCodeDate;
import com.vertek.corporate.qto.multiedit.request.MultiEditRequestDto;
import com.vertek.corporate.qto.multiedit.response.EntityDescription;
import com.vertek.corporate.qto.multiedit.response.MultiEditError;
import com.vertek.corporate.qto.multiedit.response.MultiEditResponseDto;
import com.vertek.corporate.qto.note.ServiceNote;
import com.vertek.corporate.qto.note.ServiceNoteManager;
import com.vertek.corporate.qto.notification.NotificationManager;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;
import com.vertek.corporate.qto.service.macd.MultiMacdRequestDto;
import com.vertek.corporate.qto.service.macd.request.MacdDto;
import com.vertek.corporate.qto.service.macd.request.MacdRequestDto;
import com.vertek.corporate.qto.service.macd.response.MultiMacdError;
import com.vertek.corporate.qto.service.macd.response.MultiMacdResponseDto;
import com.vertek.corporate.qto.service.snapshot.ServiceSnapshot;
import com.vertek.corporate.qto.service.snapshot.ServiceSnapshotManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.vertek.corporate.qto.multiedit.MultiEditUtility.containsKey;
import static com.vertek.corporate.qto.multiedit.MultiEditUtility.getKeyBigDecimal;
import static com.vertek.corporate.qto.multiedit.MultiEditUtility.getKeyDate;
import static com.vertek.corporate.qto.multiedit.MultiEditUtility.getKeyLong;
import static com.vertek.corporate.qto.multiedit.MultiEditUtility.getKeyString;

/**
 * @author rcasey
 * @since 1/6/2023
 */
@Stateless
public class ServiceManager extends StandardManager<Service> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);
    /**
     * Persistence tier for services.
     */
    @Inject
    private ServiceJpaDao dao;
    /**
     * Manager for getting the properly injected manager for a given service type.
     */
    @Inject
    private ServiceManagerFactory serviceManagerFactory;
    /**
     * Business logic tier for orders.
     */
    @Inject
    private OrderManager orderManager;
    /**
     * Business logic tier for locations.
     */
    @Inject
    private LocationManager locationManager;
    /**
     * Business logic tier for service notes.
     */
    @Inject
    private ServiceNoteManager serviceNoteManager;
    /**
     * Business logic tier for service jeops.
     */
    @Inject
    private ServiceJeopManager serviceJeopManager;
    /**
     * Business logic tier for milestone.
     */
    @Inject
    private MilestoneManager milestoneManager;
    /**
     * Business logic tier for milestone display sets.
     */
    @Inject
    private MilestoneDisplaySetManager mdsManager;
    /**
     * Business logic tier for service milestone instances.
     */
    @Inject
    private ServiceMilestoneInstanceManager serviceMilestoneInstanceManager;
    /**
     * Business logic tier for companies.
     */
    @Inject
    private CompanyManager companyManager;
    /**
     * Notification manager.
     */
    @Inject
    private NotificationManager notificationManager;

    /**
     * Business logic tier for pending disconnects.
     */
    @Inject
    private PendingDisconnectManager pendingDisconnectManager;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private ServiceFileAttachmentManager serviceFileAttachmentManager;

    @Inject
    private ActivationAttemptManager attemptManager;

    @Inject
    private ActivationIssueManager attemptIssueManager;

    @Inject
    private ActivationScheduleManager scheduleManager;

    @Inject
    private CostHistoryManager costHistoryManager;

    @Inject
    private DisputeManager disputeManager;

    @Inject
    private ServiceBrokerageManager brokerageManager;

    @Inject
    private ServiceSnapshotManager snapshotManager;

    @Inject
    private ServiceSurchargeManager surchargeManager;


    @Inject
    private FileAttachmentManager fileAttachmentManager;

    @Inject
    private ServiceEquipmentManager serviceEquipmentManager;


    @Override
    protected ServiceJpaDao getDao() {
        return dao;
    }

    /**
     * Retrieves all services matching the given criteria.
     *
     * @param criteria the criteria to filter by.
     * @return matching services.
     */
    public PaginatedResult<Service> findBySearchCriteria(final ServiceSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    @Override
    public Service create(final Service entity) {
        throw new RuntimeException("Services should be created with their type-specific manager.");
    }

    @Override
    public Service edit(final Service entity) {
        throw new RuntimeException("Services should be edited with their type-specific manager.");
    }

    public Service updateClientFlag(final Service entity) {
        return super.edit(entity);
    }

    /**
     * Get list of services for a location.
     *
     * @param locationId locationId.
     * @return services.
     */
    public List<Service> findByLocationId(final Long locationId) {
        return getDao().findByLocationId(locationId);
    }

    /**
     * Returns a Service.
     *
     * @param serviceId Service ID.
     * @param tenantId  Tenant ID.
     * @return Activation Scheule Object.
     */
    public Service findByIdAndTenant(final Long serviceId, final Long tenantId) {
        return dao.findByIdAndTenant(serviceId, tenantId);
    }

    /**
     * Returns a List of Services.
     *
     * @param serviceIds service IDs.
     * @param tenantId   tenantId
     * @return List of Services.
     */
    public List<Service> findByIdsAndTenant(final List<Long> serviceIds, final Long tenantId) {
        return dao.findByIdsAndTenant(serviceIds, tenantId);
    }

    /**
     * Returns a Service.
     *
     * @param ticketNumber Ticket ID.
     * @param tenantId     Tenant ID.
     * @return Activation Scheule Object.
     */
    public List<Service> findByTicketNumberAndTenant(final String ticketNumber, final Long tenantId) {
        return dao.findByTicketNumberAndTenant(ticketNumber, tenantId);
    }

    /**
     * Returns a Service.
     *
     * @param clientServiceId Client Service ID.
     * @param tenantId        Tenant ID.
     * @return a Service
     */
    public List<Service> findByClientServiceIdAndTenant(final String clientServiceId, final Long tenantId) {
        return dao.findByClientServiceIdAndTenant(clientServiceId, tenantId);
    }

    /**
     * Returns a Service.
     *
     * @param clientServiceId Client Service ID.
     * @param locationId      Location ID.
     * @return a Service
     */
    public Service findByClientServiceIdAndLocationId(final String clientServiceId, final Long locationId) {
        return dao.findByClientServiceIdAndLocationId(clientServiceId, locationId);
    }

    /**
     * Given a collection of service IDs and fields to process, loops through the services and makes the appropriate
     * changes/additions.
     *
     * @param multiEditRequest the request.
     * @param <T>              the type of service being processed at the time.
     * @return a response indicating any failures.
     */
    public <T extends Service> MultiEditResponseDto multiEdit(final MultiEditRequestDto multiEditRequest) {
        try {
            MultiEditResponseDto response = new MultiEditResponseDto();
            //for each service ID
            for (Long serviceId : multiEditRequest.getIds()) {
                MultiEditError multiEditError = null;
                Service service = retrieve(serviceId);
                // Assign to a wildcard capture
                AbstractServiceManager<T> manager
                        = (AbstractServiceManager<T>) serviceManagerFactory.getManager(
                        ServiceType.fromServiceName(service.getType()));

                //let's get a copy of the existing service before we start changing it
                T existingTypedService = manager.retrieve(serviceId);
                manager.detach(existingTypedService);
                T typedService = manager.retrieve(serviceId);
                Location location = locationManager.retrieve(typedService.getLocationId());
                //order level fields
                Order order = orderManager.retrieve(typedService.getOrderId());
                if (order != null) {
                    if (containsKey(multiEditRequest, "qaManager")) {
                        order.setQaManager(getKeyLong(multiEditRequest, "qaManager"));
                    }
                    if (containsKey(multiEditRequest, "clientProjectManager")) {
                        order.setClientProjectManager(getKeyString(multiEditRequest, "clientProjectManager"));
                    }
                    if (containsKey(multiEditRequest, "vertekProjectManager") || containsKey(multiEditRequest, "i90ProjectManager")) {
                        Long key = containsKey(multiEditRequest, "vertekProjectManager") ? getKeyLong(multiEditRequest, "vertekProjectManager") : getKeyLong(multiEditRequest, "i90ProjectManager");
                        orderManager.handleProjectManagerChangeEmail(order, order.getVertekProjectManager(), key);
                        order.setVertekProjectManager(key);
                    }
                    if (containsKey(multiEditRequest, "provisioner")) {
                        Long subjectId = getKeyLong(multiEditRequest, "provisioner");
                        orderManager.handleProvisionerChangeEmail(order, order.getProvisioner(), subjectId);
                        orderManager.handleProvisionerChange(order, subjectId, subjectId != null);
                    }
                    if (containsKey(multiEditRequest, "masterCustomer")) {
                        Long companyId = getKeyLong(multiEditRequest, "masterCustomer");
                        if (companyId != null) {
                            Company masterCustomer = companyManager.retrieve(companyId);
                            if (masterCustomer != null && order.getCompany() != null
                                    && masterCustomer.getTenantId() == order.getCompany().getTenantId()) {
                                Company endCustomer = companyManager.retrieve(order.getCompany().getId());
                                endCustomer.setParentCompany(masterCustomer);
                                order.setCompany(companyManager.edit(endCustomer));
                            } else {
                                multiEditError = addFieldError(multiEditError, typedService, location, "masterCustomer");
                            }
                        } else if (order.getCompany() != null) {
                            order.getCompany().setMasterCustomerId(null);
                        } else {
                            multiEditError = addFieldError(multiEditError, typedService, location, "masterCustomer");
                        }
                    }
                    if (containsKey(multiEditRequest, "endCustomer")) {
                        Long companyId = getKeyLong(multiEditRequest, "endCustomer");
                        if (companyId != null) {
                            Company endCustomer = companyManager.retrieve(companyId);
                            if (endCustomer != null) {
                                order.setCompany(endCustomer);
                            } else {
                                multiEditError = addFieldError(multiEditError, typedService, location, "endCustomer");
                            }
                        }
                    }
                    orderManager.edit(order);
                }
                //location level fields
                if (location != null) {
                    if (multiEditRequest.getFieldValues().containsKey("levelOfEffort")) {
                        location.setLevelOfEffort(getKeyString(multiEditRequest, "levelOfEffort"));
                    }
                    if (multiEditRequest.getFieldValues().containsKey("clientLocationType")) {
                        location.setClientLocationType(getKeyString(multiEditRequest, "clientLocationType"));
                    }
                    if (multiEditRequest.getFieldValues().containsKey("clientLocationInfo")) {
                        location.setClientLocationInfo(getKeyString(multiEditRequest, "clientLocationInfo"));
                    }
                }
                //service level fields
                //service note
                if (containsKey(multiEditRequest, "serviceNote") && !Strings.isNullOrEmpty(getKeyString(multiEditRequest, "serviceNote"))) {
                    ServiceNote note = new ServiceNote();
                    note.setServiceId(serviceId);
                    note.setNote(getKeyString(multiEditRequest, "serviceNote"));
                    note.setCreatedDate(new Date());
                    //get current user
                    String username = SecurityUtils.getLoggedInUser();
                    Subject subject = subjectManager.retrieve(multiEditRequest.getSubjectId());
                    note.setCreatedBy(subject == null ? username : subject.getDisplayName());
                    note.setCreatedById(subject.getId());
                    note.setCategory(typedService.getType());
                    note.setTenantId(typedService.getTenantId());
                    serviceNoteManager.create(note);
                }
                //service jeop - just checking the required fields for a new jeop
                if (containsKey(multiEditRequest, "jeopDescription")
                        && containsKey(multiEditRequest, "jeopResponsibility")
                        && containsKey(multiEditRequest, "jeopStartDate")) {
                    ServiceJeop jeop = new ServiceJeop();
                    jeop.setServiceId(serviceId);
                    jeop.setDescription(getKeyString(multiEditRequest, "jeopDescription"));
                    jeop.setResponsibility(getKeyString(multiEditRequest, "jeopResponsibility"));
                    jeop.setStartDate(getKeyDate(multiEditRequest, "jeopStartDate"));
                    if (containsKey(multiEditRequest, "jeopAssignedTo")
                            && !Strings.isNullOrEmpty(getKeyString(multiEditRequest, "jeopAssignedTo"))) {
                        jeop.setAssignedTo(getKeyString(multiEditRequest, "jeopAssignedTo"));
                    }
                    if (containsKey(multiEditRequest, "jeopNote")
                            && !Strings.isNullOrEmpty(getKeyString(multiEditRequest, "jeopNote"))) {
                        jeop.setNote(getKeyString(multiEditRequest, "jeopNote"));
                    }
                    jeop.setTenantId(typedService.getTenantId());
                    serviceJeopManager.create(jeop);
                } else if (containsKey(multiEditRequest, "jeopDescription")
                        || containsKey(multiEditRequest, "jeopResponsibility")
                        || containsKey(multiEditRequest, "jeopStartDate")
                        || containsKey(multiEditRequest, "jeopAssignedTo")
                        || containsKey(multiEditRequest, "jeopNote")) {
                    if (containsKey(multiEditRequest, "jeopDescription")) {
                        multiEditError = addFieldError(multiEditError, typedService, location, "jeopDescription");
                    }
                    if (containsKey(multiEditRequest, "jeopResponsibility")) {
                        multiEditError = addFieldError(multiEditError, typedService, location, "jeopResponsibility");
                    }
                    if (containsKey(multiEditRequest, "jeopStartDate")) {
                        multiEditError = addFieldError(multiEditError, typedService, location, "jeopStartDate");
                    }
                    if (containsKey(multiEditRequest, "jeopAssignedTo")) {
                        multiEditError = addFieldError(multiEditError, typedService, location, "jeopAssignedTo");
                    }
                    if (containsKey(multiEditRequest, "jeopNote")) {
                        multiEditError = addFieldError(multiEditError, typedService, location, "jeopNote");
                    }
                }
                //service level fields
                if (containsKey(multiEditRequest, "provider")) {
                    typedService.setProvider(getKeyString(multiEditRequest, "provider"));
                }
                if (containsKey(multiEditRequest, "uploadSpeed")) {
                    typedService.setUploadSpeed(getKeyString(multiEditRequest, "uploadSpeed"));
                }
                if (containsKey(multiEditRequest, "downloadSpeed")) {
                    typedService.setDownloadSpeed(getKeyString(multiEditRequest, "downloadSpeed"));
                }
                if (containsKey(multiEditRequest, "networkProtocol")) {
                    //reflection to set the network protocol
                    String setterName = "set" + StringUtils.capitalize("networkProtocol");
                    try {
                        Method setterMethod = typedService.getClass().getMethod(setterName, String.class);
                        setterMethod.invoke(typedService, getKeyString(multiEditRequest, "networkProtocol"));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        //not all service types have a network protocol
                        multiEditError = addFieldError(multiEditError, typedService, location, "networkProtocol");
                    }
                }
                if (containsKey(multiEditRequest, "mediaType")) {
                    typedService.setMediaType(getKeyString(multiEditRequest, "mediaType"));
                }
                if (containsKey(multiEditRequest, "disconnectReason")) {
                    typedService.setDisconnectReason(getKeyString(multiEditRequest, "disconnectReason"));
                }
                if (containsKey(multiEditRequest, "providerOrderNum")) {
                    typedService.setProviderOrderNum(getKeyString(multiEditRequest, "providerOrderNum"));
                }
                if (containsKey(multiEditRequest, "earlyTerminationFee")) {
                    typedService.setEarlyTerminationFee(getKeyBigDecimal(multiEditRequest, "earlyTerminationFee"));
                }
                if (containsKey(multiEditRequest, "mrc")) {
                    typedService.setMrc(getKeyBigDecimal(multiEditRequest, "mrc"));
                }
                if (containsKey(multiEditRequest, "nrc")) {
                    typedService.setNrc(getKeyBigDecimal(multiEditRequest, "nrc"));
                }
                if (containsKey(multiEditRequest, "clientServiceType")) {
                    typedService.setClientServiceType(getKeyString(multiEditRequest, "clientServiceType"));
                }
                if (containsKey(multiEditRequest, "clientServiceInfo")) {
                    typedService.setClientServiceInfo(getKeyString(multiEditRequest, "clientServiceInfo"));
                }
                if (containsKey(multiEditRequest, "contractSignedDate")) {
                    typedService.setContractSignedDate(getKeyDate(multiEditRequest, "contractSignedDate"));
                }
                if (containsKey(multiEditRequest, "contractTerm")) {
                    typedService.setContractTerm(getKeyString(multiEditRequest, "contractTerm"));
                }
                if (containsKey(multiEditRequest, "accountNumber")) {
                    typedService.setAccountNumber(getKeyString(multiEditRequest, "accountNumber"));
                }

                if (containsKey(multiEditRequest, "summaryBill")) {
                    typedService.setSummaryBill(getKeyString(multiEditRequest, "summaryBill"));
                }
                if (containsKey(multiEditRequest, "providerCircuitId")) {
                    typedService.setProviderCircuitId(getKeyString(multiEditRequest, "providerCircuitId"));
                }
                if (containsKey(multiEditRequest, "followUpDate")) {
                    String followUpDateString = getKeyString(multiEditRequest, "followUpDate");
                    typedService.setFollowUpDate(followUpDateString != null
                            ? new Date(Long.parseLong(followUpDateString)) : null);
                }
                T updated = manager.edit(typedService);
                //service milestones
                MilestoneDisplaySet mds = mdsManager.getByDisplayType(updated.getType());
                for (MilestoneCodeDate milestoneCodeDate : multiEditRequest.getMilestones()) {
                    //check to see if the milestone code exists for this service type
                    boolean exists = mds.getDisplaySetIncludes().stream()
                                    .anyMatch(mdsi -> mdsi.getMilestone().getCode().equals(milestoneCodeDate.getCode()));

                    if (exists) {
                        if (milestoneCodeDate.getDate() != null) {
                            ServiceMilestoneInstance existing
                                    = serviceMilestoneInstanceManager
                                    .retrieveCurrentMilestoneByCode(serviceId, milestoneCodeDate.getCode());
                            if (existing == null) {
                                Milestone milestone = milestoneManager.retrieveByCode(milestoneCodeDate.getCode());
                                ServiceMilestoneInstance milestoneInstance = new ServiceMilestoneInstance();
                                milestoneInstance.setServiceId(serviceId);
                                milestoneInstance.setMilestone(milestone);
                                milestoneInstance.setMilestoneDate(milestoneCodeDate.getDate());
                                milestoneInstance.setTenantId(updated.getTenantId());
                                serviceMilestoneInstanceManager.create(milestoneInstance);
                            } else {
                                existing.setMilestoneDate(milestoneCodeDate.getDate());
                                serviceMilestoneInstanceManager.edit(existing);
                            }
                        } else {
                            ServiceMilestoneInstance existing
                                    = serviceMilestoneInstanceManager
                                    .retrieveCurrentMilestoneByCode(serviceId, milestoneCodeDate.getCode());
                            if (existing != null) {
                                existing.setMilestoneDate(null);
                                serviceMilestoneInstanceManager.edit(existing);
                            }
                        }
                    };
                }
                //close jeops
                if (containsKey(multiEditRequest, "closeJeops") && getKeyString(multiEditRequest, "closeJeops").equalsIgnoreCase("yes")) {
                    List<ServiceJeop> openJeops = serviceJeopManager.getOpen(serviceId);
                    for (ServiceJeop jeop : openJeops) {
                        jeop.setEndDate(new Date());
                        serviceJeopManager.edit(jeop);
                    }
                }
                //inventory audit notes
                if (TerminalServiceStatuses.getStatuses().contains(updated.getStatus())) {
                    String auditString = serviceNoteManager.getAuditString(updated, existingTypedService);
                    if (auditString.length() > 0) {
                        serviceNoteManager.create(updated.getId(), auditString, "Audit");
                    }
                }
                if (multiEditError != null) {
                    response.getCantEdit().add(multiEditError);
                }
            }

            //create a notification for the provisioner
            if (containsKey(multiEditRequest, "provisioner")) {
                Long subjectId = getKeyLong(multiEditRequest, "provisioner");
                if (subjectId != null) {
                    Long orderCount = orderManager.getOrderCount(multiEditRequest.getIds());
                    String header = (orderCount > 1) ? "Orders Assigned" : "Order Assigned";
                    String body = orderCount + ((orderCount > 1) ? " orders have " : " order has ") + "been assigned to you";
                    notificationManager.create(subjectId, header, body, "priority_high", null);
                }
            }

            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to perform multi edit operation. " + e.getMessage());
        }
    }

    /**
     * Utility for adding a field error to the multiEditError.
     *
     * @param multiEditError the current multiEditError instance. If it's null, a new one will be created.
     * @param service        the service used to populate the service description for the error.
     * @param location       the location used to populate the service description for the error.
     * @param fieldName      the field name to add to the errors for the service.
     * @return the updated/created multiEditError instance.
     */
    private MultiEditError addFieldError(MultiEditError multiEditError, final Service service, final Location location,
                                         final String fieldName) {
        if (multiEditError == null) {
            multiEditError = new MultiEditError();
            EntityDescription serviceDescription
                    = new EntityDescription(service.getId(), getDisplayName(service, location));
            multiEditError.setDescription(serviceDescription);
        }
        multiEditError.getFields().add(fieldName);
        return multiEditError;
    }

    /**
     * Utility method to get the service description string.
     *
     * @param service  the service.
     * @param location the location.
     * @return the service description string.
     */
    private String getDisplayName(final Service service, final Location location) {
        return location.getClientLocationId()
                + (!Strings.isNullOrEmpty(location.getAddress1()) ? ", " + location.getAddress1() : "")
                + ", " + service.getClientServiceId();
    }

    /**
     * Utility for calculating the Circuit Term End Date.
     *
     * @param date         Starting Date.
     * @param contractTerm Term.
     * @return Returns the calculated date.
     */
    public static Date dateAdd(final Date date, final String contractTerm) {
        int value;
        switch (contractTerm) {
            case "MTM":
                value = 30;
                break;
            case "12 Month":
                value = 365;
                break;
            case "24 Month":
                value = 730;
                break;
            case "36 Month":
                value = 1095;
                break;
            case "48 Month":
                value = 1461;
                break;
            case "60 Month":
                value = 1862;
                break;
            case "72 Month":
                value = 2190;
                break;
            default:
                value = 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date newDate;
        c.add(Calendar.DATE, value);
        newDate = c.getTime();
        return newDate;
    }

    /**
     * Get a disconnect service.
     *
     * @param parentId parentId.
     * @return service.
     */
    public Service findDisconnectByParentId(final Long parentId) {
        return dao.findDisconnectByParentId(parentId);
    }

    public <X extends Service> X createMacd(final MacdRequestDto macdRequestDto) {
        Service inventoryService = retrieve(macdRequestDto.getServiceId());
        AbstractServiceManager<Service> manager = (AbstractServiceManager<Service>) serviceManagerFactory
                .getManager(ServiceType.fromServiceName(inventoryService.getType()));

        List<String> orderTypes
                = macdRequestDto.getMacds().stream().map(MacdDto::getOrderType).collect(Collectors.toList());
        List<String> subOrderTypes = macdRequestDto.getMacds().stream()
                .map(MacdDto::getSubOrderType).filter(Objects::nonNull).collect(Collectors.toList());
        List<String> disconnectReasons
                = macdRequestDto.getMacds().stream().map(MacdDto::getDisconnectReason).collect(Collectors.toList());
        List<String> serviceTypes
                = macdRequestDto.getMacds().stream().map(MacdDto::getServiceType).collect(Collectors.toList());
        List<String> projectNames
                = macdRequestDto.getMacds().stream().map(MacdDto::getProjectName).collect(Collectors.toList());
        AbstractServiceManager<X> createManager;
        if (serviceTypes.size() > 0 && serviceTypes.get(0) != null) {
            createManager = (AbstractServiceManager<X>) serviceManagerFactory
                    .getManager(ServiceType.fromServiceName(serviceTypes.get(0)));
        } else {
            createManager = (AbstractServiceManager<X>) manager;
        }

        //If the createForLinkedOrBundled is checked then create the macd for all associated services and then
        //create the macd for the parent service for the return
        if (macdRequestDto.isCreateLinkedBundled()) {
            List<Service> services = findAssociatedByParentId(inventoryService.getLinkedBundledParentId());
            for (Service s : services) {
                //process all the associated services, the parent will be processed last as it needs to be the one returned
                if (!s.getId().equals(inventoryService.getId())) {
                    //reset the createManger to the associated services service type
                    createManager = (AbstractServiceManager<X>) serviceManagerFactory
                    .getManager(ServiceType.fromServiceName(s.getType()));
                    X newService = createManager.createMacd(s, orderTypes, subOrderTypes, disconnectReasons, projectNames,
                            macdRequestDto.getMacdNote(), macdRequestDto.getSubjectId());
                    //create pending disconnect if necessary
                    if (macdRequestDto.getMacds().get(0).isCreateDisconnectUponCompletion()) {
                        PendingDisconnect pendingDisconnect = new PendingDisconnect();
                        pendingDisconnect.setParentService(s);
                        pendingDisconnect.setNewService(newService);
                        pendingDisconnect.setDisconnectReason(disconnectReasons.get(0));
                        pendingDisconnectManager.create(pendingDisconnect);
                    }
                }
            }
            //reset the createManager to the parent service
            createManager = (AbstractServiceManager<X>) serviceManagerFactory
                    .getManager(ServiceType.fromServiceName(inventoryService.getType()));
        }



        X newService = createManager.createMacd(inventoryService, orderTypes, subOrderTypes, disconnectReasons, projectNames,
                macdRequestDto.getMacdNote(), macdRequestDto.getSubjectId());
        if (macdRequestDto.getFileAttachments() != null) {
            for (Long attachmentID : macdRequestDto.getFileAttachments()) {
                FileAttachment attachment = fileAttachmentManager.retrieve(attachmentID);
                ServiceFileAttachment newAttachment = new ServiceFileAttachment();
                for (Field attachmentField : FileAttachment.class.getDeclaredFields()) {
                    if (!attachmentField.getName().equalsIgnoreCase("id")
                            && !attachmentField.getName().equalsIgnoreCase("serviceId")
                            && !attachmentField.getName().equalsIgnoreCase("parentFileAttachmentId")
                            && !attachmentField.getName().equalsIgnoreCase("content")
                            && !attachmentField.getName().equalsIgnoreCase("version")
                            && !attachmentField.getName().equalsIgnoreCase("tenantId")
                            && !attachmentField.getName().equalsIgnoreCase("parentOwnerId")) {
                        attachmentField.setAccessible(true);
                        try {
                            attachmentField.set(newAttachment, attachmentField.get(attachment));
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                }
                newAttachment.setTenantId(newService.getTenantId());
                newAttachment.setServiceId(newService.getId());
                newAttachment.setParentFileAttachmentId(attachment.getId());
                FileAttachmentContent content = new FileAttachmentContent(attachment.getContent().getData());
                newAttachment.setContent(content);
                serviceFileAttachmentManager.create(newAttachment);
            }
        }

        //create pending disconnect if necessary
        if (macdRequestDto.getMacds().get(0).isCreateDisconnectUponCompletion()) {
            PendingDisconnect pendingDisconnect = new PendingDisconnect();
            pendingDisconnect.setParentService(inventoryService);
            pendingDisconnect.setNewService(newService);
            pendingDisconnect.setDisconnectReason(disconnectReasons.get(0));
            pendingDisconnectManager.create(pendingDisconnect);
        }
        //return the created service
        inventoryService.setProvisioningServiceId(newService.getId());
        manager.editSuper(inventoryService);
        return newService;
    }

    public Location getInventoryLocation(final Service service) {
        Location inventoryLocation = null;
        if (!service.isCurrentInventory()) {
            if (service.getInventoryServiceId() != null) {
                Location location = locationManager.retrieve(service.getLocationId());
                inventoryLocation = locationManager.retrieve(location.getInventoryLocationId());
            } else if (service.getParentServiceId() != null) {
                Service parentService = retrieve(service.getParentServiceId());
                inventoryLocation = locationManager.retrieve(parentService.getLocationId());
            } else {
                Location location = locationManager.retrieve(service.getLocationId());
                if (location.getInventoryLocationId() != null) {
                    inventoryLocation = locationManager.retrieve(location.getInventoryLocationId());
                }
            }
        }

        return inventoryLocation;
    }

    public Service findLeafServiceForService(final Long id) {
        return dao.findLeafServiceForService(id);
    }

    /**
     * Finds all services that are children of the given parent id.
     *
     * @param parentId the parent id.
     * @return the list of services.
     */
    public List<Service> findByParentId(final Long parentId) {
        return dao.findByParentId(parentId);
    }

    /**
     * Finds all services that are children of the given parent id and are not in a terminal status.
     *
     * @param inventoryId the parent id.
     * @return the list of services.
     */
    public List<Service> findOpenByInventoryId(final Long inventoryId) {
        return dao.findOpenByInventoryId(inventoryId);
    }

    /**
     * Finds all non-terminal or hold services that have an alternate ID.
     *
     * @param tenantId the tenant id to filter by.
     * @return matching services.
     */
    public List<Service> findOpenServicesWithAlternateId(final Long tenantId) {
        return dao.findOpenServicesWithAlternateId(tenantId);
    }

    /**
     * Validates the MacdRequestDto.
     *
     * @param request the request to validate.
     * @return an array of ValidationErrors if there are any, empty array otherwise.
     */
    public List<ValidationError> validateMacdRequest(final MacdRequestDto request) {
        Service parentService = retrieve(request.getServiceId());
        List<ValidationError> errors = new ArrayList<>();
        if (!parentService.getBillable()) {
            errors.add(new ValidationError("",
                    "Service must be billable to create a MACD from it."));
        }
        if (!parentService.isActive()) {
            errors.add(new ValidationError("",
                    "Service must be active to create a MACD from it."));
        }
        List<Service> children = findByParentId(request.getServiceId());
        boolean hasNonTerminalDisconnect = false;
        boolean hasNonTerminalMac = false;
        for (Service service : children) {
            if (!TerminalServiceStatuses.getStatuses().contains(service.getStatus())) {
                if (OrderType.getMacTypes().contains(service.getOrderType())) {
                    hasNonTerminalMac = true;
                } else if (OrderType.DISCONNECT.getOrderType().equals(service.getOrderType())) {
                    hasNonTerminalDisconnect = true;
                }
            }
        }

        List<String> orderTypes
                = request.getMacds().stream().map(MacdDto::getOrderType).collect(Collectors.toList());
        ValidationError error = null;
        // if a Disconnect request, verify that there are no other non terminal Disconnects on the service
        // if a MAC request, verify that there are no other non terminal MACS on the service
        if (orderTypes.contains(OrderType.DISCONNECT.getOrderType()) && hasNonTerminalDisconnect) {
            errors.add(new ValidationError("",
                    "Only one non terminal Disconnect is allowed per service."));
        } else if (!orderTypes.contains(OrderType.DISCONNECT.getOrderType()) && hasNonTerminalMac) {
            errors.add(new ValidationError("",
                    "Only one non terminal MAC is allowed per service."));
        }
        return errors;
    }

    /**
     * Creates MACD(s) for the given service id(s).
     *
     * @param dto the request dto.
     * @return the response dto.
     */
    public MultiMacdResponseDto multiCreateMacd(final MultiMacdRequestDto dto) {
        MultiMacdResponseDto responseDto = new MultiMacdResponseDto();
        for (Long serviceId : dto.getIds()) {
            MacdRequestDto macdRequestDto = new MacdRequestDto();
            macdRequestDto.setServiceId(serviceId);
            macdRequestDto.setMacds(dto.getMacds());
            macdRequestDto.setMacdNote(dto.getMacdNote());
            macdRequestDto.setFileAttachments(dto.getFileAttachments());
            macdRequestDto.setSubjectId(dto.getSubjectId());
            List<ValidationError> validationErrors = validateMacdRequest(macdRequestDto);
            if (validationErrors.isEmpty()) {
                Service newService = createMacd(macdRequestDto);
                responseDto.getCreated().add(newService.getId());
            } else {
                MultiMacdError multiMacdError = new MultiMacdError();
                Service service = retrieve(serviceId);
                Location location = locationManager.retrieve(service.getLocationId());
                EntityDescription entityDescription
                        = new EntityDescription(serviceId, getDisplayName(service, location));
                multiMacdError.setDescription(entityDescription);
                multiMacdError.setError(validationErrors.stream()
                        .map((ValidationError::getMessage)).collect(Collectors.joining(" ")));
                responseDto.getCantEdit().add(multiMacdError);
            }
        }
        return responseDto;
    }

    /**
     * Returns all services with matching tenant id.
     *
     * @param tenantId the tenant id
     * @return list of services
     */
    public List<Service> findByTenantId(final Long tenantId) {
        return dao.findByTenantId(tenantId);
    }


    public void link(Long incomingServiceId, String selectedItems, String linkType) {
        if ("Inventory".equals(linkType)) {
            Service service = retrieve(incomingServiceId);
            service.setParentServiceId(Long.valueOf(selectedItems));
            dao.edit(service);
        } else {
            List<String> selectedIds = Lists.newArrayList(Splitter.on(',').split(selectedItems));
            for (String selectedId : selectedIds) {
                Long id = Long.valueOf(selectedId);
                Service service = dao.retrieve(id);
                if ("Link".equals(linkType)) {
                    service.setLinked(true);
                } else if ("Bundle".equals(linkType)) {
                    service.setBundled(true);
                }
                service.setLinkedBundledParentId(incomingServiceId);
                dao.edit(service);
            }
            Service parentService = dao.retrieve(incomingServiceId);
            if ("Link".equals(linkType)) {
                parentService.setLinked(true);
            } else if ("Bundle".equals(linkType)) {
                parentService.setBundled(true);
            }
            parentService.setLinkedBundledParentId(incomingServiceId);
            parentService.setLinkedBundledParent(true);
            dao.edit(parentService);
        }
    }

    /**
     * Finds all services that are children of the given associated parent id.
     *
     * @param parentId the parent id.
     * @return the list of services.
     */
    public List<Service> findAssociatedByParentId(final Long parentId) {
        return dao.findAssociatedByParentId(parentId);
    }

    public <X extends Service> X findInventoryByProvisioningId(final Long provisioningServiceId) {
        return (X) dao.findInventoryByProvisioningId(provisioningServiceId);
    }

    public Service findProvisioningByInventoryId(Long inventoryServiceId) {
        return dao.findProvisioningByInventoryId(inventoryServiceId);
    }

    public List<Service> findAllProvisioningByInventoryId(Long inventoryServiceId) {
        return dao.findAllProvisioningByInventoryId(inventoryServiceId);
    }

    public void updateMcId(Long locId, Long mcId) {

        List<Service> services = findByLocationId(locId);
        for (Service service : services) {

            service.setMasterCustomerId(mcId);
            dao.edit(service);

            List<ServiceJeop> jeops = serviceJeopManager.getOpen(service.getId());
            jeops.forEach(jeop -> {
                jeop.setMasterCustomerId(mcId);
                serviceJeopManager.edit(jeop);
            });

            List<ActivationAttempt> attempts = attemptManager.findByServiceId(service.getId());
            attempts.forEach(attempt -> {
                attempt.setMasterCustomerId(mcId);
                attemptManager.edit(attempt);
                List<ActivationIssue> issues = attemptIssueManager.findByAttemptId(attempt.getId());
                issues.forEach(issue -> {
                    issue.setMasterCustomerId(mcId);
                    attemptIssueManager.edit(issue);
                });
            });

            List<ActivationSchedule> schedules = scheduleManager.findByServiceId(service.getId());
            schedules.forEach(schedule -> {
                schedule.setMasterCustomerId(mcId);
                scheduleManager.edit(schedule);
            });

            List<CostHistory> costHistories = costHistoryManager.findByServiceId(service.getId());
            costHistories.forEach(costHistory -> {
                costHistory.setMasterCustomerId(mcId);
                costHistoryManager.editSuper(costHistory);
            });

            List<Dispute> disputes = disputeManager.findByServiceId(service.getId());
            disputes.forEach(dispute -> {
                dispute.setMasterCustomerId(mcId);
                disputeManager.edit(dispute);
            });

            List<ServiceMilestoneInstance> milestones = serviceMilestoneInstanceManager.findByServiceId(service.getId());
            milestones.forEach(milestone -> {
                milestone.setMasterCustomerId(mcId);
                serviceMilestoneInstanceManager.editNoEventHandler(milestone);
            });

            List<ServiceFileAttachment> attachments = serviceFileAttachmentManager.findByServiceId(service.getId());
            attachments.forEach(attachment -> {
                attachment.setMasterCustomerId(mcId);
                serviceFileAttachmentManager.edit(attachment);
            });

            List<ServiceNote> notes = serviceNoteManager.findByServiceId(service.getId());
            notes.forEach(note -> {
                note.setMasterCustomerId(mcId);
                serviceNoteManager.edit(note);
            });

            List<ServiceSnapshot> snapshots = snapshotManager.findByServiceId(service.getId());
            snapshots.forEach(snapshot -> {
                snapshot.setMasterCustomerId(mcId);
                snapshotManager.edit(snapshot);
            });

            List<ServiceSurcharge> surcharges = surchargeManager.findByServiceId(service.getId());
            surcharges.forEach(surcharge -> {
                surcharge.setMasterCustomerId(mcId);
                surchargeManager.edit(surcharge);
            });

            ServiceBrokerage brokerage = brokerageManager.findByServiceId(service.getId());
            if (brokerage != null) {
                brokerage.setMasterCustomerId(mcId);
                brokerageManager.edit(brokerage);
            }

            List<ServiceEquipment> equipments = serviceEquipmentManager.findByServiceId(service.getId());
            equipments.forEach(equipment -> {
                equipment.setMasterCustomerId(mcId);
                serviceEquipmentManager.edit(equipment);
            });
        }

    }

      /**
     * Get a macd service.
     * @param parentId parentId.
     * @return services.
     */
    public List<Service> findMacdByParentId(final Long parentId) {
        return dao.findMacdByParentId(parentId);
    }

    public <X extends Service> void deleteServiceJob() {
        List<Service> services = dao.findMarkedForDeletion();
        for (Service service : services) {
            AbstractServiceManager<Service> manager = (AbstractServiceManager<Service>) serviceManagerFactory
                .getManager(ServiceType.fromServiceName(service.getType()));
            LOGGER.info("Deleting service: " + service.getId());
            manager.deleteService(service.getId());
        }
    }
}
