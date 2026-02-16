package com.endeavorms.velocity.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import org.springframework.stereotype.Repository;

import static com.endeavorms.velocity.qto.custom.iss.parser.QOutputNotes.outputNotes;

@Repository
public class OutPutNotesJpaDao extends AbstractJpaDao<OutputNotes, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<OutputNotes> findAll(final Long tenantId) {
        return new JPAQuery<OutputNotes>(entityManager)
                .from(outputNotes)
                .where(outputNotes.tenantId.eq(tenantId))
                .fetch();
    }

}
