CREATE TABLE service_riskmdr
(
    riskmdr_service_id    SERIAL
        PRIMARY KEY,
    service_id              int NOT NULL,
    number_of_assets  varchar(100) NULL,
    number_of_ips     varchar(100) NULL,
    ip_technical_notes   varchar(1000) NULL,

    CONSTRAINT FK_service_riskmdr_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);
