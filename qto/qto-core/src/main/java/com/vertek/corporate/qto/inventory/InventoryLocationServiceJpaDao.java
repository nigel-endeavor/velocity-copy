package com.vertek.corporate.qto.inventory;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.inventory.QInventoryLocationService.inventoryLocationService;
import static com.vertek.corporate.qto.service.QService.service;

@Deprecated
@Stateless
public class InventoryLocationServiceJpaDao extends AbstractJpaDao<InventoryLocationService, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<InventoryLocationService> findByLocationId(final Long locationId) {
        return new JPAQuery<InventoryLocationService>(entityManager)
                .from(inventoryLocationService)
                .where(inventoryLocationService.inventoryLocationId.eq(locationId))
                .fetch();
    }

    public List<InventoryLocationService> getActiveInventory(final Long tenantId) {
        return new JPAQuery<InventoryLocationService>(entityManager)
                .from(inventoryLocationService)
                .join(service).on(inventoryLocationService.id.eq(service.id))
                .where(service.active.eq(true)
                        .and(service.billable.eq(true))
                        .and(service.tenantId.eq(tenantId)))
                .fetch();
    }

}
