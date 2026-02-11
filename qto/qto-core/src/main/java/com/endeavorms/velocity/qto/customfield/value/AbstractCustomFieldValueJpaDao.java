package com.endeavorms.velocity.qto.customfield.value;

import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMultitenantJpaDao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class AbstractCustomFieldValueJpaDao<T extends CustomFieldValue> extends AbstractMultitenantJpaDao<T, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract List<T> findByRecordId(final Long recordId);

}
