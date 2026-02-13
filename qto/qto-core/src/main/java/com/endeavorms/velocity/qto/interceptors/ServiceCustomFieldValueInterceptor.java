package com.endeavorms.velocity.qto.interceptors;

import com.endeavorms.velocity.qto.customfield.field.CustomField;
import com.endeavorms.velocity.qto.customfield.field.CustomFieldManager;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValue;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValueListDto;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValueManager;
import com.endeavorms.velocity.qto.note.ServiceNoteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceCustomFieldValueInterceptor {
    /** Logging Facade. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCustomFieldValueInterceptor.class);

    private final CustomFieldManager customFieldManager;
    private final ServiceCustomFieldValueManager serviceCustomFieldValueManager;
    private final ServiceNoteManager serviceNoteManager;

    public ServiceCustomFieldValueInterceptor(CustomFieldManager customFieldManager,
                                              ServiceCustomFieldValueManager serviceCustomFieldValueManager,
                                              ServiceNoteManager serviceNoteManager) {
        this.customFieldManager = customFieldManager;
        this.serviceCustomFieldValueManager = serviceCustomFieldValueManager;
        this.serviceNoteManager = serviceNoteManager;
    }

    /**
     * Performs audit logging for custom field value changes. Call this before saveValues.
     */
    public void validateBeforeSave(final ServiceCustomFieldValueListDto entity) {
        LOGGER.info("ServiceCustomFieldValueInterceptor.validateBeforeSave() called");
        if (entity.getValues().isEmpty()) {
            return;
        }
        Long serviceId = entity.getValues().get(0).getServiceId();
        List<ServiceCustomFieldValue> existingFields = serviceCustomFieldValueManager.findByRecordId(serviceId);
        String auditString = getAuditString(entity, existingFields, "Service");
        if (auditString.length() > 0) {
            serviceNoteManager.create(serviceId, auditString, "Audit");
        }
    }

    private String getAuditString(final ServiceCustomFieldValueListDto entity,
                                  final List<ServiceCustomFieldValue> existingFields,
                                  final String auditTarget) {
        LOGGER.debug("Auditing {} {}", auditTarget, entity.getValues().get(0).getServiceId());
        StringBuilder result = new StringBuilder();
        for (ServiceCustomFieldValue field : entity.getValues()) {
            CustomField customField = customFieldManager.retrieve(field.getCustomFieldId());
            String oldValue = null;
            String newValue = null;
            if (field.getId() != null) {
                for (ServiceCustomFieldValue existingField : existingFields) {
                    if (field.getId().equals(existingField.getId())
                            && !field.getValue().equals(existingField.getValue())) {
                        // existing value to new value
                        oldValue = existingField.getValue();
                        newValue = field.getValue();

                        getAuditLine(customField, oldValue, newValue, result);
                    }
                }
            } else {
                // blank to new value
                newValue = field.getValue();

                getAuditLine(customField, oldValue, newValue, result);
            }
        }

        if (result.length() > 0) {
            result.insert(0, "Service updated by admin:\n");
        }
        LOGGER.debug("Done audit of {} {}", auditTarget, entity.getValues().get(0).getServiceId());
        return result.toString();
    }

    private void getAuditLine(final CustomField customField, final String oldValue, final String newValue, final StringBuilder result) {
        String line = "Changed the " + customField.getLabel() + " from "
                + formatValue(customField, oldValue) + " (old value) to "
                + formatValue(customField, newValue) + " (new value)";
        result.append(line).append("\n");
    }

    private String formatValue(final CustomField customField, String value) {
        //handle different custom field types.
        return value == null ? "<Blank>" : value;
    }
}
