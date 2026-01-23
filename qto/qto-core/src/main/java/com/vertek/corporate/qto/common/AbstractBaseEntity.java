package com.vertek.corporate.qto.common;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

/**
 * From vertek-commons.
 * @param <KeyType>
 */
@MappedSuperclass
public abstract class AbstractBaseEntity<KeyType extends Serializable> implements BaseEntity<KeyType> {
    public AbstractBaseEntity() {
    }

    /** @deprecated */
    @Deprecated
    public boolean isNew() {
        return this.getId() == null;
    }
}
