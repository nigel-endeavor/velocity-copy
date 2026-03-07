package com.vertek.corporate.qto.order;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.order.QOrder.order;
import static com.vertek.corporate.qto.service.QService.service;

/**
 * @author rcasey
 * @since 1/9/2023
 */
@Stateless
public class OrderJpaDao extends AbstractMasterCustomerJpaDao<Order> {
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
     * Finds a single Order by a quote ID.
     *
     * @param quoteId the quote ID to fitler by.
     * @return the single matching Order, otherwise null.
     */
    public Order getByQuoteId(final String quoteId) {
        return new JPAQuery<Order>(entityManager)
                .from(order).where(order.quoteId.eq(quoteId)).fetchOne();
    }

    /**
     * Returns an Order.
     *
     * @param orderId  Order ID.
     * @param tenantId Tenant ID.
     * @return Activation Scheule Object.
     */
    public Order findByIdAndTenant(final Long orderId, final Long tenantId) {
        return new JPAQuery<Order>(entityManager)
                .from(order)
                .where(order.id.eq(orderId)
                        .and(order.tenantId.eq(tenantId)))
                .fetchOne();
    }

    /**
     * Gets the count of orders for a given list of service IDs.
     * @param serviceIds list of service Ids.
     * @return the count of orders.
     */
    public Long getOrderCount(final List<Long> serviceIds) {
        return new JPAQuery<Long>(entityManager)
                .select(order.count())
                .from(order)
                .where(order.id.in(
                        JPAExpressions.select(service.orderId)
                                .from(service)
                                .where(service.id.in(serviceIds))
                        )
                )
                .fetchOne();
    }

    public Order findByInventoryOrderId(Long inventoryOrderId) {
        return new JPAQuery<Order>(entityManager)
                .from(order)
                .where(order.inventoryOrderId.eq(inventoryOrderId))
                .fetchOne();
    }

    public List<Order> findInvByProvisioningId(Long provisioningId) {
        return new JPAQuery<Order>(entityManager)
                .from(order)
                .where(order.provisioningOrderId.eq(provisioningId)
                        .and(order.isCurrentInventory.eq(true)))
                .fetch();
    }

    /**
     * Performs a mass update of all order provisioners by master customer ID.
     * @param id master customer ID.
     * @param provisioner provisioner ID.
     */
    public void updateProvisionerForMasterCompany(final Long id, final Long provisioner) {
        new JPAUpdateClause(entityManager, order)
                .set(order.provisioner, provisioner)
                .where(order.masterCustomerId.eq(id))
                .execute();
    }

    public List<Order> findByCompanyId(final Long companyId) {
        return new JPAQuery<Order>(entityManager)
                .from(order)
                .where(order.company.id.eq(companyId))
                .fetch();
    }
}
