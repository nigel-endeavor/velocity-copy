package com.vertek.corporate.qto.invoicing.invoiceCharge;

import com.google.common.base.Strings;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMultitenantJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.invoicing.surcharge.service.ServiceSurcharge;
import com.vertek.corporate.qto.invoicing.surcharge.service.ServiceSurchargeManager;
import com.vertek.corporate.qto.milestone.LocationMilestoneInstance;
import com.vertek.corporate.qto.milestone.LocationMilestoneInstanceManager;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstance;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.vertek.corporate.qto.invoicing.invoiceCharge.QInvoiceCharge.invoiceCharge;

/**
 * @author mwelicka
 * @since 7/30/2023
 */

@Stateless
public class InvoiceChargeJpaDao extends AbstractMultitenantJpaDao<InvoiceCharge, Long> {

    @Inject
    private ServiceMilestoneInstanceManager serviceMilestoneManager;

    @Inject
    private LocationMilestoneInstanceManager locationMilestoneManager;

    @Inject
    private ServiceSurchargeManager surchargeManager;

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
     * Returns Invoices that match the provided search criteria.
     *
     * @param criteria what to match on.
     * @return the matching entities, if any.
     */
    public PaginatedResult<InvoiceCharge> findBySearchCriteria(final InvoiceChargeSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<InvoiceCharge>(entityManager)
                .from(invoiceCharge)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults()); //todo: assess deprecation, this will require changes to PaginatedResult
    }

    /**
     * Gets the where clause for a given criteria.
     *
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    private Predicate getExpression(final InvoiceChargeSearchCriteria criteria) {

        BooleanExpression expression = invoiceCharge.invoiceId.eq(criteria.getInvoiceId());

        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    invoiceCharge.itemDesc.contains(criteria.getSearch())
                            .or(invoiceCharge.chargeDesc.contains(criteria.getSearch()))
                            .or(invoiceCharge.chargeLevel.contains(criteria.getSearch()))
                            .or(invoiceCharge.billableEventMilestoneDescription.contains(criteria.getSearch()))
                            .or(invoiceCharge.masterCustomerName.contains(criteria.getSearch()))
                            .or(invoiceCharge.endCustomerName.contains(criteria.getSearch()))
            );
        }


        expression = getContainsExpression(expression, invoiceCharge.itemDesc, criteria.getItemDesc());
        expression = getContainsExpression(expression, invoiceCharge.chargeDesc, criteria.getChargeDesc());
        expression = getContainsExpression(expression, invoiceCharge.chargeType, criteria.getChargeType());
        expression = getContainsExpression(expression, invoiceCharge.chargeLevel, criteria.getChargeLevel());
        expression = getContainsExpression(expression, invoiceCharge.masterCustomerName, criteria.getMasterCustomer());
        expression = getContainsExpression(expression, invoiceCharge.endCustomerName, criteria.getEndCustomer());
        expression = getContainsExpression(expression, invoiceCharge.billableEventMilestoneDescription, criteria.getBillableEvent());
        expression = getDateComparisonExpression(expression, invoiceCharge.billableEventDate, criteria.getBillableEventDate(),
                criteria.getBillableEventDateRange());
        return expression;
    }

    /**
     * Gets the order by expression for invoice charges based on the given criteria. Default is by ID ascending.
     *
     * @param criteria the criteria to use to build the order by expression.
     * @return the order by expression.
     */
    private OrderSpecifier getOrderBy(final InvoiceChargeSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.ASC, invoiceCharge.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<InvoiceCharge> pathBuilder = new PathBuilder<>(InvoiceCharge.class, invoiceCharge.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    public void removeExistingCharges(final Long invoiceId) {
        List<InvoiceCharge> charges = new JPAQuery<InvoiceCharge>(entityManager)
                .from(invoiceCharge)
                .where(invoiceCharge.invoiceId.eq(invoiceId))
                .fetch();

        //when the invoice charges are being reset we need to reset all the milestones used to generate the draft
        //invoice. The Milestone Instance has the invoice id and will need to be reset to null;
        List<LocationMilestoneInstance> lmis = locationMilestoneManager.findByInvoiceId(invoiceId);
        for (LocationMilestoneInstance lmi : lmis) {
            lmi.setInvoiceId(null);
            locationMilestoneManager.editInvoiceId(lmi);
        }
        List<ServiceMilestoneInstance> smis = serviceMilestoneManager.findByInvoiceId(invoiceId);
        for (ServiceMilestoneInstance smi : smis) {
            smi.setInvoiceId(null);
            serviceMilestoneManager.editInvoiceId(smi);
        }

        for (InvoiceCharge charge : charges) {
            if ("Surcharge".equalsIgnoreCase(charge.getChargeType())) {
                //reset the surcharges
                ServiceSurcharge surcharge = surchargeManager.findByInvoiceID(charge.getId());
                if (surcharge != null) {
                    surcharge.setInvoiceChargeId(null);
                    surchargeManager.edit(surcharge);
                }
            }
            remove(charge.getId());
        }
    }

    /**
     * Gets the total amount billed for a given location.
     *
     * @param locationId the location id.
     * @return the total amount billed.
     */
    public BigDecimal getPreviouslyBilledAmt(final long locationId) {
        return new JPAQuery<InvoiceCharge>(entityManager)
                .select(invoiceCharge.invoicedAmount.sum())
                .from(invoiceCharge)
                .where(invoiceCharge.locationId.eq(locationId)
                        .and(invoiceCharge.chargeType.eq("Level Of Effort")))
                .fetchOne();
    }

    /**
     * Gets the total amount billed for an invoice.
     *
     * @param invoiceId the invoice id.
     * @return the total amount billed.
     */
    public BigDecimal getTotalCharges(final long invoiceId) {
        return new JPAQuery<InvoiceCharge>(entityManager)
                .select(invoiceCharge.invoicedAmount.sum())
                .from(invoiceCharge)
                .where(invoiceCharge.invoiceId.eq(invoiceId))
                .fetchOne();
    }
}
