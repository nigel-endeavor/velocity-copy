package com.vertek.corporate.qto.service.dia;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author fcurran
 * since 1/24/2023
 */
@Stateless
public class DiaServiceManager extends AbstractServiceManager<DiaService> {

    /**
     * Persistence tier for services.
     */
    @Inject
    private DiaServiceJpaDao dao;

    @Override
    protected DiaServiceJpaDao getDao() {
        return dao;
    }

    @Override
    protected void macdServiceMapping(DiaService source, DiaService target, List<String> orderType, List<String> subOrderType) {

        if (!subOrderType.contains("Same Service - Speed change")
                && !subOrderType.contains("Same Service - Rate or term change")) {
            target.setBurstableSpeedCost(source.getBurstableSpeedCost());
        }
        target.setProviderActivationMethod(source.getProviderActivationMethod());
        target.setInterfaceConnector(source.getInterfaceConnector());
        target.setNpaNxx(source.getNpaNxx());
        target.setLastMileProvider(source.getLastMileProvider());
        target.setNewAccessCircuitId(source.getNewAccessCircuitId());
        target.setBurstableSpeed(source.getBurstableSpeed());
        target.setRouterSerialNumber(source.getRouterSerialNumber());
        target.setRouterMacAddress(source.getRouterMacAddress());
        target.setLocationHours(source.getLocationHours());

    }
}
