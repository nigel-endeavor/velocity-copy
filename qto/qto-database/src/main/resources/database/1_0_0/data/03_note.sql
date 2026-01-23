CREATE TABLE note
(
	note_id      int auto_increment
		PRIMARY KEY,
	note         longtext      NOT NULL,
	category     varchar(100)  NOT NULL,
	created_date datetime NULL,
	created_by   varchar(200)  NOT NULL,
	version      int DEFAULT 1 NOT NULL
);

