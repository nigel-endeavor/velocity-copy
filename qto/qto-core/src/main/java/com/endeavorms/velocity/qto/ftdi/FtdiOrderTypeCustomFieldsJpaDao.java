package com.endeavorms.velocity.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.ftdi.QFtdiOrderTypeCustomFields.ftdiOrderTypeCustomFields;

@Component
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
