package com.vertek.corporate.qto.order;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.EmailUtility;
import com.vertek.corporate.qto.RecordSource;
import com.vertek.corporate.qto.brokerage.ServiceBrokerage;
import com.vertek.corporate.qto.brokerage.ServiceBrokerageManager;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.common.ValidationError;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.config.CompanyConfigKey;
import com.vertek.corporate.qto.config.CompanyConfigPropertyManager;
import com.vertek.corporate.qto.config.CompanyConfigurationProperty;
import com.vertek.corporate.qto.config.ConfigPropertyManager;
import com.vertek.corporate.qto.config.ConfigurationProperty;
import com.vertek.corporate.qto.contact.Contact;
import com.vertek.corporate.qto.contact.ContactManager;
import com.vertek.corporate.qto.contact.ContactType;
import com.vertek.corporate.qto.contact.location.LocationContact;
import com.vertek.corporate.qto.contact.location.LocationContactManager;
import com.vertek.corporate.qto.contact.masterCustomer.MasterCustomerContact;
import com.vertek.corporate.qto.contact.masterCustomer.MasterCustomerContactManager;
import com.vertek.corporate.qto.contact.order.OrderContact;
import com.vertek.corporate.qto.contact.order.OrderContactManager;
import com.vertek.corporate.qto.customfield.value.ServiceCustomFieldValue;
import com.vertek.corporate.qto.customfield.value.ServiceCustomFieldValueManager;
import com.vertek.corporate.qto.jeop.OrderJeop;
import com.vertek.corporate.qto.jeop.OrderJeopManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.location.TerminalLocationStatuses;
import com.vertek.corporate.qto.milestone.LocationMilestoneInstance;
import com.vertek.corporate.qto.milestone.LocationMilestoneInstanceManager;
import com.vertek.corporate.qto.milestone.OrderMilestoneInstance;
import com.vertek.corporate.qto.milestone.OrderMilestoneInstanceManager;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstance;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import com.vertek.corporate.qto.note.OrderNote;
import com.vertek.corporate.qto.note.OrderNoteManager;
import com.vertek.corporate.qto.note.ServiceNoteManager;
import com.vertek.corporate.qto.notification.NotificationManager;
import com.vertek.corporate.qto.order.dto.OrderCreateDto;
import com.vertek.corporate.qto.order.dto.OrderCreateDtoWrapper;
import com.vertek.corporate.qto.order.dto.OrderCreateLocation;
import com.vertek.corporate.qto.order.dto.OrderCreateService;
import com.vertek.corporate.qto.order.dto.OrderCreateServiceCustomFields;
import com.vertek.corporate.qto.service.AbstractServiceManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.ServiceManagerFactory;
import com.vertek.corporate.qto.service.ServiceType;
import com.vertek.corporate.qto.service._4g5g.GService;
import com.vertek.corporate.qto.service.broadband.BroadbandService;
import com.vertek.corporate.qto.service.crossconnect.CrossConnectService;
import com.vertek.corporate.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingService;
import com.vertek.corporate.qto.service.engineeringIAM.EngineeringIAMService;
import com.vertek.corporate.qto.service.engineeringInfoProtection.EngineeringInfoProtectionService;
import com.vertek.corporate.qto.service.engineeringMDM.EngineeringMDMService;
import com.vertek.corporate.qto.service.cyber360MXDR.Cyber360MXDRService;
import com.vertek.corporate.qto.service.engineeringEndpoint.EngineeringEndpointService;
import com.vertek.corporate.qto.service.dia.DiaService;
import com.vertek.corporate.qto.service.ethernet.EthernetService;
import com.vertek.corporate.qto.service.microsoftLicenses.MicrosoftLicensesService;
import com.vertek.corporate.qto.service.mpls.MplsService;
import com.vertek.corporate.qto.service.ransomMDR.RansomMDRService;
import com.vertek.corporate.qto.service.riskMDR.RiskMDRService;
import com.vertek.corporate.qto.service.television.TelevisionService;
import com.vertek.corporate.qto.service.threatMDR.ThreatMDRService;
import com.vertek.corporate.qto.service.ucaas.UcaasService;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author rcasey
 * @since 1/10/2023
 */
@Stateless
public class OrderManager extends StandardManager<Order> {

    /**
     * Logging Facade.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(OrderManager.class);

    /**
     * Persistence tier for Orders.
     */
    @Inject
    private OrderJpaDao dao;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private OrderContactManager orderContactManager;

    @Inject
    private LocationManager locationManager;

    @Inject
    private LocationMilestoneInstanceManager locationMilestoneInstanceManager;

    @Inject
    private ServiceMilestoneInstanceManager serviceMilestoneInstanceManager;

    @Inject
    private OrderMilestoneInstanceManager orderMilestoneInstanceManager;

    @Inject
    private NotificationManager notificationManager;

    @Inject
    private LocationContactManager locationContactManager;

    @Inject
    private MasterCustomerContactManager masterCustomerContactManger;

    @Inject
    private ContactManager contactManager;

    @Inject
    private ServiceManagerFactory serviceManagerFactory;

    @Inject
    private OrderJeopManager orderJeopManager;

    @Inject
    private OrderNoteManager orderNoteManager;

    @Inject
    private ServiceNoteManager serviceNoteManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> companyConfigPropertyManager;

    @Inject
    private ServiceManager svcManager;

    @Inject
    private ServiceBrokerageManager serviceBrokerageManager;

    @Inject
    private ServiceCustomFieldValueManager serviceCustomFieldValueManager;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private EmailUtility emailUtility;

    @Inject
    private ConfigPropertyManager configPropertyManager;


    @Override
    protected OrderJpaDao getDao() {
        return dao;
    }

    @Override
    public Order retrieve(final Long var1) {
        Order order = super.retrieve(var1);
        for (Location loc : order.getLocations()) {
            if (loc.getId() != null) {
                loc.setLcon(locationContactManager.retrieveByTypeAndLocationId(ContactType.LCON, loc.getId()));
            }
        }
        return order;
    }

    public Order createMacd (final Order entity) {
        Company company = companyManager.retrieve(entity.getCompany().getId());
        entity.setTenantId(company.getTenantId());
        entity.setMasterCustomerId(company.getMasterCustomerId());
        entity.setCompany(company);
        Order created = super.create(entity);
        orderMilestoneInstanceManager.create(created.getId(), "CREATED", new Date());
        orderMilestoneInstanceManager.create(created.getId(), "RECEIVED", new Date());
        return created;
    }

    @Override
    public Order create(final Order entity) {
        Company company = companyManager.retrieve(entity.getCompany().getId());
        entity.setTenantId(company.getTenantId());
        entity.setMasterCustomerId(company.getMasterCustomerId());
        entity.setCompany(company);
        Order created = super.create(entity);
//        handleEmailNotificationsOnCreate(created);
        if (created.getMasterCustomerId() != null) {
            List<MasterCustomerContact> masterCustomerContacts = masterCustomerContactManger.getByMasterCustomerContactId(created.getMasterCustomerId());
            for (MasterCustomerContact mcc : masterCustomerContacts) {
                OrderContact oc = new OrderContact();
                oc.setOrderId(created.getId());
                oc.setTenantId(created.getTenantId());
                oc.setMasterCustomerId(created.getMasterCustomerId());
                oc.setPhone(mcc.getPhone());
                oc.setEmail(mcc.getEmail());
                oc.setType(mcc.getType());
                oc.setFirstName(mcc.getFirstName());
                oc.setLastName(mcc.getLastName());
                oc.setCompanyId(created.getCompany().getId());
                oc.setNotes(mcc.getNotes());
                oc.setRole(mcc.getRole());
                orderContactManager.create(oc);
            }
        }
        orderMilestoneInstanceManager.create(created.getId(), "CREATED", new Date());
        orderMilestoneInstanceManager.create(created.getId(), "RECEIVED", new Date());
        return created;
    }

