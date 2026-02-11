package com.endeavorms.velocity.qto.location;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.base.Strings;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.endeavorms.velocity.qto.RecordSource;
import com.endeavorms.velocity.qto.activation.attempt.ActivationAttempt;
import com.endeavorms.velocity.qto.activation.attempt.ActivationAttemptManager;
import com.endeavorms.velocity.qto.activation.requirement.ActivationAttemptRequirement;
import com.endeavorms.velocity.qto.activation.requirement.Requirement;
import com.endeavorms.velocity.qto.activation.requirement.RequirementTemplate;
import com.endeavorms.velocity.qto.activation.requirement.RequirementTemplateManager;
import com.endeavorms.velocity.qto.attachment.FileAttachment;
import com.endeavorms.velocity.qto.attachment.FileAttachmentContent;
import com.endeavorms.velocity.qto.attachment.LocationFileAttachment;
import com.endeavorms.velocity.qto.attachment.LocationFileAttachmentManager;
import com.endeavorms.velocity.qto.authentication.Permissions;
import com.endeavorms.velocity.qto.common.FlatMapUtil;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.company.jms.CompanyMessage;
import com.endeavorms.velocity.qto.company.jms.CompanyMessageHandler;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import com.endeavorms.velocity.qto.config.ConfigPropertyManager;
import com.endeavorms.velocity.qto.contact.Contact;
import com.endeavorms.velocity.qto.contact.ContactType;
import com.endeavorms.velocity.qto.contact.location.LocationContact;
import com.endeavorms.velocity.qto.contact.location.LocationContactManager;
import com.endeavorms.velocity.qto.contact.order.OrderContact;
import com.endeavorms.velocity.qto.contact.order.OrderContactManager;
import com.endeavorms.velocity.qto.customfield.value.LocationCustomFieldValue;
import com.endeavorms.velocity.qto.customfield.value.LocationCustomFieldValueManager;
import com.endeavorms.velocity.qto.jeop.LocationJeop;
import com.endeavorms.velocity.qto.jeop.LocationJeopManager;
import com.endeavorms.velocity.qto.location.jms.LocationMessageHandler;
import com.endeavorms.velocity.qto.message.MessageThread;
import com.endeavorms.velocity.qto.message.MessageThreadManager;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstanceManager;
import com.endeavorms.velocity.qto.note.LocationNote;
import com.endeavorms.velocity.qto.note.LocationNoteManager;
import com.endeavorms.velocity.qto.note.Note;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.service.AbstractServiceManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.ServiceManagerFactory;
import com.endeavorms.velocity.qto.service.ServiceType;
import com.endeavorms.velocity.qto.service.broadband.BroadbandServiceManager;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.endeavorms.velocity.qto.common.SecurityUtils.SCHEDULER;
import static com.endeavorms.velocity.qto.common.SecurityUtils.getLoggedInUser;

/**
 * @author rcasey
 * @since 1/10/2023
 */
