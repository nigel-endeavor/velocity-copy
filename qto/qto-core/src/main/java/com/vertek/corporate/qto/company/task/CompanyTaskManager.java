package com.vertek.corporate.qto.company.task;

import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.company.Company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CompanyTaskManager extends StandardManager<CompanyTask> {
    @Inject
    private CompanyTaskJpaDao dao;
    @Inject
    private TaskGroupManager taskGroupManager;

    @Override
    protected CompanyTaskJpaDao getDao() {
        return dao;
    }

    @Override
    public CompanyTask edit(final CompanyTask companyTask) {
        CompanyTask existingCompanyTask = retrieve(companyTask.getId());
        companyTask.setMasterCustomerId(existingCompanyTask.getMasterCustomerId());
        companyTask.setTenantId(existingCompanyTask.getTenantId());
        return super.edit(companyTask);
    }

    public List<CompanyTask> findByCompanyId(final Long companyId) {
        return getDao().findByCompanyId(companyId);
    }

    /**
     * Create company tasks for a company based on a task group.
     * Deletes any existing company tasks for the company if they exist.
     * @param company The company to create tasks for.
     * @param taskGroupId The task group to create tasks from.
     * @return The list of created company tasks.
     */
    public List<CompanyTask> updateCompanyTasks(final Company company, final Long taskGroupId) {
        List<CompanyTask> existingTasks = findByCompanyId(company.getId());
//        List<CompanyTask> completedTasks = new ArrayList<>();
        for (CompanyTask existingTask : existingTasks) {
//            if (existingTask.getCompleteDate() == null) {
            remove(existingTask.getId());
//            } else {
//                completedTasks.add(existingTask);
//            }
        }
        List<CompanyTask> createdTasks = new ArrayList<>();
        if (taskGroupId != null) {
            TaskGroup taskGroup = taskGroupManager.retrieve(taskGroupId);
            if (taskGroup != null) {
                List<Task> tasks = taskGroup.getTasks();
                List<CompanyTask> leftoverTasks = findByCompanyId(company.getId());
                for (Task task : tasks) {
                    if (leftoverTasks.stream().anyMatch(t -> t.getTask().getId().equals(task.getId()))) {
                        continue;
                    }
                    CompanyTask companyTask = new CompanyTask();
                    companyTask.setCompanyId(company.getId());
                    companyTask.setTask(task);
                    companyTask.setValue(task.getLookupValue().getValue());
                    companyTask.setSortOrder(task.getSortOrder());
                    companyTask.setMasterCustomerId(company.getMasterCustomerId());
                    companyTask.setTenantId(company.getTenantId());
                    createdTasks.add(create(companyTask));
                }
            }
        }
        return createdTasks;
    }

    public void removeIncompleteByTaskIds(final List<Long> taskIds) {
        getDao().removeIncompleteByTaskIds(taskIds);
    }

    public void createNewTasksForIncompleteCustomersWithTaskGroup(final TaskGroup taskGroup, final List<Task> newTasks) {
        //get all companies with incomplete customer tasks for that task group
        List<Company> companys = getDao().findCompanyIdsWithIncompleteTasksForTaskGroup(taskGroup.getId());
        for (Company company : companys) {
            List<CompanyTask> existingCompanyTasks = findByCompanyId(company.getId());
            for (Task task : newTasks) {
                if (existingCompanyTasks.stream().anyMatch(ct -> ct.getValue().equals(task.getValue()))) {
                    continue;
                }
                CompanyTask companyTask = new CompanyTask();
                companyTask.setCompanyId(company.getId());
                companyTask.setTask(task);
                companyTask.setValue(task.getLookupValue().getValue());
                companyTask.setSortOrder(task.getSortOrder());
                companyTask.setMasterCustomerId(company.getMasterCustomerId());
                companyTask.setTenantId(company.getTenantId());
                create(companyTask);
            }
        }
    }

    public void removeTaskAssociations(final List<Long> taskIds) {
        getDao().removeTaskAssociations(taskIds);
    }

    public List<CompanyTask> findByTaskId(final Long taskId) {
        return getDao().findByTaskId(taskId);
    }
}
