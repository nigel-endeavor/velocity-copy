package com.endeavorms.velocity.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.ftdi.QFtdiCustomField.ftdiCustomField;

@Component
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
