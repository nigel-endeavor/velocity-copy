CREATE TABLE file_attachment_content
(
	file_attachment_content_id SERIAL
		PRIMARY KEY,
	file_embed                 bytea      NULL,
	version                    int DEFAULT 1 NOT NULL
);


CREATE TABLE file_attachment
(
	file_attachment_id         SERIAL
		PRIMARY KEY,
	file_embed                 bytea          NULL,
	file_name                  varchar(255)      NULL,
	mime_type                  varchar(255)      NULL,
	file_size                  int           NULL,
	upload_date                timestamp          NULL,
	file_modified_date         timestamp          NULL,
	uploaded_by_username       varchar(200)      NULL,
	description                varchar(500)      NULL,
	file_attachment_content_id int               NULL,
	vendor_id                  int               NULL,
	version                    int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_file_attachment_file_attachment_content
		FOREIGN KEY (file_attachment_content_id) REFERENCES file_attachment_content (file_attachment_content_id)
);


