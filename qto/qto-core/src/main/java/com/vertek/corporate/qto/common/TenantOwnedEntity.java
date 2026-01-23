package com.vertek.corporate.qto.common;

import java.io.Serializable;

/**
 * From platform.
 * Interface to be implemented by entities associated to tenants (aka tenant owned entities).
 * @author mmeehan
 * @since 1/7/13 11:29 AM
 * @param <KeyType> the Entity's Key Type.
 */
public interface TenantOwnedEntity<KeyType extends Serializable> extends BaseEntity<KeyType> {

    /**
     * Gets the Tenant identifier.
     * @return the Tenant identifier.
     */
    Long getTenantId();

    /**
     * Sets the Tenant identifier.
     * @param tenantId the Tenant identifier.
     */
    void setTenantId(final Long tenantId);

}
