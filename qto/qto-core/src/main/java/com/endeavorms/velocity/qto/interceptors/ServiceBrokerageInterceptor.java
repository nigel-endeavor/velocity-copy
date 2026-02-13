package com.endeavorms.velocity.qto.interceptors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.endeavorms.velocity.qto.authentication.Permissions;
import com.endeavorms.velocity.qto.brokerage.ServiceBrokerage;
import com.endeavorms.velocity.qto.brokerage.ServiceBrokerageManager;
import com.endeavorms.velocity.qto.common.AbstractBaseEntity;
import com.endeavorms.velocity.qto.common.FlatMapUtil;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.note.ServiceNoteManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.TerminalServiceStatuses;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
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

@Component
public class ServiceBrokerageInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBrokerageInterceptor.class);

    private final ServiceBrokerageManager manager;
    private final ServiceManager serviceManager;
    private final ServiceNoteManager serviceNoteManager;

    public ServiceBrokerageInterceptor(ServiceBrokerageManager manager,
                                        ServiceManager serviceManager,
                                        ServiceNoteManager serviceNoteManager) {
        this.manager = manager;
        this.serviceManager = serviceManager;
        this.serviceNoteManager = serviceNoteManager;
    }

    public void validateBeforeSave(ServiceBrokerage entity) {
        LOGGER.info("ServiceBrokerageInterceptor.validateBeforeSave() called");
        ServiceBrokerage existing = entity.getId() != null
                ? manager.retrieve(entity.getId()) : new ServiceBrokerage();
        Service service = serviceManager.retrieve(entity.getServiceId());
        if (TerminalServiceStatuses.getStatuses().contains(service.getStatus())) {
            if (SecurityUtils.hasAuthority(Permissions.ADMIN)
                    || SecurityUtils.hasAuthority(Permissions.ORDER_WRITE_TERMINAL)
                    || SecurityUtils.hasAuthority(Permissions.INVENTORY_WRITE)) {
                LOGGER.debug(
                        "service {} is in terminal status or is inventory: {}, " +
                        "service brokerage is being updated by user {}",
                        entity.getId(), service.getStatus(), SecurityUtils.getLoggedInUser());
                String auditString = getAuditString(entity, existing, "Service", "/fieldmapping/service-brokerage-field-mapping.json", null);
                if (auditString.length() > 0) {
                    serviceNoteManager.create(entity.getServiceId(), auditString, "Audit");
                }
            }
        }
    }

    private String getAuditString(final AbstractBaseEntity entity,
                                  final AbstractBaseEntity existing,
                                  final String auditTarget,
                                  final String mappingFile,
                                  final List<String> keysToIgnore) {
        LOGGER.debug("Auditing {} {}", auditTarget, entity.getId());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> type = new TypeReference<>() {
        };

        JsonObject mappingJson;
        try {
            String fieldMapping = IOUtils.resourceToString(mappingFile, StandardCharsets.UTF_8);
            JsonReader jsonReader = Json.createReader(new StringReader(fieldMapping));
            mappingJson = jsonReader.readObject();
            jsonReader.close();
        } catch (Exception e) {
            LOGGER.error("Error reading " + mappingFile, e);
            mappingJson = JsonValue.EMPTY_JSON_OBJECT;
        }

        try {
            StringBuilder result = new StringBuilder();
            Map<String, Object> map = FlatMapUtil.flatten(mapper.readValue(ow.writeValueAsString(entity), type));
            Map<String, Object> existingMap = FlatMapUtil.flatten(mapper.readValue(ow.writeValueAsString(existing), type));
            MapDifference<String, Object> difference = Maps.difference(map, existingMap);

            for (Map.Entry<String, MapDifference.ValueDifference<Object>> entry
                    : difference.entriesDiffering().entrySet()) {
                if (keysToIgnore != null && keysToIgnore.contains(entry.getKey())) {
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
                        LOGGER.warn("Mapping config {} is missing field key {}", mappingFile, k);
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

                String newValue = entry.getValue().leftValue() == null
                        ? "<Blank>" : entry.getValue().leftValue().toString();
                String oldValue = entry.getValue().rightValue() == null
                        ? "<Blank>" : entry.getValue().rightValue().toString();

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

                String line = "Changed the " + fieldJson.getString("value") + " from "
                        + oldValue + " (old value) to " + newValue + " (new value)";
                result.append(line).append("\n");
            }

            if (result.length() > 0) {
                result.insert(0, auditTarget +" updated by admin:\n");
            }

            LOGGER.debug("Done audit of {} {}", auditTarget, entity.getId());
            return result.toString();
        } catch (Exception e) {
            String message = "Error auditing " + auditTarget;
            LOGGER.error(message, e);
            return message;
        }
    }
}
