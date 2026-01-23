CREATE TABLE orders
(
	order_id            int AUTO_INCREMENT
		PRIMARY KEY,
	company_id          int                      NOT NULL,
	client_order_id     varchar(100) NULL,
	quote_id            int NULL,
	provisioner         varchar(100) NULL,
	project_manager     varchar(100) NULL,
	activation_engineer varchar(100) NULL,
	order_mrc           decimal(19, 2) DEFAULT 0 NOT NULL,
	order_nrc           decimal(19, 2) DEFAULT 0 NOT NULL,
	order_icb           decimal(19, 2) DEFAULT 0 NOT NULL,
	order_osp           decimal(19, 2) DEFAULT 0 NOT NULL,
	order_status        varchar(100) NULL,
	tenant_id           int                      NOT NULL,
	version             int            DEFAULT 1 NOT NULL
);

CREATE TABLE order_note
(
	order_id int NOT NULL,
	note_id  int NOT NULL,
	CONSTRAINT FK_order_note_note
		FOREIGN KEY (note_id) REFERENCES note (note_id),
	CONSTRAINT FK_order_note_order
		FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

CREATE TABLE order_jeop_instance
(
	order_id         int NOT NULL,
	jeop_instance_id int NOT NULL,
	CONSTRAINT FK_order_jeop_instance_jeop_instance
		FOREIGN KEY (jeop_instance_id) REFERENCES jeop_instance (jeop_instance_id),
	CONSTRAINT FK_order_jeop_instance_order
		FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

CREATE TABLE order_contact
(
	order_id   int NOT NULL,
	contact_id int NOT NULL,
	CONSTRAINT FK_order_contact_contact
		FOREIGN KEY (contact_id) REFERENCES contact (contact_id),
	CONSTRAINT FK_order_contact_order
		FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

CREATE TABLE order_file_attachment
(
	order_id           int NOT NULL,
	file_attachment_id int NOT NULL,
	CONSTRAINT FK_order_file_attachment_file_attachment
		FOREIGN KEY (file_attachment_id) REFERENCES file_attachment (file_attachment_id),
	CONSTRAINT FK_order_file_attachment_order
		FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

CREATE TABLE order_milestone_instance
(
	order_id              int NOT NULL,
	milestone_instance_id int NOT NULL,
	CONSTRAINT FK_order_milestone_instance_milestone_instance
		FOREIGN KEY (milestone_instance_id) REFERENCES milestone_instance (milestone_instance_id),
	CONSTRAINT FK_order_milestone_instance_order
		FOREIGN KEY (order_id) REFERENCES orders (order_id)
);
