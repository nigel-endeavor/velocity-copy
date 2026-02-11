CREATE TABLE activation_attempt
(
	activation_attempt_id         int AUTO_INCREMENT
		PRIMARY KEY,
	scheduled_attempt_status      varchar(100) NULL,
	internal_tech_assigned        varchar(100) NULL,
	field_dispatch_vendor         varchar(100) NULL,
	field_tech_name               varchar(100) NULL,
	field_tech_phone              varchar(100) NULL,
	field_tech_check_in           datetime NULL,
	field_tech_check_out          datetime NULL,
	tested_download_speed         varchar(100) NULL,
	tested_upload_speed           varchar(100) NULL,
	latency                       varchar(100) NULL,
	backup_download_speed         varchar(100) NULL,
	backup_upload_speed           varchar(100) NULL,
	signal_rsrp                   varchar(100) NULL,
	sinr_rsrq                     varchar(100) NULL,
	location_downtown_for_cutover varchar(100) NULL,
	closeout_code                 varchar(100) NULL,
	network_complete_date         date NULL,
	voip_complete_date            date NULL,
	issue_notes                   longtext NULL,
	duplicate_to_related          bit DEFAULT 0 NOT NULL,
	warning_message               varchar(200) NULL,
	po_number                     varchar(100) NULL,
	version                       int     DEFAULT 1 NOT NULL
);





