package com.vertek.corporate.qto.attachment;

import com.vertek.corporate.qto.common.AbstractFileAttachmentManager;
import com.vertek.corporate.qto.common.PaginatedResult;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 1/24/2023
 */
@Stateless
public class ServiceFileAttachmentManager extends AbstractFileAttachmentManager<ServiceFileAttachment> {

    /**
     * Persistence tier for ServiceFileAttachments.
     */
    @Inject
    private ServiceFileAttachmentJpaDao dao;

    @Override
    protected ServiceFileAttachmentJpaDao getDao() {
        return dao;
    }

    /**
     * Retrieves all ServiceFileAttachments with a given service id.
     * @param serviceId service id.
     * @return matching ServiceFileAttachments.
     */
    public PaginatedResult<ServiceFileAttachment> findByServiceIdPaginated(final Long serviceId) {
        return getDao().findByServiceIdPaginated(serviceId);
    }

    /**
     * Retrieves all ServiceFileAttachments with a given service id.
     * @param serviceId service id.
     * @return matching ServiceFileAttachments.
     */
    public List<ServiceFileAttachment> findByServiceId(final Long serviceId) {
        return getDao().findByServiceId(serviceId);
    }

    /**
     * Retrieves all ServiceFileAttachments for services associated with a given location id.
     * @param locationId location id.
     * @return matching ServiceFileAttachments.
     */
    public PaginatedResult<ServiceFileAttachment> findByLocationId(final Long locationId) {
        return getDao().findByLocationId(locationId);
    }

    public ServiceFileAttachment findByInventoryRecordId(Long parentOwnerId, Long parentFileAttachmentId) {
        return getDao().findByInventoryRecordId(parentOwnerId, parentFileAttachmentId);
    }

    public ServiceFileAttachment findByParentId(Long id) {
        return getDao().findByParentId(id);
    }
}
