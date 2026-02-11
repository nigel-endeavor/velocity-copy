CREATE TABLE milestone
(
	milestone_id   int AUTO_INCREMENT
		PRIMARY KEY,
	milestone_name varchar(100)                NOT NULL,
	milestone_code varchar(100) DEFAULT 'TODO' NOT NULL,
	version        int          DEFAULT 1      NOT NULL
);

CREATE INDEX idx_milestone_milestone_code
	ON milestone (milestone_code);



CREATE TABLE milestone_display_set
(
	milestone_display_set_id int AUTO_INCREMENT
		PRIMARY KEY,
	display_group            varchar(50)   NOT NULL,
	display_set_label        varchar(100) NULL,
	version                  int DEFAULT 1 NOT NULL
);


CREATE TABLE milestone_display_set_include
(
	milestone_display_set_include_id int AUTO_INCREMENT
		PRIMARY KEY,
	milestone_display_set_id         int           NOT NULL,
	milestone_id                     int           NOT NULL,
	milestone_active                 bit DEFAULT 1 NOT NULL,
	milestone_sequence               int DEFAULT 0 NOT NULL,
	milestone_required               bit DEFAULT 0 NOT NULL,
	adjustable                       bit DEFAULT 0 NOT NULL,
	workflow_driven                  bit DEFAULT 0 NOT NULL,
	has_time                         bit DEFAULT 0 NOT NULL,
	disallow_future                  bit DEFAULT 0 NOT NULL,
	version                          int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_milestone_display_set_include_milestone
		FOREIGN KEY (milestone_id) REFERENCES milestone (milestone_id),
	CONSTRAINT FK_milestone_display_set_include_milestone_display_set
		FOREIGN KEY (milestone_display_set_id) REFERENCES milestone_display_set (milestone_display_set_id)
);

CREATE TABLE milestone_instance
(
	milestone_instance_id    int AUTO_INCREMENT
		PRIMARY KEY,
	milestone_id             int           NOT NULL,
	milestone_date           datetime      NOT NULL,
	milestone_instance_count int DEFAULT 1 NOT NULL,
	milestone_param          varchar(100) NULL,
	historic                 bit DEFAULT 0 NOT NULL,
	version                  int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_milestone_instance_milestone
		FOREIGN KEY (milestone_id) REFERENCES milestone (milestone_id)
);

CREATE INDEX IX_milestone_instance_milestone
	ON milestone_instance (milestone_id);

CREATE INDEX idx_milestone_instance_historic
	ON milestone_instance (historic);

