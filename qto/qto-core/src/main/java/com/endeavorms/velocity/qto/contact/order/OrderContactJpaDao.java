package com.endeavorms.velocity.qto.contact.order;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.contact.order.QOrderContact.orderContact;

/**
 * @author fcurran
 * @since 1/26/2023
 */
@Component
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
