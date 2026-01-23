package com.vertek.corporate.qto.custom.iss.parser;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.custom.iss.parser.QOutputRecord.outputRecord;

public class OutputRecordJpaDao extends AbstractJpaDao<OutputRecord, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<OutputRecord> findAll(final Long tenantId) {
        return new JPAQuery<OutputRecord>(entityManager)
                .from(outputRecord)
                .where(outputRecord.tenantId.eq(tenantId)
                        .and(outputRecord.installationStatus.ne("Change In Assignment")))
                .fetch();
    }
}
