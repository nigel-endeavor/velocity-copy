package com.vertek.corporate.qto.company.task;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.common.lookup.LookupValueManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fcurran
 * @since 9/5/2024
 */
@Stateless
public class TaskGroupManager extends StandardManager<TaskGroup> {

    /**
     * Persistence tier for TaskGroup.
     */
    @Inject
    private TaskGroupJpaDao dao;

    @Inject
    private LookupValueManager lookupValueManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private CompanyTaskManager companyTaskManager;


    @Override
    protected TaskGroupJpaDao getDao() {
        return dao;
    }

    /**
     * Finds task groups by search criteria.
     * @param criteria what to match task groups on.
     * @return the matching task groups, if any.
     */
    public PaginatedResult<TaskGroup> findBySearchCriteria(final TaskGroupSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    public TaskGroup getDefaultTaskGroup(final Long tenantId) {
        return dao.getDefaultTaskGroup(tenantId);
    }

    @Override
    public TaskGroup create(final TaskGroup entity) {
        entity.setTenantId(entity.getTenantId() == null ? tenantSubjectManager.getCurrentTenant().getId() : entity.getTenantId());
        List<Task> tasks = new ArrayList<>(entity.getTasks());
        entity.setTasks(Collections.EMPTY_LIST);
        TaskGroup created = super.create(entity);
        for (Task task : tasks) {
            task.setTaskGroupId(created.getId());
            task.setLookupValue(lookupValueManager.retrieve(task.getLookupValue().getId()));
            task.setValue(task.getLookupValue().getValue());
            created.getTasks().add(task);
        }
        return super.edit(created);
    }

    @Override
    public TaskGroup edit(final TaskGroup entity) {
        TaskGroup existing = retrieve(entity.getId());
        List<Long> existingTaskIds = existing.getTasks().stream().map(Task::getId).collect(Collectors.toList());
        Map<Long, String> existingTaskMap = existing.getTasks().stream().collect(Collectors.toMap(Task::getId, Task::getValue));
//        List<Long> tasksToAdd = newTaskIds.stream().filter(nt -> !existingTaskIds.contains(nt)).collect(Collectors.toList());
//        List<Long> tasksToRemove = existingTaskIds.stream().filter(et -> !newTaskIds.contains(et)).collect(Collectors.toList());
        //find tasks that are in the incoming entity but not in the existing entity
        //create a company task for each new task for all customers with that task group that have incomplete tasks
//        companyTaskManager.createNewTasksForIncompleteCustomersWithTaskGroup(entity, entity.getTasks().stream().filter(t -> tasksToAdd.contains(t.getId())).collect(Collectors.toList()));
        //find tasks that aren't in the incoming entity
        //remove any incomplete company tasks for the tasks to remove for all customers
//        companyTaskManager.removeIncompleteByTaskIds(tasksToRemove);
        //remove the task associations for the tasks to remove
        //so that the next time we retrieve the company tasks we won't get an error
        // saying the task with that id doesn't exist
//        companyTaskManager.removeTaskAssociations(tasksToRemove);

        entity.setTenantId(existing.getTenantId());
        entity.setMasterCustomerId(existing.getMasterCustomerId());
        long sortOrder = 1L;
        for (Task task : entity.getTasks()) {
            task.setTaskGroupId(entity.getId());
            task.setSortOrder(sortOrder++);
            if (task.getLookupValue() != null) {
                task.setLookupValue(lookupValueManager.retrieve(task.getLookupValue().getId()));
            }
        }
        TaskGroup edited = super.edit(entity);

        //all task ids after the edit
        List<Long> currentTaskIds = edited.getTasks().stream().map(Task::getId).collect(Collectors.toList());
        //all tasks ids after that edit that were not there before
        List<Task> newTasks = edited.getTasks().stream().filter(ct -> !existingTaskIds.contains(ct.getId())).collect(Collectors.toList());
//        List<Long> tasksToAdd = newTaskIds.stream().filter(nt -> !existingTaskIds.contains(nt)).collect(Collectors.toList());
        List<Long> tasksToRemove = existingTaskIds.stream().filter(et -> !currentTaskIds.contains(et)).collect(Collectors.toList());
        companyTaskManager.removeIncompleteByTaskIds(tasksToRemove);
        companyTaskManager.removeTaskAssociations(tasksToRemove);
        companyTaskManager.createNewTasksForIncompleteCustomersWithTaskGroup(entity, newTasks);
        return edited;
    }

    /**
     * Finds task groups by task name.
     * @param taskName the name of the task.
     * @return the matching task groups, if any.
     */
    public List<TaskGroup> findByTask(final String taskName) {
        return dao.findByTask(taskName);
    }


}
