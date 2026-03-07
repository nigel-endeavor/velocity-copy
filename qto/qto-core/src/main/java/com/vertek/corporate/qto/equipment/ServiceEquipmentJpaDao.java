package com.vertek.corporate.qto.equipment;

import com.google.common.base.Strings;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.equipment.QServiceEquipment.serviceEquipment;


/**
 * Service Equipment JPA DAO.
 * @author fcurran
 * @since 1.15.0
 */
@Stateless
public class ServiceEquipmentJpaDao extends AbstractMasterCustomerJpaDao<ServiceEquipment> {

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
     * Find all equipment for a service.
     *
     * @param serviceId the service id
     * @return the list of notes
     */
    public List<ServiceEquipment> findByServiceId(Long serviceId) {
        return new JPAQuery<ServiceEquipment>(entityManager)
                .from(serviceEquipment).where(serviceEquipment.serviceId.eq(serviceId)).fetch();
    }

    /**
     * Find all equipment by the given criteria.
     * @param criteria the search criteria.
     * @return the list of equipment.
     */
    public PaginatedResult<ServiceEquipment> findBySearchCriteria(final ServiceEquipmentSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<ServiceEquipment>(entityManager).from(serviceEquipment)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Get the order by clause.
     * @param criteria the search criteria.
     * @return the order by clause.
     */
    private OrderSpecifier[] getOrderBy(final ServiceEquipmentSearchCriteria criteria) {
        OrderSpecifier orderByDecommision = new OrderSpecifier<>(Order.ASC, serviceEquipment.decommission);
        OrderSpecifier orderByDeliveredDate = new OrderSpecifier<>(
                Order.DESC,
                Expressions.booleanTemplate("CASE WHEN {0} IS NULL THEN 1 ELSE 0 END", serviceEquipment.deliveredDate)
        );
        OrderSpecifier orderById = new OrderSpecifier(Order.DESC, serviceEquipment.id);
        OrderSpecifier[] orderBy = new OrderSpecifier[] { orderByDecommision, orderByDeliveredDate, orderById };
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<ServiceEquipment> pathBuilder
                    = new PathBuilder<>(ServiceEquipment.class, serviceEquipment.getMetadata());
            OrderSpecifier orderBySortField = new OrderSpecifier(criteria.getSortDirection(),
                    pathBuilder.get(criteria.getSortField()));
            orderBy = new OrderSpecifier[] { orderBySortField };
        }
        return orderBy;
    }

    /**
     * Get the expression for the given criteria.
     * @param criteria the search criteria.
     * @return the expression.
     */
    private Predicate getExpression(final ServiceEquipmentSearchCriteria criteria) {
        BooleanExpression expression = serviceEquipment.id.isNotNull();
        if (criteria.getServiceId() != null) {
            expression = expression.and(serviceEquipment.serviceId.eq(criteria.getServiceId()));
        }
        return expression;
    }
}
