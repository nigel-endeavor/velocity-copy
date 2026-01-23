package com.vertek.corporate.qto.customfield.value;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.customfield.value.QLocationCustomFieldValue.locationCustomFieldValue;

@Stateless
public class LocationCustomFieldValueJpaDao extends AbstractCustomFieldValueJpaDao<LocationCustomFieldValue> {

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

    @Override
    public List<LocationCustomFieldValue> findByRecordId(final Long recordId) {
        return new JPAQuery<LocationCustomFieldValue>(entityManager)
                .from(locationCustomFieldValue)
                .where(locationCustomFieldValue.locationId.eq(recordId))
                .fetch();
    }

}
