package com.vertek.corporate.qto.fileimport.ucaas;

import com.vertek.corporate.qto.fileimport.AbstractInventoryServiceImportColumns;

import java.util.ArrayList;

/**
 * @author rcasey
 * @since 9/13/2023
 */
final class UcaasImportColumns extends AbstractInventoryServiceImportColumns {
    static final String TIME_ZONE = "Time Zone";
    static final String CLIENT_LOCATION_INFO = "Client Location Info";
    static final String CLIENT_LOCATION_TYPE = "Client Location Type";
    static final String QUOTE_SOLUTION_ID = "Quote Solution ID";
    static final String PROJECT_NAME = "Project Name";
    static final String SERVICE_SUB_STATUS = "Service Sub Status";
    static final String OWNER = "Owner";
    static final String PROVIDER = "Provider";
    static final String UNDERLYING_PROVIDER = "Underlying Provider";
    static final String SUB_PRODUCT_TYPE = "Sub Product Type";
    static final String CONTRACT_TERM = "Contract Term";
    static final String CONTRACT_SIGNED_DATE = "Contract Signed Date";
    static final String CONTRACT_END_DATE = "Contract End Date";
    static final String PO_NUMBER = "PO Number";
    static final String MRC = "MRC";
    static final String NRC = "NRC";
    static final String DATE_ENTERED_IN_INVENTORY = "Date Entered in Inventory";
    static final String PROVIDER_ORDER = "Provider Order #";
    static final String BILL_CYCLE = "Bill Cycle";
    static final String PROVIDER_SITE_ACCOUNT_NUMBER_BAN = "Provider Site Account Number/BAN";
    static final String PUBLISHED_TN = "Published TN";
    static final String TEMPORARY_TN = "Temporary TN";
    static final String NUMBER_OF_HANDSETS = "Number of Handsets";
    static final String NOTES_TO_IMPORT = "Notes to Import";

    private static final ArrayList<String> COL_LIST = new ArrayList<>();

    static {
        COL_LIST.add(MASTER_CUSTOMER);
        COL_LIST.add(END_CUSTOMER);
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
        COL_LIST.add(SERVICE_SUB_STATUS);
        COL_LIST.add(OWNER);
        COL_LIST.add(PROVIDER);
        COL_LIST.add(UNDERLYING_PROVIDER);
        COL_LIST.add(SUB_PRODUCT_TYPE);
        COL_LIST.add(CONTRACT_TERM);
        COL_LIST.add(CONTRACT_SIGNED_DATE);
        COL_LIST.add(CONTRACT_END_DATE);
        COL_LIST.add(PO_NUMBER);
        COL_LIST.add(MRC);
        COL_LIST.add(NRC);
        COL_LIST.add(ORIGINAL_MRC);
        COL_LIST.add(DATE_ENTERED_IN_INVENTORY);
        COL_LIST.add(PROVIDER_ORDER);
        COL_LIST.add(BILL_CYCLE);
        COL_LIST.add(PROVIDER_SITE_ACCOUNT_NUMBER_BAN);
        COL_LIST.add(PUBLISHED_TN);
        COL_LIST.add(TEMPORARY_TN);
        COL_LIST.add(NUMBER_OF_HANDSETS);
        COL_LIST.add(NOTES_TO_IMPORT);
    }

    private UcaasImportColumns() { }

    public static ArrayList<String> getExpectedColumns() {
        return COL_LIST;
    }
}