    private void handleEmailNotificationsOnCreate(final Order created, final OrderCreateDto dto) {
         //if provisioner or project manager has been updated send email
        ConfigurationProperty orderViewLink = null;
        String orderLink = null;
        String loggedInUser = null;
        Subject subject = null;
        List<String> emails = new ArrayList<>();
        Company tenantCompany = null;
        String mc = created.getCompany().getParentCompany().getName();
        String ec = created.getCompany().getName();
        int locCnt = dto.getLocations().size();
        int svcCnt = 0;
        StringBuilder svcTypes = new StringBuilder();
        for (OrderCreateLocation location : dto.getLocations()) {
            svcCnt += location.getServices().size();
            for (OrderCreateService service : location.getServices()) {
                svcTypes.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp").append(service.getServiceType()).append("<br>");
            }
        }

        if (created.getProvisioner() != null) {
            tenantCompany = companyManager.findTenantByTenantId(created.getTenantId());
            CompanyConfigurationProperty emailOnCreate = companyConfigPropertyManager.findByKey(tenantCompany.getId(), CompanyConfigKey.EMAIL_PROVISIONER_ON_ORDER_ASSIGNMENT);
            if (emailOnCreate.getValue().equalsIgnoreCase("true")) {
                loggedInUser = SecurityUtils.getLoggedInUser();
                subject = subjectManager.findByUsername(loggedInUser);
                loggedInUser = subject.getDisplayName();

                orderViewLink = configPropertyManager.findByKey("ORDER_RECEIPT_VIEW_LINK");
                orderLink = orderViewLink.getValue();
                orderLink = orderLink.replace("services?workflowView=Order%20Receipt", "order/" + created.getId());
                Subject provisioner = subjectManager.retrieve(created.getProvisioner());
                if (!loggedInUser.equalsIgnoreCase(provisioner.getDisplayName())) {
                    String emailHeader = "i90 orders have been assigned to you";
                    String emailBody = "You were recently assigned as a Provisioner to an Order in the i90 platform by " + loggedInUser + ":<br>"
                            + mc + ": " + ec + "<br>"
                            + "Location Count: " + locCnt + "<br>"
                            + "Service Count: " + svcCnt + "<br>"
                            + "Services included in the Order: " + "<br>"
                            + svcTypes + "<br>"
                            + "Follow the link below to review the order.<br>"
                            + orderLink;
                    emails.add(provisioner.getEmailAddress());
                    emailUtility.sendEmail(emailHeader, emailBody, emails);
                }
            }
        }

        //if Project Manager has been updated, notify the new PM
        if (created.getVertekProjectManager() != null && !Objects.equals(created.getProvisioner(), created.getVertekProjectManager())) {
            if (tenantCompany == null) {
                tenantCompany = companyManager.findTenantByTenantId(created.getTenantId());
            }
            CompanyConfigurationProperty emailOnCreate = companyConfigPropertyManager.findByKey(tenantCompany.getId(), CompanyConfigKey.EMAIL_PROJECT_MANAGER_ON_ORDER_ASSIGNMENT);
            if (emailOnCreate.getValue().equalsIgnoreCase("true")) {
                if (subject == null) {
                    loggedInUser = SecurityUtils.getLoggedInUser();
                    subject = subjectManager.findByUsername(loggedInUser);
                    loggedInUser = subject.getDisplayName();
                }
                if (orderViewLink == null) {
                    orderViewLink = configPropertyManager.findByKey("ORDER_RECEIPT_VIEW_LINK");
                    orderLink = orderViewLink.getValue();
                    orderLink = orderLink.replace("services?workflowView=Order%20Receipt", "order/" + created.getId());
                }
                Subject pm = subjectManager.retrieve(created.getVertekProjectManager());
                if (!loggedInUser.equalsIgnoreCase(pm.getDisplayName())) {
                    emails.clear();
                    String emailHeader = "i90 orders have been assigned to you";
                    String emailBody = "You were recently assigned as an i90 Project Manager to an Order in the i90 platform by " + loggedInUser + ":<br>"
                            + mc + ": " + ec + "<br>"
                            + "Location Count: " + locCnt + "<br>"
                            + "Service Count: " + svcCnt + "<br>"
                            + "Services included in the Order: " + "<br>"
                            + svcTypes + "<br>"
                            + "Follow the link below to review the order.<br>"
                            + orderLink;
                    emails.add(pm.getEmailAddress());
                    emailUtility.sendEmail(emailHeader, emailBody, emails);
                }
            }
        }
    }

    @Override
    public Order edit(final Order entity) {
        Order existing = retrieve(entity.getId());
        Long existingMcId = existing.getMasterCustomerId();
        Long existingCompanyId = existing.getCompany().getId();
        Long existingProvisioner = existing.getProvisioner();
        Long existingPM = existing.getVertekProjectManager();

        Company company = companyManager.retrieve(entity.getCompany().getId());
        if (company.getParentCompany() == null || !Objects.equals(company.getParentCompany().getId(), entity.getCompany().getParentCompany().getId())) {
            company.setParentCompany(entity.getCompany().getParentCompany());
            companyManager.edit(company);
        }
        Long tenantId = company.getTenantId();
        Long masterCustomerId = company.getMasterCustomerId();
        entity.setTenantId(tenantId);
        entity.setMasterCustomerId(masterCustomerId);

        if (entity.getContacts() != null) {
            for (OrderContact oc : entity.getContacts()) {
                oc.setTenantId(tenantId);
                oc.setMasterCustomerId(masterCustomerId);
                if (oc.getId() == null) {
                    orderContactManager.create(oc);
                } else {
                    orderContactManager.edit(oc);
                }
            }
        }

        String assigned = "";
        if (entity.getProvisioner() != null && existing.getProvisioner() == null) {
            entity.setStatus("Engineer Assigned");
            assigned = "assigned";
        } else if (entity.getProvisioner() == null && existing.getProvisioner() != null) {
            assigned = "unassigned";
        }
        Order edited = super.edit(entity);

        if ("assigned".equals(assigned)) {
            handleProvisionerChange(edited, edited.getProvisioner(), true);

        } else if ("unassigned".equals(assigned)) {
            handleProvisionerChange(edited, null, false);
        }


        //if provisioner or project manager has been updated send email
        handleProvisionerChangeEmail(edited, existingProvisioner, edited.getProvisioner());
        handleProjectManagerChangeEmail(edited, existingPM, edited.getVertekProjectManager());

        //if the master customer has changed update all child tables
        //else if just the end customer has changed update the order contacts and location contacts
        if (!Objects.equals(existingMcId, entity.getCompany().getParentCompany().getId())) {
            updateMcId(entity.getId(), entity.getCompany().getId(), masterCustomerId);
        } else if (!Objects.equals(existingCompanyId, entity.getCompany().getId())) {
            List<OrderContact> orderContacts = orderContactManager.findByOrderId(entity.getId());
            orderContacts.forEach(oc -> {
                oc.setCompanyId(entity.getCompany().getId());
                orderContactManager.edit(oc);
            });

            List<Location> locations = locationManager.findbyOrderId(entity.getId());
            for (Location location : locations) {
                List<LocationContact> locationContacts = locationContactManager.retrieveByLocationId(location.getId());
                locationContacts.forEach(lc -> {
                    lc.setCompanyId(entity.getCompany().getId());
                    locationContactManager.edit(lc);
                });
            }

        }


        //because on the provisioning side there could be multiple Locations on an Order and on the
        //inventory side there can only be one Location on an Order there could be multiple Inventory Orders
        //with the same provisioning order id.  We need to update all of them.
        List<Order> inventoryOrders = dao.findInvByProvisioningId(edited.getId());
        for (Order inventoryOrder : inventoryOrders) {
            Order updated = updateInventory(edited, inventoryOrder);
            if (updated.getId().equals(edited.getId())) {
                edited = updated;
            }
        }
        return edited;
    }

