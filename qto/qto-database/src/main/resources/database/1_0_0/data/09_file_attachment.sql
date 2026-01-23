CREATE TABLE file_attachment_content
(
	file_attachment_content_id int AUTO_INCREMENT
		PRIMARY KEY,
	file_embed                 longblob      NULL,
	version                    int DEFAULT 1 NOT NULL
);


CREATE TABLE file_attachment
(
	file_attachment_id         int AUTO_INCREMENT
		PRIMARY KEY,
	file_embed                 longblob          NULL,
	file_name                  varchar(255)      NULL,
	mime_type                  varchar(255)      NULL,
	file_size                  int           NULL,
	upload_date                datetime          NULL,
	file_modified_date         datetime          NULL,
	uploaded_by_username       varchar(200)      NULL,
	description                varchar(500)      NULL,
	file_attachment_content_id int               NULL,
	vendor_id                  int               NULL,
	version                    int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_file_attachment_file_attachment_content
		FOREIGN KEY (file_attachment_content_id) REFERENCES file_attachment_content (file_attachment_content_id)
);


