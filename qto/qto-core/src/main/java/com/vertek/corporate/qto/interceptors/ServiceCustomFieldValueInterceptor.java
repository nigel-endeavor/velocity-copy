package com.vertek.corporate.qto.interceptors;

import com.vertek.corporate.qto.customfield.field.CustomField;
import com.vertek.corporate.qto.customfield.field.CustomFieldManager;
import com.vertek.corporate.qto.customfield.value.ServiceCustomFieldValue;
import com.vertek.corporate.qto.customfield.value.ServiceCustomFieldValueListDto;
import com.vertek.corporate.qto.customfield.value.ServiceCustomFieldValueManager;
import com.vertek.corporate.qto.note.ServiceNoteManager;
import com.vertek.corporate.qto.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import java.util.List;

public class ServiceCustomFieldValueInterceptor {
    /** Logging Facade. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCustomFieldValueInterceptor.class);

    @Inject
    private CustomFieldManager customFieldManager;
    @Inject
    private ServiceCustomFieldValueManager serviceCustomFieldValueManager;

    /** The manager for ServiceNotes. */
    @Inject
    private ServiceNoteManager serviceNoteManager;

    /**
     * Validates the incoming ServiceCustomFieldValueListDto.
     *
     * @param context the context of the invocation.
     * @return the result of the invocation.
     * @throws Exception if the invocation fails.
     */
    @AroundInvoke
    public Object validate(final InvocationContext context) throws Exception {
        LOGGER.info("ServiceCustomFieldValueInterceptor.validate() called");
        for (Object param : context.getParameters()) {
            if (param instanceof ServiceCustomFieldValueListDto) {
                ServiceCustomFieldValueListDto entity = (ServiceCustomFieldValueListDto) param;
                if (entity.getValues().isEmpty()) {
                    break;
                }
                Long serviceId = entity.getValues().get(0).getServiceId();
                List<ServiceCustomFieldValue> existingFields = serviceCustomFieldValueManager.findByRecordId(serviceId);
                String auditString = getAuditString(entity, existingFields, "Service");
                if (auditString.length() > 0) {
                    serviceNoteManager.create(serviceId, auditString, "Audit");
                }
            }
        }
        return context.proceed();
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
