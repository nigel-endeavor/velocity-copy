package com.endeavorms.velocity.qto.common;

import java.io.Serializable;

public interface VersionedBaseEntity<KeyType extends Serializable> extends BaseEntity<KeyType> {
    Integer getVersion();
}
