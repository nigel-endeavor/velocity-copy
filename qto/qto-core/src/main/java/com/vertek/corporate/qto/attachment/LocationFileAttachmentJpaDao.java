package com.vertek.corporate.qto.attachment;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.vertek.corporate.qto.attachment.QLocationFileAttachment.locationFileAttachment;

/**
 * @author rcasey
 * @since 1/24/2023
 */
@Stateless
public class LocationFileAttachmentJpaDao extends AbstractMasterCustomerJpaDao<LocationFileAttachment> {
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
     * Retrieves all LocationFileAttachments with a given location id.
     * @param locationId location id.
     * @return matching LocationFileAttachments.
     */
    public PaginatedResult<LocationFileAttachment> findByLocationId(final Long locationId) {
        BooleanExpression expression = locationFileAttachment.locationId.eq(locationId);
        expression = addMasterCustomerAndTenantFilter(expression, locationFileAttachment.masterCustomerId, locationFileAttachment.tenantId, true);

        return new PaginatedResult<>(new JPAQuery<LocationFileAttachment>(entityManager)
                .from(locationFileAttachment)
                .where(expression)
                .fetchResults()); //todo: assess deprecation, this will require changes to PaginatedResult
    }

    /**
     * Retrieves a LocationFileAttachment by its inventory location or service id.
     * @param existingLocationId parent attachment's location id.
     * @param id attachment parent id.
     * @return matching LocationFileAttachment.
     */
    public LocationFileAttachment findByInventoryRecordId(final Long existingLocationId, final Long id) {
        return new JPAQuery<LocationFileAttachment>(entityManager)
                .from(locationFileAttachment)
                .where(locationFileAttachment.id.eq(id)
                        .and(locationFileAttachment.locationId.eq(existingLocationId)))
                .fetchOne();
    }

    /**
     * Retrieves an inventory LocationFileAttachment by its parent id.
     * @param id parent id.
     * @return matching LocationFileAttachment.
     */
    public LocationFileAttachment findByParentId(final Long id) {
        return new JPAQuery<LocationFileAttachment>(entityManager)
                .from(locationFileAttachment)
                .where(locationFileAttachment.parentFileAttachmentId.eq(id)).fetchOne();
    }
}
