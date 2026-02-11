package com.endeavorms.velocity.qto.contact.order;

import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author fcurran
 * @since 1/26/2023
 */
@Component
public class OrderContactManager extends StandardManager<OrderContact> {

    /**
     * Persistence tier for Contacts.
     */
    @Inject
    private OrderContactJpaDao dao;

    /** Business logic tier for companies. */
    @Inject
    private CompanyManager companyManager;

    @Override
    protected OrderContactJpaDao getDao() {
        return dao;
    }

    @Override
    public OrderContact create(final OrderContact orderContact) {
        if (orderContact.getTenantId() == null) {
            Company parentCompany = companyManager.retrieve(orderContact.getCompanyId());
            orderContact.setTenantId(parentCompany.getTenantId());
            orderContact.setMasterCustomerId(parentCompany.getMasterCustomerId());
        }
        return super.create(orderContact);
    }
    @Override
    public OrderContact edit(final OrderContact orderContact) {
        if (orderContact.getTenantId() == null) {
            Company parentCompany = companyManager.retrieve(orderContact.getCompanyId());
            orderContact.setTenantId(parentCompany.getTenantId());
            orderContact.setMasterCustomerId(parentCompany.getMasterCustomerId());
        }
        return super.edit(orderContact);
    }

    /**
     * Finds contacts by a given contact ID and email.
     * @param companyId the ID of an existing company.
     * @param email the email of the contact to filter by.
     * @return all matching contacts.
     */
    public List<OrderContact> getByCompanyAndEmail(final Long companyId, final String email) {
        return dao.getByCompanyAndEmail(companyId, email);
    }

        /**
     * Finds contacts by a given order ID.
     * @param orderId the ID of an existing order.
     * @return all matching contacts.
     */
    public List<OrderContact> findByOrderId(final Long orderId) {
        return dao.findByOrderId(orderId);
    }
}
