package com.vertek.corporate.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.vertek.corporate.qto.ftdi.QFtdiCustomField.ftdiCustomField;

@Stateless
public class FtdiCustomFieldJpaDao extends AbstractJpaDao<FtdiCustomField, Long> {

  @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

        /**
     * Get Custom Field.
     * @param fieldName field name.
     * @return Custom Field object.
     */
    public FtdiCustomField findCustomFieldByName(final String fieldName)    {
        return new JPAQuery<FtdiCustomField>(entityManager)
                .from(ftdiCustomField)
                .where(ftdiCustomField.fieldName.eq(fieldName))
                .fetchOne();
    }
}
