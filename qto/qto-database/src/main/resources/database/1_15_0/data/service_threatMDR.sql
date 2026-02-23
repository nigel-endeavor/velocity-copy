CREATE TABLE service_threatmdr
(
    threatmdr_service_id    SERIAL
        PRIMARY KEY,
    service_id              int NOT NULL,
    sensors                 int NULL,
    hot_storage_retention   varchar(100) NULL,
    usm_anywhere_control_node varchar(100) NULL,

    CONSTRAINT FK_service_threatmdr_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);
