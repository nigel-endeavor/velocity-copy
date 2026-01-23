package com.vertek.corporate.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.custom.iss.parser.QInputRecord1.inputRecord1;

public class InputRecord1JpaDao extends AbstractJpaDao<InputRecord1, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public InputRecord1 findByTicket(final String ticketNumber) {
        return new JPAQuery<InputRecord1>(entityManager).from(inputRecord1)
                .where(inputRecord1.ticketNumber.eq(ticketNumber)).fetchOne();
    }

}
