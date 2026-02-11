package com.endeavorms.velocity.qto.common.lookup;

import com.google.common.base.Preconditions;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;

import static com.endeavorms.velocity.qto.common.lookup.QLookupType.lookupType;
import static com.endeavorms.velocity.qto.common.lookup.QLookupValue.lookupValue;


/**
 * JPA DAO implementation for LookupType entities.
 * @author rconnolly
 * @since 1.0
 * @param <T> A LookupType Type.
 */
public abstract class AbstractLookupTypeJpaDao<T extends LookupType> extends AbstractJpaDao<T, Long> {

    /**
     * Fetches a LookupType by type code.
     * @param typeCode the LookupType's type code.
     * @return a LookupType by type code.
     */
    public LookupType retrieveByTypeCode(final String typeCode) {
        PreconditionsUtil.checkArgument(typeCode, "A typeCode is required.");

        return ((JPAQuery<LookupType>) new JPAQuery(entityManager)
                .from(lookupType, lookupValue)
                .where(lookupValue.active.isTrue()
                        .and(lookupType.typeCode.eq(typeCode)))
                .orderBy(lookupValue.sortSequence.asc(), lookupValue.value.asc()))
                .fetchOne();
    }

    /**
     * Fetches a list of {@link LookupType}s.
     * @param offset page start.
     * @param limit page size.
     * @return a list of {@link LookupType}s.
     */
    public PaginatedResult<LookupType> listOrderedAlphabetically(final int offset, final int limit) {
        Preconditions.checkArgument(offset >= 0, INVALID_OFFSET);
        JPAQuery query = (JPAQuery) new JPAQuery(entityManager)
                .from(lookupType)
                .orderBy(lookupType.name.asc());

        PaginatedResult<LookupType> pagedResult = new PaginatedResult<LookupType>();
        pagedResult.setOffset(offset);
        pagedResult.setLimit(limit);
        pagedResult.setTotal(new Long(query.fetch().size()).intValue());

        query.offset(offset);
        query.limit(limit);

        pagedResult.setCollection(query.fetch());

        return pagedResult;
    }
}
