package com.endeavorms.velocity.qto.customfield.value;

import com.endeavorms.velocity.qto.common.StandardManager;

import java.util.List;

public abstract class AbstractCustomFieldValueManager<T extends CustomFieldValue> extends StandardManager<T> {

    @Override
    protected abstract AbstractCustomFieldValueJpaDao<T> getDao();

    public List<T> findByRecordId(final Long recordId) {
        return getDao().findByRecordId(recordId);
    }

}
