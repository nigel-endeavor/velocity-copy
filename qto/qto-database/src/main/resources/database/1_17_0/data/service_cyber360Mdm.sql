CREATE TABLE service_cyber360_mdm
(
    cyber360_mdm_service_id    SERIAL
        PRIMARY KEY,
    service_id              int NOT NULL,


    CONSTRAINT FK_service_cyber360_mdm_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);



