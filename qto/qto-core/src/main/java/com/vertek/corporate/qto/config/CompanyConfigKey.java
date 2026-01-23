package com.vertek.corporate.qto.config;

/**
 * This class enumerates the company configuration properties which are expected to exist in the database.
 * @author fcurran
 * @since 2.7.0
 */
public enum CompanyConfigKey {

    /** Days to delay creating the disconnect MACD.*/
    DISCONNECT_DELAY,

    /** Determines whether to enforce the client id uniqueness constraint.*/
    CLIENT_ID_UNIQUE_CONSTRAINT,

    /** If true, only allow one location per order. */
    LOCK_ONE_LOCATION_PER_ORDER,

    /** Default Level of Effort. */
    LOE_DEFAULT,

    /** New Quote URL. */
    NEW_QUOTE_URL,

    /** If true, the ServiceSnapshotJob will not create snapshots for this tenant. */
    NIGHTLY_SERVICE_SNAPSHOTS_DISABLED,

    /** If true, this tenant uses the brokerage tab in the ui. */
    SHOW_BROKERAGE_FIELDS,

    /** Default value for the internal only flag on a note. */
    SERVICE_NOTE_INTERNAL_ONLY_DEFAULT,

    /** Default value for the internal only flag on a dispute note. */
    DISPUTE_NOTE_INTERNAL_ONLY_DEFAULT,

    /** Subjects to alert on order creation. */
    ALERT_ON_ORDER_CREATE,

    /** When a new Service is created through the New Order form, add it to any open Locations */
    COMBINE_OPEN_ORDERS,

    /** Determines if i90 should create the Client Service ID. */
    AUTO_CREATE_CLIENT_SERVICE_ID,

    /**Determines if the UI shows the Ordering worklists */
    TELECOM_CLIENT,

    /**Determines if the UI shows the Cyber Security worklists */
    CYBER_SECURITY_CLIENT,

    /** Subjects to email on order creation. */
    EMAIL_ON_ORDER_CREATE,

    /** Determines if an email should be sent to the Provisioner upon order assignment. */
    EMAIL_PROVISIONER_ON_ORDER_ASSIGNMENT,

    /** Determines if an email should be sent to the PM upon order assignment. */
    EMAIL_PROJECT_MANAGER_ON_ORDER_ASSIGNMENT;

}
