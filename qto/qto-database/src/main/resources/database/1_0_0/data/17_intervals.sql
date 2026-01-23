CREATE TABLE interval_type
(
	interval_type_id       int AUTO_INCREMENT
		PRIMARY KEY,
	interval_type_descr    varchar(100)  NOT NULL,
	interval_type_code     varchar(100)  NOT NULL,
	open_milestone_id      int           NOT NULL,
	open_milestone_min_max varchar(10)   NOT NULL,
	version                int DEFAULT 1 NOT NULL
);


CREATE TABLE interval_type_close
(
	interval_type_close_id  int AUTO_INCREMENT
		PRIMARY KEY,
	interval_type_id        int           NOT NULL,
	close_milestone_id      int           NOT NULL,
	close_milestone_min_max varchar(10)   NOT NULL,
	version                 int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_interval_type_interval_type_close
		FOREIGN KEY (interval_type_id) REFERENCES interval_type (interval_type_id)
);


CREATE TABLE interval_instance
(
	interval_instance_id        int AUTO_INCREMENT
		PRIMARY KEY,
	interval_type_id            int            NOT NULL,
	open_milestone_instance_id  int            NOT NULL,
	close_milestone_instance_id int            NULL,
	interval_time               decimal(12, 2) NULL,
	deduct_time                 decimal(12, 2) NULL,
	version                     int DEFAULT 1  NOT NULL,
	CONSTRAINT FK_interval_instance_close_milestone
		FOREIGN KEY (close_milestone_instance_id) REFERENCES milestone_instance (milestone_instance_id),
	CONSTRAINT FK_interval_instance_interval_type
		FOREIGN KEY (interval_type_id) REFERENCES interval_type (interval_type_id),
	CONSTRAINT FK_interval_instance_open_milestone
		FOREIGN KEY (open_milestone_instance_id) REFERENCES milestone_instance (milestone_instance_id)
);