    public void updateMcId(Long orderId, Long ecId, Long mcId) {

        List<OrderMilestoneInstance> oMilestones = orderMilestoneInstanceManager.findByOrderId(orderId);
        oMilestones.forEach(om -> {
            om.setMasterCustomerId(mcId);
            orderMilestoneInstanceManager.edit(om);
        });

        List<OrderJeop> oJeops = orderJeopManager.findByOrderId(orderId);
        oJeops.forEach(oj -> {
            oj.setMasterCustomerId(mcId);
            orderJeopManager.edit(oj);
        });

        List<OrderNote> oNotes = orderNoteManager.findByOrderId(orderId);
        oNotes.forEach(on -> {
            on.setMasterCustomerId(mcId);
            orderNoteManager.edit(on);
        });

        locationManager.updateMcId(orderId, mcId);

    }

    public Order handleProvisionerChange(final Order order, final Long subjectId, final boolean assigned) {
        order.setProvisioner(subjectId);
        List<Long> locationIds = order.getLocations().stream().map(Location::getId).collect(Collectors.toList());
        for (Long locationId : locationIds) {
            if (locationId != null) {
                Location l = locationManager.retrieve(locationId);
                LocationMilestoneInstance lmi = locationMilestoneInstanceManager.retrieveCurrentMilestoneByCode(l.getId(), "ENGINEER_ASSIGNED");
                if (assigned && lmi == null) {
                    locationMilestoneInstanceManager.create(l.getId(), "ENGINEER_ASSIGNED", new Date());
                    List<Long> serviceIds = l.getServices().stream().map(Service::getId).collect(Collectors.toList());
                    for (Long serviceId : serviceIds) {
                        ServiceMilestoneInstance smi = serviceMilestoneInstanceManager.retrieveCurrentMilestoneByCode(
                                serviceId, "ENGINEER_ASSIGNED");
                        if (smi != null) {
                            smi.setMilestoneDate(new Date());
                            serviceMilestoneInstanceManager.edit(smi);
                        }
                    }
                } else if (!assigned && lmi != null) {
                    lmi.setMilestoneDate(null);
                    locationMilestoneInstanceManager.edit(lmi);
                    List<Long> serviceIds = l.getServices().stream().map(Service::getId).collect(Collectors.toList());
                    for (Long serviceId : serviceIds) {
                        ServiceMilestoneInstance smi = serviceMilestoneInstanceManager.retrieveCurrentMilestoneByCode(
                                serviceId, "ENGINEER_ASSIGNED");
                        if (smi != null) {
                            smi.setMilestoneDate(null);
                            serviceMilestoneInstanceManager.edit(smi);
                        }
                    }
                }
            }
        }
        return edit(order);
    }

