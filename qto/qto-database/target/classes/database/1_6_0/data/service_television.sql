CREATE TABLE television_service
(
    television_service_id    int AUTO_INCREMENT
		PRIMARY KEY,
	service_id              int NOT NULL,
	plan                    varchar(100) NULL,
    dvr_included            bit default b'0' null,
    receiver                varchar(100) NULL,
    receiver_mac            varchar(100) NULL,
    dvr                     varchar(100) NULL,
    dvr_mac                 varchar(100) NULL,
	CONSTRAINT FK_television_service_service
		FOREIGN KEY (service_id) REFERENCES service (service_id)
);
