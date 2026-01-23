CREATE TABLE company
(
	company_id             int AUTO_INCREMENT
		PRIMARY KEY,
	company_name           varchar(100)  NOT NULL,
	company_type           varchar(100) NULL,
	company_active         bit DEFAULT 1 NOT NULL,
	company_uuid           varchar(100)  NOT NULL,
	billing_account_number varchar(100) NULL,
	address_id             int NULL,
	tenant_id              int           NOT NULL,
	version                int DEFAULT 1 NOT NULL,
		CONSTRAINT company_uuid_unique
			UNIQUE (company_uuid)
);

CREATE INDEX fk_company_tenant
	ON company (tenant_id);