    public OrderCreateDtoWrapper createFromDto(final OrderCreateDtoWrapper dtoWrapper) {
        // If there is more than one order in the dto wrapper, set the record source to bulk import
        if (dtoWrapper.getDtoList().size() > 1) {
            for (OrderCreateDto dto : dtoWrapper.getDtoList()) {
                for (OrderCreateLocation loc : dto.getLocations()) {
                    loc.setRecordSource(RecordSource.BULK_IMPORT.getName());
                    loc.getServices().forEach(s -> s.setRecordSource(RecordSource.BULK_IMPORT.getName()));
                }
            }
        }

        // Check the lockOneLocationPerOrder company config
        Long tenantId = tenantSubjectManager.getCurrentTenant().getId();
        Company tenantCompany = companyManager.findTenantByTenantId(tenantId);
        Boolean lockOneLocationPerOrder = companyConfigPropertyManager.getBoolean(tenantCompany.getId(), CompanyConfigKey.LOCK_ONE_LOCATION_PER_ORDER);
        Boolean clientIdUniqueConstraint = companyConfigPropertyManager.getBoolean(tenantCompany.getId(), CompanyConfigKey.CLIENT_ID_UNIQUE_CONSTRAINT);
        List<OrderCreateDto> ordersToCreate = new ArrayList<>();
        Location openLocation = null;

        if (Boolean.TRUE.equals(clientIdUniqueConstraint)) {
//            empty list used to collect any validation errors
            List<ValidationError> errors = new ArrayList<>();
            for (OrderCreateDto dto : dtoWrapper.getDtoList()) {
                for (OrderCreateLocation loc : dto.getLocations()) {
                    String clientLocationId = loc.getClientLocationId();

                    // Debug log for checking clientLocationId
                    LOGGER.debug("Processing OrderCreateLocation with clientLocationId: " + clientLocationId);

                    // Fetch existing locations with the same clientLocationId and tenantId
                    List<Location> existingLocations = locationManager.findByClientLocIdAndTenant(clientLocationId, tenantId);

                    //verify if client location id already exists that it belongs to the same master customer/end customer
                    if (!existingLocations.isEmpty()) {
                        Company invMc = companyManager.retrieve(existingLocations.get(0).getMasterCustomerId());
                        if (dto.getMasterCustomer() != null && invMc != null && !invMc.getId().equals(dto.getMasterCustomer().getId())) {
                            throw new BadRequestException("Client Location ID already exists under Master Customer " + invMc.getName());
                        } else {
                            Order order = retrieve(existingLocations.get(0).getOrderId());
                            if (dto.getEndCustomer() != null && order != null && !order.getCompany().getId().equals(dto.getEndCustomer().getId())) {
                                throw new BadRequestException("Client Location ID already exists under End Customer " + order.getCompany().getName());
                            }

                        }
                    }

//                    if (combineOpenOrders) {
//                        // Filter to get only open locations
//                        List<Location> openLocations = existingLocations.stream()
//                                .filter(l -> !TerminalLocationStatuses.getStatuses().contains(l.getStatus()))
//                                .collect(Collectors.toList());
//
//
//                        // If there are open locations with the same clientLocationId, add a validation error
//                        if (!openLocations.isEmpty()) {
//                            openLocation = openLocations.get(0);
////                           throw new BadRequestException("Client Location ID " + clientLocationId + " is associated with another open location.");
//                        }
//                    }
                }
            }
        }

        if (Boolean.TRUE.equals(lockOneLocationPerOrder)) {
            for (OrderCreateDto dto : dtoWrapper.getDtoList()) {
                if (dto.getLocations().size() > 1) {
                    for (OrderCreateLocation loc : dto.getLocations()) {
                        OrderCreateDto o = new OrderCreateDto();
                        o.setMasterCustomer(dto.getMasterCustomer());
                        o.setEndCustomer(dto.getEndCustomer());
                        o.setClientOrderId(dto.getClientOrderId());
                        o.setProvisioner(dto.getProvisioner());
                        o.setActivationEngineer(dto.getActivationEngineer());
                        o.setClientProjectManager(dto.getClientProjectManager());
                        o.setVertekProjectManager(dto.getVertekProjectManager());
                        o.setQaManager(dto.getQaManager());
                        o.setHoldProvisioning(dto.isHoldProvisioning());
                        o.setJeopDescription(dto.getJeopDescription());
                        o.setJeopResponsibility(dto.getJeopResponsibility());
                        o.getLocations().add(loc);
                        ordersToCreate.add(o);
                    }
                } else {
                    ordersToCreate.add(dto);
                }
            }
        } else {
            ordersToCreate = dtoWrapper.getDtoList();
        }

        // Create the orders
        OrderCreateDtoWrapper result = new OrderCreateDtoWrapper();
        for (OrderCreateDto dto : ordersToCreate) {
            createFromDto(dto);
            result.getDtoList().add(dto);
        }

        // send i90 notifications
        CompanyConfigurationProperty alertOnCreate = companyConfigPropertyManager.findByKey(tenantCompany.getId(), CompanyConfigKey.ALERT_ON_ORDER_CREATE);
        List<String> usernames = new ArrayList<>();
        if (alertOnCreate != null && alertOnCreate.getValue() != null) {
            usernames = List.of(alertOnCreate.getValue().split(", "));
        }
        String notificationHeader;
        String notificationBody;
        if (result.getDtoList().size() > 1) {
            notificationHeader = "Orders Created";
            notificationBody = result.getDtoList().size() + " new orders have been created";
        } else {
            notificationHeader = "Order Created";
            notificationBody = "A new order has been created";
        }
        for (String username : usernames) {
            try {
                if (!"".equalsIgnoreCase(username)) {
                    notificationManager.create(subjectManager.findByEmailAddress(username).getId(), notificationHeader, notificationBody, "priority_high", "/services?workflowView=Order%20Receipt");
                }
            } catch (Exception e) {
                LOGGER.error("Error sending notification to " + username, e);
            }
        }

        //send email notification
        CompanyConfigurationProperty emailOnCreate = companyConfigPropertyManager.findByKey(tenantCompany.getId(), CompanyConfigKey.EMAIL_ON_ORDER_CREATE);
        ConfigurationProperty orderReceiptViewLink = configPropertyManager.findByKey("ORDER_RECEIPT_VIEW_LINK");
        List<String> emails = new ArrayList<>();
        if (emailOnCreate != null && emailOnCreate.getValue() != null) {
            emails = List.of(emailOnCreate.getValue().split(", "));
        }
        if (!emails.isEmpty()) {
            String emailHeader;
            String emailBody = "";
            String mc = result.getDtoList().get(0).getMasterCustomer().getName();
            String ec = result.getDtoList().get(0).getEndCustomer().getName();
            //get the link from the first service on the first location on the first order for the email
            String link = result.getDtoList().get(0).getLocations().get(0).getServices().get(0).getLink();
            String[] parts = link.split("/");
            String orderId = null;
            // Find "order" and get the orderId
            for (int i = 0; i < parts.length; i++) {
                if ("order".equals(parts[i]) && i + 1 < parts.length) {
                    orderId = parts[i + 1];
                    System.out.println(parts[i + 1]); // Output: 5381
                    break;
                }
            }

            String orderLink = orderReceiptViewLink.getValue();
            orderLink = orderLink.replace("services?workflowView=Order%20Receipt", "order/" + orderId);
            int locCnt = result.getDtoList().get(0).getLocations().size();
            int orderCnt = result.getDtoList().size();
            int svcCnt = 0;
            StringBuilder svcTypes = new StringBuilder();
            for (OrderCreateLocation location : result.getDtoList().get(0).getLocations()) {
                svcCnt += location.getServices().size();
                for (OrderCreateService service : location.getServices()) {
                    svcTypes.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp").append(service.getServiceType()).append("<br>");
                }
            }
            if (result.getDtoList().size() > 1) {
                emailHeader = "New i90 Orders Created";
            } else {
                emailHeader = "New i90 Order Created";
            }
            emailBody += "An Order has been created for " + mc + ": " + ec + ":<br>";
            if (orderCnt > 1) {
                emailBody += "Order Count: " + orderCnt + "<br>";
            }
            emailBody += "Location Count: " + locCnt + "<br>"
                    + "Service Count: " + svcCnt + "<br>"
                    + "Services included in the Order: " + "<br>"
                    + svcTypes + "<br><br>"
                    + "Follow the link below to view the Order in the worklist." + "<br>"
                    + orderLink;
            emailUtility.sendEmail(emailHeader, emailBody, emails);
        }
        return result;
    }

