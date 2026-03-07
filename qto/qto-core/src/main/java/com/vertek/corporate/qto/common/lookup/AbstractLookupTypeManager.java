package com.vertek.corporate.qto.common.lookup;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ws.rs.NotFoundException;

/**
 * Base class for LookupType related business logic.
 * @author rconnolly
 * @since 1.1.0
 * @param <T> a LookupType Type.
 */
public abstract class AbstractLookupTypeManager<T extends LookupType> extends StandardManager<T> {

    @Override
    protected abstract AbstractLookupTypeJpaDao<T> getDao();

    /**
     * Gets a LookupType by type code.
     * @param typeCode the LookupType's type code.
     * @return a LookupType with the given type code.
     */
    public LookupType retrieveByTypeCode(final String typeCode) {
        PreconditionsUtil.checkArgument(typeCode, "A type code is required");
        LookupType lookupType = getDao().retrieveByTypeCode(typeCode);
        if (lookupType == null) {
            throw new NotFoundException(typeCode);
        }
        return lookupType;
    }

    /**
     * Fetches a list of {@link LookupType}s.
     * @param offset page start.
     * @param limit page size.
     * @return a list of {@link LookupType}s.
     */
    public PaginatedResult<LookupType> listOrderedAlphabetically(final int offset, final int limit) {
        return getDao().listOrderedAlphabetically(offset, limit);
    }
}
