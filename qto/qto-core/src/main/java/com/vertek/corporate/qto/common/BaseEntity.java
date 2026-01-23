package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

/**
 * From vertek-commons.
 * @param <KeyType>
 */
public interface BaseEntity<KeyType extends Serializable> extends Serializable {
    KeyType getId();

    /** @deprecated */
    @JsonIgnore
    @Deprecated
    boolean isNew();
}