    /**
     * Creates an Order from an OrderCreateDto.
     *
     * @param dto the OrderCreateDto
     */
    private void createFromDto(final OrderCreateDto dto) {

        Long tenantId = tenantSubjectManager.getCurrentTenant().getId();
        Company tenantCompany = companyManager.findTenantByTenantId(tenantId);
        Boolean brokerage = companyConfigPropertyManager.getBoolean(tenantCompany.getId(), CompanyConfigKey.SHOW_BROKERAGE_FIELDS);
        Company endCustomer = companyManager.retrieve(dto.getEndCustomer().getId());
        boolean combineOpenOrders = companyConfigPropertyManager.getBoolean(tenantCompany.getId(), CompanyConfigKey.COMBINE_OPEN_ORDERS);

        boolean createOrder = false;

        if (combineOpenOrders) {
            //Loop through the location to see if there are any open ones
            for (OrderCreateLocation loc : dto.getLocations()) {
                List<Location> existingLocations = locationManager.findByClientLocIdAndTenant(loc.getClientLocationId(), tenantId);
                List<Location> openLocations = existingLocations.stream()
                        .filter(l -> !TerminalLocationStatuses.getStatuses().contains(l.getStatus()))
                        .collect(Collectors.toList());
                if (openLocations.isEmpty()) {
                    createOrder = true;
                } else {
                    loc.setOpenLocationId(openLocations.get(0).getId());
                }
            }
        } else {
            createOrder = true;
        }

        Long orderId = Long.valueOf(0);
        //only create the order if there are no open locations under it
        if (createOrder) {
            Order order = new Order();
            order.setCompany(endCustomer);
            order.setTenantId(endCustomer.getTenantId());
            order.setMasterCustomerId(endCustomer.getMasterCustomerId());

            order.setProvisioner(dto.getProvisioner());
            order.setVertekProjectManager(dto.getVertekProjectManager());
            order.setActivationEngineer(dto.getActivationEngineer());
            order.setClientProjectManager(dto.getClientProjectManager());
            order.setQaManager(dto.getQaManager());
            order.setClientOrderId(dto.getClientOrderId());
            Order created = super.create(order);
            handleEmailNotificationsOnCreate(created, dto);
            orderId = created.getId();

            orderMilestoneInstanceManager.create(created.getId(), "CREATED", new Date());
            orderMilestoneInstanceManager.create(created.getId(), "RECEIVED", new Date());

            List<Contact> contacts = new ArrayList<>();
            if (dto.getSalesContact() != null) {
                if (dto.getSalesContact().getId() != null) {
                    contacts.add(contactManager.retrieve(dto.getSalesContact().getId()));
                } else {
                    Contact contact = new Contact();
                    contact.setFirstName(dto.getSalesContact().getFirstName());
                    contact.setLastName(dto.getSalesContact().getLastName());
                    contact.setEmail(dto.getSalesContact().getEmail());
                    contact.setPhone(dto.getSalesContact().getPhone());
                    contact.setRole(dto.getSalesContact().getRole());
                    contact.setType(ContactType.SALES);
                    contacts.add(contact);
                }
            }
            if (dto.getTechContact() != null) {
                if (dto.getTechContact().getId() != null) {
                    contacts.add(contactManager.retrieve(dto.getTechContact().getId()));
                } else {
                    Contact contact = new Contact();
                    contact.setFirstName(dto.getTechContact().getFirstName());
                    contact.setLastName(dto.getTechContact().getLastName());
                    contact.setEmail(dto.getTechContact().getEmail());
                    contact.setPhone(dto.getTechContact().getPhone());
                    contact.setRole(dto.getTechContact().getRole());
                    contact.setType(ContactType.TECH);
                    contacts.add(contact);
                }
            }
            if (dto.getAuthContact() != null) {
                if (dto.getAuthContact().getId() != null) {
                    contacts.add(contactManager.retrieve(dto.getAuthContact().getId()));
                } else {
                    Contact contact = new Contact();
                    contact.setFirstName(dto.getAuthContact().getFirstName());
                    contact.setLastName(dto.getAuthContact().getLastName());
                    contact.setEmail(dto.getAuthContact().getEmail());
                    contact.setPhone(dto.getAuthContact().getPhone());
                    contact.setRole(dto.getAuthContact().getRole());
                    contact.setType(ContactType.AUTHORIZATION);
                    contacts.add(contact);
                }
            }

            for (Contact contact : contacts) {
                if (contact != null) {
                    OrderContact oc = new OrderContact();
                    oc.setOrderId(created.getId());
                    oc.setTenantId(created.getTenantId());
                    oc.setMasterCustomerId(created.getMasterCustomerId());
                    oc.setPhone(contact.getPhone());
                    oc.setEmail(contact.getEmail());
                    oc.setType(contact.getType());
                    oc.setFirstName(contact.getFirstName());
                    oc.setLastName(contact.getLastName());
                    oc.setCompanyId(created.getCompany().getId());
                    oc.setNotes(contact.getNotes());
                    oc.setRole(contact.getRole());
                    orderContactManager.create(oc);
                }
            }

            if (dto.isHoldProvisioning()) {
                //create an order jeop
                OrderJeop jeop = new OrderJeop();
                jeop.setOrderId(created.getId());
                jeop.setStartDate(new Date());
                jeop.setDescription(dto.getJeopDescription());
                jeop.setResponsibility(dto.getJeopResponsibility());
                orderJeopManager.create(jeop);
            }
        }

        List<OrderCreateService> svcsForAssociation = new ArrayList<>();
        for (OrderCreateLocation dtoLoc : dto.getLocations()) {
            svcsForAssociation.addAll(dtoLoc.getServices());
            Long locId = dtoLoc.getOpenLocationId();
            if (dtoLoc.getOpenLocationId() == null || dtoLoc.getOpenLocationId() == 0) {
                // Create the location if there isn't an open location
                Location location = new Location();
                location.setOrderId(orderId);
                location.setRecordSource(dtoLoc.getRecordSource() == null ? RecordSource.MANUAL_ENTRY.getName() : dtoLoc.getRecordSource());
                location.setClientLocationId(dtoLoc.getClientLocationId());
                location.setClientLocationInfo(dtoLoc.getLocationInfo());
                location.setClientLocationType(dtoLoc.getLocationType());
                location.setLevelOfEffort(dtoLoc.getLevelOfEffort());
                location.setAddress1(dtoLoc.getAddress().getAddress1());
                location.setAddress2(dtoLoc.getAddress().getAddress2());
                location.setCity(dtoLoc.getAddress().getCity());
                location.setState(dtoLoc.getAddress().getState());
                location.setPostalCode(dtoLoc.getAddress().getPostalCode());
                location.setCountry(dtoLoc.getAddress().getCountry());
                // LCON
                LocationContact lcon = new LocationContact();
                String lconName = dtoLoc.getLconName();
                if (!Strings.isNullOrEmpty(lconName) && lconName.contains(" ")) {
                    lcon.setFirstName(lconName.substring(0, lconName.indexOf(" ")));
                    lcon.setLastName(lconName.substring(lconName.indexOf(" ") + 1));
                } else {
                    lcon.setFirstName(lconName);
                }
                lcon.setPhone(dtoLoc.getLconPhone());
                lcon.setEmail(dtoLoc.getLconEmail());
                location.setLcon(lcon);

                Location createdLoc = locationManager.create(location);
                locId = createdLoc.getId();
            }

            for (OrderCreateService dtoService : dtoLoc.getServices()) {
                // Create the service
                AbstractServiceManager serviceManager = serviceManagerFactory.getManager(ServiceType.fromServiceName(dtoService.getServiceType()));
                Service service;
                switch (ServiceType.fromServiceName(dtoService.getServiceType())) {
                    case DIA:
                        service = new DiaService();
                        break;
                    case BROADBAND:
                        service = new BroadbandService();
                        break;
                    case UCAAS:
                        service = new UcaasService();
                        break;
                    case G:
                        service = new GService();
                        break;
                    case CROSSCONNECT:
                        service = new CrossConnectService();
                        break;
                    case ETHERNET:
                        service = new EthernetService();
                        break;
                    case TELEVISION:
                        service = new TelevisionService();
                        break;
                    case MPLS:
                        service = new MplsService();
                        break;
                    case THREATMDR:
                        service = new ThreatMDRService();
                        break;
                    case RANSOMMDR:
                        service = new RansomMDRService();
                        break;
                    case RISKMDR:
                        service = new RiskMDRService();
                        break;
                    case ENGINEERING_MDM:
                        service = new EngineeringMDMService();
                        break;
                    case ENGINEERING_IAM:
                        service = new EngineeringIAMService();
                        break;
                    case ENGINEERING_ENDPOINT:
                        service = new EngineeringEndpointService();
                        break;
                    case ENGINEERING_INFO_PROTECTION:
                        service = new EngineeringInfoProtectionService();
                        break;
                    case ENGINEERING_EMAIL_MESSAGING:
                        service = new EngineeringEmailMessagingService();
                        break;
                    case CYBER360MXDR:
                        service = new Cyber360MXDRService();
                        break;
                    case MICROSOFTLICENSES:
                        service = new MicrosoftLicensesService();
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported service type '" + dtoService.getServiceType() + "'.");
                }
                //get the open location to add the service to
                if (dtoLoc.getOpenLocationId() != null) {
                    Location openLocation = locationManager.retrieve(dtoLoc.getOpenLocationId());
                    orderId = openLocation.getOrderId();
                    locId = openLocation.getId();
                }
                service.setOrderId(orderId);
                service.setLocationId(locId);
                service.setRecordSource(dtoService.getClientLocationId() == null ? RecordSource.MANUAL_ENTRY.getName() : dtoService.getRecordSource());
                service.setClientServiceId(dtoService.getClientServiceId());
                service.setQuoteSolutionId(dtoService.getQuoteId());
                service.setProjectName(dtoService.getProjectName());
                service.setProvider(dtoService.getProvider());
                service.setServiceBilledTo(dtoService.getServiceBilledTo());
                service.setSubProductType(dtoService.getSubProductType());
                service.setClientServiceInfo(dtoService.getServiceInfo());
                service.setClientServiceType(dtoService.getClientServiceType());
                service.setContractTerm(dtoService.getContractTerm());
                service.setStatus("Pending Assignment");
                service.setPoNumber(dtoService.getPoNumber());
                if (dtoService.getMrc() != null) {
                    service.setMrc(dtoService.getMrc());
                }
                if (dtoService.getNrc() != null) {
                    service.setNrc(dtoService.getNrc());
                }
                if (dtoService.getAnnualNrc() != null) {
                    service.setAnnualRecurringCost(dtoService.getAnnualNrc());
                }
                service.setDownloadSpeed(dtoService.getDownloadSpeed());
                service.setUploadSpeed(dtoService.getUploadSpeed());
                service.setMediaType(dtoService.getMediaType());
                service.setDescription(dtoService.getDescription());
                service.setAutoRenewal(dtoService.isAutoRenewal());
                service.setCoTerminus(dtoService.isCoTerminus());
                service.setRenewalCancelNoticePeriod(dtoService.getNoticePeriodForRenewal());
                service.setContractInfo(dtoService.getContractInfo());
                service.setAdditionalIpBlock(dtoService.getAdditionalIpBlock());
                service.setDmarc(dtoService.getDmarc());
                service.setAddress1(dto.getBillingAddress().getAddress1());
                service.setAddress2(dto.getBillingAddress().getAddress2());
                service.setCity(dto.getBillingAddress().getCity());
                service.setState(dto.getBillingAddress().getState());
                service.setPostalCode(dto.getBillingAddress().getPostalCode());
                service.setCountry(dto.getBillingAddress().getCountry());
                service.setContractSignedDate(dtoService.getContractSignedDate());
                service.setFieldServicesProvider(dtoService.getFieldServicesProvider());
                service.setOpportunityNum(dtoService.getOpportunityNum());
                if (dtoService.getMrr() != null) {
                    service.setMrr(dtoService.getMrr());
                }
                if (dtoService.getNrr() != null) {
                    service.setNrr(dtoService.getNrr());
                }
                service.setManagedService(dtoService.isManagedService());

                //Z Address
                if (service instanceof EthernetService) {
                    ((EthernetService) service).setzAddress1(dtoService.getzAddress().getAddress1());
                    ((EthernetService) service).setzAddress2(dtoService.getzAddress().getAddress2());
                    ((EthernetService) service).setzCity(dtoService.getzAddress().getCity());
                    ((EthernetService) service).setzState(dtoService.getzAddress().getState());
                    ((EthernetService) service).setzPostalCode(dtoService.getzAddress().getPostalCode());
                    ((EthernetService) service).setzCountry(dtoService.getzAddress().getCountry());
                }

                //persist service
                service = serviceManager.create(service);
                dtoService.setClientServiceId(service.getClientServiceId());

                //create custom fields
                if (dtoService.getCustomFields() != null) {
                    for (OrderCreateServiceCustomFields cf : dtoService.getCustomFields()) {
                        ServiceCustomFieldValue scfv = new ServiceCustomFieldValue();
                        scfv.setServiceId(service.getId());
                        scfv.setCustomFieldId((cf.getCustomFieldId()));
                        scfv.setValue(cf.getValue());
                        scfv.setTenantId(service.getTenantId());
                        serviceCustomFieldValueManager.create(scfv);
                    }
                }

                if (brokerage != null && brokerage) {
                    ServiceBrokerage brokerageInfo = new ServiceBrokerage();
                    brokerageInfo.setServiceId(service.getId());
                    brokerageInfo.setSubmittedInAdvToProvider(dtoService.isSubmittedInAdvToProvider());
                    brokerageInfo.setParentTsd(dtoService.getParentTsd());
                    brokerageInfo.setSubmittedInAdvToTsd(dtoService.isSubmittedInAdvToTsd());
                    brokerageInfo.setCieTeamedDealInfo(dtoService.getCieTeamedDealInfo());
                    brokerageInfo.setNetProviderPoints(dtoService.getNetProviderPoints());
                    brokerageInfo.setPromotions(dtoService.getPromotions());

                    if (dtoService.getCommissionableMrc() != null) {
                        brokerageInfo.setCommissionableMrc(dtoService.getCommissionableMrc());
                    }

                    if (dtoService.getCommissionableNrc() != null) {
                        brokerageInfo.setCommissionableNrc(dtoService.getCommissionableNrc());
                    }

                    if (dtoService.getCommissionableArc() != null) {
                        brokerageInfo.setCommissionableArc(dtoService.getCommissionableArc());
                    }

                    if (dtoService.getSubAgentPercent() != null) {
                        double subAgentPercent = dtoService.getSubAgentPercent();
                        if (subAgentPercent < 1) {
                            subAgentPercent = subAgentPercent * 100;
                        }
                        brokerageInfo.setSubAgentPercent(subAgentPercent);
                    }
                    brokerageInfo.setSubAgent(dtoService.getSubAgent());
                    brokerageInfo.setReferral(dtoService.isReferral());
                    brokerageInfo.setReferralName(dtoService.getReferralName());

                    if (dtoService.getCompanyReferralPercent() != null) {
                        double companyReferralPercent = dtoService.getCompanyReferralPercent();
                        if (companyReferralPercent < 1) {
                            companyReferralPercent = companyReferralPercent * 100;
                        }
                        brokerageInfo.setReferralPercent(companyReferralPercent);
                    }

                    if (dtoService.getSpiffAmount() != null) {
                        brokerageInfo.setSpiffAmount(dtoService.getSpiffAmount());
                    }

                    brokerageInfo.setEngineerResource(dtoService.isEngineerResource());
                    if (dtoService.getEngineerResourceAllocation() != null) {
                        double engineerResourceAllocation = dtoService.getEngineerResourceAllocation();
                        if (engineerResourceAllocation < 1) {
                            engineerResourceAllocation = engineerResourceAllocation * 100;
                        }
                        brokerageInfo.setEngineerResourceAllocation(engineerResourceAllocation);
                    }

                    brokerageInfo.setCommissionPaymentType(dtoService.getCommissionPaymentType());

                    if (dtoService.getExpectedCommission() != null) {
                        brokerageInfo.setExpectedCommission(dtoService.getExpectedCommission());
                    }
                    if (dtoService.getCommissionReductionPercent() != null) {
                        double commissionReductionPercent = dtoService.getCommissionReductionPercent();
                        if (commissionReductionPercent < 1) {
                            commissionReductionPercent = commissionReductionPercent * 100;
                        }
                        brokerageInfo.setCommissionReductionPercent( commissionReductionPercent);
                    }

                    brokerageInfo.setCommissionIcb(dtoService.isCommissionIcb());
                    brokerageInfo.setInternalCommissionsComments(dtoService.getInternalCommissionsComments());
                    brokerageInfo.setAgent(dtoService.getAgent());
                    brokerageInfo.setAgentPercent(dtoService.getAgentPercent());
                    brokerageInfo.setAgentRep(dtoService.getAgentRep());
                    brokerageInfo.setSubAgentRep(dtoService.getSubAgentRep());
                    serviceBrokerageManager.create(brokerageInfo);
                }

                dtoService.setServiceId(service.getId());
                String link = "order/" + orderId + "/location/" + locId + "/service/" + service.getId();
                dtoService.setLink(link);

                //CUSTOMER_REQUESTED_INSTALL milestone
                if (dtoService.getCustomerRequestedInstallDate() != null) {
                    serviceMilestoneInstanceManager.create(service.getId(), "CUSTOMER_REQUESTED_INSTALL", dtoService.getCustomerRequestedInstallDate());
                }
                if (dto.getProvisioner() != null) {
                    serviceMilestoneInstanceManager.create(service.getId(), "ENGINEER_ASSIGNED", new Date());
                }

                //create service notes and on hold milestone
                String noteBody = "";
                if (dto.isHoldProvisioning()) {
                    serviceMilestoneInstanceManager.create(service.getId(), "ON_HOLD", new Date());
                    noteBody = "Order entry created the service request in an On Hold Status";
                    if (dtoService.getCustomerRequestedInstallDate() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
                        noteBody += " with a Customer Requested Install date of " + sdf.format(dtoService.getCustomerRequestedInstallDate());
                    }
                } else {
                    noteBody = "Order entry created the service request";
                }
                serviceNoteManager.create(service.getId(), noteBody, service.getType());

            }

            //create Service Associations
            for (OrderCreateService dtoService : svcsForAssociation) {
                if (!"none".equalsIgnoreCase(dtoService.getLinkedOrBundled())) {
                    AbstractServiceManager serviceManager = serviceManagerFactory.getManager(ServiceType.fromServiceName(dtoService.getServiceType()));
                    Service parent = svcManager.findByClientServiceIdAndLocationId(dtoService.getLinkedBundledClientServiceId(), locId);
                    Service service = svcManager.retrieve(dtoService.getServiceId());
                    if ("linked".equalsIgnoreCase(dtoService.getLinkedOrBundled())) {
                        service.setLinked(true);
                    } else if ("bundled".equalsIgnoreCase(dtoService.getLinkedOrBundled())) {
                        service.setBundled(true);
                    }
                    service.setLinkedBundledParentId(parent.getId());
                    if (parent.getId().equals(service.getId())) {
                        service.setLinkedBundledParent(true);
                    }
                    serviceManager.edit(service);
                }
            }
        }

//        if (dto.isHoldProvisioning()) {
//            //set the on hold milestone
//            orderMilestoneInstanceManager.create(orderId, "ON_HOLD", new Date());
//        }

    }

