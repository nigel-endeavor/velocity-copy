package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class FtdiDispatchManager extends StandardManager<FtdiDispatch> {

          @Inject
    public FtdiDispatchJpaDao dao;

    @Override
    public FtdiDispatchJpaDao getDao() { return dao; }


    /**
     * Returns an Activation Schedule.
     *
     * @param vendorId Dispatch Vendor ID.
     * @param tenantId   Tenaan ID.
     * @return Activation Schedule Object.
     */
    public List<FtdiDispatch> findByVendorIdAndTenant(final String vendorId, final Long tenantId) {

        return dao.findByVendorIdAndTenant(vendorId, tenantId);
    }

    /**
     * Returns an Activation Schedule.
     *
     * @param vendorId Dispatch Vendor ID.
     * @param tenantId Tenaan ID.
     * @return Activation Schedule Object.
     */
    public FtdiDispatch findByTopDispatchByVendorIdAndTenant(final String vendorId, final Long tenantId) {
        return dao.findByTopDispatchByVendorIdAndTenant(vendorId, tenantId);
    }

            /**
     * Returns an Activation Schedule.
     *
     * @param scheduleId Solution ID.
     * @param tenantId   Tenant ID.
     * @return Activation Schedule Object.
     */
    public List<FtdiDispatch> findByScheduleIdAndTenant(final Long scheduleId, final Long tenantId) {
        return dao.findByScheduleIdAndTenant(scheduleId, tenantId);
    }



      /**
     * Returns an Activation Schedule.
     *
     * @param id Dispatch ID.
     * @param tenantId   Tenant ID.
     * @return Activation Schedule Object.
     */
    public FtdiDispatch findByIdAndTenant(final Long id, final Long tenantId) {
        return dao.findByIdAndTenant(id, tenantId);
    }

         /**
     * Returns an Activation Schedule.
     *
     * @param id Dispatch ID.
     * @return Activation Schedule Object.
     */
    public FtdiDispatch findByLegacyId(final Long id) {
        return dao.findByLegacyId(id);
    }

        /**
     * Returns dispatches for the Mass Update job
     * @param tenantId
     * @return
     */
    public List<String> findOpenDispatchesForMassUpdate(final Long tenantId) {
        return dao.findOpenDispatchesForMassUpdate(tenantId);
    }

    /**
     * Returns list of existing Vendor IDs for the Mass Update job
     * @param tenantId
     * @return
     */
    public List<String> findAllCtns(final Long tenantId) {
        return dao.findAllCtns(tenantId);
    }
}
