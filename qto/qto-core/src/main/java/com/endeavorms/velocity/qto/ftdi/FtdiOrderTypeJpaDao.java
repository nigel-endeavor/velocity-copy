package com.endeavorms.velocity.qto.ftdi;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.ftdi.QFtdiOrderType.ftdiOrderType;

/**
 * @author rcasey
 * @since 4/12/2023
 */
@Component
public class FtdiOrderTypeJpaDao extends AbstractJpaDao<FtdiOrderType, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Get list by Vendor.
     *
     * @param vendor Vendor.
     * @return List of FTDI Order types.
     */
    public List<FtdiOrderType> findByVendor(final String vendor) {
        return new JPAQuery<FtdiOrderType>(entityManager)
                .from(ftdiOrderType)
                .where(ftdiOrderType.vendorName.eq(vendor))
                .fetch();
    }

    /**
     * Get single item by Vendor.
     *
     * @param vendor Vendor.
     * @param id     order type ID.
     * @return A single FTDI Order type.
     */
    public FtdiOrderType findByVendorAndId(final String vendor, final String id) {
        return new JPAQuery<FtdiOrderType>(entityManager)
                .from(ftdiOrderType)
                .where(ftdiOrderType.vendorName.eq(vendor)
                        .and(ftdiOrderType.vendorOrderTypeId.eq(id)))
                .fetchOne();
    }

    /**
     * Get single item by Vendor and Name.
     *
     * @param vendor Vendor.
     * @param name   order type name.
     * @return A single FTDI Order type.
     */
    public FtdiOrderType findByVendorAndName(final String vendor, final String name) {
        return new JPAQuery<FtdiOrderType>(entityManager)
                .from(ftdiOrderType)
                .where(ftdiOrderType.vendorName.eq(vendor)
                        .and(ftdiOrderType.orderType.eq(name)))
                .fetchOne();
    }

    /**
     * Get list by Vendor.
     *
     * @param vendor Vendor.
     * @return List of FTDI Order types.
     */
    public List<FtdiOrderType> findActiveByVendor(final String vendor) {
        return new JPAQuery<FtdiOrderType>(entityManager)
                .from(ftdiOrderType)
                .where(ftdiOrderType.vendorName.eq(vendor)
                        .and(ftdiOrderType.active.eq(true)))
                .orderBy(new OrderSpecifier(Order.ASC, ftdiOrderType.orderType))
                .fetch();
    }

    public List<String> findSort1ByVendor(final String vendor) {
        return new JPAQuery<FtdiOrderType>(entityManager)
                .select(ftdiOrderType.sort1)
                .from(ftdiOrderType)
                .where(ftdiOrderType.vendorName.eq(vendor)
                        .and(ftdiOrderType.active.eq(true)))
                .distinct()
                .orderBy(new OrderSpecifier(Order.ASC, ftdiOrderType.sort1))
                .fetch();
    }

    public List<String> findSort2(final String sort1) {
        return new JPAQuery<FtdiOrderType>(entityManager)
                .select(ftdiOrderType.sort2)
                .from(ftdiOrderType)
                .where(ftdiOrderType.sort1.eq(sort1)
                        .and(ftdiOrderType.active.eq(true)))
                .distinct()
                .orderBy(new OrderSpecifier(Order.ASC, ftdiOrderType.sort2))
                .fetch();
    }

    public List<FtdiOrderType> findOrdertype(final String sort1, final String sort2) {
        return new JPAQuery<FtdiOrderType>(entityManager)
                .from(ftdiOrderType)
                .where(ftdiOrderType.sort1.eq(sort1)
                        .and(ftdiOrderType.sort2.eq(sort2))
                        .and(ftdiOrderType.active.eq(true)))
                .orderBy(new OrderSpecifier(Order.ASC, ftdiOrderType.orderType))
                .fetch();
    }
}
