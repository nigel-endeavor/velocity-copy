package com.vertek.corporate.qto.common.lookup;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Base class for business logic pertaining to implementations.
 * @author jboomhower
 * @since 2.7.0
 */
@Stateless
public class LookupValueManager extends AbstractLookupValueManager<LookupValue> {

    /** Data Access for entities. */
    @Inject
    private LookupValueJpaDao dao;

    /** Lookup Type Manager. */
    @Inject
    private LookupTypeManager lookupTypeManager;

    @Override
    protected LookupValueJpaDao getDao() {
        return dao;
    }

    public void setDao(final LookupValueJpaDao dao) {
        this.dao = dao;
    }

    public LookupTypeManager getLookupTypeManager() {
        return lookupTypeManager;
    }

        /**
     * Fetches a {@link LookupValue} by type and name.
     * @param type
     * @param name
     * @return
     */
    public LookupValue findByNameAndType(final String type, final String name) {
        return getDao().findByNameAndType(type, name);
    }

    public List<LookupValue> findByTypeCodeAndTenantId(final String typeCode, final Long tenantId) {
        return getDao().findByTypeCodeAndTenantId(typeCode, tenantId);
    }


     public List<LookupValue> findByParentAndTenantId(final Long parent, final Long tenantId) {
        return getDao().findByParentAndTenantId(parent, tenantId);
     }
}
