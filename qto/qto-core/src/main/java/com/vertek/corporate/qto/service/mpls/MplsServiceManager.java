package com.vertek.corporate.qto.service.mpls;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 5/17/2024
 */
@Stateless
public class MplsServiceManager extends AbstractServiceManager<MplsService> {

    @Inject
    private MplsServiceJpaDao mplsServiceJpaDao;

    @Override
    protected MplsServiceJpaDao getDao() {
        return mplsServiceJpaDao;
    }

    @Override
    protected void macdServiceMapping(MplsService source, MplsService target, List<String> orderType, List<String> subOrderType) {
        target.setLastMileProvider(source.getLastMileProvider());
        target.setAccessCircuitId(source.getAccessCircuitId());
        target.setPortCircuitId(source.getPortCircuitId());
        target.setMplsType(source.getMplsType());
        target.setPortSpeed(source.getPortSpeed());
        target.setInterfaceConnector(source.getInterfaceConnector());
        target.setProviderActivationMethod(source.getProviderActivationMethod());
        target.setNpaNxx(source.getNpaNxx());
        target.setRoutingProtocol(source.getRoutingProtocol());
        target.setCerIps(source.getCerIps());
        target.setPerIps(source.getPerIps());
        target.setVlanTag1(source.getVlanTag1());
        target.setVlanTag2(source.getVlanTag2());
        target.setVlanTag3(source.getVlanTag3());
        target.setVlanTag4(source.getVlanTag4());
        target.setOtherTechnicalNotes(source.getOtherTechnicalNotes());
    }

}
