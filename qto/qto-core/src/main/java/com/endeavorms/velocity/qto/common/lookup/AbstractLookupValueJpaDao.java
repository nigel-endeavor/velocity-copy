package com.endeavorms.velocity.qto.common.lookup;

import com.google.common.base.Strings;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.common.AbstractMultitenantJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;
import com.endeavorms.velocity.qto.subject.CompanySubjectManager;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Locale;

import static com.endeavorms.velocity.qto.common.lookup.QLookupType.lookupType;
import static com.endeavorms.velocity.qto.common.lookup.QLookupValue.lookupValue;
import static com.endeavorms.velocity.qto.company.QCompany.company;

/**
 * JPA DAO implementation for LookupValue entities.
 *
 * @author rconnolly
 * @since 1.0
 *
 * @param <T> a LookupValue type.
 */
public abstract class AbstractLookupValueJpaDao<T extends LookupValue> extends AbstractMultitenantJpaDao<T, Long> {

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private CompanySubjectManager companySubjectManager;


    /**
     * Retrieves LookupValues by type and value.
     *
     * @param typeCode the LookupType's typeCode.
     * @param value    the LookupValue's value.
     * @return a List of LookupValues that match this criteria.
     */
    public LookupValue retrieveByTypeAndValue(final String typeCode, final String value) {
        return retrieveByTypeAndValue(typeCode, value, null);
    }

    /**
     * Retrieves LookupValues by type and value.
     *
     * @param typeCode the LookupType's typeCode.
     * @param value    the LookupValue's value.
     * @param isActive only include active lookup values.
     * @return a List of LookupValues that match this criteria.
     */
    public LookupValue retrieveByTypeAndValue(final String typeCode, final String value, final Boolean isActive) {
        PreconditionsUtil.checkArgument(typeCode, "A typeCode is required");
        PreconditionsUtil.checkArgument(value, "A value is required");

        BooleanExpression expr = lookupValue.lookupType.typeCode.lower().eq(typeCode.toLowerCase(Locale.getDefault()))
                .and(lookupValue.value.toLowerCase().eq(value.toLowerCase()));
        expr = addTenantFilter(expr, lookupValue.tenantId, false);

        if (isActive != null) {
            expr = expr.and(lookupValue.active.eq(isActive));
        }

        return ((JPAQuery<LookupValue>)new JPAQuery(entityManager)
                .from(lookupValue)
                .where(expr))
                .fetchFirst();
    }

    /**
     * Retrieves LookupValues by type and value using like operator.
     *
     * @param typeCode the LookupType's typeCode.
     * @param value    the LookupValue's value.
     * @return a List of LookupValues that match this criteria.
     */
    public LookupValue retrieveByTypeAndValueLike(final String typeCode, final String value) {
        return retrieveByTypeAndValueLike(typeCode, value, null);
    }

    /**
     * Retrieves LookupValues by type and value using like operator.
     *
     * @param typeCode the LookupType's typeCode.
     * @param value    the LookupValue's value.
     * @param isActive only include active lookup values.
     * @return a List of LookupValues that match this criteria.
     */
    public LookupValue retrieveByTypeAndValueLike(final String typeCode, final String value, final Boolean isActive) {
        PreconditionsUtil.checkArgument(typeCode, "A typeCode is required");
        PreconditionsUtil.checkArgument(value, "A value is required");

        BooleanExpression expr = lookupValue.lookupType.typeCode.lower().eq(typeCode.toLowerCase(Locale.getDefault()))
                .and(lookupValue.value.like(value));

        if (isActive != null) {
            expr = expr.and(lookupValue.active.eq(isActive));
        }

        return ((JPAQuery<LookupValue>)new JPAQuery(entityManager)
                .from(lookupValue)
                .where(expr))
                .fetchFirst();
    }

    /**
     * Created this method b/c failed uniqueResult on .retreiveByTypeAndValue()
     * caused invalid transaction state, preventing ongoing use of connection
     * Note that this method will cause similar problem if lookupType is not
     * found or not unique (should not normally occur) This method does not
     * check to make sure only one value per lookupType is found.
     * todo: Come up w/ comprehensive solution to this transaction issue
     * todo: add corresponding wrapper method to manager
     *
     * @param typeCode the LookupType's type code.
     * @param value    a value to match on.
     * @return true should there be a value for the given type code, false otherwise.
     */
    public boolean isValidTypeAndValue(final String typeCode, final String value) {
        PreconditionsUtil.checkArgument(typeCode, "A typeCode is required");
        PreconditionsUtil.checkArgument(value, "A value is required");

        JPQLQuery query = new JPAQuery(entityManager);

        long itemCount = 0;

        LookupType existing;
        try {
            // Could not figure out inner join syntax; previous method was returning too many records
            //      ( previous method: .from(LOOKUP_TYPE, LOOKUP_VALUE) )
            existing = ((JPAQuery<LookupType>) query.from(lookupType)
                    .where(lookupType.typeCode.lower().eq(typeCode.toLowerCase())))
                    .fetchOne();

            if (existing == null) {
                return false;
            }

            itemCount = ((JPAQuery<LookupValue>) query.from(lookupValue)
                    .where((lookupValue.value.lower().eq(value.toLowerCase())
                            .and(lookupValue.lookupType.eq(existing)))))
                    .fetch().size();

        } catch (Exception e) {
            e.printStackTrace();
            // todo: I want to throw exception here but don't want to implement
            // something we're not ready to do:
            // throw e;
        }

        // if at least one value found, return true;
        return (itemCount > 0);
    }


