package com.vertek.corporate.qto.report;

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
import static com.vertek.corporate.qto.report.QProviderIntervalsView.providerIntervalsView;

@Stateless
public class ProviderIntervalsJpaDao extends AbstractJpaDao<ProviderIntervalsView, Long> {

    @Inject
    protected TenantSubjectManager tenantSubjectManager;

    @Inject
    protected CompanySubjectManager companySubjectManager;

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private BooleanExpression getExpression(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = providerIntervalsView.serviceId.isNotNull();
        //tenant
        if (!criteria.getTenantNames().isEmpty()) {
            expression = expression.and(tenantView.name.in(criteria.getTenantNames()));
        } else {
            List<Long> tenantIds = tenantSubjectManager.getAllowedTenantIds();
            if (!tenantIds.isEmpty()) {
                expression = expression.and(tenantView.id.in(tenantIds));
            }
        }
        //master customer
        if (!criteria.getMasterCompanyNames().isEmpty()) {
            expression = expression.and(providerIntervalsView.masterCompanyName.in(criteria.getMasterCompanyNames()));
        } else {
            List<Long> companyIds = companySubjectManager.getAllowedMasterCustomerIds();
            if (!companyIds.isEmpty()) {
                expression = expression.and(providerIntervalsView.masterCustomerId.in(companyIds));
            }
        }
        //end customer
        if (!criteria.getCompanyNames().isEmpty()) {
            expression = expression.and(providerIntervalsView.companyName.in(criteria.getCompanyNames()));
        }
        //service type
        if (!criteria.getServiceTypes().isEmpty()) {
            expression = expression.and(providerIntervalsView.serviceType.in(criteria.getServiceTypes()));
        }
        //provider
        if (!criteria.getProviders().isEmpty()) {
            expression = expression.and(providerIntervalsView.provider.in(criteria.getProviders()));
        }
        //service billed to
        if (!criteria.getServiceBilledTos().isEmpty()) {
            expression = expression.and(providerIntervalsView.serviceBilledTo.in(criteria.getServiceBilledTos()));
        }
        return expression;
    }

    public List<ProviderIntervalsView> getProviderIntervals(final DashboardSearchCriteria criteria, final String intervalTypeCode,
                                                            final Integer numOfMonths) {
        BooleanExpression expression = getExpression(criteria);
        expression = expression.and(providerIntervalsView.intervalTypeCode.eq(intervalTypeCode)
                .and(providerIntervalsView.provider.isNotEmpty())
                .and(providerIntervalsView.endDate.isNotNull()));
        if (numOfMonths != 0) {
            Date currentDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.MONTH, -numOfMonths);
            expression = expression.and(providerIntervalsView.endDate.between(c.getTime(), currentDate));
        }
        return new JPAQuery<ProviderIntervalsView>(entityManager)
                .from(providerIntervalsView)
                .join(tenantView).on(providerIntervalsView.tenantId.eq(tenantView.id))
                .where(expression)
                .orderBy(providerIntervalsView.provider.asc())
                .fetch();
    }
}
