package com.vertek.corporate.qto.attachment;

import com.vertek.corporate.qto.common.AbstractFileAttachmentManager;
import com.vertek.corporate.qto.common.PaginatedResult;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 1/24/2023
 */
@Stateless
public class LocationFileAttachmentManager extends AbstractFileAttachmentManager<LocationFileAttachment> {

    /**
     * Persistence tier for LocationFileAttachments.
     */
    @Inject
    private LocationFileAttachmentJpaDao dao;

    @Override
    protected LocationFileAttachmentJpaDao getDao() {
        return dao;
    }

    /**
     * Retrieves all LocationFileAttachments with a given location id.
     * @param locationId location id.
     * @return matching LocationFileAttachments.
     */
    public PaginatedResult<LocationFileAttachment> findByLocationId(final Long locationId) {
        return getDao().findByLocationId(locationId);
    }

    public LocationFileAttachment findByInventoryRecordId(final Long existingLocationId, final Long id) {
        return getDao().findByInventoryRecordId(existingLocationId, id);
    }

    public LocationFileAttachment findByParentId(final Long id) {
        return getDao().findByParentId(id);
    }
}
