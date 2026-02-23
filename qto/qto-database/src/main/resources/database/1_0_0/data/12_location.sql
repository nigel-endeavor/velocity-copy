CREATE TABLE location
(
	location_id        SERIAL
		PRIMARY KEY,
	order_id           int                      NOT NULL,
	address_id         int,
	client_location_id varchar(100)             NOT NULL,
	quote_location_id  int NULL,
	location_name      varchar(250) NULL,
	location_status    varchar(100) NULL,
	building_type      varchar(100) NULL,
	lcon_name          varchar(100) NULL,
	lcon_email         varchar(100) NULL,
	lcon_phone         VARCHAR(100) NULL,
	location_mrc       decimal(19, 2) DEFAULT 0 NOT NULL,
	location_nrc       decimal(19, 2) DEFAULT 0 NOT NULL,
	location_icb       decimal(19, 2) DEFAULT 0 NOT NULL,
	location_osp       decimal(19, 2) DEFAULT 0 NOT NULL,
	tenant_id          int                      NOT NULL,
	version            int            DEFAULT 1 NOT NULL
);


CREATE TABLE location_note
(
	location_id int NOT NULL,
	note_id     int NOT NULL,
	CONSTRAINT FK_location_note_note
		FOREIGN KEY (note_id) REFERENCES note (note_id),
	CONSTRAINT FK_location_note_location
		FOREIGN KEY (location_id) REFERENCES location (location_id)
);

CREATE TABLE location_jeop_instance
(
	location_id      int NOT NULL,
	jeop_instance_id int NOT NULL,
	CONSTRAINT FK_location_jeop_instance_jeop_instance
		FOREIGN KEY (jeop_instance_id) REFERENCES jeop_instance (jeop_instance_id),
	CONSTRAINT FK_location_jeop_instance_location
		FOREIGN KEY (location_id) REFERENCES location (location_id)
);

CREATE TABLE location_contact
(
	location_id int NOT NULL,
	contact_id  int NOT NULL,
	CONSTRAINT FK_location_contact_contact
		FOREIGN KEY (contact_id) REFERENCES contact (contact_id),
	CONSTRAINT FK_location_contact_location
		FOREIGN KEY (location_id) REFERENCES location (location_id)
);

CREATE TABLE location_file_attachment
(
	location_id        int NOT NULL,
	file_attachment_id int NOT NULL,
	CONSTRAINT FK_location_file_attachment_file_attachment
		FOREIGN KEY (file_attachment_id) REFERENCES file_attachment (file_attachment_id),
	CONSTRAINT FK_location_file_attachment_location
		FOREIGN KEY (location_id) REFERENCES location (location_id)
);

CREATE TABLE location_milestone_instance
(
	location_id           int NOT NULL,
	milestone_instance_id int NOT NULL,
	CONSTRAINT FK_location_milestone_instance_milestone_instance
		FOREIGN KEY (milestone_instance_id) REFERENCES milestone_instance (milestone_instance_id),
	CONSTRAINT FK_location_milestone_instance_location
		FOREIGN KEY (location_id) REFERENCES location (location_id)
);

CREATE TABLE location_shipment_tracking
(
	location_id          int NOT NULL,
	shipment_tracking_id int NOT NULL,
	CONSTRAINT FK_location_shipment_tracking_shipment_tracking
		FOREIGN KEY (shipment_tracking_id) REFERENCES shipment_tracking (shipment_tracking_id),
	CONSTRAINT FK_location_shipment_tracking_location
		FOREIGN KEY (location_id) REFERENCES location (location_id)
);


