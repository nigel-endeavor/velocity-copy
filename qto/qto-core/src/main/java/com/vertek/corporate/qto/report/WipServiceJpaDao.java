package com.vertek.corporate.qto.report;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.location.TerminalLocationStatuses;
import com.vertek.corporate.qto.service.TerminalServiceStatuses;
import com.vertek.corporate.qto.subject.CompanySubjectManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.selectOne;
import static com.vertek.corporate.qto.common.QTenantView.tenantView;
import static com.vertek.corporate.qto.report.QWipLocationJeopView.wipLocationJeopView;
import static com.vertek.corporate.qto.report.QWipServiceJeopView.wipServiceJeopView;
import static com.vertek.corporate.qto.report.QWipServiceView.wipServiceView;
import static com.vertek.corporate.qto.service.QService.service;
import static com.vertek.corporate.qto.service.view.QServiceView.serviceView;

@Stateless
public class WipServiceJpaDao extends AbstractJpaDao<WipServiceView, Long> {

    @Inject
    protected TenantSubjectManager tenantSubjectManager;

    @Inject
    protected CompanySubjectManager companySubjectManager;

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private BooleanExpression getExpressionForWipServiceView(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = wipServiceView.serviceId.isNotNull();
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
            expression = expression.and(wipServiceView.masterCompanyName.in(criteria.getMasterCompanyNames()));
        } else {
            List<Long> companyIds = companySubjectManager.getAllowedMasterCustomerIds();
            if (!companyIds.isEmpty()) {
                expression = expression.and(wipServiceView.masterCustomerId.in(companyIds));
            }
        }
        //end customer
        if (!criteria.getCompanyNames().isEmpty()) {
            expression = expression.and(wipServiceView.companyName.in(criteria.getCompanyNames()));
        }
        //service type
        if (!criteria.getServiceTypes().isEmpty()) {
            expression = expression.and(wipServiceView.serviceType.in(criteria.getServiceTypes()));
        }
        //provider
        expression = getInExpression(expression, wipServiceView.provider, criteria.getProviders());
        //service billed to
        expression = getInExpression(expression, wipServiceView.serviceBilledTo, criteria.getServiceBilledTos());

