CREATE TABLE cross_connect_service
(
    service_id          int PRIMARY KEY,
    cross_connect_id               varchar(100) NULL,
    cross_connect_room             varchar(100) NULL,
    cross_connect_rack             varchar(100) NULL,
    cross_connect_port             varchar(100) NULL,
    cross_connect_type             varchar(100) NULL,
    cross_connect_data_center_name varchar(100) NULL,
    CONSTRAINT FK_cross_connect_service_service
        FOREIGN KEY (service_id) REFERENCES service (service_id)
);
