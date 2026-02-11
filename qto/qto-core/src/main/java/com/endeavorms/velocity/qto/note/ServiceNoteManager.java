package com.endeavorms.velocity.qto.note;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.base.Strings;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.endeavorms.velocity.qto.authentication.Permissions;
import com.endeavorms.velocity.qto.common.FlatMapUtil;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.subject.Subject;
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
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Component
public class ServiceNoteManager extends AbstractNoteManager<ServiceNote> {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceNoteManager.class);

    /**
     * Persistence tier for ServiceNote.
     */
    @Inject
    private ServiceNoteJpaDao dao;

    @Inject
    private NoteUnionViewJpaDao noteUnionViewJpaDao;

    @Inject
    private ServiceManager serviceManager;

    @Override
    protected ServiceNoteJpaDao getDao() {
        return dao;
    }

    @Override
    public boolean determineEditability(final ServiceNote note) {
        boolean editable = false;
        if (SecurityUtils.hasAuthority(Permissions.ADMIN)
                || SecurityUtils.hasAuthority(Permissions.ORDER_WRITE_TERMINAL)
                || SecurityUtils.hasAuthority(Permissions.INVENTORY_WRITE)) {
            if (SecurityUtils.hasAuthority(Permissions.ADMIN)) {
                editable = true;
            } else if (SecurityUtils.hasAuthority(Permissions.ORDER_WRITE_TERMINAL)
                    && (note.getServiceId() != null && (!serviceManager.retrieve(note.getServiceId()).isCurrentInventory()))) {
                editable = true;
            } else if (SecurityUtils.hasAuthority(Permissions.INVENTORY_WRITE)
                    && (note.getServiceId() != null && (serviceManager.retrieve(note.getServiceId()).isCurrentInventory()))) {
                editable = true;
            }
        }
        if (!editable) {
            //can use the logged-in username to find the subject
            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            //need to use the subject id because the subject's name could be changed
            if (note.getCreatedById() != null && note.getCreatedById().equals(subject.getId())) {
                editable = true;
            }
        }
        return editable;
    }

    public ServiceNote updateClientFlag(final ServiceNote entity) {
        return super.edit(entity);
    }

    /**
     * Creates a new note for a service.
     * @param serviceId the service id.
     * @param noteBody the note body.
     * @param category category of the note.
     * @return the created note.
     */
    public ServiceNote create(final Long serviceId, final String noteBody, final String category) {
        return create(serviceId, noteBody, category, null);
    }

    /**
     * Creates a new note for a service.
     * @param serviceId the service id.
     * @param noteBody the note body.
     * @param category category of the note.
     * @param user if null, the current user is used.
     * @return the created note.
     */
    public ServiceNote create(final Long serviceId, final String noteBody, final String category, final String user) {
        ServiceNote note = new ServiceNote();
        note.setServiceId(serviceId);
        note.setNote(noteBody);
        if (Strings.isNullOrEmpty(user)) {
            String loggedInUser = SecurityUtils.getLoggedInUser();
            if (!SecurityUtils.SCHEDULER.equals(loggedInUser)) {
                Subject subject = subjectManager.findByUsername(loggedInUser);
                loggedInUser = subject.getDisplayName();
                note.setCreatedById(subject.getId());
            }
            note.setCreatedBy(loggedInUser);
        } else {
            note.setCreatedBy(user);
            Subject subject = subjectManager.findByDisplayName(user);
            note.setCreatedById(subject.getId());
        }
        note.setCreatedDate(new Date());
        Service service = serviceManager.retrieve(serviceId);
        note.setTenantId(service.getTenantId());
        note.setMasterCustomerId(service.getMasterCustomerId());
        note.setCategory(category);
        return super.create(note);
    }

    public List<NoteUnionView> getActivationNotes(final Long serviceId) {
        return noteUnionViewJpaDao.getActivationNotes(serviceId);
    }

    /**
     * Find all notes for a service.
     *
     * @param serviceId the service id
     * @return the list of notes
     */
    public List<ServiceNote> findByServiceId(Long serviceId) {
        return dao.findByServiceId(serviceId);
    }

    /**
     * Creates a string of the differences between the service and the existing service.
     *
     * @param service  the service being updated.
     * @param existing the existing service.
     * @return a string of the differences between the service and the existing service.
     */
    public String getAuditString(final Service service, final Service existing) {
        //keys to ignore when creating diff String
        List<String> keysToIgnore = Arrays.asList("/lastUpdateDate", "/lastUpdateBy");

        LOGGER.debug("Auditing service {}", service.getId());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> type = new TypeReference<>() {
        };

        JsonObject mappingJson;
        try {
            String fieldMapping = IOUtils.resourceToString("/fieldmapping/service-field-mapping.json", StandardCharsets.UTF_8);
            JsonReader jsonReader = Json.createReader(new StringReader(fieldMapping));
            mappingJson = jsonReader.readObject();
            jsonReader.close();
        } catch (Exception e) {
            LOGGER.error("Error reading SERVICE_PROPERTY_NAME_MAPPING config property", e);
            mappingJson = JsonValue.EMPTY_JSON_OBJECT;
        }

        try {
            StringBuilder result = new StringBuilder();
            Map<String, Object> map = FlatMapUtil.flatten(mapper.readValue(ow.writeValueAsString(service), type));
            Map<String, Object> existingMap = FlatMapUtil.flatten(mapper.readValue(ow.writeValueAsString(existing), type));
            MapDifference<String, Object> difference = Maps.difference(map, existingMap);

            for (Map.Entry<String, MapDifference.ValueDifference<Object>> entry
                    : difference.entriesDiffering().entrySet()) {
                if (keysToIgnore.contains(entry.getKey())) {
                    continue;
                }
                //ignore differences in integer and double values that are equal
                //this is common with the mrc and nrc fields
                Double left = null, right = null;
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
                        LOGGER.warn("Mapping config SERVICE_PROPERTY_NAME_MAPPING is missing field key {}", k);
                        cont = true;
                    } else {
                        fieldJson = mapping.asJsonObject();
                        if ("object".equals(fieldJson.getString("type"))) {
                            fieldJson = fieldJson.get("value").asJsonObject();
                        }
                    }
                }
                if (cont) {
                    continue;
                }

                String newValue = entry.getValue().leftValue() == null ? "<Blank>" : entry.getValue().leftValue().toString();
                String oldValue = entry.getValue().rightValue() == null ? "<Blank>" : entry.getValue().rightValue().toString();

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
                result.insert(0, "Service updated by admin:\n");
            }

            LOGGER.debug("Done audit of service {}", service.getId());
            return result.toString();
        } catch (Exception e) {
            String message = "Error auditing service";
            LOGGER.error(message, e);
            return message;
        }
    }

    @Override
    protected StandardManager getParentManager() {
        return serviceManager;
    }

    @Override
    protected Long getOwnerEntityId(final ServiceNote note) {
        return note.getServiceId();
    }
}
