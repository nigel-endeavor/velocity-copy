package com.vertek.corporate.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.vertek.corporate.qto.ftdi.QFtdiEquipmentType.ftdiEquipmentType;

@Stateless
public class FtdiEquipmentTypeJpaDao extends AbstractJpaDao<FtdiEquipmentType, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Get by Item Number.
     *
     * @param itemNumber Item Number.
     * @return Equipment.
     */
    public FtdiEquipmentType findByItemNumberAndPartNumber(final String itemNumber, final String partNumber) {
        return new JPAQuery<FtdiEquipmentType>(entityManager)
                .from(ftdiEquipmentType)
                .where(ftdiEquipmentType.itemNumber.eq(itemNumber)
                        .and(ftdiEquipmentType.partNumber.eq(partNumber)))
                .fetchOne();
    }

}
