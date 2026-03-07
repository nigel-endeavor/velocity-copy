package com.vertek.corporate.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.vertek.corporate.qto.ftdi.QFtdiOrderTypeCustomFields.ftdiOrderTypeCustomFields;

@Stateless
public class FtdiOrderTypeCustomFieldsJpaDao extends AbstractJpaDao<FtdiOrderTypeCustomFields, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

                /**
     * Get Custom Field.
     * @param id field name ID.
     * @return Custom Field object.
     */
    public FtdiOrderTypeCustomFields findByCustomFieldIdAndOrder(final Long id, final Long orderId)    {
        return new JPAQuery<FtdiOrderTypeCustomFields>(entityManager)
                .from(ftdiOrderTypeCustomFields)
                .where(ftdiOrderTypeCustomFields.customFieldId.eq(id)
                        .and(ftdiOrderTypeCustomFields.orderTypeId.eq(orderId)))
                .fetchOne();
    }
}
