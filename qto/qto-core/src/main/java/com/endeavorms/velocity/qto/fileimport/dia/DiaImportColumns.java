package com.endeavorms.velocity.qto.fileimport.dia;

import com.endeavorms.velocity.qto.fileimport.AbstractInventoryServiceImportColumns;

import java.util.ArrayList;

/**
 * @author rcasey
 * @since 9/13/2023
 */
final class DiaImportColumns extends AbstractInventoryServiceImportColumns {
    static final String TIME_ZONE = "Time Zone";
    static final String CLIENT_LOCATION_INFO = "Location Info";
    static final String CLIENT_LOCATION_TYPE = "Location Type";
    static final String QUOTE_SOLUTION_ID = "Quote Solution ID";
    static final String PROJECT_NAME = "Project Name";
    static final String SERVICE_BILLED_TO = "Service Billed To";
    static final String PROVIDER = "Provider";
    static final String UNDERLYING_PROVIDER = "Underlying Provider";
    static final String SUB_PRODUCT_TYPE = "Sub Product Type";
    static final String PO_NUMBER = "PO Number";
    static final String MANAGED_SERVICE = "Managed Service";
    static final String PRODUCTION_IMPACTING = "Production Impacting";
    static final String LAST_MILE_PROVIDER = "Last Mile Provider";
    static final String CONTRACT_TERM = "Contract Term";
    static final String CONTRACT_SIGNED_DATE = "Contract Signed Date";
    static final String CONTRACT_END_DATE = "Contract End Date";
    static final String MRC = "MRC";
    static final String NRC = "NRC";
    static final String ANNUAL_RECURRING_COST = "Annual Recurring Cost";
    static final String BURSTABLE_SPEED_COST = "Burstable Speed Cost";
    static final String CIRCUIT_DOWNLOAD_SPEED = "Circuit Download Speed";
    static final String CIRCUIT_UPLOAD_SPEED = "Circuit Upload Speed";
    static final String BURSTABLE_SPEED = "Burstable Speed";
    static final String PROVIDER_ORDER = "Provider Order #";
    static final String BILL_CYCLE = "Bill Cycle";
    static final String ACCOUNT_NUMBER_BAN = "Account Number / BAN";
    static final String SUMMARY_BILL = "Summary Bill";
    static final String ACCESS_CIRCUIT_ID = "Access Circuit ID";
    static final String PROVIDER_CIRCUIT_ID = "Provider Circuit ID";
    static final String CIRCUIT_INSTALLED_DATE = "Circuit Installed Date";
    static final String MEDIA_TYPE = "Media Type";
    static final String INTERFACE_CONNECTOR = "Interface Connector";
    static final String PROVIDER_ORDER_ACTIVATION_METHOD = "Provider Order Activation Method";
    static final String ACTIVATION_LINK = "Activation Link";
    static final String ACTIVATION_PHONE = "Activation Phone";
    static final String DMARC = "DMARC";
    static final String NPA_NXX = "NPA/NXX";
    static final String LOCATION_HOURS = "Location Hours";
    static final String PROVIDER_ROUTER = "Provider Router";
    static final String PROVIDER_ROUTER_MAC = "Provider Router MAC";
    static final String IP_FORMAT = "IP Format";
    static final String WAN_BLOCK = "WAN Block";
    static final String WAN_IPS = "WAN IP's";
    static final String WAN_GATEWAY = "WAN Gateway";
    static final String WAN_SUBNET = "WAN Subnet";
    static final String LAN_BLOCK = "LAN Block";
    static final String LAN_IPS = "LAN IP's";
    static final String LAN_GATEWAY = "LAN Gateway";
    static final String LAN_SUBNET = "LAN Subnet";
    static final String DNS_1 = "DNS 1";
    static final String DNS_2 = "DNS 2";
    static final String TSP_CODE = "TSP Code";
    static final String TSP_CODE_EXPIRATION_DATE = "TSP Code Expiration Date";
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
        COL_LIST.add(LAST_MILE_PROVIDER);
        COL_LIST.add(CONTRACT_TERM);
        COL_LIST.add(CONTRACT_SIGNED_DATE);
        COL_LIST.add(CONTRACT_END_DATE);
        COL_LIST.add(CONTRACT_INFO);
        COL_LIST.add(MRC);
        COL_LIST.add(NRC);
        COL_LIST.add(ANNUAL_RECURRING_COST);
        COL_LIST.add(ORIGINAL_MRC);
        COL_LIST.add(BURSTABLE_SPEED_COST);
        COL_LIST.add(CIRCUIT_DOWNLOAD_SPEED);
        COL_LIST.add(CIRCUIT_UPLOAD_SPEED);
        COL_LIST.add(BURSTABLE_SPEED);
        COL_LIST.add(DATE_ENTERED_IN_INVENTORY);
        COL_LIST.add(PROVIDER_ORDER);
        COL_LIST.add(BILL_CYCLE);
        COL_LIST.add(ACCOUNT_NUMBER_BAN);
        COL_LIST.add(SUMMARY_BILL);
        COL_LIST.add(ACCESS_CIRCUIT_ID);
        COL_LIST.add(PROVIDER_CIRCUIT_ID);
        COL_LIST.add(CIRCUIT_INSTALLED_DATE);
        COL_LIST.add(MEDIA_TYPE);
        COL_LIST.add(INTERFACE_CONNECTOR);
        COL_LIST.add(PROVIDER_ORDER_ACTIVATION_METHOD);
        COL_LIST.add(ACTIVATION_LINK);
        COL_LIST.add(ACTIVATION_PHONE);
        COL_LIST.add(DMARC);
        COL_LIST.add(NPA_NXX);
        COL_LIST.add(LOCATION_HOURS);
        COL_LIST.add(PROVIDER_ROUTER);
        COL_LIST.add(PROVIDER_ROUTER_MAC);
        COL_LIST.add(IP_FORMAT);
        COL_LIST.add(WAN_BLOCK);
        COL_LIST.add(WAN_IPS);
        COL_LIST.add(WAN_GATEWAY);
        COL_LIST.add(WAN_SUBNET);
        COL_LIST.add(LAN_BLOCK);
        COL_LIST.add(LAN_IPS);
        COL_LIST.add(LAN_GATEWAY);
        COL_LIST.add(LAN_SUBNET);
        COL_LIST.add(DNS_1);
        COL_LIST.add(DNS_2);
        COL_LIST.add(TSP_CODE);
        COL_LIST.add(TSP_CODE_EXPIRATION_DATE);
        COL_LIST.add(NOTES_TO_IMPORT);
        COL_LIST.add(BILLING_ADDRESS_1);
        COL_LIST.add(BILLING_ADDRESS_2);
        COL_LIST.add(BILLING_CITY);
        COL_LIST.add(BILLING_STATE_PROVINCE_REGION);
        COL_LIST.add(BILLING_ZIP_POSTAL_CODE);
        COL_LIST.add(BILLING_COUNTRY);
        COL_LIST.add(BILLING_EMAIL);
    }

    private DiaImportColumns() { }

    public static ArrayList<String> getExpectedColumns() {
        return COL_LIST;
    }
}
