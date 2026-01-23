CREATE TABLE service_cyber360_ems
(
    cyber360_ems_service_id    int AUTO_INCREMENT
        PRIMARY KEY,
    service_id              int NOT NULL,


    CONSTRAINT FK_service_cyber360_ems_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);



