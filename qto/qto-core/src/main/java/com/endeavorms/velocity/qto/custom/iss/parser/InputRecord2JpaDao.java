package com.endeavorms.velocity.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.custom.iss.parser.QInputRecord2.inputRecord2;

public class InputRecord2JpaDao extends AbstractJpaDao<InputRecord2, Long> {
       @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public InputRecord2 findByInputRecord1Id(final Long inputRecord1Id) {
       return new JPAQuery<InputRecord2>(entityManager)
                .from(inputRecord2).where(inputRecord2.inputRecord1Id.eq(inputRecord1Id)).fetchOne();
    }
}
