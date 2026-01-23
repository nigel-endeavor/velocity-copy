package com.vertek.corporate.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import static com.vertek.corporate.qto.custom.iss.parser.QInputRecord3.inputRecord3;

public class InputRecord3JpaDao extends AbstractJpaDao<InputRecord3, Long> {
       @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

        public InputRecord3 findByInputRecord1Id(final Long inputRecord1Id) {
       return new JPAQuery<InputRecord3>(entityManager)
                .from(inputRecord3).where(inputRecord3.inputRecord1Id.eq(inputRecord1Id)).fetchOne();
    }
}
