package com.endeavorms.velocity.qto.contact.masterCustomer;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.contact.location.LocationContact;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.endeavorms.velocity.qto.contact.masterCustomer.QMasterCustomerContact.masterCustomerContact;

@Component
public class MasterCustomerContactJpaDao extends AbstractJpaDao<MasterCustomerContact, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Returns master customer contacts.
     * @param masterCustomerContactId master customer id.
     * @return the matching contacts, if any.
     */
    public List<MasterCustomerContact> getByMasterCustomerContactId(final Long masterCustomerContactId) {
            return new JPAQuery<MasterCustomerContact>(entityManager).from(masterCustomerContact)
                .where(masterCustomerContact.masterCustomerContactId.eq(masterCustomerContactId))
                .fetch();
    }



}
