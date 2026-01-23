package com.vertek.corporate.qto.common.lookup;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.common.StandardManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * Base class for LookupValue business logic.
 * @author rconnolly
 * @since 1.1.0
 * @param <T> a LookupValue Type.
 */
public abstract class AbstractLookupValueManager<T extends LookupValue> extends StandardManager<T> {

    @Override
    protected abstract AbstractLookupValueJpaDao<T> getDao();

    /**
     * Implementations must supply an AbstractLookupTypeManager implementation.
     * @return an AbstractLookupTypeManager implementation.
     */
    protected abstract AbstractLookupTypeManager<? extends LookupType> getLookupTypeManager();


    @Override
    @RequiresPermissions(value = { "admin:lookups" })
    public T create(final T lookupValue) {
        return super.create(fetchAndSetLookupTypeAndSequence(lookupValue));
    }

    @Override
    @RequiresPermissions(value = { "admin:lookups" })
    public T edit(final T lookupValue) {
        return super.edit(fetchAndSetLookupTypeAndSequence(lookupValue));
    }

    /**
     * Gets a LookupValue by type code and value.
     * @param typeCode a type code.
     * @param value a value.
     * @return a LookupValue with the given type code and value.
     */
    public LookupValue retrieveByTypeAndValue(final String typeCode, final String value) {
        return retrieveByTypeAndValue(typeCode, value, null);
    }

    /**
     * Gets a LookupValue by type code and value.
     * @param typeCode a type code.
     * @param value a value.
     * @param isActive only include active lookup values.
     * @return a LookupValue with the given type code and value.
     */
    public LookupValue retrieveByTypeAndValue(final String typeCode, final String value, final Boolean isActive) {
        PreconditionsUtil.checkArgument(typeCode, "A typeCode is required");
        PreconditionsUtil.checkArgument(value, "A value is required");
        return getDao().retrieveByTypeAndValue(typeCode, value, isActive);
    }

    /**
     * Gets a LookupValue by type code and value using like operator.
     * @param typeCode a type code.
     * @param value a value.
     * @return a LookupValue with the given type code and value.
     */
    public LookupValue retrieveByTypeAndValueLike(final String typeCode, final String value) {
        return retrieveByTypeAndValueLike(typeCode, value, null);
    }

    /**
     * Gets a LookupValue by type code and value using like operator.
     * @param typeCode a type code.
     * @param value a value.
     * @param isActive only include active lookup values.
     * @return a LookupValue with the given type code and value.
     */
    public LookupValue retrieveByTypeAndValueLike(final String typeCode, final String value, final Boolean isActive) {
        PreconditionsUtil.checkArgument(typeCode, "A typeCode is required");
        PreconditionsUtil.checkArgument(value, "A value is required");
        return getDao().retrieveByTypeAndValueLike(typeCode, value, isActive);
    }

    /**
     * Fetches the LookupType and sets it on the given LookupValue implementation. Applies a sequence value if it
     * doesn't exist.
     * @param lookupValue the LookupValue to set a managed LookupType on.
     * @return the LookupValue with a managed LookupType set on it.
     */
    private T fetchAndSetLookupTypeAndSequence(final T lookupValue) {
        String typeCode = lookupValue.getLookupType().getTypeCode();
        // todo: why don't we find by id?  not passed by some GWT UI code?
        LookupType lookupType = getLookupTypeManager().retrieveByTypeCode(typeCode);
        lookupValue.setLookupType(lookupType);

        if (lookupValue.getSortSequence() == null) {
            lookupValue.setSortSequence(getNextLookupValueSequenceId(typeCode));
        }
        return lookupValue;
    }

    /**
     * Finds the next sequence value for a given LookupType.
     * @param typeCode the LookupType to use for finding the next sequence value.
     * @return the next sequence value for that LookupType.
     */
    public Integer getNextLookupValueSequenceId(final String typeCode) {
        PreconditionsUtil.checkArgument(typeCode, "A typeCode is required");
        return getDao().getNextLookupValueSequenceId(typeCode);
    }


    /**
     * Finds by Search Criteria.
     * @param criteria search criteria.
     * @return List of LookupValues by search criteria.
     */
    public PaginatedResult<LookupValue> findBySearchCriteria(final LookupValueSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria, "A Search Criteria instance is required");
        return getDao().findBySearchCriteria(criteria);
    }

}
