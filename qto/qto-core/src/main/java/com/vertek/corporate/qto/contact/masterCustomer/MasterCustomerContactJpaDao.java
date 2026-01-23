package com.vertek.corporate.qto.contact.masterCustomer;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.contact.location.LocationContact;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.contact.masterCustomer.QMasterCustomerContact.masterCustomerContact;

@Stateless
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
