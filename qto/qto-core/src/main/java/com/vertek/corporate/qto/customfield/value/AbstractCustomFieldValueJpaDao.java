package com.vertek.corporate.qto.customfield.value;

import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMultitenantJpaDao;

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
