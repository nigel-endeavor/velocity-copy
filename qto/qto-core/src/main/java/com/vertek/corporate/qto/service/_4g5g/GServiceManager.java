package com.vertek.corporate.qto.service._4g5g;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 6/7/2023
 */
@Stateless
public class GServiceManager extends AbstractServiceManager<GService> {

    /**
     * Persistence tier.
     */
    @Inject
    private GServiceJpaDao dao;

    @Override
    protected GServiceJpaDao getDao() {
        return dao;
    }



    /**
     * Returns a list of Service.
     *
     * @param locationId Location ID.
     * @return List if services.
     */
    public List<GService> findByLocationId(final Long locationId) {
        return dao.findByLocationId(locationId);
    }

    @Override
    protected void macdServiceMapping(GService source, GService target, List<String> orderType, List<String> subOrderType) {
        target.setUid(source.getUid());
        target.setIccid(source.getIccid());
        target.setImei(source.getImei());
        target.setMdn(source.getMdn());
        target.setApn(source.getApn());
        target.setRatePlan(source.getRatePlan());
        target.setRsrp(source.getRsrp());
        target.setRsrq(source.getRsrq());
        target.setRssi(source.getRssi());
        target.setReplace4g5g(source.getReplace4g5g());
        target.setMacAddress(source.getMacAddress());
        target.setSerialNumber(source.getSerialNumber());
        target.setCircuitPriority(source.getCircuitPriority());
    }
}
