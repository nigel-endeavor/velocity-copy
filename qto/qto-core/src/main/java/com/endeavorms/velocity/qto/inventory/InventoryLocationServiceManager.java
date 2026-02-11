package com.endeavorms.velocity.qto.inventory;

import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.service.ServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Deprecated
@Component
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
