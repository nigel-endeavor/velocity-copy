package com.endeavorms.velocity.qto.fileimport._4g5g;

import com.endeavorms.velocity.qto.fileimport.AbstractInventoryServiceImportColumns;

import java.util.ArrayList;

/**
 * @author rcasey
 * @since 9/13/2023
 */
final class GImportColumns extends AbstractInventoryServiceImportColumns {
    static final String TIME_ZONE = "Time Zone";
    static final String CLIENT_LOCATION_INFO = "Location Info";
    static final String CLIENT_LOCATION_TYPE = "Location Type";
    static final String CLIENT_SERVICE_ID = "Client Service ID";
    static final String QUOTE_SOLUTION_ID = "Quote Solution ID";
    static final String PROJECT_NAME = "Project Name";
    static final String SERVICE_SUB_STATUS = "Service Sub Status";
    static final String OWNER = "Owner";
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
    static final String DATE_ENTERED_IN_INVENTORY = "Date Entered in Inventory";
    static final String PROVIDER_ORDER = "Provider Order #";
    static final String BILL_CYCLE = "Bill Cycle";
    static final String ACCOUNT_NUMBER_BAN = "Account Number / BAN";
    static final String SUMMARY_BILL = "Summary Bill";
    static final String CIRCUIT_PRIORITY = "Circuit Priority";
    static final String UID = "UID";
    static final String ICCID = "ICCID";
    static final String IMEI = "IMEI";
    static final String MOBILE_DEVICE_NUMBER_MDN = "Mobile Device Number (MDN)";
    static final String APN = "APN";
    static final String RATE_PLAN = "Rate Plan";
    static final String DEVICE_SERIAL_NUMBER = "Device Serial Number";
    static final String DEVICE_MAC_ADDRESS = "Device MAC Address";
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
        COL_LIST.add(SERVICE_SUB_STATUS);
        COL_LIST.add(OWNER);
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
        COL_LIST.add(ORIGINAL_MRC);
        COL_LIST.add(DATE_ENTERED_IN_INVENTORY);
        COL_LIST.add(PROVIDER_ORDER);
        COL_LIST.add(BILL_CYCLE);
        COL_LIST.add(ACCOUNT_NUMBER_BAN);
        COL_LIST.add(SUMMARY_BILL);
        COL_LIST.add(CIRCUIT_PRIORITY);
        COL_LIST.add(UID);
        COL_LIST.add(ICCID);
        COL_LIST.add(IMEI);
        COL_LIST.add(MOBILE_DEVICE_NUMBER_MDN);
        COL_LIST.add(APN);
        COL_LIST.add(RATE_PLAN);
        COL_LIST.add(DEVICE_SERIAL_NUMBER);
        COL_LIST.add(DEVICE_MAC_ADDRESS);
        COL_LIST.add(NOTES_TO_IMPORT);
    }

    private GImportColumns() { }

    public static ArrayList<String> getExpectedColumns() {
        return COL_LIST;
    }

}
