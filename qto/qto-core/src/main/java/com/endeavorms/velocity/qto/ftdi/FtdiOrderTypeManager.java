package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 4/12/2023
 */
@Component
public class FtdiOrderTypeManager extends StandardManager<FtdiOrderType> {

    @Inject
    private FtdiOrderTypeJpaDao dao;

    @Override
    protected FtdiOrderTypeJpaDao getDao() {
        return dao;
    }

    public List<FtdiOrderType> findByVendor(final String vendor) {
        return dao.findByVendor(vendor);
    }

    /**
     * Get single item by Vendor.
     *
     * @param vendor Vendor.
     * @param id     order type ID.
     * @return A single FTDI Order type.
     */
    public FtdiOrderType findByVendorAndId(final String vendor, final String id) {
        return dao.findByVendorAndId(vendor, id);
    }

    /**
     * Get list by Vendor.
     *
     * @param vendor Vendor.
     * @return List of FTDI Order types.
     */
    public List<FtdiOrderType> findActiveByVendor(final String vendor) {
        return dao.findActiveByVendor(vendor);
    }

            /**
     * Get single item by Vendor and Name.
     *
     * @param vendor Vendor.
     * @param name     order type name.
     * @return A single FTDI Order type.
     */
    public FtdiOrderType findByVendorAndName(final String vendor, final String name) {
        return dao.findByVendorAndName(vendor, name);
    }


    public List<String> findSort2(final String sort1) {
        return dao.findSort2(sort1);
    }

    public List<FtdiOrderType> findOrderType(final String sort1, final String sort2) {
        return dao.findOrdertype(sort1, sort2);
    }

    public List<String> findSort1ByVendor(final String vendor) {
        return dao.findSort1ByVendor(vendor);
    }
}
