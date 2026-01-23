package com.vertek.corporate.qto.fileimport.customer;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.adapter.ExcelAdapter;
import com.vertek.corporate.qto.common.mapping.StringSourceMapper;
import com.vertek.corporate.qto.common.mapping.ValidatingSourceMapper;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.contact.Contact;
import com.vertek.corporate.qto.contact.ContactManager;
import com.vertek.corporate.qto.contact.ContactType;
import com.vertek.corporate.qto.fileimport.AbstractImporter;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCompanyImporter extends AbstractImporter {

    @Inject
    private CompanyManager companyManager;

    @Inject
    private ContactManager contactManager;

    protected void createCompany(final Map<String, ValidatingSourceMapper> sourceMappers, final String nameColumn,
                                 final Long tenantId, final String type, final Company parentCompany) throws Exception {
        //create company
        Company company = new Company();
        company.setType(type);
        company.setParentCompany(parentCompany);
        company.setName(sourceMappers.get(nameColumn).getValue());
        company.setClientId(sourceMappers.get(CustomerImportColumns.CLIENT_ID).getValue());
        company.setAccountNotes(sourceMappers.get(CustomerImportColumns.PERMANENT_ACCOUNT_NOTES).getValue());
        company.setTenantId(tenantId);
        company.setActive(true);
        company.setAddress1(sourceMappers.get(CustomerImportColumns.BILLING_ADDRESS_1).getValue());
        company.setAddress2(sourceMappers.get(CustomerImportColumns.BILLING_ADDRESS_2).getValue());
        company.setCity(sourceMappers.get(CustomerImportColumns.BILLING_CITY).getValue());
        company.setState(sourceMappers.get(CustomerImportColumns.BILLING_STATE).getValue());
        company.setPostalCode(sourceMappers.get(CustomerImportColumns.BILLING_POSTAL_CODE).getValue());
        company.setCountry(sourceMappers.get(CustomerImportColumns.BILLING_COUNTRY).getValue());
        Company persistedCompany = companyManager.create(company);

        //create billing contact
        getContact(sourceMappers, persistedCompany, CustomerImportColumns.BILLING_CONTACT_NAME,
                CustomerImportColumns.BILLING_CONTACT_EMAIL, CustomerImportColumns.BILLING_CONTACT_PHONE,
                ContactType.BILLING);
        //create technical contact
        getContact(sourceMappers, persistedCompany, CustomerImportColumns.TECHNICAL_CONTACT_NAME,
                CustomerImportColumns.TECHNICAL_CONTACT_EMAIL, CustomerImportColumns.TECHNICAL_CONTACT_PHONE,
                ContactType.TECH);
        //create sales contact
        getContact(sourceMappers, persistedCompany, CustomerImportColumns.SALES_CONTACT_NAME,
                CustomerImportColumns.SALES_CONTACT_EMAIL, CustomerImportColumns.SALES_CONTACT_PHONE,
                ContactType.SALES);
    }

    /**
     * Create a contact for the given company.
     * @param sourceMappers the source mappers for the row.
     * @param company the company to create the contact for.
     * @param nameColumn the column containing the contact name.
     * @param emailColumn the column containing the contact email.
     * @param phoneColumn the column containing the contact phone.
     * @param contactType the type of contact to create.
     * @return the created contact.
     * @throws Exception if an error occurs.
     */
    protected Contact getContact(final Map<String, ValidatingSourceMapper> sourceMappers, final Company company,
                                 final String nameColumn, final String emailColumn, final String phoneColumn,
                                 final ContactType contactType) throws Exception {
        Contact contact = new Contact();
        contact.setType(contactType);
        String nameValue = sourceMappers.get(nameColumn).getValue();
        if (!Strings.isNullOrEmpty(nameValue)) {
            String[] name = sourceMappers.get(nameColumn).getValue().split(" ", 2);
            contact.setFirstName(name[0]);
            if (name.length > 1) {
                contact.setLastName(name[1]);
            }
        }
        contact.setEmail(sourceMappers.get(emailColumn).getValue());
        contact.setPhone(sourceMappers.get(phoneColumn).getValue());
        contact.setCompanyId(company.getId());
        contact = contactManager.create(contact);
        return contact;
    }

    public CompanyManager getCompanyManager() {
        return companyManager;
    }

    public ContactManager getContactManager() {
        return contactManager;
    }

    @Override
    protected Map<String, ValidatingSourceMapper> buildSourceMappers(final ExcelAdapter adapter) {
        HashMap<String, ValidatingSourceMapper> sourceMappers = new HashMap<>();
        sourceMappers.put(CustomerImportColumns.MASTER_CUSTOMER_NAME, new StringSourceMapper(adapter, CustomerImportColumns.MASTER_CUSTOMER_NAME, 100, true));
        sourceMappers.put(CustomerImportColumns.CLIENT_ID, new StringSourceMapper(adapter, CustomerImportColumns.CLIENT_ID, 100, false));
        sourceMappers.put(CustomerImportColumns.BILLING_ADDRESS_1, new StringSourceMapper(adapter, CustomerImportColumns.BILLING_ADDRESS_1, 100, false));
        sourceMappers.put(CustomerImportColumns.BILLING_ADDRESS_2, new StringSourceMapper(adapter, CustomerImportColumns.BILLING_ADDRESS_2, 100, false));
        sourceMappers.put(CustomerImportColumns.BILLING_CITY, new StringSourceMapper(adapter, CustomerImportColumns.BILLING_CITY, 100, false));
        sourceMappers.put(CustomerImportColumns.BILLING_STATE, new StringSourceMapper(adapter, CustomerImportColumns.BILLING_STATE, 100, false));
        sourceMappers.put(CustomerImportColumns.BILLING_POSTAL_CODE, new StringSourceMapper(adapter, CustomerImportColumns.BILLING_POSTAL_CODE, 20, false));
        sourceMappers.put(CustomerImportColumns.BILLING_COUNTRY, new StringSourceMapper(adapter, CustomerImportColumns.BILLING_COUNTRY, 100, false));
        sourceMappers.put(CustomerImportColumns.BILLING_CONTACT_NAME, new StringSourceMapper(adapter, CustomerImportColumns.BILLING_CONTACT_NAME, 100, false));
        sourceMappers.put(CustomerImportColumns.BILLING_CONTACT_EMAIL, new StringSourceMapper(adapter, CustomerImportColumns.BILLING_CONTACT_EMAIL, 100, false));
        sourceMappers.put(CustomerImportColumns.BILLING_CONTACT_PHONE, new StringSourceMapper(adapter, CustomerImportColumns.BILLING_CONTACT_PHONE, 100, false));
        sourceMappers.put(CustomerImportColumns.TECHNICAL_CONTACT_NAME, new StringSourceMapper(adapter, CustomerImportColumns.TECHNICAL_CONTACT_NAME, 100, false));
        sourceMappers.put(CustomerImportColumns.TECHNICAL_CONTACT_EMAIL, new StringSourceMapper(adapter, CustomerImportColumns.TECHNICAL_CONTACT_EMAIL, 100, false));
        sourceMappers.put(CustomerImportColumns.TECHNICAL_CONTACT_PHONE, new StringSourceMapper(adapter, CustomerImportColumns.TECHNICAL_CONTACT_PHONE, 100, false));
        sourceMappers.put(CustomerImportColumns.SALES_CONTACT_NAME, new StringSourceMapper(adapter, CustomerImportColumns.SALES_CONTACT_NAME, 100, false));
        sourceMappers.put(CustomerImportColumns.SALES_CONTACT_EMAIL, new StringSourceMapper(adapter, CustomerImportColumns.SALES_CONTACT_EMAIL, 100, false));
        sourceMappers.put(CustomerImportColumns.SALES_CONTACT_PHONE, new StringSourceMapper(adapter, CustomerImportColumns.SALES_CONTACT_PHONE, 100, false));
        sourceMappers.put(CustomerImportColumns.PERMANENT_ACCOUNT_NOTES, new StringSourceMapper(adapter, CustomerImportColumns.PERMANENT_ACCOUNT_NOTES, 1000, false));
        return sourceMappers;
    }
}
