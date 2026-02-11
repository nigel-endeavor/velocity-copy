package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

@Component
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
