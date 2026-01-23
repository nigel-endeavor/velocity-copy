package com.vertek.corporate.qto.company.task;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.company.Company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.company.QCompany.company;
import static com.vertek.corporate.qto.company.task.QCompanyTask.companyTask;

@Stateless
public class CompanyTaskJpaDao extends AbstractMasterCustomerJpaDao<CompanyTask> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }

    public List<CompanyTask> findByCompanyId(final Long companyId) {
        BooleanExpression expression = companyTask.companyId.eq(companyId);
        addTenantFilter(expression, companyTask.tenantId, false);
        return new JPAQuery<CompanyTask>(entityManager)
                .from(companyTask)
                .where(expression)
                .fetch();
    }

    public void removeIncompleteByTaskIds(final List<Long> array) {
        BooleanExpression expression = companyTask.task.id.in(array).and(companyTask.completeDate.isNull());
        addTenantFilter(expression, companyTask.tenantId, false);
        new JPADeleteClause(entityManager, companyTask)
                .where(expression)
                .execute();
    }

    public List<Company> findCompanyIdsWithIncompleteTasksForTaskGroup(final Long taskGroupId) {
        return new JPAQuery<Company>(entityManager)
                .from(company)
                .innerJoin(companyTask).on(company.id.eq(companyTask.companyId))
                .where(company.taskGroupId.eq(taskGroupId)
                        .and(companyTask.completeDate.isNull()))
                .select(company)
                .distinct()
                .fetch();
    }

    public void removeTaskAssociations(final List<Long> array) {
        new JPAUpdateClause(entityManager, companyTask)
                .setNull(companyTask.task)
                .where(companyTask.task.id.in(array))
                .execute();
    }

    public List<CompanyTask> findByTaskId(final Long taskId) {
        BooleanExpression expression = companyTask.task.id.eq(taskId);
        addTenantFilter(expression, companyTask.tenantId, false);
        return new JPAQuery<CompanyTask>(entityManager)
                .from(companyTask)
                .where(expression)
                .fetch();
    }
}
