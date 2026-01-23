CREATE TABLE service_cyber360_mxdr
(
    cyber360_mxdr_service_id    int AUTO_INCREMENT
        PRIMARY KEY,
    service_id              int NOT NULL,
    number_of_endpoints_devices   varchar(100) NULL,


    CONSTRAINT FK_service_cyber360_mxdr_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);



