CREATE TABLE contact
(
	contact_id     int AUTO_INCREMENT
		PRIMARY KEY,
	company_id     int NULL,
	first_name     varchar(100)  NOT NULL,
	last_name      varchar(100) NULL,
	contact_active bit DEFAULT 1 NOT NULL,
	notes          varchar(1000) NULL,
	contact_type   varchar(50) NULL,
	address_id     int NULL,
	version        int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_contact_company
		FOREIGN KEY (company_id) REFERENCES company (company_id),
	CONSTRAINT fk_contact_address
		FOREIGN KEY (address_id) REFERENCES address (address_id)
);

CREATE TABLE contact_info
(
	contact_info_id   int AUTO_INCREMENT
		PRIMARY KEY,
	contact_id        int           NOT NULL,
	contact_method    varchar(100)  NOT NULL,
	contact_data      varchar(200)  NOT NULL,
	primary_by_method bit DEFAULT 0 NOT NULL,
	preferred_method  bit DEFAULT 0 NOT NULL,
	version           int DEFAULT 1 NOT NULL,
	CONSTRAINT FK_contact_info_contact
		FOREIGN KEY (contact_id) REFERENCES contact (contact_id)
);


