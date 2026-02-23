CREATE TABLE import_activity
(
	import_activity_id   SERIAL
		PRIMARY KEY,
	file_attachment_id   int           NULL,
	status               varchar(25)   NOT NULL,
	status_details       varchar(1000) NULL,
	num_successful       int           NULL,
	num_failed           int           NULL,
	uploaded_by_username varchar(200)  NULL,
	import_start_date    timestamp      NULL,
	import_end_date      timestamp      NULL,
	import_type          varchar(50)   NULL,
	tenant_id            int           NOT NULL,
	version              int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_import_activity_file_attachment
		FOREIGN KEY (file_attachment_id) REFERENCES file_attachment (file_attachment_id)
);
