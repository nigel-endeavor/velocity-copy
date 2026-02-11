package com.endeavorms.velocity.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.ftdi.QFtdiShipment.ftdiShipment;

@Component
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
