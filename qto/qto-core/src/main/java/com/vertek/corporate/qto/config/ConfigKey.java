package com.vertek.corporate.qto.config;

/**
 * This class enumerates the configuration properties which are expected to exist in the database.
 * @since 2.6.0
 */
public enum ConfigKey {

    /**
     * The hostname of the SMTP server which should be used to send email.
     */
    SMTP_HOSTNAME,

    /**
     * The port number which should be used to open connections to the SMTP server which is used to send email.
     */
    SMTP_PORT_NUMBER,

    /**
     * The SMTP password which should be used to authenticate against the SMTP server.
     */
    SMTP_PASSWORD,

    /**
     * The SMTP username which should be used to authenticate against the SMTP server.
     */
    SMTP_USERNAME,

    /**
     * This is a boolean flag which indicates if the system should attempt to establish a secure connection to
     * the SMTP server.
     */
    SMTP_SECURE_CONNECTION,

    /**
     * This is a boolean flag which indicates if the configured SMTP server requires authentication.
     */
    SMTP_AUTHENTICATION_REQUIRED,

    /** The URL of the CTS service. */
    CTS_URL,

    /** The client id for the CTS service. */
    CTS_CLIENT_ID,

    /** The client secret for the CTS service. */
    CTS_CLIENT_SECRET,

    /** The maximum size for file attachments. */
    MAX_FILE_UPLOAD_SIZE_BYTES,

    /** Excluded file extensions for file attachments. */
    EXCLUDED_FILE_EXTENSIONS,

    /** Azure dev ops PAT*/
    ADO_ACCESS_TOKEN,

    /** Order receipt view link. */
    ORDER_RECEIPT_VIEW_LINK;
}
