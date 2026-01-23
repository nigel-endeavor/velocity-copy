CREATE TABLE company_config_property
(
	company_config_property_id int(10) AUTO_INCREMENT
		PRIMARY KEY,
	company_id                 int(10)           NOT NULL,
	config_property_key        varchar(200)      NOT NULL,
	config_property_value      varchar(5000)     NULL,
	version                    int(10) DEFAULT 1 NULL,
	tenant_id                  int               NOT NULL
);

CREATE INDEX fk_company_config_property_tenant
	ON company_config_property (tenant_id);


CREATE TABLE config_property
(
	config_property_id    int(10) AUTO_INCREMENT
		PRIMARY KEY,
	config_property_key   varchar(200)      NOT NULL,
	config_property_value varchar(1000)     NULL,
	version               int(10) DEFAULT 1 NULL
);
