CREATE TABLE broadband_service
(
	broadband_service_id    int AUTO_INCREMENT
		PRIMARY KEY,
	service_id              int           NOT NULL,
	on_net                  varchar(3) NULL,
	modem_make              varchar(100) NULL,
	mac_address             varchar(100) NULL,
	customer_prem_equipment varchar(100) NULL,
	version                 int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_broadband_service_service
		FOREIGN KEY (service_id) REFERENCES service (service_id)
);
