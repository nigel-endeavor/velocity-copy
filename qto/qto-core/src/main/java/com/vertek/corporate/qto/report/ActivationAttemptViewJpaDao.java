package com.vertek.corporate.qto.report;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.subject.CompanySubjectManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.vertek.corporate.qto.common.QTenantView.tenantView;
import static com.vertek.corporate.qto.report.QActivationAttemptView.activationAttemptView;

@Stateless
public class ActivationAttemptViewJpaDao extends AbstractJpaDao<ActivationAttemptView, Long> {

    @Inject
    protected TenantSubjectManager tenantSubjectManager;

    @Inject
    protected CompanySubjectManager companySubjectManager;

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ActivationAttemptView> getServiceIntervals(final DashboardSearchCriteria criteria, final Integer numOfMonths){
        BooleanExpression expression = activationAttemptView.serviceComplete.isNotNull()
                .and(activationAttemptView.attemptNumber.isNotNull());
        if (!criteria.getMasterCompanyNames().isEmpty()) {
            expression = expression.and(activationAttemptView.masterCompanyName.in(criteria.getCompanyNames()));
        } else {
            List<Long> companyIds = companySubjectManager.getAllowedMasterCustomerIds();
            if (!companyIds.isEmpty()) {
                expression = expression.and(activationAttemptView.masterCustomerId.in(companyIds));
            }
        }
        if (!criteria.getTenantNames().isEmpty()) {
            expression = expression.and(tenantView.name.in(criteria.getTenantNames()));
        } else {
            List<Long> tenantIds = tenantSubjectManager.getAllowedTenantIds();
            if (!tenantIds.isEmpty()) {
                expression = expression.and(tenantView.id.in(tenantIds));
            }
        }
        if (!criteria.getCompanyNames().isEmpty()) {
            expression = expression.and(activationAttemptView.companyName.in(criteria.getCompanyNames()));
        }
        if (!criteria.getServiceTypes().isEmpty()) {
            expression = expression.and(activationAttemptView.serviceType.in(criteria.getServiceTypes()));
        }
        if (!criteria.getProviders().isEmpty()) {
            expression = expression.and(activationAttemptView.provider.in(criteria.getProviders()));
        }
        if (!criteria.getServiceBilledTos().isEmpty()) {
            expression = expression.and(activationAttemptView.serviceBilledTo.in(criteria.getServiceBilledTos()));
        }

        if (numOfMonths != 0) {
            Date currentDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.MONTH, -numOfMonths);
            expression = expression.and(activationAttemptView.serviceComplete.between(c.getTime(), currentDate));
        }
        return new JPAQuery<ActivationAttemptView>(entityManager)
                .from(activationAttemptView)
                .join(tenantView).on(activationAttemptView.tenantId.eq(tenantView.id))
                .where(expression)
                .orderBy(new OrderSpecifier<>(Order.DESC, activationAttemptView.activationAttemptId))
                .fetch();

    }
}
