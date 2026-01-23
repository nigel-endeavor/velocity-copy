package com.vertek.corporate.qto.common.lookup;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.company.task.CompanyTask;
import com.vertek.corporate.qto.company.task.CompanyTaskManager;
import com.vertek.corporate.qto.company.task.Task;
import com.vertek.corporate.qto.company.task.TaskGroup;
import com.vertek.corporate.qto.company.task.TaskGroupManager;
import com.vertek.corporate.qto.company.task.TaskManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class LookupTypeManager extends AbstractLookupTypeManager<LookupType> {

    /** Data Access for lookup types. */
    @Inject
    private LookupTypeJpaDao dao;

    @Inject
    private LookupValueManager lookupValueManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private TaskGroupManager taskGroupManager;

    @Inject
    private TaskManager taskManager;

    @Inject
    private CompanyTaskManager companyTaskManager;

    @Override
    protected LookupTypeJpaDao getDao() {
        return dao;
    }

    public void setDao(final LookupTypeJpaDao dao) {
        this.dao = dao;
    }


    /**
     * Fetches a list of Modifiable {@link LookupType}s.
     * @param offset page start.
     * @param limit page size.
     * @return a list of {@link LookupType}s.
     */
    public PaginatedResult<LookupType> listModifiableAlphabetically(final int offset, final int limit) {
        return getDao().listModifiableAlphabetically(offset, limit);
    }

        /**
     * Fetches a {@link LookupType} by type.
     * @param type
     * @return
     */
    public LookupType findByType(final String type) {
        return getDao().findByType(type);
    }

    public void setValues(final LookupType type) {
        Long tenantId = tenantSubjectManager.getCurrentTenant().getId();
        //remove values that are not in the incoming list
        Long parentId = null;
        if (!type.getValues().isEmpty()) {
            parentId = type.getValues().get(0).getParentId();
        }
        List<LookupValue> valuesToRemove;
        if (parentId != null && parentId > 0) {
             valuesToRemove = lookupValueManager.findByParentAndTenantId(parentId, tenantId).stream()
                .filter(lv -> !type.getValues().stream().map(LookupValue::getId).collect(Collectors.toList()).contains(lv.getId())).collect(Collectors.toList());

        } else {
             valuesToRemove = lookupValueManager.findByTypeCodeAndTenantId(type.getTypeCode(), tenantId).stream()
                .filter(lv -> !type.getValues().stream().map(LookupValue::getId).collect(Collectors.toList()).contains(lv.getId())).collect(Collectors.toList());
        }
        for (LookupValue value : valuesToRemove) {
            if ("ACCOUNT_ONBOARDING_TASKS".equalsIgnoreCase(type.getTypeCode())) {
                List<TaskGroup> groups = taskGroupManager.findByTask(value.getValue());
                if (!groups.isEmpty()) {
                  String names = groups.stream().map(TaskGroup::getName).collect(Collectors.joining(", "));
                    throw new IllegalArgumentException("The Save has been aborted. You cannot delete task '" + value.getValue() + "' because it is used in Task Group(s) '" + names + "'. Please remove the task from the group(s) and try again.");
                }
            }
            lookupValueManager.remove(value.getId());
        }
        //set the sort sequence
        int sequence = 10;
        for (LookupValue value : type.getValues()) {
            value.setLookupType(type);
            value.setTenantId(tenantId);
            value.setValue(value.getDisplay());
            if (type.getSortStrategy() == 1) {
                value.setSortSequence(sequence);
                sequence += 10;
            }
            if (value.getId() == null) {
                lookupValueManager.create(value);
            } else {
                lookupValueManager.edit(value);
                if ("ACCOUNT_ONBOARDING_TASKS".equalsIgnoreCase(type.getTypeCode())) {
                    updateTaskGroup(value);
                }
            }
        }
    }

    private void updateTaskGroup(LookupValue value) {
        String newValue = value.getValue();
        List<Task> tasks = taskManager.findByLookupValueId(value.getId());
        for (Task task : tasks) {
            if (!task.getValue().equals(newValue)) {
                task.setValue(newValue);
                taskManager.edit(task);
                List<CompanyTask> companyTasks = companyTaskManager.findByTaskId(task.getId());
                for (CompanyTask companyTask : companyTasks) {
                    companyTask.setValue(newValue);
                    companyTaskManager.edit(companyTask);
                }
            }
        }
    }
}
