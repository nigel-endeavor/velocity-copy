package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class FtdiOrderTypeEquipmentManager extends StandardManager<FtdiOrderTypeEquipment> {

    @Inject
    FtdiOrderTypeEquipmentJpaDao dao;

    @Override
    public FtdiOrderTypeEquipmentJpaDao getDao() {
        return dao;
    }

                /**
     * Get list by Order Type.
     *
     * @param orderTypeId Order Type Id.
     * @return List of Equipment.
     */
    public List<FtdiOrderTypeEquipment> findActiveByOrderType(final Long orderTypeId) {
        return dao.findActiveByOrderType(orderTypeId);
    }
      /**
     * Get by Item Number.
     *
     * @param itemNumber Item Number
     * @param orderId    Order ID
     * @return Equipment.
     */
    public FtdiOrderTypeEquipment findByItemNumberAndOrderId(final String itemNumber, final Long orderId) {
        return dao.findByItemNumberAndOrderId(itemNumber, orderId);
    }
}
