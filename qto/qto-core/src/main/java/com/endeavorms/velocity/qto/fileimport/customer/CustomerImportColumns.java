package com.endeavorms.velocity.qto.fileimport.customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerImportColumns {

    static final String MASTER_CUSTOMER_NAME = "Master Customer Name";
    static final String CLIENT_ID = "Client ID";
    static final String BILLING_ADDRESS_1 = "Billing address 1";
    static final String BILLING_ADDRESS_2 = "Billing address 2";
    static final String BILLING_CITY = "Billing City";
    static final String BILLING_STATE = "Billing State";
    static final String BILLING_POSTAL_CODE = "Billing Postal code";
    static final String BILLING_COUNTRY = "Billing Country";
    static final String BILLING_CONTACT_NAME = "Billing Contact Name";
    static final String BILLING_CONTACT_EMAIL = "Billing Contact Email";
    static final String BILLING_CONTACT_PHONE = "Billing Contact Phone";
    static final String TECHNICAL_CONTACT_NAME = "Technical Contact Name";
    static final String TECHNICAL_CONTACT_EMAIL = "Technical Contact Email";
    static final String TECHNICAL_CONTACT_PHONE = "Technical Contact Phone";
    static final String SALES_CONTACT_NAME = "Sales Contact Name";
    static final String SALES_CONTACT_EMAIL = "Sales Contact Email";
    static final String SALES_CONTACT_PHONE = "Sales Contact Phone";
    static final String PERMANENT_ACCOUNT_NOTES = "Permanent Account Notes";

    protected static final ArrayList<String> COL_LIST = new ArrayList<>();

    static {
        COL_LIST.add(MASTER_CUSTOMER_NAME);
        COL_LIST.add(CLIENT_ID);
        COL_LIST.add(BILLING_ADDRESS_1);
        COL_LIST.add(BILLING_ADDRESS_2);
        COL_LIST.add(BILLING_CITY);
        COL_LIST.add(BILLING_STATE);
        COL_LIST.add(BILLING_POSTAL_CODE);
        COL_LIST.add(BILLING_COUNTRY);
        COL_LIST.add(BILLING_CONTACT_NAME);
        COL_LIST.add(BILLING_CONTACT_EMAIL);
        COL_LIST.add(BILLING_CONTACT_PHONE);
        COL_LIST.add(TECHNICAL_CONTACT_NAME);
        COL_LIST.add(TECHNICAL_CONTACT_EMAIL);
        COL_LIST.add(TECHNICAL_CONTACT_PHONE);
        COL_LIST.add(SALES_CONTACT_NAME);
        COL_LIST.add(SALES_CONTACT_EMAIL);
        COL_LIST.add(SALES_CONTACT_PHONE);
        COL_LIST.add(PERMANENT_ACCOUNT_NOTES);
    }

    protected CustomerImportColumns() { }

    public static List<String> getExpectedColumns() {
        return COL_LIST;
    }

}
