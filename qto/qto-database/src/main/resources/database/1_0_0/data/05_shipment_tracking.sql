CREATE TABLE shipment_tracking
(
	shipment_tracking_id       int AUTO_INCREMENT
		PRIMARY KEY,
	tracking_number            varchar(100)  NULL,
	courier                    varchar(100)  NULL,
	delivery_status            varchar(100)  NULL,
	delivery_status_datetime   datetime      NULL,
	courier_delivery_status    varchar(100)  NULL,
	scheduled_delivery_date    datetime      NULL,
	actual_delivery_date       datetime      NULL,
	delivery_location          varchar(100)  NULL,
	destination_full_address   varchar(100)  NULL,
	destination_street_address varchar(100)  NULL,
	destination_city           varchar(100)  NULL,
	destination_state          varchar(100)  NULL,
	destination_zip_code       varchar(100)  NULL,
	delivery_signature         varchar(100)  NULL,
	package_weight             varchar(100)  NULL,
	delivery_notes             varchar(100)  NULL,
	version                    int DEFAULT 1 NOT NULL
);

