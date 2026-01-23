CREATE TABLE lookup_value
(
	lookup_value_id     int AUTO_INCREMENT
		PRIMARY KEY,
	lookup_type_id      int           NOT NULL,
	lookup_display      varchar(100) NULL,
	lookup_value        varchar(100)  NOT NULL,
	sort_seq            int           NOT NULL,
	modifiable          bit DEFAULT 1 NOT NULL,
	lookup_value_active bit DEFAULT 1 NOT NULL,
	version             int DEFAULT 1 NOT NULL
);


CREATE TABLE lookup_type
(
	lookup_type_id     int AUTO_INCREMENT
		PRIMARY KEY,
	lookup_type_descr  varchar(50)   NOT NULL,
	lookup_type_code   varchar(50)   NOT NULL,
	lookup_type_active bit DEFAULT 1 NOT NULL,
	modifiable         bit DEFAULT 1 NOT NULL,
	sortStrategy       int DEFAULT 0 NULL,
	version            int DEFAULT 1 NOT NULL
);