@Component
public class LocationManager extends StandardManager<Location> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationManager.class);

    /**
     * Persistence tier for Location.
     */
    @Inject
    private LocationJpaDao dao;

    @Inject
    private OrderManager orderManager;

    @Inject
    private LocationMilestoneInstanceManager locationMilestoneInstanceManager;

    @Inject
    private RequirementTemplateManager requirementTemplateManager;

    @Inject
    private LocationContactManager locationContactManager;

    @Inject
    private ConfigPropertyManager configPropertyManager;

    @Inject
    private ActivationAttemptManager activationAttemptManager;

    @Inject
    private LocationNoteManager locationNoteManager;

    @Inject
    private LocationMessageHandler locationMessageHandler;

    @Inject
    private MessageThreadManager messageThreadManager;

    @Inject
    private CompanyMessageHandler companyMessageHandler;

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> companyConfigPropertyManager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private LocationFileAttachmentManager locationFileAttachmentManager;

    @Inject
    private LocationJeopManager locationJeopManager;

    @Inject
    private BroadbandServiceManager bbManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private OrderContactManager orderContactManager;

    @Inject
    private LocationCustomFieldValueManager customFieldValueManager;

    @Override
    protected LocationJpaDao getDao() {
        return dao;
    }

    /**
     * The factory for ServiceManagers.
     */
    @Inject
    private ServiceManagerFactory serviceManagerFactory;

    @Override
    public Location create(final Location entity) {
        Location created;
        Order order = orderManager.retrieve(entity.getOrderId());
        Company tenantCompany = companyManager.findTenantByTenantId(order.getTenantId());
        if (entity.isCurrentInventory() && RecordSource.MANUAL_ENTRY.getName().equalsIgnoreCase(entity.getRecordSource())) {
            Order newOrder = new Order();
            newOrder.setCompany(order.getCompany());
            newOrder.setClientOrderId(order.getClientOrderId());
            newOrder.setQuoteId(order.getQuoteId());
            newOrder.setProvisioner(order.getProvisioner());
            newOrder.setClientProjectManager(order.getClientProjectManager());
            newOrder.setActivationEngineer(order.getActivationEngineer());
            newOrder.setStatus(order.getStatus());
            newOrder.setQuoteNumber(order.getQuoteNumber());
            newOrder.setLastUpdateBy(getLoggedInUser());
            newOrder.setLastUpdateDate(new Date());
            newOrder.setVertekProjectManager(order.getVertekProjectManager());
            newOrder.setQaManager(order.getQaManager());
            newOrder.setCurrentInventory(true);
            newOrder.setContacts(order.getContacts());
            newOrder = orderManager.create(newOrder);


            List<OrderContact> ocons = orderContactManager.findByOrderId(order.getId());
            for (OrderContact ocon : ocons) {
                OrderContact newOcon = new OrderContact();
                newOcon.setEmail(ocon.getEmail());
                newOcon.setFirstName(ocon.getFirstName());
                newOcon.setCompanyId(ocon.getCompanyId());
                newOcon.setType(ocon.getType());
                newOcon.setRole(ocon.getRole());
                newOcon.setPhone(ocon.getPhone());
                newOcon.setMasterCustomerId(newOrder.getMasterCustomerId());
                newOcon.setTenantId(newOrder.getTenantId());
                newOcon.setOrderId(newOrder.getId());
                orderContactManager.create(newOcon);
            }


            entity.setOrderId(newOrder.getId());
            entity.setTenantId(newOrder.getTenantId());
            entity.setActive(true);
            entity.setMasterCustomerId(newOrder.getMasterCustomerId());
            created = super.create(entity);
            persistTechContact(created, order);
            persistLcon(entity, created.getId(), order);

            //create location milestones
            locationMilestoneInstanceManager.create(created.getId(), "CREATED", new Date());
            locationMilestoneInstanceManager.create(created.getId(), "RECEIVED", new Date());
            locationMilestoneInstanceManager.create(created.getId(), "COMPLETE", new Date());

        } else {
            Boolean lockOneLocationPerOrder = companyConfigPropertyManager.getBoolean(tenantCompany.getId(), CompanyConfigKey.LOCK_ONE_LOCATION_PER_ORDER);
            if (Boolean.TRUE.equals(lockOneLocationPerOrder)) {
                List<Location> locations = findbyOrderId(entity.getOrderId());
                if (!locations.isEmpty()) {
                    throw new IllegalArgumentException("Tenant configuration only allows one location per order");
                }
            }
            String LoeDefault = companyConfigPropertyManager.getString(tenantCompany.getId(), CompanyConfigKey.LOE_DEFAULT);
            if (entity.getLevelOfEffort() == null && LoeDefault != null) {
                entity.setLevelOfEffort(LoeDefault);
            }
            List<Location> locations = findByClientLocIdAndTenant(entity.getClientLocationId(), order.getTenantId());

            List<Location> inventoryLocations = locations.stream()
                    .filter(Location::isCurrentInventory).collect(Collectors.toList());

            if (Strings.isNullOrEmpty(entity.getRecordSource())) {
                entity.setRecordSource(RecordSource.UNKNOWN.getName());
            }

            entity.setTenantId(order.getTenantId());
            entity.setMasterCustomerId(order.getMasterCustomerId());

            //set location requirement template to end customer default, if not set already
            RequirementTemplate template = requirementTemplateManager.getDefaultTemplate(order.getCompany().getId());
            if (template != null && entity.getRequirementTemplateId() == null) {
                entity.setRequirementTemplateId(template.getId());
            }

            //persist location
            created = super.create(entity);

            if (!inventoryLocations.isEmpty()) {
                Location inventoryLocation = inventoryLocations.get(0);
                if (inventoryLocation.getProvisioningLocationId() == null || inventoryLocation.getParentLocationId() == null) {
                    inventoryLocation.setProvisioningLocationId(created.getId());
                    super.edit(inventoryLocation);
                }
            }

            persistTechContact(created, order);
            persistLcon(entity, created.getId(), order);

            //create location milestones
            locationMilestoneInstanceManager.create(created.getId(), "CREATED", new Date());
            locationMilestoneInstanceManager.create(created.getId(), "RECEIVED", new Date());
            if (order.getProvisioner() != null) {
                locationMilestoneInstanceManager.create(created.getId(), "ENGINEER_ASSIGNED", new Date());
            }
        }
        companyMessageHandler.sendMessageToQueue(new CompanyMessage(order.getCompany().getId(), "updateCounts"));
        return created;
    }

    @Override
    public Location edit(final Location entity) {
        Location existing = retrieve(entity.getId());

        //verify that the location can be edited based on its status
        if (TerminalLocationStatuses.getStatuses().contains(existing.getStatus())) {
            if (!SCHEDULER.equals(SecurityUtils.getLoggedInUser()) && SecurityUtils.hasAuthority(Permissions.ORDER_WRITE_TERMINAL)) {
                LOGGER.debug("location {} is in terminal status: {}, location is being updated by admin user {}", entity.getId(), entity.getStatus(), SecurityUtils.getLoggedInUser());
                String auditString = getLocationAuditString(entity, existing);
                if (!auditString.isEmpty()) {
                    locationNoteManager.create(entity.getId(), auditString, "Audit");
                }
            } else {
                LOGGER.debug("location {} is in terminal status: {}, location will not be updated. user = {}",
                        entity.getId(), entity.getStatus(), SecurityUtils.getLoggedInUser());
                try {
                    BeanUtils.copyProperties(entity, existing);
                } catch (Exception e) {
                    LOGGER.error("error rolling back location {} during edit by non-admin user {}", entity.getId(), SecurityUtils.getLoggedInUser(), e);
                }
            }
        }

        Order order = orderManager.retrieve(entity.getOrderId());
        entity.setTenantId(order.getTenantId());
        entity.setMasterCustomerId(order.getMasterCustomerId());

        persistTechContact(entity, order);
        persistLcon(entity, entity.getId(), order);

        //if location requirement template has been changed, replace the requirement list on any open activation attempts
        if (entity.getRequirementTemplateId() != null && !entity.getRequirementTemplateId().equals(existing.getRequirementTemplateId())) {
            RequirementTemplate template = requirementTemplateManager.retrieve(entity.getRequirementTemplateId());
            for (Service service : entity.getServices()) {
                List<ActivationAttempt> attempts = activationAttemptManager.findByServiceId(service.getId());
                for (ActivationAttempt attempt : attempts) {
                    if ("Schedule Date Confirmed".equals(attempt.getScheduledAttemptStatus())) {
                        attempt.getRequirements().clear();
                        for (Requirement req : template.getRequirements()) {
                            ActivationAttemptRequirement aaRequirement = new ActivationAttemptRequirement();
                            aaRequirement.setRequirement(req);
                            aaRequirement.setActivationAttemptId(attempt.getId());
                            attempt.getRequirements().add(aaRequirement);
                        }
                        activationAttemptManager.edit(attempt);
                    }
                }
            }
        }


        //if parent location id has been set for the first time, copy forward non-empty fields from the parent
        if (existing.getParentLocationId() == null && entity.getParentLocationId() != null) {
            Location parentLocation = retrieve(entity.getParentLocationId());
            //client location id
            if (Strings.isNullOrEmpty(entity.getClientLocationId()) && !Strings.isNullOrEmpty(parentLocation.getClientLocationId())) {
                entity.setClientLocationId(parentLocation.getClientLocationId());
            }
            //quote location id
            if (Strings.isNullOrEmpty(entity.getQuoteLocationId()) && !Strings.isNullOrEmpty(parentLocation.getQuoteLocationId())) {
                entity.setQuoteLocationId(parentLocation.getQuoteLocationId());
            }
            //name
            if (Strings.isNullOrEmpty(entity.getName()) && !Strings.isNullOrEmpty(parentLocation.getName())) {
                entity.setName(parentLocation.getName());
            }
            //phone number
            if (Strings.isNullOrEmpty(entity.getPhoneNumber()) && !Strings.isNullOrEmpty(parentLocation.getPhoneNumber())) {
                entity.setPhoneNumber(parentLocation.getPhoneNumber());
            }
            //address
            if (Strings.isNullOrEmpty(entity.getAddress1()) && !Strings.isNullOrEmpty(parentLocation.getAddress1())) {
                entity.setAddress1(parentLocation.getAddress1());
            }
            if (Strings.isNullOrEmpty(entity.getAddress2()) && !Strings.isNullOrEmpty(parentLocation.getAddress2())) {
                entity.setAddress2(parentLocation.getAddress2());
            }
            if (Strings.isNullOrEmpty(entity.getCity()) && !Strings.isNullOrEmpty(parentLocation.getCity())) {
                entity.setCity(parentLocation.getCity());
            }
            if (Strings.isNullOrEmpty(entity.getState()) && !Strings.isNullOrEmpty(parentLocation.getState())) {
                entity.setState(parentLocation.getState());
            }
            if (Strings.isNullOrEmpty(entity.getPostalCode()) && !Strings.isNullOrEmpty(parentLocation.getPostalCode())) {
                entity.setPostalCode(parentLocation.getPostalCode());
            }
            if (Strings.isNullOrEmpty(entity.getCountry()) && !Strings.isNullOrEmpty(parentLocation.getCountry())) {
                entity.setCountry(parentLocation.getCountry());
            }
            //time zone
            if (Strings.isNullOrEmpty(entity.getTimezone()) && !Strings.isNullOrEmpty(parentLocation.getTimezone())) {
                entity.setTimezone(parentLocation.getTimezone());
            }
            //level of effort
            if (entity.getLevelOfEffort() == null && parentLocation.getLevelOfEffort() != null) {
                entity.setLevelOfEffort(parentLocation.getLevelOfEffort());
            }
            //client location info
            if (Strings.isNullOrEmpty(entity.getClientLocationInfo()) && !Strings.isNullOrEmpty(parentLocation.getClientLocationInfo())) {
                entity.setClientLocationInfo(parentLocation.getClientLocationInfo());
            }
            //client location type
            if (Strings.isNullOrEmpty(entity.getClientLocationType()) && !Strings.isNullOrEmpty(parentLocation.getClientLocationType())) {
                entity.setClientLocationType(parentLocation.getClientLocationType());
            }
            //requirement template
            if (entity.getRequirementTemplateId() == null && parentLocation.getRequirementTemplateId() != null) {
                entity.setRequirementTemplateId(parentLocation.getRequirementTemplateId());
            }

            //contacts
            List<LocationContact> contacts = locationContactManager.retrieveByLocationId(parentLocation.getId());
            for (LocationContact contact : contacts) {
                if (!ContactType.LCON.equals(contact.getType())) {
                    LocationContact newContact = new LocationContact();
                    newContact.setTenantId(order.getTenantId());
                    newContact.setMasterCustomerId(order.getMasterCustomerId());
                    newContact.setType(contact.getType());
                    newContact.setRole(contact.getRole());
                    newContact.setFirstName(contact.getFirstName());
                    newContact.setLastName(contact.getLastName());
                    newContact.setEmail(contact.getEmail());
                    newContact.setPhone(contact.getPhone());
                    newContact.setCompanyId(contact.getCompanyId());
                    newContact.setLocationId(entity.getId());
                    locationContactManager.create(newContact);
                }
            }

        }

        Location updated = super.edit(entity);

        //if the location is being updated and not in a terminal status, update the inventory location

        updated = updateInventory(updated);

        companyMessageHandler.sendMessageToQueue(new CompanyMessage(order.getCompany().getId(), "updateCounts"));
        return super.edit(updated);
    }

    /**
     * Persists the tech contact for the given location.
     *
     * @param entity the location to persist the tech contact for.
     * @param order  the order the location belongs to.
     */
    private void persistTechContact(final Location entity, final Order order) {
        List<LocationContact> locContacts = locationContactManager.retrieveByLocationId(entity.getId());
        LocationContact locTechContact = locContacts.stream().filter(c -> c.getType() == ContactType.TECH).findFirst().orElse(null);
        if (locTechContact == null) {
            Contact orderTech = orderContactManager.findByOrderId(order.getId()).stream().filter(c -> c.getType() == ContactType.TECH).findFirst().orElse(null);
            if (orderTech != null
            && (orderTech.getFirstName() != null || orderTech.getLastName() != null
                    || orderTech.getEmail() != null || orderTech.getPhone() != null)) {
                LocationContact techContact = new LocationContact();
                techContact.setRole("Technical");
                techContact.setType(ContactType.TECH);
                techContact.setTenantId(entity.getTenantId());
                techContact.setMasterCustomerId(entity.getMasterCustomerId());
                techContact.setLocationId(entity.getId());
                techContact.setFirstName(orderTech.getFirstName());
                techContact.setLastName(orderTech.getLastName());
                techContact.setEmail(orderTech.getEmail());
                techContact.setPhone(orderTech.getPhone());
                techContact.setCompanyId(order.getCompany().getId());
                if (techContact.getId() == null) {
                    locationContactManager.create(techContact);
                } else {
                    locationContactManager.edit(techContact);
                }
            }
        }
    }

    /**
     * Persists the LCON for the given location.
     *
     * @param entity     the location to persist the LCON for.
     * @param locationId the location id.
     * @param order      the order the location belongs to.
     */
    private void persistLcon(final Location entity, final Long locationId, final Order order) {
        LocationContact lcon = entity.getLcon();
        if (lcon != null
                && (lcon.getFirstName() != null || lcon.getLastName() != null
                || lcon.getEmail() != null || lcon.getPhone() != null)) {
            lcon.setTenantId(order.getTenantId());
            lcon.setMasterCustomerId(order.getMasterCustomerId());
            lcon.setCompanyId(order.getCompany().getId());
            if (lcon.getId() == null) {
                lcon.setRole("Customer LCON");
                lcon.setType(ContactType.LCON);
                lcon.setActive(true);
                lcon.setLocationId(locationId);
                entity.setLcon(locationContactManager.create(lcon));
            } else {
                entity.setLcon(locationContactManager.edit(lcon));
            }
        }
    }

    public PaginatedResult<Location> findBySearchCriteria(final LocationSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    /**
     * Get list of locations by Order ID.
     *
     * @param orderId orderId.
     * @return list of locations;
     */
    public List<Location> findbyOrderId(final Long orderId) {
        return getDao().findByOrderId(orderId);
    }

    /**
     * Gets services by the given location id.
     *
     * @param locationId the location id to filter by.
     * @return list of services;
     */
    public List<Service> findServicesByLocationId(final Long locationId) {
        return dao.findServicesByLocationId(locationId);
    }

    /**
     * Returns a Location.
     *
     * @param locationId Location ID.
     * @param tenantId   Tenant ID.
     * @return Activation Scheule Object.
     */
    public Location findByIdAndTenant(final Long locationId, final Long tenantId) {
        return dao.findByIdAndTenant(locationId, tenantId);
    }


    /**
     * Returns a Location.
     *
     * @param clientLocationId Location ID.
     * @param tenantId         Tenant ID.
     * @return Activation Scheule Object.
     */
    public List<Location> findByClientLocIdAndTenant(final String clientLocationId, final Long tenantId) {
        return dao.findByClientLocIdAndTenant(clientLocationId, tenantId);
    }

        /**
     * Returns a Location.
     *
     * @param clientLocationId Location ID.
     * @param tenantId         Tenant ID.
     * @return Activation Scheule Object.
     */
    public List<Location> findOpenByClientLocIdAndTenant(final String clientLocationId, final Long tenantId) {
        return dao.findOpenByClientLocIdAndTenant(clientLocationId, tenantId);
    }

    /**
     * Returns an Inventory Location.
     *
     * @param clientLocationId Location ID.
     * @param tenantId         Tenant ID.
     * @return Activation Scheule Object.
     */
    public Location findInvByClientLocIdAndTenant(final String clientLocationId, final Long tenantId) {
        return dao.findInvByClientLocIdAndTenant(clientLocationId, tenantId);
    }


    /**
     * Returns a Location.
     *
     * @param legacyId Location ID.
     * @param tenantId Tenant ID.
     * @return Activation Scheule Object.
     */
    public Location findByLegacyIdAndTenant(final String legacyId, final Long tenantId) {
        return dao.findByLegacyIdAndTenant(legacyId, tenantId);
    }

    /**
     * Get list of locations by Order ID.
     *
     * @param parentId parentId.
     * @return list of locations;
     */
    public List<Location> findByParentId(Long parentId) {
        return getDao().findByParentId(parentId);
    }

    /**
     * Gets all Locations in the tree for the given location id.
     *
     * @param locationId locationId
     * @return root location id
     */
    public List<Location> getLocationTreeList(final Long locationId) {
        List<Location> locations = new ArrayList<>();
        List<Long> leaves = new ArrayList<>();

        //get the root location in the tree, adds it to the list
        Long rootId = dao.getLocationTreeRoot(locationId);
        Location rootLocation = retrieve(rootId);
        locations.add(rootLocation);
        leaves.add(rootId);

        //while there are leaves to evaluate, identify their children and add them to the list
        while (!leaves.isEmpty()) {
            List<Location> children = dao.findByParentId(leaves.get(0));
            for (Location child : children) {
                locations.add(child);
                leaves.add(child.getId());
            }
            //remove processed leaf from the list
            leaves.remove(0);
        }
        return locations;
    }

    /**
     * Generates the string to be used as the audit note body for a location.
     *
     * @param location the incoming location to be audited
     * @param existing the existing location to be audited against
     * @return the audit note body
     */
    private String getLocationAuditString(final Location location, final Location existing) {
        //location level keys to ignore when creating diff String
        List<String> keysToIgnore = Arrays.asList("/lastUpdateDate", "/lastUpdateBy");

        LOGGER.debug("Auditing location {}", location.getId());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> type = new TypeReference<>() {
        };

        JsonObject mappingJson;
        try {
            String fieldMapping = IOUtils.resourceToString("/fieldmapping/location-field-mapping.json", StandardCharsets.UTF_8);
            JsonReader jsonReader = Json.createReader(new StringReader(fieldMapping));
            mappingJson = jsonReader.readObject();
            jsonReader.close();
        } catch (Exception e) {
            LOGGER.error("Error reading LOCATION_PROPERTY_NAME_MAPPING config property", e);
            mappingJson = JsonValue.EMPTY_JSON_OBJECT;
        }

        try {
            StringBuilder result = new StringBuilder();
            Map<String, Object> locationMap = FlatMapUtil.flatten(mapper.readValue(ow.writeValueAsString(location), type));
            Map<String, Object> existingMap = FlatMapUtil.flatten(mapper.readValue(ow.writeValueAsString(existing), type));
            MapDifference<String, Object> difference = Maps.difference(existingMap, locationMap);

            for (Map.Entry<String, MapDifference.ValueDifference<Object>> entry : difference.entriesDiffering().entrySet()) {
                if (keysToIgnore.contains(entry.getKey())) {
                    continue;
                }
                //ignore differences in integer and double values that are equal
                //this is common with the mrc and nrc fields
                Double left = null;
                Double right = null;
                if (entry.getValue().leftValue() instanceof Integer) {
                    left = ((Integer) entry.getValue().leftValue()).doubleValue();
                } else if (entry.getValue().leftValue() instanceof Double) {
                    left = (Double) entry.getValue().leftValue();
                }
                if (entry.getValue().rightValue() instanceof Integer) {
                    right = ((Integer) entry.getValue().rightValue()).doubleValue();
                } else if (entry.getValue().rightValue() instanceof Double) {
                    right = (Double) entry.getValue().rightValue();
                }
                if (left != null && left.equals(right)) {
                    continue;
                }

                String key = entry.getKey().charAt(0) == '/' ? entry.getKey().substring(1) : entry.getKey();
                String[] keys = key.split("/");
                JsonObject fieldJson = mappingJson;
                boolean cont = false;
                for (String k : keys) {
                    JsonValue mapping = fieldJson.get(k);
                    if (mapping == null) {
                        LOGGER.warn("Mapping config LOCATION_PROPERTY_NAME_MAPPING is missing field key {}", k);
                        cont = true;
                    } else {
                        fieldJson = fieldJson.get(k).asJsonObject();
                        if ("object".equals(fieldJson.getString("type"))) {
                            fieldJson = fieldJson.get("value").asJsonObject();
                        }
                    }
                }
                if (cont) {
                    continue;
                }

                String oldValue = entry.getValue().leftValue() == null ? "<Blank>" : entry.getValue().leftValue().toString();
                String newValue = entry.getValue().rightValue() == null ? "<Blank>" : entry.getValue().rightValue().toString();

                if ("date".equals(fieldJson.getString("type"))) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    if (!"<Blank>".equals(oldValue)) {
                        Date oldDate = new Date(Long.parseLong(oldValue));
                        oldValue = sdf.format(oldDate);
                    }
                    if (!"<Blank>".equals(newValue)) {
                        Date newDate = new Date(Long.parseLong(newValue));
                        newValue = sdf.format(newDate);
                    }
                } else if ("currency".equals(fieldJson.getString("type"))) {
                    oldValue = oldValue.equals("<Blank>") ? oldValue : "$" + oldValue;
                    newValue = newValue.equals("<Blank>") ? newValue : "$" + newValue;
                }

                String line = "Changed the " + fieldJson.getString("value") + " from " + oldValue + " (old value) to " + newValue + " (new value)";
                result.append(line).append("\n");
            }

            if (result.length() > 0) {
                result.insert(0, "Location updated by admin:\n");
            }

            LOGGER.debug("Done audit of location: {}", location.getId());
            return result.toString();
        } catch (Exception e) {
            String message = "Error auditing location";
            LOGGER.error(message, e);
            return message;
        }
    }


    public Location createInventoryLocation(Location existingLocation, Long orderId) {
        Location inventoryLocation = new Location();
        try {
            for (Field field : Location.class.getDeclaredFields()) {
                if (!field.getName().equalsIgnoreCase("isCurrentInventory")
                        && !field.getName().equalsIgnoreCase("provisioningLocationId")
                        && !field.getName().equalsIgnoreCase("inventoryLocationId")
                        && !field.getName().equalsIgnoreCase("id")
                        && !field.getName().equalsIgnoreCase("orderid")
                        && !field.getName().equalsIgnoreCase("address")
                        && !field.getName().equalsIgnoreCase("services")
                        && !field.getName().equalsIgnoreCase("inventoryServices")
                        && !field.getName().equalsIgnoreCase("lcon")
                        && !field.getName().equalsIgnoreCase("active")
                        && !field.getName().equalsIgnoreCase("parentLocationId")
                        && !field.getName().equalsIgnoreCase("eligibleForInventory")
                        && !field.getName().equalsIgnoreCase("finalUpdate")
                        && !field.getName().equalsIgnoreCase("isDisconnect")
                        && !field.getName().equalsIgnoreCase("version")) {
                    field.setAccessible(true);
                    field.set(inventoryLocation, field.get(existingLocation));
                }
            }
            inventoryLocation.setTenantId(existingLocation.getTenantId());
            inventoryLocation.setMasterCustomerId(existingLocation.getMasterCustomerId());
            inventoryLocation.setCurrentInventory(true);
            inventoryLocation.setProvisioningLocationId(existingLocation.getId());
            inventoryLocation.setParentLocationId(existingLocation.getId());
            inventoryLocation.setOrderId(orderId);
            inventoryLocation.setActive(true);

            inventoryLocation = super.create(inventoryLocation);
            locationMilestoneInstanceManager.createNoEventHandler(inventoryLocation.getId(), "CREATED", new Date());

            //insert contacts/milestones/notes/file attachments
            copySupportingData(existingLocation, inventoryLocation);
            existingLocation.setInventoryLocationId(inventoryLocation.getId());
            super.edit(existingLocation);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return inventoryLocation;
    }

    private void copySupportingData(Location existingLocation, Location inventoryLocation) {

        try {
            //duplicate notes
            List<LocationNote> notes = locationNoteManager.findByLocationId(existingLocation.getId());
            for (LocationNote note : notes) {
                LocationNote newNote = new LocationNote();
                for (Field field : Note.class.getDeclaredFields()) {
                    if (!field.getName().equalsIgnoreCase("id")
                            && !field.getName().equalsIgnoreCase("locationId")
                            && !field.getName().equalsIgnoreCase("parentNoteId")
                            && !field.getName().equalsIgnoreCase("update_client")
                            && !field.getName().equalsIgnoreCase("masterCustomerId")
                            && !field.getName().equalsIgnoreCase("version")) {
                        field.setAccessible(true);
                        field.set(newNote, field.get(note));
                    }
                }
                newNote.setTenantId(inventoryLocation.getTenantId());
                newNote.setLocationId(inventoryLocation.getId());
                newNote.setMasterCustomerId(inventoryLocation.getMasterCustomerId());
                newNote.setUpdateClient(false);
                newNote.setParentNoteId(note.getId());
                locationNoteManager.create(newNote);
            }

            //for file attachments check to synchronize the data with the location
            //duplicate the attachment if it doesn't exist
            List<LocationFileAttachment> fileAttachments =
                    locationFileAttachmentManager.findByLocationId(existingLocation.getId()).getCollection();
            for (LocationFileAttachment fileAttachment : fileAttachments) {
                LocationFileAttachment inventoryAttachment = locationFileAttachmentManager.findByParentId(fileAttachment.getId());
                if (inventoryAttachment == null) {
                    inventoryAttachment = new LocationFileAttachment();
                }
                for (Field field : FileAttachment.class.getDeclaredFields()) {
                    if (!field.getName().equalsIgnoreCase("id")
                            && !field.getName().equalsIgnoreCase("locationId")
                            && !field.getName().equalsIgnoreCase("parentFileAttachmentId")
                            && !field.getName().equalsIgnoreCase("content")
                            && !field.getName().equalsIgnoreCase("version")
                            && !field.getName().equalsIgnoreCase("tenantId")
                            && !field.getName().equalsIgnoreCase("masterCustomerId")
                            && !field.getName().equalsIgnoreCase("parentOwnerId")) {
                        field.setAccessible(true);
                        field.set(inventoryAttachment, field.get(fileAttachment));
                    }
                }
                inventoryAttachment.setTenantId(inventoryLocation.getTenantId());
                inventoryAttachment.setLocationId(inventoryLocation.getId());
                inventoryAttachment.setParentFileAttachmentId(fileAttachment.getId());
                inventoryAttachment.setParentOwnerId(fileAttachment.getLocationId());
                inventoryAttachment.setMasterCustomerId(inventoryLocation.getMasterCustomerId());
                FileAttachmentContent content = new FileAttachmentContent(fileAttachment.getContent().getData());
                inventoryAttachment.setContent(content);
                if (inventoryAttachment.getId() == null) {
                    locationFileAttachmentManager.create(inventoryAttachment);
                } else {
                    locationFileAttachmentManager.edit(inventoryAttachment);
                }
            }
            //check the inventory for deleted file attachments from the existing provisioning order and remove from inventory
            //we only check the existing location and not any of the historical locations because at some point we may purge
            //the file attachments from completed locations after a period of time
            List<LocationFileAttachment> inventoryLocationFileAttachments =
                    locationFileAttachmentManager.findByLocationId(inventoryLocation.getId()).getCollection();
            for (LocationFileAttachment inventoryLocationFileAttachment : inventoryLocationFileAttachments) {
                if (inventoryLocationFileAttachment.getParentOwnerId() != null && inventoryLocationFileAttachment.getParentFileAttachmentId() != null) {
                    LocationFileAttachment existingFileAttachment = locationFileAttachmentManager.findByInventoryRecordId(inventoryLocationFileAttachment.getParentOwnerId(), inventoryLocationFileAttachment.getParentFileAttachmentId());
                    if (existingFileAttachment == null) {
                        locationFileAttachmentManager.remove(inventoryLocationFileAttachment.getId());
                    }
                } else {
                    locationFileAttachmentManager.remove(inventoryLocationFileAttachment.getId());
                }
            }

            //duplicate milestones
            List<LocationMilestoneInstance> milestones = locationMilestoneInstanceManager.findByLocationId(existingLocation.getId());
            for (LocationMilestoneInstance milestone : milestones) {
                if (!milestone.getMilestone().getCode().equalsIgnoreCase("CREATED")) {
                    LocationMilestoneInstance newMilestone = new LocationMilestoneInstance();
                    newMilestone = locationMilestoneInstanceManager.createNoEventHandler(inventoryLocation.getId(),
                            milestone.getMilestone().getCode(), milestone.getMilestoneDate());
                    newMilestone.setParentMilestoneInstanceId(milestone.getId());
                    locationMilestoneInstanceManager.editNoEventHandler(newMilestone);
                }
            }

            //duplicate contacts
            List<LocationContact> lcons = locationContactManager.retrieveByLocationId(existingLocation.getId());
            for (LocationContact lcon : lcons) {
                LocationContact newLcon = new LocationContact();
                for (Field field : Contact.class.getDeclaredFields()) {
                    if (!field.getName().equalsIgnoreCase("id")
                            && !field.getName().equalsIgnoreCase("locationId")
                            && !field.getName().equalsIgnoreCase("parentContactId")
                            && !field.getName().equalsIgnoreCase("masterCustomerId")
                            && !field.getName().equalsIgnoreCase("version")) {
                        field.setAccessible(true);
                        field.set(newLcon, field.get(lcon));
                    }
                }
                newLcon.setTenantId(inventoryLocation.getTenantId());
                newLcon.setLocationId(inventoryLocation.getId());
                newLcon.setMasterCustomerId(inventoryLocation.getMasterCustomerId());
                newLcon.setParentContactId(lcon.getId());
                locationContactManager.create(newLcon);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Location updateInventory(Location existingLocation) {
        //only process if eligible for update and not a final update and not inventory
        if (!existingLocation.isEligibleForInventory()
                || existingLocation.getFinalUpdate()
                || existingLocation.isCurrentInventory()) {
            return existingLocation;
        }

        Location updateLocation = existingLocation;
        Location inventoryLocation = null;

        if (updateLocation.getInventoryLocationId() != null) {
            inventoryLocation = dao.retrieve(updateLocation.getInventoryLocationId());
            if (inventoryLocation != null) {
                try {
                    for (Field field : Location.class.getDeclaredFields()) {
                        if (!field.getName().equalsIgnoreCase("isCurrentInventory")
                                && !field.getName().equalsIgnoreCase("provisioningLocationId")
                                && !field.getName().equalsIgnoreCase("inventoryLocationId")
                                && !field.getName().equalsIgnoreCase("id")
                                && !field.getName().equalsIgnoreCase("orderid")
                                && !field.getName().equalsIgnoreCase("address")
                                && !field.getName().equalsIgnoreCase("services")
                                && !field.getName().equalsIgnoreCase("inventoryServices")
                                && !field.getName().equalsIgnoreCase("lcon")
                                && !field.getName().equalsIgnoreCase("active")
                                && !field.getName().equalsIgnoreCase("parentLocationId")
                                && !field.getName().equalsIgnoreCase("eligibleForInventory")
                                && !field.getName().equalsIgnoreCase("finalUpdate")
                                && !field.getName().equalsIgnoreCase("isDisconnect")
                                && !field.getName().equalsIgnoreCase("masterCustomerId")
                                && !field.getName().equalsIgnoreCase("version")) {
                            field.setAccessible(true);
                            field.set(inventoryLocation, field.get(updateLocation));
                        }
                    }
                    inventoryLocation.setActive(true);
                    inventoryLocation = super.edit(inventoryLocation);

                    deleteSupportingData(inventoryLocation);
                    copySupportingData(updateLocation, inventoryLocation);

                    updateLocation.setFinalUpdate(TerminalLocationStatuses.getStatuses().contains(updateLocation.getStatus()));
                    return(super.edit(updateLocation));

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return existingLocation;
    }

    private Location findInvByProvisioningId(Long provisioningLocationId) {
        return dao.findInvByProvisioningId(provisioningLocationId);
    }

    private void deleteSupportingData(Location inventoryLocation) {
        List<LocationNote> notes = locationNoteManager.findByLocationId(inventoryLocation.getId());
        for (LocationNote note : notes) {
            if (note.getParentNoteId() != null) {
                locationNoteManager.remove(note.getId());
            }
        }

        //we don't delete file attachments from inventory locations we syncronize them with the provisioning location
        //because the inventory is going to be the repository of all the files attachments for the location

        List<LocationMilestoneInstance> milestones = locationMilestoneInstanceManager.findByLocationId(inventoryLocation.getId());
        for (LocationMilestoneInstance milestone : milestones) {
            if (!milestone.getMilestone().getCode().equalsIgnoreCase("CREATED")) {
                locationMilestoneInstanceManager.remove(milestone.getId());
            }
        }

        List<LocationContact> lcons = locationContactManager.retrieveByLocationId(inventoryLocation.getId());
        for (LocationContact lcon : lcons) {
            locationContactManager.remove(lcon.getId());
        }
    }

    public Location editSuper(final Location inventoryLocation) {
        return super.edit(inventoryLocation);
    }

    public void updateMcId(Long orderId, Long mcId) {
        List<Location> locations = findbyOrderId(orderId);
        for (Location location : locations) {

            location.setMasterCustomerId(mcId);
            edit(location);

            List<LocationJeop> lJeops = locationJeopManager.getByLocationId(location.getId());
            lJeops.forEach(lJeop -> {
                lJeop.setMasterCustomerId(mcId);
                locationJeopManager.edit(lJeop);
            });

            List<LocationMilestoneInstance> lMilestones = locationMilestoneInstanceManager.findByLocationId(location.getId());
            lMilestones.forEach(lMilestone -> {
                lMilestone.setMasterCustomerId(mcId);
                locationMilestoneInstanceManager.editNoEventHandler(lMilestone);
            });

            List<LocationContact> lContacts = locationContactManager.retrieveByLocationId(location.getId());
            lContacts.forEach(lContact -> {
                lContact.setMasterCustomerId(mcId);
                locationContactManager.edit(lContact);
            });

            List<LocationFileAttachment> lAttachments = locationFileAttachmentManager.findByLocationId(location.getId()).getCollection();
            lAttachments.forEach(lAttachment -> {
                lAttachment.setMasterCustomerId(mcId);
                locationFileAttachmentManager.edit(lAttachment);
            });

            List<MessageThread> lMessages = messageThreadManager.findByLocationId(location.getId());
            lMessages.forEach(lMessage -> {
                lMessage.setMasterCustomerId(mcId);
                messageThreadManager.edit(lMessage);
            });

            List<LocationNote> lNotes = locationNoteManager.findByLocationId(location.getId());
            lNotes.forEach(lNote -> {
                lNote.setMasterCustomerId(mcId);
                locationNoteManager.edit(lNote);
            });
            serviceManager.updateMcId(location.getId(), mcId);
        }
    }

    public void deleteLocationJob() {
        List<Location> locations = dao.findMarkedForDeletion();

        for (Location location : locations) {
            Long locationId = location.getId();
            //order of deletions for Location
            //location_contact
            //contact
            List<LocationContact> contacts = locationContactManager.retrieveByLocationId(locationId);
            contacts.forEach(contact -> {
                locationContactManager.remove(contact.getId());
            });

            //location_custom_field_value
            //custom_field_value
            List<LocationCustomFieldValue> customFieldValues = customFieldValueManager.findByRecordId(locationId);
            customFieldValues.forEach(customFieldValue -> {
                customFieldValueManager.remove(customFieldValue.getId());
            });

            //jeop_instance_note
            //location_jeop_instance
            //jeop_instance
            List<LocationJeop> jeops = locationJeopManager.getByLocationId(locationId);
            jeops.forEach(jeop -> {
                locationJeopManager.remove(jeop.getId());
            });

            //location_note
            //note
            List<LocationNote> notes = locationNoteManager.findByLocationId(locationId);
            notes.forEach(note -> {
                locationNoteManager.remove(note.getId());
            });

            //location_file_attachment
            //file_attachment
            //file_attachment_content
            List<LocationFileAttachment> attachments = locationFileAttachmentManager.findByLocationId(locationId).getCollection();
            attachments.forEach(attachment -> {
                locationFileAttachmentManager.remove(attachment.getId());
            });

            //location_interval_instance
            //interval_instance
            //not implemented

            //location_milestone_instance
            //milestone_instance_history
            //milestone_instance
            List<LocationMilestoneInstance> milestones = locationMilestoneInstanceManager.findByLocationId(locationId);
            milestones.forEach(milestone -> {
                locationMilestoneInstanceManager.remove(milestone.getId());
            });

            //location_shipment_tracking
            //shipment_tracking
            // not implemented
            Long orderId = location.getOrderId();
            dao.remove(locationId);
            List<Location> remainingLocations = findbyOrderId(orderId);
            if (remainingLocations.isEmpty()) {
                orderManager.deleteOrder(orderId);
            }
        }
    }

    public <X extends Service> void markForDeletion(Long locationId) {
        Location location = retrieve(locationId);
        Long inventoryId = location.getInventoryLocationId();


        if (inventoryId != null && !location.isCurrentInventory()) {
            List<Location> provLocations = dao.findAllProvisioningByInventoryId(inventoryId);
            Optional<Location> latestLocation = provLocations.stream()
                    .filter(l -> !l.getStatus().equals(TerminalLocationStatuses.CANCELLED.getStatus())
                    && !l.getStatus().equals(TerminalLocationStatuses.CHANGE_IN_ASSIGNMENT.getStatus())
                    && !l.getId().equals(locationId))
                    .sorted(Comparator.comparing(Location::getId).reversed())
                    .findFirst();
            Location inventoryLocation = retrieve(inventoryId);
            inventoryLocation.setProvisioningLocationId(latestLocation.map(Location::getId).orElse(null));
            edit(inventoryLocation);
        }

        if (location.isCurrentInventory()) {
            List<Location> provLocations = dao.findAllProvisioningByInventoryId(locationId);
            provLocations.forEach(provLocation -> {
                provLocation.setInventoryLocationId(null);
                edit(provLocation);
            });
        }
        List<Service> services = serviceManager.findByLocationId(locationId);
        services.forEach(service -> {
              AbstractServiceManager<X> manager = (AbstractServiceManager<X>) serviceManagerFactory
                        .getManager(ServiceType.fromServiceName(service.getType()));

            service.setMarkedForDeletion(true);
            service.setDeletionDate(new Date());
            service.setStatus("Deleted");
            manager.editSuper(service);
        });
        location.setMarkedForDeletion(true);
        location.setDeletionDate(new Date());
        location.setStatus("Deleted");
        edit(location);
    }
}
