package com.vertek.corporate.qto.common;

import java.io.Serializable;

public interface GenericDao<T extends BaseEntity<KeyType>, KeyType extends Serializable> extends Manager<T, KeyType> {
    Class<T> getEntityClass();
}
