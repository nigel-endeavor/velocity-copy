package com.vertek.corporate.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.ftdi.QFtdiEquipmentType.ftdiEquipmentType;
import static com.vertek.corporate.qto.ftdi.QFtdiOrderTypeEquipment.ftdiOrderTypeEquipment;

@Stateless
public class FtdiOrderTypeEquipmentJpaDao extends AbstractJpaDao<FtdiOrderTypeEquipment, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Get list by Order Type.
     *
     * @param orderTypeId Order Type Id.
     * @return List of Equipment.
     */
    public List<FtdiOrderTypeEquipment> findActiveByOrderType(final Long orderTypeId) {
        return new JPAQuery<FtdiOrderTypeEquipment>(entityManager)
                .from(ftdiOrderTypeEquipment).join(ftdiEquipmentType)
                .on(ftdiOrderTypeEquipment.equipmentTypeId.eq(ftdiEquipmentType.id))
                .where(ftdiOrderTypeEquipment.id.eq(orderTypeId)
                        .and(ftdiEquipmentType.active.eq(true)))
                .fetch();
    }

        /**
     * Get by Item Number.
     *
     * @param itemNumber Item Number.
     * @param orderId    Order ID
     * @return Equipment.
     */
    public FtdiOrderTypeEquipment findByItemNumberAndOrderId(final String itemNumber, final Long orderId) {
        return new JPAQuery<FtdiOrderTypeEquipment>(entityManager)
                .from(ftdiOrderTypeEquipment).join(ftdiEquipmentType)
                .on(ftdiOrderTypeEquipment.equipmentTypeId.eq(ftdiEquipmentType.id)
                        .and(ftdiOrderTypeEquipment.orderTypeId.eq(orderId)))
                .where(ftdiEquipmentType.itemNumber.eq(itemNumber))
                .fetchOne();
    }

}
