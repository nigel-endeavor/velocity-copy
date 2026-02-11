CREATE TABLE service_cloudservicesiam
(
    cloudservicesiam_service_id    int AUTO_INCREMENT
        PRIMARY KEY,
    service_id              int NOT NULL,
    microsoft_licensing  varchar(100) NULL,
    number_of_users         int NULL,


    CONSTRAINT FK_service_cloudservicesiam_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);



