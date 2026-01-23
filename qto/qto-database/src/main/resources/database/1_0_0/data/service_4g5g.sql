CREATE TABLE 4g5g_service
(
    service_id              int          NOT NULL,
    billing_account_number  varchar(100) NULL,
    uid                     varchar(100) NULL,
    iccid                   varchar(100) NULL,
    imei                    varchar(100) NULL,
    mdn                     varchar(100) NULL,
    apn                     varchar(100) NULL,
    rate_plan               varchar(100) NULL,
    rsrp                    decimal(19, 2) DEFAULT 0 NULL,
    rsrq                    decimal(19, 2) DEFAULT 0 NULL,
    sinr                    decimal(19, 2) DEFAULT 0 NULL,
    rssi                    decimal(19, 2) DEFAULT 0 NULL,
    replace_4g_5g           varchar(100) NULL,
    mac_address             varchar(100) NULL,
    serial_number           varchar(100) NULL,
    CONSTRAINT FK_4g5g_service_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);
