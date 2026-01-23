package com.vertek.corporate.qto.company;

import com.google.common.base.Strings;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.TenantOwnedEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.vertek.corporate.qto.common.SecurityUtils.SCHEDULER;
import static com.vertek.corporate.qto.company.QCompany.company;

/**
 * @author rcasey
 * @since 1/19/2023
 */
@Stateless
public class CompanyJpaDao extends AbstractMasterCustomerJpaDao<Company> {

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


    @Override
    protected boolean verifyTenant(final TenantOwnedEntity entity) {
        String username = SecurityUtils.getLoggedInUser();

        // "scheduler@vertek.com" subject has access to all tenants
        if (username.equals(SCHEDULER)) {
            return true;
        }

        Company company = (Company) entity;

        return ("End Customer".equals(company.getType()) && getAllowedCompanyIds().contains(company.getMasterCustomerId()))
                || ("Master Customer".equals(company.getType()) && getAllowedCompanyIds().contains(company.getId()))
                || getAllowedTenantIds().contains(entity.getTenantId());
    }

    public PaginatedResult<Company> findBySearchCriteria(final CompanySearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<Company>(entityManager).from(company)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .orderBy(new OrderSpecifier<>(Order.ASC, company.name))
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given LocationSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevant results.
     */
    public Predicate getExpression(final CompanySearchCriteria criteria) {
        BooleanExpression expression = company.id.isNotNull();
        if ("Vertek Client".equals(criteria.getType())) {
            expression = expression.and(company.tenantId.in(getAllowedTenantIds()));
        } else if ("Master Customer".equals(criteria.getType())) {
            expression = addMasterCustomerAndTenantFilter(expression, company.id, company.tenantId, true);
        } else {
            expression = addMasterCustomerAndTenantFilter(expression, company.masterCustomerId, company.tenantId, true);
        }
        if (!Strings.isNullOrEmpty(criteria.getType())) {
            expression = expression.and(company.type.eq(criteria.getType()));
        }
        if (!Strings.isNullOrEmpty(criteria.getName())) {
            expression = getContainsExpression(expression, company.name, criteria.getName());
        }
        if (!criteria.getTenants().isEmpty()) {
            List<Long> tenantIds = new ArrayList<>();
            for (String tenantName : criteria.getTenants()) {
                Company company = findTenantByName(tenantName.trim());
                tenantIds.add(company.getTenantId());
            }
            expression = expression.and(company.tenantId.in(tenantIds));
        }
        if (!criteria.getMasterCustomers().isEmpty()) {
            List<Long> masterCustomerIds = new ArrayList<>();
            for (String masterCustomerName : criteria.getMasterCustomers()) {
                Company company = findMasterCustomerByNameAndTenantId(masterCustomerName.trim(), getTenantId());
                masterCustomerIds.add(company.getId());
            }
            expression = expression.and(company.parentCompany.id.in(masterCustomerIds));
        }
        if (criteria.getActive() != null) {
            expression = expression.and(company.active.eq(criteria.getActive()));
        }

        return expression;
    }

     /**
     * Finds a companies by a given name.
     * @param name the name to search by.
     * @return matching companies.
     */
    public Company findTenantByName(final String name) {
        return new JPAQuery<Company>(entityManager).from(company).where(company.name.eq(name)
                .and(company.type.eq("Vertek Client"))).fetchOne();
    }

    /**
     * Finds a companies by a given tenantId.
     * @param tenantId the tenantId to search by.
     * @return matching companies.
     */
    public Company findTenantByTenantId(final Long tenantId) {
        return new JPAQuery<Company>(entityManager).from(company).where(company.tenantId.eq(tenantId)
                .and(company.type.eq("Vertek Client"))).fetchOne();
    }

    /**
     * Finds all companies by a given name.
     *
     * @param name     the name to search by.
     * @param tenantId the related tenant.
     * @return all matching companies.
     */
    public Company findMasterCustomerByNameAndTenantId(final String name, final Long tenantId) {
        return new JPAQuery<Company>(entityManager).from(company)
                .where(company.name.eq(name)
                        .and(company.tenantId.eq(tenantId))
                        .and(company.type.eq("Master Customer")))
                .fetchOne();
    }

    /**
     * Finds all companies by a given name.
     *
     * @param name     the name to search by.
     * @param tenantId the related tenant.
     * @return all matching companies.
     */
    public Company findEndCustomerByNameAndTenantId(final String name, final Long tenantId) {
        return new JPAQuery<Company>(entityManager).from(company)
                .where(company.name.eq(name)
                        .and(company.tenantId.eq(tenantId))
                        .and(company.type.eq("End Customer")))
                .fetchOne();
    }

    /**
     * Finds end customer by name, masterCustomerId, and tenantId.
     * @param name the name to search by.
     * @param masterCustomerId the masterCustomerId to search by.
     * @param tenantId the related tenant.
     * @return a single matching company.
     */
    public Company findEndCustomerByNameMasterCustomerIdAndTenantId(final String name, final Long masterCustomerId,
                                                                  final Long tenantId) {
        return new JPAQuery<Company>(entityManager).from(company)
                .where(company.name.eq(name)
                        .and(company.tenantId.eq(tenantId))
                        .and(company.type.eq("End Customer"))
                        .and(company.parentCompany.id.eq(masterCustomerId)))
                .fetchOne();
    }


    /**
     * Finds a tenant record.
     *
     * @param tenantId the related tenant.
     * @return all matching companies.
     */
    public Company getCompanyIdForTenant(final Long tenantId) {
        return new JPAQuery<Company>(entityManager).
                from(company).where(company.type.eq("Vertek Client")
                        .and(company.tenantId.eq(tenantId))).fetchOne();
    }


    public Company findParentByName(String name, Long tenantId) {
        return new JPAQuery<Company>(entityManager).
                from(company).where(company.type.eq("Master Customer")
                        .and(company.name.eq(name))
                        .and(company.tenantId.eq(tenantId))).fetchOne();
    }

    public Company findEndCustByName(String name, Long tenantId) {
        return new JPAQuery<Company>(entityManager).
                from(company).where(company.type.eq("End Customer")
                        .and(company.name.eq(name))
                        .and(company.tenantId.eq(tenantId))).fetchOne();
    }

    /**
     * Finds tenant ids by names.
     * @param tenantNames the names to search by.
     * @return matching tenant ids.
     */
    public List<Long> findTenantIdsByNames(final List<String> tenantNames) {
        return new JPAQuery<Company>(entityManager).from(company).where(company.name.in(tenantNames)
                .and(company.type.eq("Vertek Client"))).select(company.tenantId).fetch();
    }

    public Long getInventoryLocationCount(final Long companyId, final String companyType) {
        return ((BigInteger) entityManager.createNativeQuery(
                "SELECT COUNT(DISTINCT(l.location_id)) " +
                        "FROM location l " +
                        "JOIN orders o on l.order_id = o.order_id " +
                        "JOIN v_tenant t on o.tenant_id = t.tenant_id " +
                        "JOIN company tc on t.name = tc.company_name " +
                        "WHERE ('Vertek Client' = :companyType AND tc.company_id = :companyId " +
                        "OR 'Master Customer' = :companyType AND o.master_customer_id = :companyId " +
                        "OR 'End Customer' = :companyType AND o.company_id = :companyId)" +
                        "AND l.active AND l.current_inventory")
                .setParameter("companyId", companyId)
                .setParameter("companyType", companyType)
                .getSingleResult())
                .longValue();
    }

    public BigDecimal getInventoryMrc(final Long companyId, final String companyType) {
        return (BigDecimal) entityManager.createNativeQuery(
                "SELECT SUM(s.service_mrc) " +
                        "FROM service s " +
                        "JOIN orders o on s.order_id = o.order_id " +
                        "JOIN v_tenant t on o.tenant_id = t.tenant_id " +
                        "JOIN company tc on t.name = tc.company_name " +
                        "WHERE ('Vertek Client' = :companyType AND tc.company_id = :companyId " +
                        "OR 'Master Customer' = :companyType AND o.master_customer_id = :companyId " +
                        "OR 'End Customer' = :companyType AND o.company_id = :companyId) " +
                        "AND s.active AND s.billable AND s.current_inventory")
                .setParameter("companyId", companyId)
                .setParameter("companyType", companyType)
                .getSingleResult();
    }

    public BigDecimal getInventoryMrr(final Long companyId, final String companyType) {
        return (BigDecimal) entityManager.createNativeQuery(
                "SELECT SUM(s.service_mrr) " +
                        "FROM service s " +
                        "JOIN orders o on s.order_id = o.order_id " +
                        "JOIN v_tenant t on o.tenant_id = t.tenant_id " +
                        "JOIN company tc on t.name = tc.company_name " +
                        "WHERE ('Vertek Client' = :companyType AND tc.company_id = :companyId " +
                        "OR 'Master Customer' = :companyType AND o.master_customer_id = :companyId " +
                        "OR 'End Customer' = :companyType AND o.company_id = :companyId) " +
                        "AND s.active AND s.billable AND s.current_inventory")
                .setParameter("companyId", companyId)
                .setParameter("companyType", companyType)
                .getSingleResult();
    }

    public BigDecimal getInventoryNrr(final Long companyId, final String companyType) {
        return (BigDecimal) entityManager.createNativeQuery(
                "SELECT SUM(s.service_nrr) " +
                        "FROM service s " +
                        "JOIN orders o on s.order_id = o.order_id " +
                        "JOIN v_tenant t on o.tenant_id = t.tenant_id " +
                        "JOIN company tc on t.name = tc.company_name " +
                        "WHERE ('Vertek Client' = :companyType AND tc.company_id = :companyId " +
                        "OR 'Master Customer' = :companyType AND o.master_customer_id = :companyId " +
                        "OR 'End Customer' = :companyType AND o.company_id = :companyId) " +
                        "AND s.active AND s.billable AND s.current_inventory")
                .setParameter("companyId", companyId)
                .setParameter("companyType", companyType)
                .getSingleResult();
    }

    public List<Long> getCompanyIdsFromNames(final List<String> companyNames, final String type, final Long tenantId) {
        return new JPAQuery<Company>(entityManager).from(company)
                .where(company.name.in(companyNames)
                        .and(company.type.eq(type))
                        .and(company.tenantId.eq(tenantId))
                )
                .select(company.id).fetch();
    }

    public List<Company> findByTypeAndTenantId(final String type, final Long tenantId) {
        return new JPAQuery<Company>(entityManager).from(company)
                .where(company.type.eq(type)
                        .and(company.tenantId.eq(tenantId)))
                .orderBy(company.name.asc())
                .fetch();
    }

    public List<Company> findByEndCustomerByParentId(Long id) {
        return new JPAQuery<Company>(entityManager).from(company)
                .where(company.type.eq("End Customer")
                        .and(company.parentCompany.id.eq(id)))
                .orderBy(company.name.asc())
                .fetch();
    }

    public List<Company> findUsedTaskGroups(Long taskGroup, Long tenantId) {
        return new JPAQuery<Company>(entityManager).from(company)
                .where(company.taskGroupId.eq(taskGroup)
                        .and(company.tenantId.eq(tenantId)))
                .fetch();
    }
}
