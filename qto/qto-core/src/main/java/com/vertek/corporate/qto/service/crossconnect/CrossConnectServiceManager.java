package com.vertek.corporate.qto.service.crossconnect;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Business logic tier for Cross Connect service.
 * @author fcurran
 * @since 1/12/2024
 */
@Stateless
public class CrossConnectServiceManager extends AbstractServiceManager<CrossConnectService> {

    /**
     * Persistence tier.
     */
    @Inject
    private CrossConnectServiceJpaDao dao;

    @Override
    protected CrossConnectServiceJpaDao getDao() {
        return dao;
    }

    @Override
    protected void macdServiceMapping(CrossConnectService source, CrossConnectService target,
                                      List<String> orderType, List<String> subOrderType) {
        target.setCrossConnectId(source.getCrossConnectId());
        target.setCrossConnectRoom(source.getCrossConnectRoom());
        target.setCrossConnectRack(source.getCrossConnectRack());
        target.setCrossConnectPort(source.getCrossConnectPort());
        target.setCrossConnectType(source.getCrossConnectType());
        target.setCrossConnectDataCenterName(source.getCrossConnectDataCenterName());
    }
}
