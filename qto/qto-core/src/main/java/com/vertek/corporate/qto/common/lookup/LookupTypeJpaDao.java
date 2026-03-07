package com.vertek.corporate.qto.common.lookup;

import com.google.common.base.Preconditions;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.PaginatedResult;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.vertek.corporate.qto.common.lookup.QLookupType.lookupType;


@Stateless
public class LookupTypeJpaDao extends AbstractLookupTypeJpaDao<LookupType> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Fetches a list of modifiable {@link LookupType}s.
     * @param offset page start.
     * @param limit page size.
     * @return a list of {@link LookupType}s.
     */
    public PaginatedResult<LookupType> listModifiableAlphabetically(final int offset, final int limit) {
        Preconditions.checkArgument(offset >= 0, INVALID_OFFSET);
        //including tenant service types because it's the parent of sub product type. We'll exclude it on the front end.
        return new PaginatedResult<>(((JPAQuery<LookupType>) new JPAQuery(entityManager)
                .from(lookupType)
                .where(lookupType.modifiable.isTrue().or(lookupType.typeCode.eq("TENANT_SERVICE_TYPES")))
                .orderBy(lookupType.name.asc()))
                .fetchResults());
    }

    /**
     * Fetches a {@link LookupType} by type.
     * @param type
     * @return
     */
    public LookupType findByType(final String type) {
        return new JPAQuery<LookupType>(entityManager)
                .from(lookupType)
                .where(lookupType.typeCode.eq(type))
                .fetchOne();
    }
}
