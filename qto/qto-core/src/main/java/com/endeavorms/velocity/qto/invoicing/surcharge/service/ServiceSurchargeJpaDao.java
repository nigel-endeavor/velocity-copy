package com.endeavorms.velocity.qto.invoicing.surcharge.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Date;
import java.util.List;

import static com.endeavorms.velocity.qto.invoicing.surcharge.service.QServiceSurcharge.serviceSurcharge;

/**
 * Persistence layer for ServiceSurcharges.
 * @author fcurran
 * @since 2023-07-11
 */
@Component
public class ServiceSurchargeJpaDao extends AbstractMasterCustomerJpaDao<ServiceSurcharge> {
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

    /**
     * Find a ServiceSurcharge by serviceId and typeId.
     * @param serviceId service id.
     * @param typeId type id.
     * @return ServiceSurcharge.
     */
    public ServiceSurcharge findByServiceIdAndType(final Long serviceId, final Long typeId) {
        return new JPAQuery<ServiceSurcharge>(entityManager).from(serviceSurcharge)
                .where(serviceSurcharge.serviceId.eq(serviceId)
                .and(serviceSurcharge.surchargeType.id.eq(typeId))).fetchOne();
    }

    /**
     * Find a ServiceSurcharge by serviceId and typeId.
     * @param serviceId service id.
     * @param type type.
     * @return ServiceSurcharge.
     */
    public List<ServiceSurcharge> findByServiceIdAndTypeName(final Long serviceId, final String type) {
        return new JPAQuery<ServiceSurcharge>(entityManager).from(serviceSurcharge)
                .where(serviceSurcharge.serviceId.eq(serviceId)
                .and(serviceSurcharge.surchargeType.type.eq(type))).fetch();
    }

    /**
     * Returns service surcharges that match the provided search criteria.
     * @param criteria what to match on.
     * @return the matching entities, if any.
     */
    public PaginatedResult<ServiceSurcharge> findBySearchCriteria(final ServiceSurchargeSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<ServiceSurcharge>(entityManager).from(serviceSurcharge)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .orderBy(serviceSurcharge.surchargeDate.desc())
                .fetchResults()); //todo: assess deprecation, this will require changes to PaginatedResult
    }

    /**
     * Gets the where clause for a given criteria.
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    private Predicate getExpression(final ServiceSurchargeSearchCriteria criteria) {
        BooleanExpression expression = serviceSurcharge.serviceId.eq(criteria.getServiceId());
        expression = addMasterCustomerAndTenantFilter(expression, serviceSurcharge.masterCustomerId, serviceSurcharge.tenantId, true);

        return expression;
    }

    /**
     * Find a ServiceSurcharge by invoiceId.
     * @param invoiceId inoviceid.
     * @return surcharge.
     */
    public ServiceSurcharge findByInvoiceID(final Long invoiceId) {
        return new JPAQuery<ServiceSurcharge>(entityManager).from(serviceSurcharge)
                .where(serviceSurcharge.invoiceChargeId.eq(invoiceId)).fetchOne();
    }

    /**
     * Find all ServiceSurcharges to be invoiced.
     * @param endDate invoice end date + 1 day.
     * @return surcharges list of surcharges.
     */
    public List<ServiceSurcharge> findForInvoice(final Date endDate, final Long tenantId) {
        return new JPAQuery<ServiceSurcharge>(entityManager).from(serviceSurcharge)
                .where(serviceSurcharge.surchargeDate.before(endDate)
                        .and(serviceSurcharge.tenantId.eq(tenantId))
                        .and(serviceSurcharge.invoiceChargeId.isNull())).fetch();
    }

    public List<ServiceSurcharge> findByServiceId(final Long serviceId) {
        return new JPAQuery<ServiceSurcharge>(entityManager).from(serviceSurcharge)
                .where(serviceSurcharge.serviceId.eq(serviceId)).fetch();
    }
}
