package com.vertek.corporate.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.ftdi.QFtdiShipment.ftdiShipment;

@Stateless
public class FtdiShipmentJpaDao extends AbstractJpaDao<FtdiShipment, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<FtdiShipment> findByDispatchId(Long id, final String trackingNumber) {
        return new JPAQuery<FtdiShipment>(entityManager)
                .from(ftdiShipment)
                .where(ftdiShipment.ftdiDispatchId.eq(id)
                        .and(ftdiShipment.trackingNumber.eq(trackingNumber)))
                .fetch();
    }
}
