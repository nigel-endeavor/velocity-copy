package com.endeavorms.velocity.qto.location.jms;

import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.contact.Contact;
import com.endeavorms.velocity.qto.contact.ContactType;
import com.endeavorms.velocity.qto.contact.location.LocationContact;
import com.endeavorms.velocity.qto.contact.location.LocationContactManager;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.endeavorms.velocity.qto.location.jms.LocationMessageHandler.LOCATION_QUEUE;

@Component
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "qto.jms.enabled", havingValue = "true", matchIfMissing = false)
public class LocationMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationMessageListener.class);

    @Autowired
    private LocationManager locationManager;

    @Autowired
    private LocationContactManager locationContactManager;

    @JmsListener(destination = LOCATION_QUEUE)
    public void onMessage(final LocationMessage locationMessage) {
        try {
            LOGGER.debug("Got location message: {}", locationMessage);
            SchedulerSecurityContext.runAsScheduler(() -> {
                String messageType = locationMessage.getMessageType();
                switch (messageType) {
                    case "disconnectContact":
                        break;
                    default:
                        LOGGER.error("No Location message type matching: {}", messageType);
                        break;
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error processing message - {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            LOGGER.debug("Done with message");
        }
    }

    private void processInventoryMerge(final Long locationId, final Location existingLocation,
                                       final Location newLocation, final Location updateLocation) {

        Field[] fields = Location.class.getDeclaredFields();
        Field[] fieldsToUpdate = new Field[0];
        List<String> fieldsToIgnore = Arrays.asList("lastUpdateDate", "lastUpdateBy",
                "services", "inventoryServices", "address", "lcon", "levelOfEffort",
                "mrc", "nrc", "icb", "osp", "status", "inventoryMrc", "inventoryNrc",
                "inventoryIcb", "inventoryOsp", "requirementTemplateId",
                "progressPercentage", "sortOrder", "active", "parentLocationId");

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object existingValue = field.get(existingLocation);
                Object newValue = field.get(newLocation);
                if (existingValue instanceof BigDecimal) {
                    if (((BigDecimal) existingValue).compareTo((BigDecimal) newValue) != 0) {
                        fieldsToUpdate = addFieldToArray(fieldsToUpdate, field);
                    }
                } else if (!fieldsToIgnore.contains(field.getName()) && !Objects.equals(existingValue, newValue)) {
                    fieldsToUpdate = addFieldToArray(fieldsToUpdate, field);
                }
            } catch (IllegalAccessException e) {
                LOGGER.error("Error merging location: {}", e.getMessage());
            }
        }

        if (fieldsToUpdate.length > 0) {
            LOGGER.debug("Updating location: {}", locationId);
            try {
                for (Field field : fieldsToUpdate) {
                   field.set(updateLocation, field.get(newLocation));
                }
                locationManager.edit(updateLocation);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (newLocation.getLcon() != null) {
            updateLocation.setLcon(locationContactManager.retrieveByTypeAndLocationId(
                    ContactType.LCON, updateLocation.getId()));
            if (updateLocation.getLcon() != null) {
                mergeLcon(existingLocation.getLcon(), newLocation.getLcon(), updateLocation.getLcon());
            }

        }
    }

    private void mergeLcon(LocationContact existingLcon, LocationContact newLcon, LocationContact updateLcon) {
        Field[] fields = Contact.class.getDeclaredFields();
        Field[] fieldsToUpdate = new Field[0];
        List<String> fieldsToIgnore = Arrays.asList("lastUpdateDate", "lastUpdateBy", "legacyId",
                "companyId", "address", "sortOrder");

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object existingValue = field.get(existingLcon);
                Object newValue = field.get(newLcon);
                if (!fieldsToIgnore.contains(field.getName()) && !Objects.equals(existingValue, newValue)) {
                    fieldsToUpdate = addFieldToArray(fieldsToUpdate, field);
                }
            } catch (IllegalAccessException e) {
                LOGGER.error("Error merging location: {}", e.getMessage());
            }
        }

        if (fieldsToUpdate.length > 0) {
            LOGGER.debug("Updating lcon: {}", existingLcon.getFirstName());
            try {
                for (Field field : fieldsToUpdate) {
                   field.set(updateLcon, field.get(newLcon));
                }
                locationContactManager.edit(updateLcon);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Field[] addFieldToArray(Field[] array, Field field) {
        int length = array.length;
        array = Arrays.copyOf(array, length + 1);
        array[length] = field;
        return array;
    }
}

