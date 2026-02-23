CREATE TABLE address
(
	address_id     SERIAL
		PRIMARY KEY,
	address_1      varchar(100) NULL,
	address_2      varchar(100) NULL,
	city           varchar(100) NULL,
	state_province varchar(100) NULL,
	postal_code    varchar(20) NULL,
	country        varchar(100) NULL,
	latitude       decimal(8, 6) NULL,
	longitude      decimal(9, 6) NULL,
	geocode_status varchar(200) NULL,
	geocode_source varchar(100) NULL,
	accuracy       varchar(50) NULL,
	version        int DEFAULT 1 NOT NULL
);


