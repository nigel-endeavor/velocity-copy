package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class FtdiAppointmentManager extends StandardManager<FtdiAppointment> {

      @Inject
    public FtdiAppointmentJpaDao dao;

    @Override
    public FtdiAppointmentJpaDao getDao() { return dao; }

    /**
     * Get Vendor Appointments.
     * @param id Schedule ID.
     * @return List of Appointments.
     */
    public List<FtdiAppointment> findVendorAppointments(final Long id)    {
        return dao.findVendorAppointments(id);
    }

    /**
     * Get Vendor Appointments.
     *
     * @param sr vendor identifier.
     * @param vendor name of vendor.
     * @return List of Appointments.
     */
    public Long findDispatchIdBySrAndVendor(final Long sr, final String vendor) {
        return dao.findDispatchIdBySrAndVendor(sr, vendor);
    }


       /**
     * Get Vendor Appointments.
     *
     * @param sr vendor identifier.
     * @param vendor name of vendor.
     * @return List of Appointments.
     */
    public FtdiAppointment findBySrAndVendor(final Long sr, final String vendor) {
        return dao.findBySrAndVendor(sr, vendor);
    }

}
