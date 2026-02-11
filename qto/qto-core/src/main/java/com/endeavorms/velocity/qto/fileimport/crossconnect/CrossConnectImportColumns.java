package com.endeavorms.velocity.qto.fileimport.crossconnect;

import com.endeavorms.velocity.qto.fileimport.AbstractInventoryServiceImportColumns;

import java.util.ArrayList;

/**
 * @author fcurran
 * @since 1/16/2024
 */
final class CrossConnectImportColumns extends AbstractInventoryServiceImportColumns {
    static final String TIME_ZONE = "Time Zone";
    static final String CLIENT_LOCATION_INFO = "Client Location Info";
    static final String CLIENT_LOCATION_TYPE = "Client Location Type";
    static final String QUOTE_SOLUTION_ID = "Quote Solution ID";
    static final String PROJECT_NAME = "Project Name";
    static final String SERVICE_BILLED_TO = "Service Billed To";
    static final String PROVIDER = "Provider";
    static final String UNDERLYING_PROVIDER = "Underlying Provider";
    static final String SUB_PRODUCT_TYPE = "Sub Product Type";
    static final String PO_NUMBER = "PO Number";
    static final String MANAGED_SERVICE = "Managed Service";
    static final String PRODUCTION_IMPACTING = "Production Impacting";
    static final String CONTRACT_TERM = "Contract Term";
    static final String CONTRACT_SIGNED_DATE = "Contract Signed Date";
    static final String CONTRACT_END_DATE = "Contract End Date";
    static final String MRC = "MRC";
    static final String NRC = "NRC";
    static final String ANNUAL_RECURRING_COST = "Annual Recurring Cost";
    static final String PROVIDER_ORDER = "Provider Order #";
    static final String BILL_CYCLE = "Bill Cycle";
    static final String ACCOUNT_NUMBER_BAN = "Account Number / BAN";
    static final String SUMMARY_BILL = "Summary Bill";
    static final String CROSS_CONNECT_ID = "Cross Connect ID";
    static final String ROOM = "Room";
    static final String RACK = "Rack";
    static final String PORT = "Port";
    static final String XC_TYPE = "XC Type";
    static final String COLO_DC_NAME = "Colo/ Data Center Name";
    static final String CIRCUIT_INSTALLED_DATE = "Circuit Installed Date";
    static final String NOTES_TO_IMPORT = "Notes to Import";

    private static final ArrayList<String> COL_LIST = new ArrayList<>();

    static {
        COL_LIST.add(MASTER_CUSTOMER);
        COL_LIST.add(END_CUSTOMER);
        COL_LIST.add(I90_PROJECT_MANAGER);
        COL_LIST.add(CLIENT_LOCATION_ID);
        COL_LIST.add(CLIENT_SERVICE_ID);
        COL_LIST.add(ADDRESS_1);
        COL_LIST.add(ADDRESS_2);
        COL_LIST.add(CITY);
        COL_LIST.add(STATE_PROVINCE_REGION);
        COL_LIST.add(ZIP_POSTAL_CODE);
        COL_LIST.add(COUNTRY);
        COL_LIST.add(TIME_ZONE);
        COL_LIST.add(LOCATION_DESCRIPTION);
        COL_LIST.add(SERVICE_DESCRIPTION);
        COL_LIST.add(LCON_NAME);
        COL_LIST.add(LCON_EMAIL);
        COL_LIST.add(LCON_PHONE);
        COL_LIST.add(CLIENT_LOCATION_INFO);
        COL_LIST.add(CLIENT_LOCATION_TYPE);
        COL_LIST.add(QUOTE_SOLUTION_ID);
        COL_LIST.add(PROJECT_NAME);
        COL_LIST.add(SERVICE_BILLED_TO);
        COL_LIST.add(PROVIDER);
        COL_LIST.add(UNDERLYING_PROVIDER);
        COL_LIST.add(SUB_PRODUCT_TYPE);
        COL_LIST.add(PO_NUMBER);
        COL_LIST.add(MANAGED_SERVICE);
        COL_LIST.add(PRODUCTION_IMPACTING);
        COL_LIST.add(CONTRACT_TERM);
        COL_LIST.add(CONTRACT_SIGNED_DATE);
        COL_LIST.add(CONTRACT_END_DATE);
        COL_LIST.add(CONTRACT_INFO);
        COL_LIST.add(MRC);
        COL_LIST.add(NRC);
        COL_LIST.add(ANNUAL_RECURRING_COST);
        COL_LIST.add(ORIGINAL_MRC);
        COL_LIST.add(DATE_ENTERED_IN_INVENTORY);
        COL_LIST.add(PROVIDER_ORDER);
        COL_LIST.add(BILL_CYCLE);
        COL_LIST.add(ACCOUNT_NUMBER_BAN);
        COL_LIST.add(SUMMARY_BILL);
        COL_LIST.add(CROSS_CONNECT_ID);
        COL_LIST.add(ROOM);
        COL_LIST.add(RACK);
        COL_LIST.add(PORT);
        COL_LIST.add(XC_TYPE);
        COL_LIST.add(COLO_DC_NAME);
        COL_LIST.add(CIRCUIT_INSTALLED_DATE);
        COL_LIST.add(NOTES_TO_IMPORT);
        COL_LIST.add(BILLING_ADDRESS_1);
        COL_LIST.add(BILLING_ADDRESS_2);
        COL_LIST.add(BILLING_CITY);
        COL_LIST.add(BILLING_STATE_PROVINCE_REGION);
        COL_LIST.add(BILLING_ZIP_POSTAL_CODE);
        COL_LIST.add(BILLING_COUNTRY);
        COL_LIST.add(BILLING_EMAIL);
    }

    private CrossConnectImportColumns() { }

    public static ArrayList<String> getExpectedColumns() {
        return COL_LIST;
    }
}
