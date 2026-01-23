package com.vertek.corporate.qto.common;

import javax.persistence.MappedSuperclass;

/**
 * From vertek-commons.
 */
@MappedSuperclass
public abstract class StandardVersionedBaseEntity extends AbstractVersionedEntity<Long> {
    private static final long serialVersionUID = -6023943827382492162L;

    public StandardVersionedBaseEntity() {
    }

    /** @deprecated */
    @Deprecated
    public boolean isNew() {
        return this.getId() == null;
    }

    /** @deprecated */
    @Deprecated
    private void setNew(boolean var1) {
    }
}