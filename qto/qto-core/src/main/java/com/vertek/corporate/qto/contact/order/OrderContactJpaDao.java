package com.vertek.corporate.qto.contact.order;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.contact.order.QOrderContact.orderContact;

/**
 * @author fcurran
 * @since 1/26/2023
 */
@Stateless
public class OrderContactJpaDao extends AbstractMasterCustomerJpaDao<OrderContact> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }

    /**
     * Finds contacts by a given contact ID and email.
     * @param companyId the ID of an existing company.
     * @param email the email of the contact to filter by.
     * @return all matching contacts.
     */
    public List<OrderContact> getByCompanyAndEmail(final Long companyId, final String email) {
        return new JPAQuery<OrderContact>(entityManager).from(orderContact)
                .where(orderContact.companyId.eq(companyId)
                        .and(orderContact.email.eq(email))).fetch();
    }

    /**
     * Finds contacts by a given order ID.
     * @param orderId the ID of an existing order.
     * @return all matching contacts.
     */
    public List<OrderContact> findByOrderId(final Long orderId) {
        return new JPAQuery<OrderContact>(entityManager).from(orderContact)
                .where(orderContact.orderId.eq(orderId)).fetch();
    }

}
