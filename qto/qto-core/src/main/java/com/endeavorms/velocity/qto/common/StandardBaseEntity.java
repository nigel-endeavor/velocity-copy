package com.endeavorms.velocity.qto.common;

import jakarta.persistence.MappedSuperclass;

/**
 * Convenience for Entities that use a Long data type as an identifier.
 */
@MappedSuperclass
public abstract class StandardBaseEntity extends AbstractBaseEntity<Long> implements BaseEntity<Long> {

    /** Servial Version id. */
    private static final long serialVersionUID = -6023943827382492162L;


    @Deprecated
    @Override
    public boolean isNew() {
        return (getId() == null);
    }
    /**
     * Empty implementation.
     * @param isNew a boolean.
     */
    @SuppressWarnings("unused")
    @Deprecated
    private void setNew(final boolean isNew) {
    }
}