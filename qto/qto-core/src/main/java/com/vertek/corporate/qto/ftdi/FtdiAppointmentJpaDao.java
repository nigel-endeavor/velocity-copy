package com.vertek.corporate.qto.ftdi;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.ftdi.QFtdiAppointment.ftdiAppointment;

@Stateless
public class FtdiAppointmentJpaDao extends AbstractJpaDao<FtdiAppointment, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Get Vendor Appointments.
     *
     * @param id Schedule ID.
     * @return List of Appointments.
     */
    public List<FtdiAppointment> findVendorAppointments(final Long id) {
        return new JPAQuery<FtdiAppointment>(entityManager)
                .from(ftdiAppointment)
                .where(ftdiAppointment.ftdiDispatchId.eq(id))
                .fetch();
    }

    /**
     * Get Vendor Appointments.
     *
     * @param sr vendor identifier.
     * @param vendor name of vendor.
     * @return List of Appointments.
     */
    public Long findDispatchIdBySrAndVendor(final Long sr, final String vendor) {
        return new JPAQuery<Long>(entityManager)
                .select(ftdiAppointment.ftdiDispatchId)
                .from(ftdiAppointment)
                .where(ftdiAppointment.sr.eq(sr)
                        .and(ftdiAppointment.vendor_name.eq(vendor)))
                .fetchOne();
    }

    /**
     * Get Vendor Appointments.
     *
     * @param sr     vendor identifier.
     * @param vendor name of vendor.
     * @return List of Appointments.
     */
    public FtdiAppointment findBySrAndVendor(final Long sr, final String vendor) {
        return new JPAQuery<FtdiAppointment>(entityManager)
                .from(ftdiAppointment)
                .where(ftdiAppointment.sr.eq(sr)
                        .and(ftdiAppointment.vendor_name.eq(vendor)))
                .fetchOne();
    }



}
