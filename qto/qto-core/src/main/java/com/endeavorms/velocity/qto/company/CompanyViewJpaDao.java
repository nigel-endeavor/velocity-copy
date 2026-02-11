package com.endeavorms.velocity.qto.company;

import com.google.common.base.Strings;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.company.QCompanyView.companyView;

/**
 * @author rcasey
 * @since 1/2/2024
 */
@Component
public class CompanyViewJpaDao extends AbstractMasterCustomerJpaDao<CompanyView> {

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

    public PaginatedResult<CompanyView> findBySearchCriteria(final CompanyViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<CompanyView>(entityManager).from(companyView)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    private Predicate getExpression(final CompanyViewSearchCriteria criteria) {
        BooleanExpression expression = companyView.id.isNotNull();
//        expression = addMasterCustomerAndTenantFilter(expression, companyView.masterCustomerId, companyView.tenantId, true);
        if ("Master Customer".equalsIgnoreCase(criteria.getType())) {
            expression = addMasterCustomerAndTenantFilter(expression, companyView.id, companyView.tenantId, true);
        } else {
            expression = addMasterCustomerAndTenantFilter(expression, companyView.masterCustomerId, companyView.tenantId, true);
        }
        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    companyView.name.containsIgnoreCase(criteria.getSearch())
                            .or(companyView.clientId.containsIgnoreCase(criteria.getSearch()))
                            .or(companyView.accountManager.containsIgnoreCase(criteria.getSearch()))
                            .or(companyView.lastCompletedTask.containsIgnoreCase(criteria.getSearch()))
                            .or(companyView.nextTask.containsIgnoreCase(criteria.getSearch()))
                            .or(companyView.nextTaskAssignedTo.containsIgnoreCase(criteria.getSearch()))
                            .or(companyView.billingContactName.containsIgnoreCase(criteria.getSearch()))
                            .or(companyView.billingContactEmail.containsIgnoreCase(criteria.getSearch()))
                            .or(companyView.billingContactPhone.containsIgnoreCase(criteria.getSearch()))
            );
        }
        if (!Strings.isNullOrEmpty(criteria.getType())) {
            expression = expression.and(companyView.type.eq(criteria.getType()));
        }
        if (!Strings.isNullOrEmpty(criteria.getTenantName())) {
            expression = expression.and(companyView.tenantName.eq(criteria.getTenantName()));
        }
        if (criteria.getMasterCustomerId() != null) {
            expression = expression.and(companyView.masterCustomerId.eq(criteria.getMasterCustomerId()));
        }
        if (criteria.getActive() != null) {
            expression = expression.and(companyView.active.eq(criteria.getActive()));
        }
        if (criteria.getOnboarding() != null && criteria.getOnboarding()) {
            expression = expression.and(companyView.status.eq("onboarding"));
        }
        if (criteria.getAssignedTo() != null && !criteria.getAssignedTo().isEmpty()) {
            if (criteria.getAssignedTo().contains("Unassigned")) {
                expression = expression.and(companyView.nextTaskAssignedTo.isNull()).or(companyView.nextTaskAssignedTo.in(criteria.getAssignedTo()));
            } else {
                expression = getInExpression(expression, companyView.nextTaskAssignedTo, criteria.getAssignedTo());
            }
        }
        return expression;
    }

    private OrderSpecifier[] getOrderBy(final CompanyViewSearchCriteria criteria) {
        OrderSpecifier orderByName = new OrderSpecifier(Order.ASC, companyView.name);
        OrderSpecifier[] orderBy = new OrderSpecifier[] { orderByName };
        if (!Strings.isNullOrEmpty(criteria.getType())) {
            NumberExpression<Integer> onboardingFirst = new CaseBuilder()
                    .when(companyView.status.eq("onboarding")).then(0)
                    .otherwise(1);
            OrderSpecifier orderByOnboardingFirst = new OrderSpecifier(Order.ASC, onboardingFirst);
            orderBy = new OrderSpecifier[] {
                    orderByOnboardingFirst,
                    orderByName
            };
        }
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            final PathBuilder<CompanyView> pathBuilder = new PathBuilder<>(CompanyView.class, "companyView");
            orderBy = new OrderSpecifier[] {
                    new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()))
            };
        }
        return orderBy;
    }

    public MasterCustomerWorklistMeta getMasterCustomerWorklistMeta(final CompanyViewSearchCriteria criteria) {
        Tuple metaCounts = new JPAQuery<Tuple>(entityManager)
                .from(companyView)
                .select(
                        companyView.status.when("active").then(1).otherwise(0).sum(),
                        companyView.status.when("onboarding").then(1).otherwise(0).sum()
                ).where(getExpression(criteria))
                .fetchOne();
        MasterCustomerWorklistMeta meta = new MasterCustomerWorklistMeta();
        // Handle potential nulls
        Integer totalActiveCustomers
                = (metaCounts.get(0, Integer.class) != null) ? metaCounts.get(0, Integer.class) : 0;
        Integer totalOnboardingCustomers
                = (metaCounts.get(1, Integer.class) != null) ? metaCounts.get(1, Integer.class) : 0;

        meta.setTotalActiveCustomers(totalActiveCustomers);
        meta.setTotalOnboardingCustomers(totalOnboardingCustomers);
        return meta;
    }
}
