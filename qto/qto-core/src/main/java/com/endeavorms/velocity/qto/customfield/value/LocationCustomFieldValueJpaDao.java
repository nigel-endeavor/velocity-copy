package com.endeavorms.velocity.qto.customfield.value;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.customfield.value.QLocationCustomFieldValue.locationCustomFieldValue;

@Component
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
