package com.vertek.corporate.qto.invoicing.invoice;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.invocing.jms.InvoiceChargeMessage;
import com.vertek.corporate.qto.invocing.jms.InvoiceChargeQueueHandler;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author mwelicka
 * @since 7/30/2023
 */
@Stateless
public class InvoiceManager extends StandardManager<Invoice> {

    @Inject
    private InvoiceJpaDao dao;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private InvoiceChargeQueueHandler invoiceChargeQueueHandler;

    @Override
    protected InvoiceJpaDao getDao() {
        return dao;
    }


    /**
     * Returns Invoices that match the provided search criteria.
     *
     * @param criteria what to match on.
     * @return the matching entities, if any.
     */
    public PaginatedResult<Invoice> findBySearchCriteria(final InvoiceSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }


    /**
     * Find all invoices for a given Invoice Number and Tenant ID.
     *
     * @param invNum Invoice Number.
     * @return List of Invoices.
     */
    public List<Invoice> findByInvoiceNumberAndTenantId(final String invNum, final Long tenantId) {
        return dao.findByInvoiceNumberAndTenantId(invNum, tenantId);
    }


    public Invoice createInvoice(Invoice invoice, String tenantName) {
        Long tenantId;
        if (!Strings.isNullOrEmpty(tenantName)) {
            Company tenant = companyManager.findTenantByName(tenantName);
            tenantId = tenant.getTenantId();
        } else {
            tenantId = tenantSubjectManager.getCurrentTenant().getId();
        }
        invoice.setTenantId(tenantId);
        invoice.setInvoiceStatus("Draft");
        Invoice createdInvoice = super.create(invoice);
        sendMessageToChargeQueue(createdInvoice.getId(), null);
        return createdInvoice;
    }

    public void sendMessageToChargeQueue(final Long id, final String messageType) {
        Subject subject = subjectManager.findByUsername(SecurityUtils.getLoggedInUser());
        InvoiceChargeMessage message = new InvoiceChargeMessage(id, subject.getId(), messageType);
        invoiceChargeQueueHandler.sendMessageToQueue(message);
    }

    public Invoice finalizeInvoice(final Invoice invoice) {
        //set all the milestone instances that are -1 to 0
        //set the invoice status to finalized
        return null;
    }

    /**
     * Find all Draft invoices for a Tenant ID.
     *
     * @param tenantId Invoice Number.
     * @return List of Invoices.
     */
    public List<Invoice> findDraftByTenantId(final Long tenantId) {
        return dao.findDraftByTenantId(tenantId);
    }

        /**
     * Find all Draft invoices for a Tenant ID.
     *
     * @param invoiceId Invoice Number.
     * @param tenantId  Invoice Number.
     * @return List of Invoices.
     */
    public List<Invoice> findForUnfinalizeByTenantId(final Long invoiceId, final Long tenantId) {
        return dao.findForUnfinalizeByTenantId(invoiceId, tenantId);
    }
}
