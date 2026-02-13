package com.endeavorms.velocity.qto.common;

import java.io.Serializable;

import com.endeavorms.velocity.qto.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeavorms.velocity.qto.common.NotFoundException;

/**
 * From commons.
 * @param <T>
 * @param <KeyType>
 */
public abstract class AbstractManager<T extends BaseEntity<KeyType>, KeyType extends Serializable> implements Manager<T, KeyType> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractManager.class);

    public AbstractManager() {
    }

    protected abstract GenericDao<T, KeyType> getDao();

    public void remove(KeyType var1) {
        this.getDao().remove(var1);
    }

    /** @deprecated */
    @Deprecated
    public void remove(T var1) {
        this.remove(var1.getId());
    }

    public T edit(T var1) {
        return this.getDao().edit(var1);
    }

    public T create(T var1) {
        LOGGER.trace(this.getClass().getName() + " create() called!");
        return this.getDao().create(var1);
    }

    /** @deprecated */
    @Deprecated
    public T save(T var1) {
        return this.getDao().save(var1);
    }

    public T retrieve(KeyType var1) {
        T var2 = this.getDao().retrieve(var1);
        if (var2 == null) {
            throw new NotFoundException(var1.toString());
        } else {
            return var2;
        }
    }

    public PaginatedResult<T> list(int var1, int var2) {
        return this.getDao().list(var1, var2);
    }

    public PaginatedResult<T> findBySearchCriteria(BaseSearchCriteria<T> var1) {
        throw new UnsupportedOperationException("findBySearchCriteria must have an implementation");
    }

    public void detach(final T service) {
        this.getDao().detach(service);
    }
}
