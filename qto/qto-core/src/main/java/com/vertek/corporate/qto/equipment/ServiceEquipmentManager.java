package com.vertek.corporate.qto.equipment;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Service Equipment Manager.
 * @author fcurran
 * @since 1.15.0
 */
@Stateless
public class ServiceEquipmentManager extends StandardManager<ServiceEquipment> {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEquipmentManager.class);

    /**
     * Persistence tier for ServiceNote.
     */
    @Inject
    private ServiceEquipmentJpaDao dao;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    /**
     * Find all equipment for a service.
     * @param searchCriteria the search criteria
     * @return the list of notes
     */
    public PaginatedResult<ServiceEquipment> findBySearchCriteria(final ServiceEquipmentSearchCriteria searchCriteria) {
        return dao.findBySearchCriteria(searchCriteria);
    }

    @Override
    protected ServiceEquipmentJpaDao getDao() {
        return dao;
    }

    @Override
    public ServiceEquipment create(final ServiceEquipment entity) {
        entity.setTenantId(entity.getTenantId() == null
                ? tenantSubjectManager.getCurrentTenant().getId()
                : entity.getTenantId());
        return super.create(entity);
    }

    @Override
    public ServiceEquipment edit(final ServiceEquipment entity) {
        ServiceEquipment existing = retrieve(entity.getId());
        entity.setTenantId(existing.getTenantId());
        entity.setMasterCustomerId(existing.getMasterCustomerId());
        if (entity.isDecommission() && entity.getDecommissionedDate() == null) {
            entity.setDecommissionedDate(new Date());
        } else if (!entity.isDecommission() && entity.getDecommissionedDate() != null) {
            entity.setDecommissionedDate(null);
        }
        return super.edit(entity);
    }

    public List<ServiceEquipment> findByServiceId(final Long id) {
        return dao.findByServiceId(id);
    }
}
