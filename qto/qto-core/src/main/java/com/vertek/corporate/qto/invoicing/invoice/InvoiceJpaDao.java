package com.vertek.corporate.qto.invoicing.invoice;

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
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.company.CompanyManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.invoicing.invoice.QInvoice.invoice;

/**
 * @author mwelicka
 * @since 7/30/2023
 */

@Stateless
public class InvoiceJpaDao extends AbstractMultitenantJpaDao<Invoice, Long> {

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

    @Inject
    protected CompanyManager companyManager;

    @Inject
    protected TenantSubjectManager tenantSubjectManager;

    /**
     * Find all invoices for a given Invoice Number and Tenant ID.
     *
     * @param invNum Invoice Number.
     * @return List of Invoices.
     */
    public List<Invoice> findByInvoiceNumberAndTenantId(final String invNum, final Long tenantId) {
        return new JPAQuery<Invoice>(entityManager)
                .from(invoice).where(invoice.invoiceNumber.eq(invNum)
                        .and(invoice.tenantId.eq(tenantId))).fetch();
    }

    /**
     * Returns Invoices that match the provided search criteria.
     *
     * @param criteria what to match on.
     * @return the matching entities, if any.
     */
    public PaginatedResult<Invoice> findBySearchCriteria(final InvoiceSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<Invoice>(entityManager).from(invoice)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .orderBy(getOrderBy(criteria))
                .fetchResults()); //todo: assess deprecation, this will require changes to PaginatedResult
    }

    /**
     * Gets the where clause for a given criteria.
     *
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    private Predicate getExpression(final InvoiceSearchCriteria criteria) {
        BooleanExpression expression = invoice.id.isNotNull();
        if (!criteria.getTenantNames().isEmpty()) {
            List<Long> tenantIds = companyManager.findTenantIdsByNames(criteria.getTenantNames());
            expression = expression.and(invoice.tenantId.in(tenantIds));
        } else {
            expression = expression.and(invoice.tenantId.eq(tenantSubjectManager.getCurrentTenant().getId()));
        }

        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    invoice.invoiceNumber.contains(criteria.getSearch())
                            .or(invoice.generatedBy.contains(criteria.getSearch()))
                            .or(invoice.invoiceStatus.contains(criteria.getSearch()))
            );
        }

        if (criteria.getInvoiceId() != null) {
            expression = expression.and(invoice.id.eq(criteria.getInvoiceId()));
        }

        expression = getContainsExpression(expression, invoice.invoiceNumber, criteria.getInvoiceNumber());
        expression = getContainsExpression(expression, invoice.invoiceStatus, criteria.getInvoiceStatus());
        expression = getContainsExpression(expression, invoice.generatedBy, criteria.getGeneratedBy());
        expression = getDateComparisonExpression(expression, invoice.invoiceStart, criteria.getInvoiceStartDate(),
                criteria.getInvoiceStartDateTimeRange());
        expression = getDateComparisonExpression(expression, invoice.invoiceEnd, criteria.getInvoiceEndDate(),
                criteria.getInvoiceEndDateTimeRange());
        expression = getDateComparisonExpression(expression, invoice.generatedDate, criteria.getGeneratedDate(),
                criteria.getGeneratedDateTimeRange());
        return expression;
    }

    /**
     * Gets the order by expression for invoice charges based on the given criteria. Default is by ID ascending.
     *
     * @param criteria the criteria to use to build the order by expression.
     * @return the order by expression.
     */
    private OrderSpecifier getOrderBy(final InvoiceSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, invoice.invoiceStart);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<Invoice> pathBuilder = new PathBuilder<>(Invoice.class, invoice.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    /**
     * Find all Draft invoices for a Tenant ID.
     *
     * @param tenantId Invoice Number.
     * @return List of Invoices.
     */
    public List<Invoice> findDraftByTenantId(final Long tenantId) {
        return new JPAQuery<Invoice>(entityManager)
                .from(invoice).where(invoice.invoiceStatus.eq("Draft")
                        .and(invoice.tenantId.eq(tenantId))).fetch();
    }

    /**
     * Find all Draft invoices for a Tenant ID.
     *
     * @param invoiceId Invoice Number.
     * @param tenantId  Invoice Number.
     * @return List of Invoices.
     */
    public List<Invoice> findForUnfinalizeByTenantId(final Long invoiceId, final Long tenantId) {
        return new JPAQuery<Invoice>(entityManager)
                .from(invoice).where(invoice.id.gt(invoiceId)
                        .and(invoice.tenantId.eq(tenantId))).fetch();
    }
}
