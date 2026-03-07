package com.vertek.corporate.qto.contact.masterCustomer;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class MasterCustomerContactManager extends StandardManager<MasterCustomerContact> {

    @Inject
    private MasterCustomerContactJpaDao dao;

    @Override
    protected MasterCustomerContactJpaDao getDao() {
        return dao;
    }


    /**
     * Returns master customer contacts.
     * @param masterCustomerContactId master customer id.
     * @return the matching contacts, if any.
     */
    public List<MasterCustomerContact> getByMasterCustomerContactId(final Long masterCustomerContactId) {
        return dao.getByMasterCustomerContactId(masterCustomerContactId);
    }
}
