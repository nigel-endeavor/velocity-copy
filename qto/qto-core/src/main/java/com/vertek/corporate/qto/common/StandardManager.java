package com.vertek.corporate.qto.common;

/**
 * From commons.
 * @param <T>
 */
public abstract class StandardManager<T extends BaseEntity<Long>> extends AbstractManager<T, Long> {
    public StandardManager() {
    }
}