        return expression;
    }

    private BooleanExpression getExpressionForWipServiceJeopView(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = wipServiceJeopView.serviceId.isNotNull();
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
            expression = expression.and(wipServiceJeopView.masterCompanyName.in(criteria.getMasterCompanyNames()));
        } else {
            List<Long> companyIds = companySubjectManager.getAllowedMasterCustomerIds();
            if (!companyIds.isEmpty()) {
                expression = expression.and(wipServiceJeopView.masterCustomerId.in(companyIds));
            }
        }
        //end customer
        if (!criteria.getCompanyNames().isEmpty()) {
            expression = expression.and(wipServiceJeopView.companyName.in(criteria.getCompanyNames()));
        }
        //service type
        if (!criteria.getServiceTypes().isEmpty()) {
            expression = expression.and(wipServiceJeopView.serviceType.in(criteria.getServiceTypes()));
        }
        //provider
        expression = getInExpression(expression, wipServiceJeopView.provider, criteria.getProviders());
        //service billed to
        expression = getInExpression(expression, wipServiceJeopView.serviceBilledTo, criteria.getServiceBilledTos());

        return expression;
    }


    public List<WipServiceView> getWipServices(final DashboardSearchCriteria criteria, final boolean allStatuses) {
        BooleanExpression expression = getExpressionForWipServiceView(criteria);

        if (!allStatuses) {
            expression = expression.and(wipServiceView.serviceStatus.notIn(TerminalServiceStatuses.getStatuses()));
        }

        return new JPAQuery<WipServiceView>(entityManager)
                .from(wipServiceView)
                .join(tenantView).on(wipServiceView.tenantId.eq(tenantView.id))
                .where(expression)
                .fetch();
    }

    public List<WipServiceJeopView> getWipServiceJeops(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = getExpressionForWipServiceJeopView(criteria);

        expression = expression.and(wipServiceJeopView.serviceStatus.notIn(TerminalServiceStatuses.getStatuses()));

        return new JPAQuery<WipServiceJeopView>(entityManager)
                .from(wipServiceJeopView)
                .join(tenantView).on(wipServiceJeopView.tenantId.eq(tenantView.id))
                .where(expression)
                .fetch();
    }

    public List<WipServiceView> getProviderReliance(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = getExpressionForWipServiceView(criteria);
        expression = expression.and(wipServiceView.active.isTrue()
                .and(wipServiceView.currentInventory.isTrue())
                .and(wipServiceView.serviceBilledTo.notLike("%Customer%"))
                .and(
                        wipServiceView.dataProvisioningCompleteDate.isNotNull()
                                .or(wipServiceView.completeDate.isNotNull())
                ));

        return new JPAQuery<WipServiceView>(entityManager)
                .from(wipServiceView)
                .join(tenantView).on(wipServiceView.tenantId.eq(tenantView.id))
                .where(expression)
                .orderBy(wipServiceView.provider.asc())
                .fetch();
    }

    /**
     * Returns services that meet the conditions of the monthly spend dashboard.
     * @param criteria the search criteria to filter services by.
     * @return matching services.
     */
    public List<WipServiceView> getServicesForMonthlySpend(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = getExpressionForWipServiceView(criteria);
        expression = expression.and(wipServiceView.active.isTrue())
                .and(wipServiceView.currentInventory.isTrue());
        return new JPAQuery<WipServiceView>(entityManager)
                .from(wipServiceView)
                .join(tenantView).on(wipServiceView.tenantId.eq(tenantView.id))
                .where(expression)
                .orderBy(wipServiceView.provider.asc())
                .fetch();
    }

    /**
     * Returns services that meet the conditions of the incremental network spend dashboard.
     * @param criteria the search criteria to filter services by.
     * @return matching services.
     */
    public List<WipServiceView> getServicesForIncrementalNetworkSpend(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = getExpressionForWipServiceView(criteria);

        Date date = new Date();
        LocalDate previousYear = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        previousYear = previousYear.plusYears(-1);
        previousYear = previousYear.plusMonths(1);
        previousYear = previousYear.withDayOfMonth(1);
        expression = expression.and(wipServiceView.dataProvisioningCompleteDate
                .goe(Date.from(previousYear.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        expression = expression.and(wipServiceView.active.isTrue());
        expression = expression.and(wipServiceView.currentInventory.isTrue());
        return new JPAQuery<WipServiceView>(entityManager)
                .from(wipServiceView)
                .join(tenantView).on(wipServiceView.tenantId.eq(tenantView.id))
                .where(expression)
                .orderBy(wipServiceView.dataProvisioningCompleteDate.desc())
                .fetch();
    }

    /**
     * Returns services that meet the conditions of the unbillable network expense accrual dashboard.
     * @param criteria the search criteria to filter services by.
     * @return matching services.
     */
    public List<WipServiceView> getServicesForUnbillableNetworkExpenseAccrual(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = getExpressionForWipServiceView(criteria);

        expression = expression.and(wipServiceView.dataProvisioningCompleteDate.isNotNull().and(wipServiceView.completeDate.isNull()));

        return new JPAQuery<WipServiceView>(entityManager)
                .from(wipServiceView)
                .join(tenantView).on(wipServiceView.tenantId.eq(tenantView.id))
                .where(expression)
                .orderBy(wipServiceView.dataProvisioningCompleteDate.desc())
                .fetch();
    }

    public List<WipLocationJeopView> getWipLocationJeops(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = getExpressionForWipLocationJeopView(criteria);

        expression = expression.and(wipLocationJeopView.locationStatus.notIn(TerminalLocationStatuses.getStatuses()));

        return new JPAQuery<WipLocationJeopView>(entityManager)
                .from(wipLocationJeopView)
                .join(tenantView).on(wipLocationJeopView.tenantId.eq(tenantView.id))
                .where(expression)
                .fetch();
    }

    private BooleanExpression getExpressionForWipLocationJeopView(final DashboardSearchCriteria criteria) {
        BooleanExpression expression = wipLocationJeopView.locationId.isNotNull();
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
            expression = expression.and(wipLocationJeopView.masterCompanyName.in(criteria.getMasterCompanyNames()));
        } else {
            List<Long> companyIds = companySubjectManager.getAllowedMasterCustomerIds();
            if (!companyIds.isEmpty()) {
                expression = expression.and(wipLocationJeopView.masterCustomerId.in(companyIds));
            }
        }
        //end customer
        if (!criteria.getCompanyNames().isEmpty()) {
            expression = expression.and(wipLocationJeopView.companyName.in(criteria.getCompanyNames()));
        }
        //service type
        if (!criteria.getServiceTypes().isEmpty()) {
            expression = expression.and(
                    selectOne().from(service)
                            .where(service.locationId.eq(wipLocationJeopView.locationId),
                                    service.type.in(criteria.getServiceTypes())).exists());
        }
        //provider
        if (!criteria.getProviders().isEmpty()) {
            if (criteria.getProviders().contains("ISEMPTY") && criteria.getProviders().size() > 1) {
                expression = expression.and(
                        selectOne().from(service)
                                .where(service.locationId.eq(wipLocationJeopView.locationId),
                                        service.provider.in(criteria.getProviders())
                                                .or(service.provider.isEmpty())).exists());
            } else if (criteria.getProviders().contains("ISEMPTY")) {
                expression = expression.and(
                        selectOne().from(service)
                                .where(service.locationId.eq(wipLocationJeopView.locationId),
                                        service.provider.isEmpty()).exists());
            } else {
                expression = expression.and(
                        selectOne().from(service)
                                .where(service.locationId.eq(wipLocationJeopView.locationId),
                                        service.provider.in(criteria.getProviders())).exists());
            }
        }
        //service billed to
        if (!criteria.getServiceBilledTos().isEmpty()) {
            if (criteria.getServiceBilledTos().contains("ISEMPTY") && criteria.getServiceBilledTos().size() > 1) {
                expression = expression.and(
                        selectOne().from(service)
                                .where(service.locationId.eq(wipLocationJeopView.locationId),
                                        service.serviceBilledTo.in(criteria.getServiceBilledTos())
                                                .or(service.serviceBilledTo.isEmpty())).exists());
            } else if (criteria.getServiceBilledTos().contains("ISEMPTY")) {
                expression = expression.and(
                        selectOne().from(service)
                                .where(service.locationId.eq(wipLocationJeopView.locationId),
                                        service.serviceBilledTo.isEmpty()).exists());
            } else {
                expression = expression.and(
                        selectOne().from(service)
                                .where(service.locationId.eq(wipLocationJeopView.locationId),
                                        service.serviceBilledTo.in(criteria.getServiceBilledTos())).exists());
            }
        }

        return expression;
    }
}
