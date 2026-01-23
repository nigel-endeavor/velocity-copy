package com.vertek.corporate.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.vertek.corporate.qto.custom.iss.parser.QInputRecord2.inputRecord2;
import static com.vertek.corporate.qto.custom.iss.parser.QInputRecord5.inputRecord5;

public class InputRecord5JpaDao extends AbstractJpaDao<InputRecord5, Long> {
       @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

        public InputRecord5 findByInputRecord1Id(final Long inputRecord1Id) {
       return new JPAQuery<InputRecord5>(entityManager)
                .from(inputRecord5).where(inputRecord5.inputRecord1Id.eq(inputRecord1Id)).fetchOne();
    }
}
