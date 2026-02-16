package com.endeavorms.velocity.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import org.springframework.stereotype.Repository;

import static com.endeavorms.velocity.qto.custom.iss.parser.QInputRecord4.inputRecord4;

@Repository
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
