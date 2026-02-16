package com.endeavorms.velocity.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import static com.endeavorms.velocity.qto.custom.iss.parser.QInputRecord3.inputRecord3;

import org.springframework.stereotype.Repository;

@Repository
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
