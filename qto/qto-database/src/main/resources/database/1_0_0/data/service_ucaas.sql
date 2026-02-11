CREATE TABLE ucaas_service
(
    service_id              int          NOT NULL,
    published_tn            varchar(100) NULL,
    temporary_tn            varchar(100) NULL,
    number_of_handsets      int          NULL,
    CONSTRAINT FK_ucaas_service_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);
