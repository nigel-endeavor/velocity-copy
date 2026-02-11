package com.endeavorms.velocity.qto.inventory;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.inventory.QInventoryLocationService.inventoryLocationService;
import static com.endeavorms.velocity.qto.service.QService.service;

@Deprecated
@Component
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
