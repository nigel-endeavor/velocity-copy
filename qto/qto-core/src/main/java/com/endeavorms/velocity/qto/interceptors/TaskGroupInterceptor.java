package com.endeavorms.velocity.qto.interceptors;

import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.company.task.TaskGroup;
import com.endeavorms.velocity.qto.company.task.TaskGroupManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Intercepts API calls related to Task Groups.
 */
@Component
public class TaskGroupInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskGroupInterceptor.class);

    private final TaskGroupManager taskGroupManager;
    private final CompanyManager companyManager;

    public TaskGroupInterceptor(TaskGroupManager taskGroupManager, CompanyManager companyManager) {
        this.taskGroupManager = taskGroupManager;
        this.companyManager = companyManager;
    }

    /**
     * Validates a TaskGroup for create or edit.
     * @return list of validation errors, empty if valid.
     */
    public List<ValidationError> validateTaskGroup(TaskGroup entity) {
        List<ValidationError> errors = Lists.newArrayList();
        if (entity.isDefault()) {
            TaskGroup existingDefault = taskGroupManager.getDefaultTaskGroup(null);
            if (existingDefault != null
                    && (entity.getId() == null || !existingDefault.getId().equals(entity.getId()))) {
                LOGGER.debug("active default task group already exists with id: {}, " +
                        "default task group will not be updated", existingDefault.getId());
                errors.add(new ValidationError("default",
                        existingDefault.getName() + " is already the active default task group."));
            }
        }
        return errors;
    }

    /**
     * Validates a TaskGroup for removal.
     * @return list of validation errors, empty if valid.
     */
    public List<ValidationError> validateRemove(Long id) {
        List<ValidationError> errors = Lists.newArrayList();
        TaskGroup entity = taskGroupManager.retrieve(id);
        List<Company> companies = companyManager.findUsedTaskGroups(id, entity.getTenantId());
        if (!companies.isEmpty()) {
            errors.add(new ValidationError("Delete Failure",
                    entity.getName() + " is assigned to one or more Master/End Customers."));
        }
        return errors;
    }

    /**
     * Returns BadRequestError if validation fails, null otherwise.
     */
    public BadRequestError validateForCreateOrEdit(TaskGroup entity) {
        List<ValidationError> errors = validateTaskGroup(entity);
        return errors.isEmpty() ? null : new BadRequestError(errors);
    }

    /**
     * Returns BadRequestError if remove validation fails, null otherwise.
     */
    public BadRequestError validateForRemove(Long id) {
        List<ValidationError> errors = validateRemove(id);
        return errors.isEmpty() ? null : new BadRequestError(errors);
    }
}
