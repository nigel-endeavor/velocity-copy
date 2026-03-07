package com.vertek.corporate.qto.inventory;

import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.service.ServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Deprecated
@Stateless
public class InventoryLocationServiceManager extends StandardManager<InventoryLocationService> {


    @Inject
    private InventoryLocationServiceJpaDao dao;

    /** Business logic for InventoryLocationServiceManager */
    @Inject
    private LocationManager locationManager;

    @Inject
    private ServiceManager serviceManager;

    @Override
    public InventoryLocationServiceJpaDao getDao() {
        return dao;
    }



    public List<InventoryLocationService> findByLocationId(final Long locationId) {
        return dao.findByLocationId(locationId);
    }

    public List<InventoryLocationService> getActiveInventory(final Long tenantId) {
        return dao.getActiveInventory(tenantId);
    }

}
