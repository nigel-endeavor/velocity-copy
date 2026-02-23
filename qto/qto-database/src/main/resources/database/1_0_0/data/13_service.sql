CREATE TABLE service
(
	service_id                   SERIAL
		PRIMARY KEY,
	location_id                  int                      NOT NULL,
	client_service_id            varchar(100)             NOT NULL,
	quote_solution_id            int NULL,
	service_status               varchar(100) NULL,
	service_sub_status           varchar(100) NULL,
	sub_product_type             varchar(100) NULL,
	order_type                   varchar(100) NULL,
	activation_link              varchar(100) NULL,
	activation_phone             varchar(100) NULL,
	follow_up_date               date NULL,
	contract_term                varchar(100) NULL,
	contract_signed_date         date NULL,
	po_number                    varchar(100) NULL,
	service_mrc                  decimal(19, 2) DEFAULT 0 NOT NULL,
	service_nrc                  decimal(19, 2) DEFAULT 0 NOT NULL,
	has_icb                      varchar(3) NULL,
	service_icb                  decimal(19, 2) DEFAULT 0 NOT NULL,
	has_osp                      varchar(3) NULL,
	service_osp                  decimal(19, 2) DEFAULT 0 NOT NULL,
	carrier                      varchar(100) NULL,
	carrier_site_account_num     varchar(100) NULL,
	carrier_order_num            varchar(100) NULL,
	new_circuit_id               varchar(100) NULL,
	inside_wiring_required       varchar(3) NULL,
	dmarc                        varchar(100) NULL,
	additional_ip_block_required varchar(3) NULL,
	additional_ip_block          varchar(100) NULL,
	wan_ips                      varchar(100) NULL,
	wan_gateway                  varchar(100) NULL,
	wan_subnet                   varchar(100) NULL,
	lan_ips                      varchar(100) NULL,
	lan_gateway                  varchar(100) NULL,
	lan_subnet                   varchar(100) NULL,
	dns1                         varchar(100) NULL,
	dns2                         varchar(100) NULL,
	osp_const_interval_est       varchar(100) NULL,
	speed                        varchar(100) NULL,
	download_speed               varchar(100) NULL,
	upload_speed                 varchar(100) NULL,
	media_type                   varchar(100) NULL,
	net_status                   varchar(100) NULL,
	location_hours               varchar(100) NULL,
	product_install_interval     int NULL,
	expedite_order               varchar(3) NULL,
	trunk_group                  varchar(100) NULL,
	connection_handoff_type      varchar(100) NULL,
	tie_down_info                varchar(100) NULL,
	tenant_id                    int                      NOT NULL,
	version                      int            DEFAULT 1 NOT NULL
);



CREATE TABLE service_note
(
	service_id int NOT NULL,
	note_id    int NOT NULL,
	CONSTRAINT FK_service_note_note
		FOREIGN KEY (note_id) REFERENCES note (note_id),
	CONSTRAINT FK_service_note_service
		FOREIGN KEY (service_id) REFERENCES service (service_id)
);

CREATE TABLE service_jeop_instance
(
	service_id       int NOT NULL,
	jeop_instance_id int NOT NULL,
	CONSTRAINT FK_service_jeop_instance_jeop_instance
		FOREIGN KEY (jeop_instance_id) REFERENCES jeop_instance (jeop_instance_id),
	CONSTRAINT FK_service_jeop_instance_service
		FOREIGN KEY (service_id) REFERENCES service (service_id)
);

CREATE TABLE service_file_attachment
(
	service_id         int NOT NULL,
	file_attachment_id int NOT NULL,
	CONSTRAINT FK_service_file_attachment_file_attachment
		FOREIGN KEY (file_attachment_id) REFERENCES file_attachment (file_attachment_id),
	CONSTRAINT FK_service_file_attachment_service
		FOREIGN KEY (service_id) REFERENCES service (service_id)
);

CREATE TABLE service_milestone_instance
(
	service_id            int NOT NULL,
	milestone_instance_id int NOT NULL,
	CONSTRAINT FK_service_milestone_instance_milestone_instance
		FOREIGN KEY (milestone_instance_id) REFERENCES milestone_instance (milestone_instance_id),
	CONSTRAINT FK_service_milestone_instance_service
		FOREIGN KEY (service_id) REFERENCES service (service_id)
);

CREATE TABLE service_shipment_tracking
(
	service_id           int NOT NULL,
	shipment_tracking_id int NOT NULL,
	CONSTRAINT FK_service_shipment_tracking_shipment_tracking
		FOREIGN KEY (shipment_tracking_id) REFERENCES shipment_tracking (shipment_tracking_id),
	CONSTRAINT FK_service_shipment_tracking_service
		FOREIGN KEY (service_id) REFERENCES service (service_id)
);
