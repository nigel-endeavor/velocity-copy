package com.vertek.corporate.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.custom.iss.parser.QOutputNotes.outputNotes;

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
