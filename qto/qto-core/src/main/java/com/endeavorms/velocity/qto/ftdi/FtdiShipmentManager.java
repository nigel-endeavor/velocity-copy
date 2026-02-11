package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
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
