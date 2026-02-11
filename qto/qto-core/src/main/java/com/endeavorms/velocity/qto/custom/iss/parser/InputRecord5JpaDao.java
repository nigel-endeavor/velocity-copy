package com.endeavorms.velocity.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.custom.iss.parser.QInputRecord2.inputRecord2;
import static com.endeavorms.velocity.qto.custom.iss.parser.QInputRecord5.inputRecord5;

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
