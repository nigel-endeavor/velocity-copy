package com.endeavorms.velocity.qto.contact.masterCustomer;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
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
