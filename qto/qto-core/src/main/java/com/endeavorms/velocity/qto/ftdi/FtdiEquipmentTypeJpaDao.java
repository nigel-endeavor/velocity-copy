package com.endeavorms.velocity.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.ftdi.QFtdiEquipmentType.ftdiEquipmentType;

@Component
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
