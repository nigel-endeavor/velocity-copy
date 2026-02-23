CREATE TABLE note
(
	note_id      SERIAL
		PRIMARY KEY,
	note         text      NOT NULL,
	category     varchar(100)  NOT NULL,
	created_date timestamp NULL,
	created_by   varchar(200)  NOT NULL,
	version      int DEFAULT 1 NOT NULL
);

