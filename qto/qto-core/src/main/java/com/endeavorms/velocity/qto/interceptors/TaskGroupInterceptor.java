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

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Intercepts API calls related to Task Groups.
 */
public class TaskGroupInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskGroupInterceptor.class);

    /** Business logic for surcharges. */
    @Inject
    private TaskGroupManager taskGroupManager;

    @Inject
    private CompanyManager companyManager;

    /**
     * Validates the incoming Surcharge.
     * @param context the context of the invocation.
     * @return the result of the invocation.
     * @throws Exception if the invocation fails.
     */
    @AroundInvoke
    public Object validate(final InvocationContext context) throws Exception {
        List<ValidationError> errors = Lists.newArrayList();
        for (Object param : context.getParameters()) {
            if (param instanceof TaskGroup) {
                TaskGroup entity = (TaskGroup) param;
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
            } else if (param instanceof Long && context.getMethod().getName().contains("remove")) {
                Long id = (Long) param;
                TaskGroup entity = taskGroupManager.retrieve(id);
                List<Company> companies = companyManager.findUsedTaskGroups(id, entity.getTenantId());
                if (!companies.isEmpty()) {
                    errors.add(new ValidationError("Delete Failure",
                            entity.getName() + " is assigned to one or more Master/End Customers."));
                }
            }
        }

        if (errors.size() > 0) {
            BadRequestError error = new BadRequestError(errors);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }
        return context.proceed();
    }
}
