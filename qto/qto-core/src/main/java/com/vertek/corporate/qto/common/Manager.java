package com.vertek.corporate.qto.common;

import java.io.Serializable;

public interface Manager<T extends BaseEntity<KeyType>, KeyType extends Serializable> {
    T edit(T var1);

    T create(T var1);

    /** @deprecated */
    @Deprecated
    T save(T var1);

    T retrieve(KeyType var1);

    void remove(KeyType var1);

    void remove(T var1);

    PaginatedResult<T> list(int var1, int var2);

    void detach(T var1);
}
