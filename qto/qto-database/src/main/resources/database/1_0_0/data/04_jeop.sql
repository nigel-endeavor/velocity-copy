CREATE TABLE jeop_instance
(
	jeop_instance_id    SERIAL
		PRIMARY KEY,
	jeop_description    varchar(250) NULL,
	jeop_level          varchar(50) NULL,
	start_date          timestamp      NOT NULL,
	end_date            timestamp NULL,
	note                varchar(500) NULL,
	assigned_subject_id int NULL,
	responsibility      varchar(100) NULL,
	originator          varchar(100) NULL,
	business_days_open  int DEFAULT 0 NULL,
	version             int DEFAULT 1 NOT NULL
);

CREATE TABLE jeop_instance_note
(
	note_id               int           NOT NULL,
	jeop_instance_id      int           NOT NULL,
	CONSTRAINT FK_jeop_instance_note_note
		FOREIGN KEY (note_id) REFERENCES note (note_id),
	CONSTRAINT FK_jeop_instance_note_jeop_instance
		FOREIGN KEY (jeop_instance_id) REFERENCES jeop_instance (jeop_instance_id)
);

