package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class FtdiEquipmentTypeManger extends StandardManager<FtdiEquipmentType> {

    @Inject
    public FtdiEquipmentTypeJpaDao dao;

    @Override
    public FtdiEquipmentTypeJpaDao getDao() { return dao; }

                /**
     * Get by Item Number.
     *
     * @param itemNumber Item Number.
     * @return Equipment.
     */
        public FtdiEquipmentType findByItemNumberAndPartNumber(final String itemNumber, final String partNumber) {
            return dao.findByItemNumberAndPartNumber(itemNumber, partNumber);
    }

}
