package com.vertek.corporate.qto.service.broadband;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 2/10/2023
 */
@Stateless
public class BroadbandServiceManager extends AbstractServiceManager<BroadbandService> {

    /**
     * Persistence tier.
     */
    @Inject
    private BroadbandServiceJpaDao dao;

    @Override
    protected BroadbandServiceJpaDao getDao() {
        return dao;
    }

    /**
     * Returns a Broadband Service.
     *
     * @param serviceId Service ID.
     * @param tenantId  Tenant ID.
     * @return Activation Scheule Object.
     */
    public BroadbandService findByIdAndTenant(final Long serviceId, final Long tenantId) {
        return dao.findByIdAndTenant(serviceId, tenantId);
    }

    @Override
    protected void macdServiceMapping(BroadbandService source, BroadbandService target,
                                      List<String> orderType, List<String> subOrderType) {

        if (!subOrderType.contains("Static IP Add")) {
            target.setNetworkProtocol("Static");
        }
        target.setModemMake(source.getModemMake());
        target.setMacAddress(source.getMacAddress());
        target.setCustomerPremEquipment(source.getCustomerPremEquipment());
        target.setPppoeUsername(source.getPppoeUsername());
        target.setPppoePassword(source.getPppoePassword());
        target.setModemSerialNumber(source.getModemSerialNumber());
    }
}
