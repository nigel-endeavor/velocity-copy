package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class FtdiShipmentManager extends StandardManager<FtdiShipment> {

    @Inject
    public FtdiShipmentJpaDao dao;

    @Override
    public FtdiShipmentJpaDao getDao() { return dao; }

    /**
     * find shipments for a Schedule dispatch.
     * @param id Schedule ID.
     * @return a list of shipemnts.
     */
    public List<FtdiShipment> findByScheduleId(final Long id, final String trackingNumber) {
        return dao.findByDispatchId(id, trackingNumber);
    }

}
