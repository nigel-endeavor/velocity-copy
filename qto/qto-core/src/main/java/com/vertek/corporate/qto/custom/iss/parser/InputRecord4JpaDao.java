package com.vertek.corporate.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.custom.iss.parser.QInputRecord4.inputRecord4;

public class InputRecord4JpaDao extends AbstractJpaDao<InputRecord4, Long> {
       @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

        public List<InputRecord4> findByInputRecord1Id(final Long inputRecord1Id) {
       return new JPAQuery<InputRecord4>(entityManager)
                .from(inputRecord4).where(inputRecord4.inputRecord1Id.eq(inputRecord1Id)).fetch();
    }
}
