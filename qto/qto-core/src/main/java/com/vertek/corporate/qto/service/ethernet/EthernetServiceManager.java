package com.vertek.corporate.qto.service.ethernet;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Business logic tier for Ethernet service.
 * @author rcasey
 * @since 1/17/2024
 */
@Stateless
public class EthernetServiceManager extends AbstractServiceManager<EthernetService> {

    /**
     * Persistence tier.
     */
    @Inject
    private EthernetServiceJpaDao dao;

    @Override
    protected EthernetServiceJpaDao getDao() {
        return dao;
    }

    @Override
    protected void macdServiceMapping(EthernetService source, EthernetService target,
                                      List<String> orderType, List<String> subOrderType) {
        target.setProductType(source.getProductType());
        target.setPortSpeed(source.getPortSpeed());
        target.setEaSpeed(source.getEaSpeed());
        target.setMtu(source.getMtu());
        target.setTrunkGroup(source.getTrunkGroup());
        target.setMux(source.getMux());
        target.setVlanForEline(source.getVlanForEline());
        target.setVlanTagging(source.getVlanTagging());
        target.setVlanId(source.getVlanId());
        target.setCableCategory(source.getCableCategory());
        target.setCableShielding(source.getCableShielding());
        target.setDataCenterName(source.getDataCenterName());
        target.setProviderCircuitId(source.getProviderCircuitId());
        target.setAccessType(source.getAccessType());
        target.setAccessHours(source.getAccessHours());
        target.setManned(source.getManned());
        target.setLoaRequired(source.getLoaRequired());
        target.setHandoffFiberMode(source.getHandoffFiberMode());
        target.setClliCode(source.getClliCode());
        target.setPopClli(source.getPopClli());
        target.setAlternatePopClli(source.getAlternatePopClli());
        target.setFloor(source.getFloor());
        target.setCfa(source.getCfa());
        target.setzAddress1(source.getzAddress1());
        target.setzAddress2(source.getzAddress2());
        target.setzCity(source.getzCity());
        target.setzState(source.getzState());
        target.setzPostalCode(source.getzPostalCode());
        target.setzCountry(source.getzCountry());
        target.setzDataCenterName(source.getzDataCenterName());
        target.setzProviderCircuitId(source.getzProviderCircuitId());
        target.setzBuildingStatus(source.getzBuildingStatus());
        target.setzAccessType(source.getzAccessType());
        target.setzInterfaceConnector(source.getzInterfaceConnector());
        target.setzAccessHours(source.getzAccessHours());
        target.setzManned(source.getzManned());
        target.setzLoaRequired(source.getzLoaRequired());
        target.setzInsideWiringRequired(source.getzInsideWiringRequired());
        target.setzHandoffMediaType(source.getzHandoffMediaType());
        target.setzHandoffFiberMode(source.getzHandoffFiberMode());
        target.setzHandoffConnectorType(source.getzHandoffConnectorType());
        target.setzClliCode(source.getzClliCode());
        target.setzPopClli(source.getzPopClli());
        target.setzAlternatePopClli(source.getzAlternatePopClli());
        target.setzFloor(source.getzFloor());
        target.setzNpaNxx(source.getzNpaNxx());
        target.setzDmarc(source.getzDmarc());
        target.setzCfa(source.getzCfa());
    }
}
