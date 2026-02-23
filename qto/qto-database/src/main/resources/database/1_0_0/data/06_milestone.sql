CREATE TABLE milestone
(
	milestone_id   SERIAL
		PRIMARY KEY,
	milestone_name varchar(100)                NOT NULL,
	milestone_code varchar(100) DEFAULT 'TODO' NOT NULL,
	version        int          DEFAULT 1      NOT NULL
);

CREATE INDEX idx_milestone_milestone_code
	ON milestone (milestone_code);



CREATE TABLE milestone_display_set
(
	milestone_display_set_id SERIAL
		PRIMARY KEY,
	display_group            varchar(50)   NOT NULL,
	display_set_label        varchar(100) NULL,
	version                  int DEFAULT 1 NOT NULL
);


CREATE TABLE milestone_display_set_include
(
	milestone_display_set_include_id SERIAL
		PRIMARY KEY,
	milestone_display_set_id         int           NOT NULL,
	milestone_id                     int           NOT NULL,
	milestone_active                 boolean DEFAULT true NOT NULL,
	milestone_sequence               int DEFAULT 0 NOT NULL,
	milestone_required               boolean DEFAULT false NOT NULL,
	adjustable                       boolean DEFAULT false NOT NULL,
	workflow_driven                  boolean DEFAULT false NOT NULL,
	has_time                         boolean DEFAULT false NOT NULL,
	disallow_future                  boolean DEFAULT false NOT NULL,
	version                          int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_milestone_display_set_include_milestone
		FOREIGN KEY (milestone_id) REFERENCES milestone (milestone_id),
	CONSTRAINT FK_milestone_display_set_include_milestone_display_set
		FOREIGN KEY (milestone_display_set_id) REFERENCES milestone_display_set (milestone_display_set_id)
);

CREATE TABLE milestone_instance
(
	milestone_instance_id    SERIAL
		PRIMARY KEY,
	milestone_id             int           NOT NULL,
	milestone_date           timestamp      NOT NULL,
	milestone_instance_count int DEFAULT 1 NOT NULL,
	milestone_param          varchar(100) NULL,
	historic                 boolean DEFAULT false NOT NULL,
	version                  int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_milestone_instance_milestone
		FOREIGN KEY (milestone_id) REFERENCES milestone (milestone_id)
);

CREATE INDEX IX_milestone_instance_milestone
	ON milestone_instance (milestone_id);

CREATE INDEX idx_milestone_instance_historic
	ON milestone_instance (historic);

