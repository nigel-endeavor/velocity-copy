package com.vertek.corporate.qto.contact;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 1/19/2023
 */
@Stateless
public class ContactManager extends StandardManager<Contact> {

    /**
     * Persistence tier for Contacts.
     */
    @Inject
    private ContactJpaDao dao;

    @Inject
    private CompanyManager companyManager;

    @Override
    protected ContactJpaDao getDao() {
        return dao;
    }

    public Contact findByCompanyIdAndType(final Long companyId, final ContactType type) {
        return dao.findByCompanyIdAndType(companyId, type);
    }

    public List<Contact> findCompanyContacts(final Long companyId) {
        return dao.findCompanyContacts(companyId);
    }

    @Override
    public Contact edit(final Contact entity) {
         if ((entity.getType().equals(ContactType.BILLING)
                 || entity.getType().equals(ContactType.TECH)
                 || entity.getType().equals(ContactType.SALES)
                 || entity.getType().equals(ContactType.AUTHORIZATION))
                 && Strings.isNullOrEmpty(entity.getFirstName())
                 && Strings.isNullOrEmpty(entity.getLastName())
                 && Strings.isNullOrEmpty(entity.getPhone())
                 && Strings.isNullOrEmpty(entity.getEmail())) {
             remove(entity.getId());
             return null;
         } else {
             Contact existing = retrieve(entity.getId());
             entity.setTenantId(existing.getTenantId());
             entity.setMasterCustomerId(existing.getMasterCustomerId());
             return super.edit(entity);
         }
    }

    @Override
    public Contact create(final Contact entity) {
        Contact existing = findByCompanyIdAndType(entity.getCompanyId(), entity.getType());
        if (existing != null && (entity.getType().equals(ContactType.BILLING)
                 || entity.getType().equals(ContactType.TECH)
                 || entity.getType().equals(ContactType.SALES)
                 || entity.getType().equals(ContactType.AUTHORIZATION))) {
            existing.setFirstName(entity.getFirstName());
            existing.setLastName(entity.getLastName());
            existing.setPhone(entity.getPhone());
            existing.setEmail(entity.getEmail());
            return edit(existing);
        }
        Company company = companyManager.retrieve(entity.getCompanyId());
        entity.setTenantId(company.getTenantId());
        if ("Master Customer".equals(company.getType())) {
            entity.setMasterCustomerId(company.getId());
        } else {
            entity.setMasterCustomerId(company.getMasterCustomerId());
        }
        Contact newContact = super.create(entity);
        if ("Master Customer".equals(company.getType())) {
            List<Company> endCustomers = companyManager.findByEndCustomerByParentId(company.getId());
            endCustomers.forEach(endCustomer -> {
                if (endCustomer.isDuplicatedMasterCustomerDetails()) {
                    Contact duplicateContact = duplicateContact(newContact);
                    duplicateContact.setCompanyId(endCustomer.getId());
                    duplicateContact.setMasterCustomerId(endCustomer.getMasterCustomerId());
                    super.create(duplicateContact);
                }
            });
        }
        return newContact;
    }

    /**
     * Duplicates a contact and returns it unpersisted. It does NOT duplicate the related address.
     * @param companyId the id of the contact to duplicate.
     * @return the unpersisted duplicated contact.
     */
    public Contact duplicateContact(final Long companyId) {
        return duplicateContact(retrieve(companyId));
    }

    /**
     * Duplicates a contact and returns it unpersisted. It does NOT duplicate the related address.
     * @param contact the contact to duplicate.
     * @return the unpersisted duplicated contact.
     */
    public Contact duplicateContact(final Contact contact) {
        Contact duplicate = new Contact();
        duplicate.setCompanyId(contact.getCompanyId());
        duplicate.setFirstName(contact.getFirstName());
        duplicate.setLastName(contact.getLastName());
        duplicate.setActive(contact.isActive());
        duplicate.setNotes(contact.getNotes());
        duplicate.setType(contact.getType());
        duplicate.setLastUpdateBy(contact.getLastUpdateBy());
        duplicate.setLastUpdateDate(contact.getLastUpdateDate());
        duplicate.setRole(contact.getRole());
        duplicate.setPhone(contact.getPhone());
        duplicate.setEmail(contact.getEmail());
        duplicate.setLegacyId(contact.getLegacyId());
        duplicate.setMasterCustomerId(contact.getMasterCustomerId());
        duplicate.setTenantId(contact.getTenantId());
        return duplicate;
    }

    public Contact duplicateContactOnEdit(ContactType type, Long parentId, Long id) {
       Contact parentContact = findByCompanyIdAndType(parentId, type);
       Contact childContact = findByCompanyIdAndType(id, type);
       if (parentContact != null && childContact != null) {
           childContact.setFirstName(parentContact.getFirstName());
           childContact.setLastName(parentContact.getLastName());
           childContact.setPhone(parentContact.getPhone());
           childContact.setEmail(parentContact.getEmail());
           childContact.setRole(parentContact.getRole());
           childContact.setNotes(parentContact.getNotes());
           childContact = edit(childContact);
       } else if (parentContact == null && childContact != null) {
          remove(childContact.getId());
       } else if (parentContact != null && childContact == null) {
           Contact duplicate = duplicateContact(parentContact);
           duplicate.setCompanyId(id);
           childContact = create(duplicate);
       }
        return childContact;
    }
}