    /**
     * Convenience for determining whether a certain LookupType contains the given LookupValue.
     *
     * @param value the value to look for.
     * @param typeCode the LookupType type code.
     * @return true if the given value is found in the LookupType with the given type code, false otherwise.
     */
    public boolean isValidValueForType(final String value, final String typeCode) {
        JPQLQuery query = new JPAQuery(entityManager);
        query.from(lookupValue)
                .where(lookupValue.lookupType.typeCode.eq(typeCode)
                        .and(lookupValue.value.eq(value)));

        return (query.fetch().size() > 0);
    }


    /**
     * Retrieves next sequence id for that LookupType.
     *
     * @param typeCode the LookupType's typeCode.
     * @return next sequence value for that LookupType.
     */
    public Integer getNextLookupValueSequenceId(final String typeCode) {
        Integer maxSequence = ((JPAQuery<Integer>) new JPAQuery(entityManager)
                .select(lookupValue.sortSequence.max())
                .from(lookupValue)
                .where(lookupValue.lookupType.typeCode.lower().eq(typeCode.toLowerCase(Locale.getDefault()))))
                .fetchOne();

        return (maxSequence != null) ? (maxSequence + 1) : 1;
    }


    /**
     * Finds by Search Criteria.
     * @param criteria search criteria.
     * @return List of LookupValues by search criteria.
     */
    public PaginatedResult<LookupValue> findBySearchCriteria(final LookupValueSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria, "A Search Criteria instance is required");

        BooleanExpression expression = lookupValue.lookupType.typeCode.isNotEmpty()
                .and(lookupValue.lookupType.typeCode.isNotNull());

        if (criteria.getCompanyId() == null) {
            //filter by users available tenants
            if (!"ALL_SERVICE_TYPES".equals(criteria.getTypeCode())) {
                List<Long> tenantIds = tenantSubjectManager.getAllowedTenantIds();
                List<Long> companyTenantIds = companySubjectManager.getAllowedMasterCustomerTenantIds();
                expression = expression.and(lookupValue.tenantId.in(tenantIds).or(lookupValue.tenantId.in(companyTenantIds)));
            }
        } else {
            //filter by given companyId
            expression = expression.and(lookupValue.tenantId.eq(
                    JPAExpressions.select(company.tenantId)
                            .from(company)
                            .where(company.id.eq(criteria.getCompanyId()))));
        }

        if (criteria.getActive() != null) {
            expression = expression.and(lookupValue.active.eq(criteria.getActive()));
        }
        if (!Strings.isNullOrEmpty(criteria.getTypeCode())) {
            expression = expression.and(
                    lookupValue.lookupType.typeCode.toLowerCase().eq(criteria.getTypeCode().toLowerCase()));
        }

        if (!Strings.isNullOrEmpty(criteria.getValue())) {
            expression = expression.and(lookupValue.value.startsWith(criteria.getValue()));
        }

        if (criteria.getParentId() != null) {
            expression = expression.and(lookupValue.parentId.eq(criteria.getParentId()));
        }

        return new PaginatedResult<LookupValue>(((JPAQuery) new JPAQuery(entityManager)
                .select(lookupValue)
                .from(lookupValue)
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .where(expression)
                .groupBy(lookupValue.id)
                .orderBy(getOrderExpression(criteria)))
                .fetchResults());
    }


    /**
     * Creates an OrderSpecifier from the given search criteria.
     * @param criteria the search criteria.
     * @return an OrderSpecifier.
     */
    private OrderSpecifier getOrderExpression(final LookupValueSearchCriteria criteria) {
        OrderSpecifier orderBy = lookupValue.sortSequence.asc();
        String sortField = criteria.getSortField();
        if (!Strings.isNullOrEmpty(sortField) && criteria.getSortDirection() != null) {
            PathBuilder<LookupValue> pathBuilder = new PathBuilder<LookupValue>(
                    LookupValue.class,
                    lookupValue.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(sortField));
        } else {
            LookupType type = ((JPAQuery<LookupType>) new JPAQuery(entityManager).from(lookupType)
                    .where(lookupType.typeCode.eq(criteria.getTypeCode())))
                    .fetchOne();
            if (type.getSortStrategy() == 0) {
                //this used to be lookupValue.value.asc(), but in most cases value and display are the same
                //STATE_PROVINCE is one of the few lookups that has different values for value and display
                //but sorting by display causes the states to be out of order (sorted by abbreviation)
                orderBy = lookupValue.display.asc();
            } else if (type.getSortStrategy() == 1) {
                orderBy = lookupValue.sortSequence.asc();
            } else if (type.getSortStrategy() == 2) {
                orderBy = lookupValue.id.asc();
            }
        }
        return orderBy;
    }

}
