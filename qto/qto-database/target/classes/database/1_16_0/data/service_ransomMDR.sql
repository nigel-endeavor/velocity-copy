CREATE TABLE service_ransommdr
(
    ransommdr_service_id    int AUTO_INCREMENT
        PRIMARY KEY,
    service_id              int NOT NULL,
    number_of_endpoints     int NULL,
    agent_deployment   varchar(100) NULL,

    CONSTRAINT FK_service_ransommdr_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);