    /**
     * Finds a single Order by a quote ID.
     *
     * @param quoteId the quote ID to filter by.
     * @return the single matching Order, if any.
     */
    public Order getByQuoteId(final String quoteId) {
        return dao.getByQuoteId(quoteId);
    }

    /**
     * Returns an Order.
     *
     * @param orderId  Order ID.
     * @param tenantId Tenant ID.
     * @return Activation Scheule Object.
     */
    public Order findByIdAndTenant(final Long orderId, final Long tenantId) {
        return dao.findByIdAndTenant(orderId, tenantId);
    }

    /**
     * Gets the count of orders for a given list of service IDs.
     *
     * @param serviceIds list of service Ids.
     * @return the count of orders.
     */
    public Long getOrderCount(final List<Long> serviceIds) {
        return dao.getOrderCount(serviceIds);
    }

    public Order createInventoryOrder(Order existingOrder) {
        Order inventoryOrder = new Order();
        try {
            for (Field field : Order.class.getDeclaredFields()) {
                if (!field.getName().equalsIgnoreCase("isCurrentInventory")
                        && !field.getName().equalsIgnoreCase("provisioningOrderId")
                        && !field.getName().equalsIgnoreCase("inventoryOrderId")
                        && !field.getName().equalsIgnoreCase("locations")
                        && !field.getName().equalsIgnoreCase("contacts")
                        && !field.getName().equalsIgnoreCase("vertekClient")
                        && !field.getName().equalsIgnoreCase("createdDate")
                        && !field.getName().equalsIgnoreCase("eligibleForInventory")
                        && !field.getName().equalsIgnoreCase("finalUpdate")
                        && !field.getName().equalsIgnoreCase("id")
                        && !field.getName().equalsIgnoreCase("version")) {
                    field.setAccessible(true);
                    field.set(inventoryOrder, field.get(existingOrder));
                }
            }
            inventoryOrder.setTenantId(existingOrder.getTenantId());
            inventoryOrder.setMasterCustomerId(existingOrder.getMasterCustomerId());
            inventoryOrder.setCurrentInventory(true);
            inventoryOrder.setProvisioningOrderId(existingOrder.getId());
            super.create(inventoryOrder);
            orderMilestoneInstanceManager.createNoEventHandler(inventoryOrder.getId(),
                    "CREATED", new Date());

            copySupportingData(existingOrder, inventoryOrder);

            existingOrder.setInventoryOrderId(inventoryOrder.getId());
            super.edit(existingOrder);
            return inventoryOrder;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void copySupportingData(Order existingOrder, Order inventoryOrder) {
        try {
            List<OrderContact> ocons = orderContactManager.findByOrderId(existingOrder.getId());
            for (OrderContact ocon : ocons) {
                OrderContact newOcon = new OrderContact();
                for (Field field : Contact.class.getDeclaredFields()) {
                    if (!field.getName().equalsIgnoreCase("id")
                            && !field.getName().equalsIgnoreCase("orderId")
                            && !field.getName().equalsIgnoreCase("parentContactId")
                            && !field.getName().equalsIgnoreCase("masterCustomerId")
                            && !field.getName().equalsIgnoreCase("version")) {
                        field.setAccessible(true);
                        field.set(newOcon, field.get(ocon));
                    }
                }
                newOcon.setTenantId(inventoryOrder.getTenantId());
                newOcon.setOrderId(inventoryOrder.getId());
                newOcon.setMasterCustomerId(inventoryOrder.getMasterCustomerId());
                newOcon.setParentContactId(ocon.getId());
                orderContactManager.create(newOcon);
            }


            List<OrderMilestoneInstance> milestones = orderMilestoneInstanceManager.findByOrderId(existingOrder.getId());
            for (OrderMilestoneInstance milestone : milestones) {
                if (!milestone.getMilestone().getCode().equalsIgnoreCase("CREATED")) {
                    OrderMilestoneInstance newMilestone =
                            orderMilestoneInstanceManager.createNoEventHandler(inventoryOrder.getId(),
                                    milestone.getMilestone().getCode(), milestone.getMilestoneDate());
                    newMilestone.setParentMilestoneInstanceId(milestone.getId());
                    orderMilestoneInstanceManager.editNoEventHandler(newMilestone);
                }
            }

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Order updateInventory(Order existingOrder, Order inventoryOrder) {
        //only proccess if eligible for update and not a final update and not inventory
        if (!existingOrder.isEligibleForInventory()
                || existingOrder.getFinalUpdate()
                || existingOrder.isCurrentInventory()) {
            return existingOrder;
        }
        Order updateOrder = existingOrder;

        if ((updateOrder.getInventoryOrderId() != null)) {
            if (inventoryOrder != null) {
                try {
                    for (Field field : Order.class.getDeclaredFields()) {
                        if (!field.getName().equalsIgnoreCase("isCurrentInventory")
                                && !field.getName().equalsIgnoreCase("provisioningOrderId")
                                && !field.getName().equalsIgnoreCase("inventoryOrderId")
                                && !field.getName().equalsIgnoreCase("locations")
                                && !field.getName().equalsIgnoreCase("contacts")
                                && !field.getName().equalsIgnoreCase("vertekClient")
                                && !field.getName().equalsIgnoreCase("createdDate")
                                && !field.getName().equalsIgnoreCase("eligibleForInventory")
                                && !field.getName().equalsIgnoreCase("masterCustomerId")
                                && !field.getName().equalsIgnoreCase("finalUpdate")
                                && !field.getName().equalsIgnoreCase("id")
                                && !field.getName().equalsIgnoreCase("version")) {
                            field.setAccessible(true);
                            field.set(inventoryOrder, field.get(updateOrder));
                        }
                    }
                    super.edit(inventoryOrder);

                    deleteSupportingData(inventoryOrder);
                    copySupportingData(updateOrder, inventoryOrder);

                    updateOrder.setFinalUpdate(TerminalOrderStatuses.getStatuses().contains(updateOrder.getStatus()));
                    return super.edit(updateOrder);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return existingOrder;
            }
        } else {
            return existingOrder;
        }
    }

    public List<Order> findInvByProvisioningId(Long provisioningId) {
        return dao.findInvByProvisioningId(provisioningId);
    }

    private void deleteSupportingData(Order inventoryOrder) {
        List<OrderContact> ocons = orderContactManager.findByOrderId(inventoryOrder.getId());
        for (OrderContact ocon : ocons) {
            orderContactManager.remove(ocon);
        }

        List<OrderMilestoneInstance> milestones = orderMilestoneInstanceManager.findByOrderId(inventoryOrder.getId());
        for (OrderMilestoneInstance milestone : milestones) {
            if (!milestone.getMilestone().getCode().equalsIgnoreCase("CREATED")) {
                orderMilestoneInstanceManager.remove(milestone);
            }
        }
    }

    public void editSuper(final Order inventoryOrder) {
        super.edit(inventoryOrder);
    }

    /**
     * Performs a mass update of all order provisioners by master customer ID.
     *
     * @param id          master customer ID.
     * @param provisioner provisioner ID.
     */
    public void updateProvisionerForMasterCompany(final Long id, final Long provisioner) {
        dao.updateProvisionerForMasterCompany(id, provisioner);
    }

    public List<Order> findByComapnyId(final Long companyId) {
        return dao.findByCompanyId(companyId);
    }

    public void handleProvisionerChangeEmail(final Order order, final Long existingProvisioner, final Long newProvisioner) {
         //if provisioner or project manager has been updated send email
        if (newProvisioner != null && !Objects.equals(newProvisioner, existingProvisioner)) {
            Company tenantCompany = companyManager.findTenantByTenantId(order.getTenantId());
            CompanyConfigurationProperty emailOnCreate = companyConfigPropertyManager.findByKey(tenantCompany.getId(), CompanyConfigKey.EMAIL_PROVISIONER_ON_ORDER_ASSIGNMENT);
            if (emailOnCreate.getValue().equalsIgnoreCase("true")) {
                String loggedInUser = SecurityUtils.getLoggedInUser();
                if (!loggedInUser.contains(SecurityUtils.SCHEDULER)) {
                    loggedInUser = subjectManager.findByEmailAddress(loggedInUser).getDisplayName();
                } else {
                    loggedInUser = "Multi Edit function";
                }
                ConfigurationProperty orderViewLink = configPropertyManager.findByKey("ORDER_RECEIPT_VIEW_LINK");
                String orderLink = orderViewLink.getValue();
                orderLink = orderLink.replace("services?workflowView=Order%20Receipt", "order/" + order.getId());
                Subject provisioner = subjectManager.retrieve(newProvisioner);

                String mc = order.getCompany().getParentCompany().getName();
                String ec = order.getCompany().getName();
                int locCnt = order.getLocations().size();
                int svcCnt = 0;
                StringBuilder svcTypes = new StringBuilder();
                for (Location location : order.getLocations()) {
                    svcCnt += location.getServices().size();
                    for (Service service : location.getServices()) {
                        svcTypes.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp").append(service.getType()).append("<br>");
                    }
                }
                if (!loggedInUser.equalsIgnoreCase(provisioner.getDisplayName())) {
                    String emailHeader = "i90 orders have been assigned to you";
                    String emailBody = "You were recently assigned as a Provisioner to an Order in the i90 platform by " + loggedInUser + ":<br>"
                            + mc + ": " + ec + "<br>"
                            + "Location Count: " + locCnt + "<br>"
                            + "Service Count: " + svcCnt + "<br>"
                            + "Services included in the Order: " + "<br>"
                            + svcTypes + "<br>"
                            + "Follow the link below to review the order.<br>"
                            + orderLink;

                    List<String> emails = new ArrayList<>();
                    emails.add(provisioner.getEmailAddress());
                    emailUtility.sendEmail(emailHeader, emailBody, emails);
                }
            }
        }
    }

    public void handleProjectManagerChangeEmail(Order order, Long existingPm, Long newPm) {
        //if Project Manager has been updated, notify the new PM
        if (newPm != null && !Objects.equals(newPm, existingPm)) {
            Company tenantCompany = companyManager.findTenantByTenantId(order.getTenantId());

            CompanyConfigurationProperty emailOnCreate = companyConfigPropertyManager.findByKey(tenantCompany.getId(), CompanyConfigKey.EMAIL_PROJECT_MANAGER_ON_ORDER_ASSIGNMENT);
            if (emailOnCreate.getValue().equalsIgnoreCase("true")) {
                String loggedInUser = SecurityUtils.getLoggedInUser();
                if (!loggedInUser.contains(SecurityUtils.SCHEDULER)) {
                    loggedInUser = subjectManager.findByEmailAddress(loggedInUser).getDisplayName();
                } else {
                    loggedInUser = "Multi Edit function";
                }

                ConfigurationProperty orderViewLink = configPropertyManager.findByKey("ORDER_RECEIPT_VIEW_LINK");
                String orderLink = orderViewLink.getValue();
                orderLink = orderLink.replace("services?workflowView=Order%20Receipt", "order/" + order.getId());

                String mc = order.getCompany().getParentCompany().getName();
                String ec = order.getCompany().getName();
                int locCnt = order.getLocations().size();
                int svcCnt = 0;
                StringBuilder svcTypes = new StringBuilder();
                for (Location location : order.getLocations()) {
                    svcCnt += location.getServices().size();
                    for (Service service : location.getServices()) {
                        svcTypes.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp").append(service.getType()).append("<br>");
                    }
                }
                Subject pm = subjectManager.retrieve(newPm);
                if (!loggedInUser.equalsIgnoreCase(pm.getDisplayName())) {
                    String emailHeader = "i90 orders have been assigned to you";
                    String emailBody = "You were recently assigned as an i90 Project Manager to an Order in the i90 platform by " + loggedInUser + ":<br>"
                            + mc + ": " + ec + "<br>"
                            + "Location Count: " + locCnt + "<br>"
                            + "Service Count: " + svcCnt + "<br>"
                            + "Services included in the Order: " + "<br>"
                            + svcTypes + "<br>"
                            + "Follow the link below to review the order.<br>"
                            + orderLink;

                    List<String> emails = new ArrayList<>();
                    emails.add(pm.getEmailAddress());
                    emailUtility.sendEmail(emailHeader, emailBody, emails);
                }
            }
        }
    }

    public void deleteOrder(final Long orderId) {
        //order_contact
        //contact
        List<OrderContact> contacts = orderContactManager.findByOrderId(orderId);
        for (OrderContact contact : contacts) {
            orderContactManager.remove(contact);
        }

        //order_jeop_instance
        //Jeop_instance
        List<OrderJeop> jeops = orderJeopManager.findByOrderId(orderId);
        for (OrderJeop jeop : jeops) {
            orderJeopManager.remove(jeop);
        }
        //order_milestone_instance
        //milestone_instance
        List<OrderMilestoneInstance> milestones = orderMilestoneInstanceManager.findByOrderId(orderId);
        for (OrderMilestoneInstance milestone : milestones) {
            orderMilestoneInstanceManager.remove(milestone);
        }
        //orders
        dao.remove(orderId);
    }
}
