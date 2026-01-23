CREATE TABLE dia_service
(
	dia_service_id            int AUTO_INCREMENT
		PRIMARY KEY,
	service_id                int           NOT NULL,
	on_net                    varchar(100) NULL,
	building_status           varchar(100) NULL,
	carrier_activation_method varchar(100) NULL,
	interface_connector       varchar(100) NULL,
	npa_nxx                   varchar(100) NULL,
	version                   int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_dia_service_service
		FOREIGN KEY (service_id) REFERENCES service (service_id)
);
