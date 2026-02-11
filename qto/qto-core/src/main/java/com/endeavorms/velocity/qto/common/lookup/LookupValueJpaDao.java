package com.endeavorms.velocity.qto.common.lookup;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.endeavorms.velocity.qto.common.lookup.QLookupValue.lookupValue;

@Component
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
