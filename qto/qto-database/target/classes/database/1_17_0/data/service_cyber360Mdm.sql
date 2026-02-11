CREATE TABLE service_cyber360_mdm
(
    cyber360_mdm_service_id    int AUTO_INCREMENT
        PRIMARY KEY,
    service_id              int NOT NULL,


    CONSTRAINT FK_service_cyber360_mdm_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);



