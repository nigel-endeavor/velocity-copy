package com.endeavorms.velocity.qto.fileimport;

/**
 * Abstract class for Inventory Service Import Columns.
 * This class contains the columns that are common to all Inventory Service Importers.
 * TODO: this is not true, it's just customer/order/location columns as this is incremental work.
 */
public abstract class AbstractInventoryServiceImportColumns {

    public static final String MASTER_CUSTOMER = "Master Customer";
    public static final String END_CUSTOMER = "End Customer";
    public static final String I90_PROJECT_MANAGER = "i90 Project Manager";
    public static final String CLIENT_LOCATION_ID = "Client Location ID";
    public static final String DATE_ENTERED_IN_INVENTORY = "Date Entered in Inventory";
    public static final String LOCATION_DESCRIPTION = "Location Description";
    public static final String SERVICE_DESCRIPTION = "Service Description";
    public static final String LCON_NAME = "LCON Name";
    public static final String LCON_EMAIL = "LCON Email";
    public static final String LCON_PHONE = "LCON Phone";
    public static final String CLIENT_SERVICE_ID = "Client Service ID";
    public static final String CONTRACT_INFO = "Contract Info";
    public static final String ORIGINAL_MRC = "Original MRC";
    public static final String ADDRESS_1 = "Address 1";
    public static final String ADDRESS_2 = "Address 2";
    public static final String CITY = "City";
    public static final String STATE_PROVINCE_REGION = "State/Province/Region";
    public static final String ZIP_POSTAL_CODE = "Zip/Postal code";
    public static final String COUNTRY = "Country";
    public static final String BILLING_ADDRESS_1 = "Billing Address 1";
    public static final String BILLING_ADDRESS_2 = "Billing Address 2";
    public static final String BILLING_CITY = "Billing City";
    public static final String BILLING_STATE_PROVINCE_REGION = "Billing State/Province/Region";
    public static final String BILLING_ZIP_POSTAL_CODE = "Billing Zip/Postal code";
    public static final String BILLING_COUNTRY = "Billing Country";
    public static final String BILLING_EMAIL = "Billing Email";

}
