CREATE TABLE dispute
(
    dispute_id   int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    open_date         date  NOT NULL,
    dispute_status    varchar(100)  NOT NULL,
    dispute_type      varchar(100)  NOT NULL,
    carrier_site_account_num      varchar(500) NULL,
    invoice_num      varchar(500)   NULL,
    amount_disputed_mrc       decimal(19, 2)    DEFAULT 0,
    amount_disputed_nrc       decimal(19, 2)    DEFAULT 0,
    vendor_tracking_num      varchar(500)   NULL,
    dispute_note    longtext    NULL,
    dispute_follow_up_date      date    NULL,
    credit_recognized boolean NULL,
    billing_review_complete_date date NULL,
    dispute_closed_date  date NULL,
    version int DEFAULT 1 NOT NULL,
    tenant_id int NOT NULL,
    service_id int NOT NULL,
    CONSTRAINT FK_dispute_service
        FOREIGN KEY (service_id)
            REFERENCES service (service_id)
);