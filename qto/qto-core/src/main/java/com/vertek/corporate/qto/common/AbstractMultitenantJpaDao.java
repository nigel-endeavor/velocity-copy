package com.vertek.corporate.qto.common;

import com.google.common.base.Preconditions;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;
import java.io.Serializable;
import java.util.List;

import static com.vertek.corporate.qto.common.QTenant.tenant;
import static com.vertek.corporate.qto.common.QTenantOwnedCompany.tenantOwnedCompany;
import static com.vertek.corporate.qto.common.SecurityUtils.SCHEDULER;
import static com.vertek.corporate.qto.subject.QTenantSubject.tenantSubject;

/**
 * DAO base class for Tenant owned Entities.
 * @author mmeehan
 * @since 1.4 - 12/5/12 10:52 PM
 * @param <T> the Entity Type.
 * @param <KeyType> the Entity's Key Type.
 */
public abstract class AbstractMultitenantJpaDao<T extends TenantOwnedEntity<KeyType>, KeyType extends Serializable>
        extends AbstractJpaDao<T, KeyType> {

    /** Logging Facade.*/
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMultitenantJpaDao.class);

    /** The Platform EntityManager.*/
    protected EntityManager platformEntityManager;

    /**
     * Implementations must supply a Platform EntityManager.
     * @param platformEntityManager the platform entity manager.
     */
    protected abstract void setPlatformEntityManager(final EntityManager platformEntityManager);



    /**
     * Gets the currently selected Tenant identifier for the currently authenticated subject.
     * @return currently selected Tenant identifier.
     */
    public Long getTenantId() {
        // todo: we probably should be checking the active flag here too.
        String username = SecurityUtils.getLoggedInUser();
        JPAQuery tenantIdQuery = (JPAQuery) new JPAQuery(platformEntityManager)
                .select(tenantSubject.tenant.id)
                .from(tenantSubject)
                .where(tenantSubject.subject.emailAddress.toLowerCase().eq(username)
                        .and(tenantSubject.isSelected.isTrue()));

        Long tenantId = (Long) tenantIdQuery.fetchOne();

        if (tenantId == null) {
            throw new RuntimeException("Invalid Subject configuration: no Tenant selected.");
        }

        return tenantId;
    }


    /**
     * Get a list of tenant id's that the current user is allowed to see.
     * @return a List of Longs.
     */
    protected List<Long> getAllowedTenantIds() {

        // todo: we probably should be checking the active flag here too.
        String username = SecurityUtils.getLoggedInUser();

        JPAQuery query = (JPAQuery) new JPAQuery(platformEntityManager)
                .select(tenantSubject.tenant.id)
                .from(tenantSubject)
                .where(tenantSubject.subject.emailAddress.toLowerCase().eq(username))
                .distinct();

        List<Long> allowedIds = query.fetch();

        LOGGER.trace("allowedIds = {}", allowedIds);

        return allowedIds;
    }

    /**
     * Get a list of tenant id's that the current user is allowed to see.
     * @return a List of Longs.
     */
    private List<Long> getActiveTenantIds() {

        JPAQuery query = (JPAQuery) new JPAQuery(entityManager)
                .select(tenantOwnedCompany.tenantId)
                .from(tenantOwnedCompany)
                .where(tenantOwnedCompany.active.isTrue()
                        .and(tenantOwnedCompany.type.equalsIgnoreCase("Vertek Client")))
                .distinct();

        List<Long> activeIds = query.fetch();

        LOGGER.debug("activeIDs = {}", activeIds);

        if (activeIds.size() == 0) {
            throw new RuntimeException("Invalid configuration: no active Tenants");
        }
        return activeIds;
    }


    /**
     * Verify that the current user has access to an entity.
     * @param entity the entity to be verified.
     * @return true if current user may access the entity, false if not.
     */
    protected boolean verifyTenant(final TenantOwnedEntity entity) {

        String userName = SecurityUtils.getLoggedInUser();

        // "scheduler@vertek.com" subject has access to all tenants
        if (userName.equals(SCHEDULER)) {
            return true;
        }

        PreconditionsUtil.checkArgument(entity.getTenantId(), "A Tenant identifier is required");

        Tenant t = platformEntityManager.find(Tenant.class, entity.getTenantId());

        if (!t.isActive()) {
            LOGGER.debug("verifyTenant: not active. Principal = {}, Tenant: {}", userName, entity.getTenantId());
            return false;
        }

        return getAllowedTenantIds().contains(t.getId());

        // selected tenant id must be the same as the Entity's tenant id.
        //return t.getId().equals(getTenantId());
    }


    @Override
    public T create(final T t) {
        if (verifyTenant(t)) {
            return super.create(t);
        } else {
            throw new IllegalArgumentException("Unable to create entity!");
        }
    }

    @Override
    public T edit(final T t) {
        if (verifyTenant(t)) {
            return super.edit(t);
        } else {
            throw new NotFoundException(t.getId().toString());
        }
    }

    @Override
    public void remove(final KeyType id) {
        T t = retrieve(id);
        super.remove(t);
    }

    @Override
    public void remove(final T t) {
        if (verifyTenant(t)) {
            super.remove(t);
        } else {
            throw new NotFoundException(t.getId().toString());
        }
    }

    @Override
    public T retrieve(final KeyType id) {
        T t = super.retrieve(id);
        if (t != null) {
            return verifyTenant(t) ? t : null;
        } else {
            return null;
        }
    }


    @Override
    public PaginatedResult<T> list(final int offset, final int limit) {
        Preconditions.checkArgument(offset >= 0, INVALID_OFFSET);

        Query countQuery = entityManager.createQuery(
                "select count(e.id) from " + getEntityClass().getSimpleName() + " as e where e.tenantId = :tenantId"
        );
        countQuery.setParameter("tenantId", getTenantId());
        Long count = (Long) countQuery.getSingleResult();

        Query query = entityManager.createQuery(
                "select e from " + getEntityClass().getSimpleName() + " as e where e.tenantId = :tenantId"
        );
        query.setParameter("tenantId", getTenantId());
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        PaginatedResult<T> pagedResult = new PaginatedResult<T>();
        pagedResult.setOffset(offset);
        pagedResult.setLimit(limit);
        pagedResult.setTotal(count.intValue());
        pagedResult.setCollection(query.getResultList());

        return pagedResult;
    }


    /**
     * Create an expression to be used in queries for filtering data matching the current user's tenant.
     * @return a BooleanExpression.
     */
    protected Predicate createTenancyExpression() {
        return tenant.id.eq(getTenantId()).and(tenant.active.isTrue());
    }

    /**
     * Create an expression to be used in queries for filtering data matching the current user's tenant.
     * @param numberPath the path to the entity's tenantId.
     * @return a BooleanExpression.
     */
    protected Predicate createTenancyExpression(final NumberPath<Long> numberPath) {
        return numberPath.eq(getTenantId());
    }

    /**
     * Add the joins and where expression to query in order to filter for tenancy.
     * @param numberPath the path to the entity's tenantId.
     * @param query the JPQLQuery to be updated.
     */
    protected void addTenantFilter(final NumberPath<Long> numberPath, final JPQLQuery query) {
        addTenantFilter(numberPath, query, false);
    }

    /**
     * Add where clause and parameter to NativeQuery for tenancy.
     * @param sql the sql string for the query
     * @param loc the possible location of the tenantId field
     * @param queryEnd the end of the query
     * @param includeAllTenants true to include all allowed tenants, false for selected tenant only.
     * @return the query with the tenant parameter set
     */
    protected Query addTenantFilter(final String sql, final String loc, final String queryEnd,
                                    final Boolean includeAllTenants) {
        String tempSql = sql;

        String loggedInUser = SecurityUtils.getLoggedInUser();

        // "scheduler@vertek.com" subject has access to all tenants so just return
        if (loggedInUser.equals(SCHEDULER)) {
            tempSql = sql + queryEnd;
            LOGGER.debug("SQL = " + tempSql);
            return entityManager.createNativeQuery(tempSql);
        } else {
            String clause = "";
            if (includeAllTenants) {
                clause = loc + "tenant_id in (:tenantIds)" + queryEnd;
            } else {
                clause = loc + "tenant_id = :tenantId " + queryEnd;
            }
            if (sql.toUpperCase().contains("WHERE")) {
                tempSql = sql + " AND " + clause;
            } else {
                tempSql = sql + " WHERE " + clause;
            }
            LOGGER.debug("SQL = " + tempSql);
            Query query = entityManager.createNativeQuery(tempSql);
            if (includeAllTenants) {
                query.setParameter("tenantIds", getAllowedTenantIds());
            } else {
                query.setParameter("tenantId", getTenantId());
            }

            return query;
        }
    }

    /**
     * Get the native sql clause for the appropriate tenancy.
     * @param loc the possible location of the tenantId field
     * @param includeAllTenants true to include all allowed tenants, false for selected tenant only.
     * @return the query with the tenant parameter set
     */
    protected String getTenantFilterClause(final String loc, final Boolean includeAllTenants) {
        String clause = "";

        String loggedInUser = SecurityUtils.getLoggedInUser();

        // "scheduler@vertek.com" subject has access to all tenants so just return
        if (loggedInUser.equals(SCHEDULER)) {
            LOGGER.debug("Clause = " + clause);
        } else {
            if (includeAllTenants) {
                clause = loc + "tenant_id in (:tenantParam) ";
            } else {
                clause = loc + "tenant_id = :tenantParam ";
            }
            LOGGER.debug("Clause = " + clause);
        }
        return clause;
    }

    /**
     * Applies the appropriate value to the tenantParam. Used in conjunction with getTenantFilterClause.
     * @param query the query to set the tenant parameter on.
     * @param includeAllTenants true to include all allowed tenants, false for selected tenant only.
     */
    protected void setTenantFilterParameter(final Query query, final Boolean includeAllTenants) {
        String loggedInUser = SecurityUtils.getLoggedInUser();

        // "scheduler@vertek.com" subject has access to all tenants so just ignore
        if (!loggedInUser.equals(SCHEDULER)) {
            if (includeAllTenants) {
                query.setParameter("tenantParam", getAllowedTenantIds());
            } else {
                query.setParameter("tenantParam", getTenantId());
            }
        }
    }

    /**
     * Add a filter to the supplied query to filter by tenant.
     * @param tenantIdProperty the path to the entity's tenantId.
     * @param query the JPQLQuery to be updated.
     * @param includeAllTenants true to include all allowed tenants, false for selected tenant only.
     */
    protected void addTenantFilter(final NumberPath<Long> tenantIdProperty,
                                   final JPQLQuery query,
                                   final Boolean includeAllTenants) {


        String loggedInUser = SecurityUtils.getLoggedInUser();

        // "scheduler@vertek.com" subject has access to all tenants so just return
        if (!loggedInUser.equalsIgnoreCase(SCHEDULER)) {
            if (includeAllTenants) {
                query.where(tenantIdProperty.in(getAllowedTenantIds()));
            } else {
                query.where(tenantIdProperty.eq(getTenantId()));
            }
        }
    }


    /**
     * Add a filter to the supplied BooleanExpression to filter by tenant.
     * @param expression the BooleanExpression to be updated.
     * @param tenantIdProperty the path to the entity's tenantId.
     * @param includeAllTenants true to include all allowed tenants, false for selected tenant only.
     * @return the input BooleanExpression with an expression added (and-ed) to filter by tenant.
     */
    protected BooleanExpression addTenantFilter(final BooleanExpression expression,
                                                final NumberPath<Long> tenantIdProperty,
                                                final Boolean includeAllTenants) {

        String loggedInUser = SecurityUtils.getLoggedInUser();

        // "scheduler@vertek.com" subject has access to all tenants so just return
        if (!loggedInUser.equalsIgnoreCase(SCHEDULER)) {
            if (Boolean.TRUE.equals(includeAllTenants)) {
                return expression.and(tenantIdProperty.in(getAllowedTenantIds()));
            } else {
                return expression.and(tenantIdProperty.eq(getTenantId()));
            }
        } else {
            return expression;
        }
    }

    /**
     * Adds the tenant filter to a given list of Predicates.
     * @param predicates the existing list of predicates to add to.
     * @param cb the criteria builder used for generating the tenant predicate.
     * @param root the query root we're applying the predicate to.
     * @param includeAllTenants true to include all allowed tenants, false for selected tenant only.
     * @return predicates including the new tenant predicate (if any).
     */
    protected List<javax.persistence.criteria.Predicate> addTenantFilter(
            final List<javax.persistence.criteria.Predicate> predicates, final CriteriaBuilder cb,
            final Root<T> root, final Boolean includeAllTenants) {

        String loggedInUser = SecurityUtils.getLoggedInUser();

        // "scheduler@vertek.com" subject has access to all tenants so just return
        if (!loggedInUser.equalsIgnoreCase(SCHEDULER)) {
            if (includeAllTenants) {
                CriteriaBuilder.In<Object> inClause = cb.in(root.get("tenantId"));
                for (Long tenantId : getAllowedTenantIds()) {
                    inClause.value(tenantId);
                }
                predicates.add(inClause);
            } else {
                predicates.add(cb.equal(root.get("tenantId"), getTenantId()));
            }
        }
        return predicates;
    }

    /**
     * Add a filter to the supplied query to filter by all active tenant.
     * @param tenantIdProperty the path to the entity's tenantId.
     * @param query the JPQLQuery to be updated.
     */
    protected void addActiveTenantFilter(final NumberPath<Long> tenantIdProperty,
                                         final JPQLQuery query) {
        query.where(tenantIdProperty.in(getActiveTenantIds()));
    }
}