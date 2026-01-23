package com.vertek.corporate.qto.common.lookup;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.common.lookup.QLookupValue.lookupValue;

@Stateless
public class LookupValueJpaDao extends AbstractLookupValueJpaDao<LookupValue> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }

    /**
     * Fetches a {@link LookupValue} by type and name.
     * @param type
     * @param name
     * @return
     */
    public LookupValue findByNameAndType(final String type, final String name) {
        return new JPAQuery<LookupValue>(entityManager)
                .from(lookupValue)
                .where(lookupValue.lookupType.typeCode.eq(type)
                .and(lookupValue.display.eq(name)))
                .fetchOne();
    }

    public List<LookupValue> findByTypeCodeAndTenantId(final String typeCode, final Long tenantId) {
        return new JPAQuery<LookupValue>(entityManager)
                .from(lookupValue)
                .where(lookupValue.lookupType.typeCode.eq(typeCode)
                        .and(lookupValue.tenantId.eq(tenantId)))
                .orderBy(lookupValue.sortSequence.asc(), lookupValue.value.asc())
                .fetch();
    }

     public List<LookupValue> findByParentAndTenantId(final Long parent, final Long tenantId) {
        return new JPAQuery<LookupValue>(entityManager)
                .from(lookupValue)
                .where(lookupValue.parentId.eq(parent)
                        .and(lookupValue.tenantId.eq(tenantId)))
                .fetch();
    }
}
